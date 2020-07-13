package com.meteor.stream.create;

import com.meteor.stream.dto.EmpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamCollectionCreateTest {
    @Test
    void stringListStreamTest() {
        List<String> stringList = Arrays.asList("kim", "lee", "park");
        stringList.forEach(s -> System.out.println("s : " + s));
    }

    @Test
    void objectListStreamTest() {
        List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").build()
                , EmpDto.builder().empno(2).ename("lee").build()
                , EmpDto.builder().empno(3).ename("park").build());

        empList.forEach(s -> System.out.println("s : " + s));
    }

    @Test
    void integerListStreamTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 3);
        integerList.forEach(s -> System.out.println("s : " + s));
    }
}