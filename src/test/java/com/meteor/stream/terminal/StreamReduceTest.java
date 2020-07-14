package com.meteor.stream.terminal;

import com.meteor.stream.dto.EmpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class StreamReduceTest {
    @Test
    void streamReduceTest() {
        List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                , EmpDto.builder().empno(2).ename("lee").salary(300).build()
                , EmpDto.builder().empno(2).ename("lee").salary(300).build()
                , EmpDto.builder().empno(3).ename("park").salary(500).build()
                , EmpDto.builder().empno(4).ename("choi").salary(500).build()
        );
        {
            long reduce = empList.stream().mapToLong(EmpDto::getSalary).reduce(Long::sum).getAsLong();
            Assertions.assertEquals(100 + 300 + 300 + 500 + 500, reduce);
        }
        {
            long initVal = 10;
            //초기값 지정(아마도 곱셈 같은곳에 많이 쓰일듯)
            long reduce = empList.stream().mapToLong(EmpDto::getSalary).reduce(initVal, Long::sum);
            Assertions.assertEquals(initVal + 100 + 300 + 300 + 500 + 500, reduce);
        }

    }
}