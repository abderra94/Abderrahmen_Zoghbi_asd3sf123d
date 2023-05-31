package com.demo.dto;


import java.util.List;

import lombok.Data;

@Data
public class StudentResponseDTO {

   List<StudentDTO> studentList;
    int currentPage;
    int totalItems;
    int totalPages;
}