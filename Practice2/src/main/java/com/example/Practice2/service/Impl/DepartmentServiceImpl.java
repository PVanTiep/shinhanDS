package com.example.Practice2.service.Impl;

import com.example.Practice2.dto.DepartmentDto;
import com.example.Practice2.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final Logger LOGGER =  LoggerFactory.getLogger(DepartmentServiceImpl.class);
    @Override
    public DepartmentDto getDepartmentDto(DepartmentDto departmentDto) {
        //DepartmentDto departmentDto1 = null;
        //departmentDto1.getDepartmenId();
        LOGGER.info(departmentDto.toString());
        return departmentDto;
    }
}
