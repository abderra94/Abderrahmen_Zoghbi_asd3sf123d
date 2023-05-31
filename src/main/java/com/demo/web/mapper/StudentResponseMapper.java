package com.demo.web.mapper;


import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import com.demo.dto.StudentDTO;
import com.demo.dto.StudentResponseDTO;

@Mapper
public interface StudentResponseMapper {
    @Mapping(target="studentList", expression = "java(toResponseDTOWithEmptyCheck(pageStudent))")
    @Mapping(target="currentPage", source="number")
    @Mapping(target="totalItems", source="totalElements")
    StudentResponseDTO toResponseDTO(Page<StudentDTO> pageStudent);

    default List<StudentDTO> toResponseDTOWithEmptyCheck(Page<StudentDTO> pageStudent) {
        return pageStudent.hasContent() ? pageStudent.getContent() : new ArrayList<StudentDTO>();
    }
}