package com.meteor.stream.terminal;

import com.meteor.stream.dto.EmpDto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class StreamIterTest {
    @Test
    void streamForeach() {
        List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                , EmpDto.builder().empno(2).ename("lee").salary(300).build()
                , EmpDto.builder().empno(2).ename("lee").salary(300).build()
                , EmpDto.builder().empno(3).ename("park").salary(500).build()
                , EmpDto.builder().empno(4).ename("choi").salary(500).build()
        );

        empList.stream()
                .peek(s -> {
                    System.out.println("peek1");
                    s.setSalary(s.getSalary() * 2);
                })
                .peek(s -> {
                    System.out.println("peek2");
                    s.setSalary(s.getSalary() * 2);
                })
                .forEach(s -> System.out.println("s : " + s));
    }
}