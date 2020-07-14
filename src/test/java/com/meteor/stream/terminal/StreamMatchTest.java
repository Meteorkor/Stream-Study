package com.meteor.stream.terminal;

import com.meteor.stream.dto.EmpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class StreamMatchTest {
    @Test
    void matchTest() {
        List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                , EmpDto.builder().empno(2).ename("lee").salary(300).build()
                , EmpDto.builder().empno(2).ename("lee").salary(300).build()
                , EmpDto.builder().empno(3).ename("park").salary(500).build()
                , EmpDto.builder().empno(4).ename("choi").salary(500).build()
        );

        //전체 탐색을 하지 않고 찾는 즉시 멈춤
        boolean anyMatch = empList.stream()
//                .peek(s-> System.out.println("anyMatch : " + s))
                .anyMatch(s -> "lee".equals(s.getEname()));
        Assertions.assertTrue(anyMatch);

        //match 되지 않는 값 찾는 즉시 멈춤
        boolean allMatch = empList.stream()
//                .peek(s-> System.out.println("allMatch : " + s))
                .allMatch(s -> "lee".equals(s.getEname()));
        Assertions.assertFalse(allMatch);

        //매치 하는 값이 있는 즉시 멈춤
        boolean noneMatch = empList.stream()
                .peek(s -> System.out.println("noneMatch : " + s))
                .noneMatch(s -> "lee".equals(s.getEname()));
        Assertions.assertFalse(noneMatch);
    }
}