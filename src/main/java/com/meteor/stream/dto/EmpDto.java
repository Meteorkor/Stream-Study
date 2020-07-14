package com.meteor.stream.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmpDto implements Comparable<EmpDto> {
    private final long empno;
    private String ename;
    private long salary;


    @Override
    public int compareTo(EmpDto o) {

        return (int) (this.getEmpno() - o.getEmpno());
    }
}