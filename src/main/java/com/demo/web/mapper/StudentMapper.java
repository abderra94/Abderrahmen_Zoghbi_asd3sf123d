package com.demo.web.mapper;


import org.mapstruct.Mapper;

import com.demo.dto.StudentDTO;
import com.demo.model.Student;

@Mapper
public interface StudentMapper {
    StudentDTO toDTO(Student student);
}