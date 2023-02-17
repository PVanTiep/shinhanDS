package com.example.Practice2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
    private long departmenId;
    @NotEmpty(message = "DeptName cannot empty")
    @Size(min = 10,max = 50, message = "Length from 10 to 50 characters")
    private String deptName;
    @NotEmpty(message = "Description cannot empty")
    private String description;
    @Valid
    List<EmployeeDto> employeeDtoList;
}
