package com.meteor.stream.terminal;

import com.meteor.stream.dto.EmpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class StreamCollectTest {
    @Test
    void streamCollectTest() {
        List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                , EmpDto.builder().empno(2).ename("lee").salary(300).build()
                , EmpDto.builder().empno(3).ename("park").salary(500).build()
                , EmpDto.builder().empno(4).ename("choi").salary(500).build()
        );
        {
            List<EmpDto> collect1 = empList.stream().filter(s -> s.getSalary() > 200).collect(Collectors.toList());
            Assertions.assertEquals(3, collect1.size());

            HashSet<Long> collect = empList.stream().mapToLong(EmpDto::getSalary).collect(HashSet::new, (longs, value) -> {
                        longs.add(value);
                    },
                    (set1, set2) -> set1.addAll(set2)
            );

            Assertions.assertEquals(3, collect.size());
            Assertions.assertEquals(3, empList.stream().map(EmpDto::getSalary).collect(Collectors.toSet()).size());

            collect.forEach(s -> {
                System.out.println("s : " + s);
            });
        }
    }
}