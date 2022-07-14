package com.meteor.stream.transform;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.meteor.stream.dto.EmpDto;

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

    @Test
    void distinctTest() {
        {
            final long count1 = IntStream.range(0, 10).map(n -> 1).distinct().count();
            Assertions.assertEquals(count1, 1);
        }

        {
            final long count1 = IntStream.range(0, 10).map(n -> 1).boxed().distinct().count();
            Assertions.assertEquals(count1, 1);
        }
        {
            List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").build()
                    , EmpDto.builder().empno(2).ename("lee").build()
                    , EmpDto.builder().empno(3).ename("park").build());

            long count = empList.stream().distinct().count();
            Assertions.assertEquals(count, 3);
        }
        {
            List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").build()
                    , EmpDto.builder().empno(1).ename("kim").build()
                    , EmpDto.builder().empno(1).ename("kim").build());

            //@Data, hashCode, equals
            long count = empList.stream().distinct().count();
            Assertions.assertEquals(count, 1);
        }
        {

            final EmpDto emp = EmpDto.builder().empno(1).ename("kim").build();
            List<EmpDto> empList = Arrays.asList(emp, emp, emp);

            long count = empList.stream().distinct().count();
            Assertions.assertEquals(count, 1);
        }
    }

}