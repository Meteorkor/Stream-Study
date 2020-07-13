package com.meteor.stream.transform;

import com.meteor.stream.dto.EmpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamMapTest {
    @Test
    void objMapTest() {
        {
            List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                    , EmpDto.builder().empno(2).ename("lee").salary(100).build()
                    , EmpDto.builder().empno(3).ename("park").salary(500).build());
            long sum = empList.stream().map(EmpDto::getSalary)
                    .reduce(Long::sum).get();
            Assertions.assertEquals(100 + 100 + 500, sum);
        }
    }

    @Test
    void objToLongTest() {
        {
            List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                    , EmpDto.builder().empno(2).ename("lee").salary(100).build()
                    , EmpDto.builder().empno(3).ename("park").salary(500).build());
            long sum = empList.stream().mapToLong(EmpDto::getSalary)
                    .sum();
            Assertions.assertEquals(100 + 100 + 500, sum);
        }
    }

    @Test
    void objFlatMapTest() {
        {
            List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                    , EmpDto.builder().empno(2).ename("lee").salary(100).build()
                    , EmpDto.builder().empno(3).ename("park").salary(500).build());
            long sum = empList.stream()
                    .flatMap(s -> Stream.of(s.getSalary()))
                    .reduce(Long::sum).get();
            Assertions.assertEquals(100 + 100 + 500, sum);
        }
    }

    @Test
    void objFlatMapLongTest() {
        {
            List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                    , EmpDto.builder().empno(2).ename("lee").salary(100).build()
                    , EmpDto.builder().empno(3).ename("park").salary(500).build());
            long sum = empList.stream()
                    .flatMapToLong(s-> LongStream.of(s.getSalary())).sum();
            Assertions.assertEquals(100 + 100 + 500, sum);
        }
    }
}