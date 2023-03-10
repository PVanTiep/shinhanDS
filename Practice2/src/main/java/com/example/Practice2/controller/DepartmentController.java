package com.example.Practice2.controller;

import com.example.Practice2.dto.DepartmentDto;
import com.example.Practice2.dto.EmployeeDto;
import com.example.Practice2.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class DepartmentController {
    @Autowired
    DepartmentService  departmentService;
    @PostMapping("/department")
    public ResponseEntity<DepartmentDto> addEmployee(@RequestBody @Valid DepartmentDto departmentDto){


        return ResponseEntity.ok(departmentService.getDepartmentDto(departmentDto));
    }
}
