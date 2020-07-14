package com.meteor.stream.transform;

import com.meteor.stream.dto.EmpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamSortingTest {

    @Test
    void objSortTest() {
        {
            List<EmpDto> empList = Arrays.asList(
                    EmpDto.builder().empno(2).ename("lee").salary(100).build()
                    , EmpDto.builder().empno(1).ename("kim").salary(100).build()
                    , EmpDto.builder().empno(3).ename("park").salary(500).build());
            {
                //자체 Comparable
                List<EmpDto> collect = empList.stream().sorted().collect(Collectors.toList());
                Assertions.assertEquals(1, collect.get(0).getEmpno());
                Assertions.assertEquals(2, collect.get(1).getEmpno());
                Assertions.assertEquals(3, collect.get(2).getEmpno());
            }
            {
                List<EmpDto> collect = empList.stream()
                        .sorted((o1, o2) -> (int) (o1.getEmpno() - o2.getEmpno())).collect(Collectors.toList());
                Assertions.assertEquals(1, collect.get(0).getEmpno());
                Assertions.assertEquals(2, collect.get(1).getEmpno());
                Assertions.assertEquals(3, collect.get(2).getEmpno());
            }
        }
    }
}