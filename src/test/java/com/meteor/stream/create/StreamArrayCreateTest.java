package com.meteor.stream.create;

import com.meteor.stream.dto.EmpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class StreamArrayCreateTest {

    @Test
    void stringArrayStreamTest() {
        String[] strings = {"kim", "lee", "park"};

        Arrays.stream(strings)
                .forEach(s -> System.out.println("s : " + s));

        Assertions.assertEquals(strings.length, Arrays.stream(strings).count());
    }

    @Test
    void objectArrayStreamTest() {
        EmpDto[] empDtos = {
                EmpDto.builder().empno(1).ename("kim").build()
                , EmpDto.builder().empno(2).ename("lee").build()
                , EmpDto.builder().empno(3).ename("park").build()
        };
        Arrays.stream(empDtos)
                .forEach(s -> System.out.println("s : " + s));
        Assertions.assertEquals(empDtos.length, Arrays.stream(empDtos).count());
    }

    @Test
    void intArrayStreamTest() {
        int[] ints = {1, 2, 3};
        //IntStream
        Arrays.stream(ints)
                .forEach(s -> System.out.println("s : " + s));
        Assertions.assertEquals(ints.length, Arrays.stream(ints).count());
    }
}