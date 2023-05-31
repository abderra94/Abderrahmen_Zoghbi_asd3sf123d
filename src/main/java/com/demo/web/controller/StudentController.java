package com.demo.web.controller;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.StudentDTO;
import com.demo.dto.StudentResponseDTO;
import com.demo.services.StudentService;
import com.demo.web.mapper.StudentMapper;
import com.demo.web.mapper.StudentResponseMapper;


@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService; 

    private StudentMapper studentMapper
      = Mappers.getMapper(StudentMapper.class);
    private StudentResponseMapper studentResponseMapper
      = Mappers.getMapper(StudentResponseMapper.class);

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDTO> getStudents(
        @RequestParam(name = "class", required = false) String className, 
        @RequestParam(name = "teacher", required = false) String teacher,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size)  {

        Pageable paging = PageRequest.of(page, size);
        try {
            Page<StudentDTO> pageStudents = studentService
                            .getStudents(className, teacher, paging)
                            .map(studentMapper::toDTO);
            StudentResponseDTO response = studentResponseMapper.toResponseDTO(pageStudents);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
          return ResponseEntity.internalServerError().build();
        }
    }

}
