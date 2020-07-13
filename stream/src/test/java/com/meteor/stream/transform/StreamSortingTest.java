package com.meteor.stream.transform;

import com.meteor.stream.dto.EmpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class StreamSortingTest {

    @Test
    void objMapTest() {
        {
            List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                    , EmpDto.builder().empno(2).ename("lee").salary(100).build()
                    , EmpDto.builder().empno(3).ename("park").salary(500).build());
            empList.stream().sorted().forEach(s->{

            });

            long sum = empList.stream().map(EmpDto::getSalary)
                    .reduce(Long::sum).get();
            Assertions.assertEquals(100 + 100 + 500, sum);
        }
    }
}