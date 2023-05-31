package com.demo.services;

import java.util.Objects;
import java.util.stream.Stream;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.demo.model.Class;
import com.demo.model.Student;
import com.demo.model.Teacher;
import com.demo.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository; 

    public Page<Student> getStudents(String classe, String teacher, Pageable paging) {
        Specification<Student> specification = combineSpecifications(hasClassName(classe), hasTeacherName(teacher));
            Page<Student> page = specification == null ?
                                studentRepository.findAll(paging): 
                                studentRepository.findAll(specification, paging);
        return page.isEmpty() ? Page.empty(paging) : page;
    }

    private static Specification<Student> combineSpecifications(Specification<Student>... specs) {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = Stream.of(specs)
                .map(spec -> (spec != null) ? spec.toPredicate(root, query, criteriaBuilder) : null)
                .filter(Objects::nonNull)
                .toArray(Predicate[]::new);

        return (predicates.length > 0) ? criteriaBuilder.and(predicates) : null;
        };
    };
    

    private static Specification<Student> hasClassName(String className) {
        return (root, query, criteriaBuilder) -> {
            if(className == null)
                return null;
            Join<Student, Class> classeJoin = root.join("classe", JoinType.INNER);
            return criteriaBuilder.equal(classeJoin.get("name"), className);
        };
    }

    private static Specification<Student> hasTeacherName(String teacherName) {
        return (root, query, criteriaBuilder) -> {
            if(teacherName == null)
                return null;
            Predicate predicate = concatenateJoinedColumns(criteriaBuilder, root, teacherName);           
            return predicate;
        };
    }

    private static Predicate concatenateJoinedColumns(CriteriaBuilder cb, Root<Student> root, String concatValue) {
        Join<Student, Class> classJoin = root.join("classe", JoinType.INNER);
        Join<Class, Teacher> teacherJoin = classJoin.join("teacher", JoinType.INNER);
        Expression<String> column1 = teacherJoin.get("firstName");
        Expression<String> column2 = teacherJoin.get("lastName");
        Expression<String> concatenatedAttribute = cb.concat(cb.concat(column1, " "), column2);
        return cb.equal(concatenatedAttribute, concatValue);
    }
}