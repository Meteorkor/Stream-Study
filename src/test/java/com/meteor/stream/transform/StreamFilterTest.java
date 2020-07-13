package com.meteor.stream.transform;

import com.meteor.stream.dto.EmpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class StreamFilterTest {
    @Test
    void intFilter() {
        int lastNum = 100;
        int asInt = IntStream.rangeClosed(0, lastNum).filter(n -> n != 50).reduce(Integer::sum)
                .orElseThrow(() ->
                        new NoSuchElementException("No value present")
                );
        Assertions.assertEquals(lastNum * (lastNum + 1) / 2 - 50, asInt);
    }


    @Test
    void listFilter() {
        {
            List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").build()
                    , EmpDto.builder().empno(2).ename("lee").build()
                    , EmpDto.builder().empno(3).ename("park").build());

            long count = empList.stream().filter(s -> s.getEmpno() != 2).count();
            Assertions.assertEquals(empList.size() - 1, count);
        }
        {
            //filter는 match 되어야 통과
            List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").build()
                    , EmpDto.builder().empno(2).ename("lee").build()
                    , EmpDto.builder().empno(3).ename("park").build());

            long count = empList.stream().filter(s -> s.getEmpno() == 4).count();
            Assertions.assertEquals(0, count);
        }
    }
}