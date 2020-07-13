package com.meteor.stream.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmpDto {
    private final long empno;
    private String ename;
    private long salary;
}