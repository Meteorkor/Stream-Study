package com.meteor.stream.terminal;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.meteor.stream.dto.EmpDto;

public class StreamReduceTest {
    @Test
    void streamLongReduceTest() {
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

    @Test
    void streamObjectReduceTest() {
        List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                , EmpDto.builder().empno(2).ename("lee").salary(300).build()
                , EmpDto.builder().empno(2).ename("lee").salary(300).build()
                , EmpDto.builder().empno(3).ename("park").salary(500).build()
                , EmpDto.builder().empno(4).ename("choi").salary(500).build()
        );
        {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            empList.stream().reduce((e1, e2) -> {
                atomicInteger.incrementAndGet();
                return EmpDto.builder().ename("sum").salary(e1.getSalary() + e2.getSalary()).build();
            });

            //reduce는 collect 같은 method call이 없더라도 정상적으로 수행됨
            Assertions.assertEquals(empList.size() - 1, atomicInteger.get());
        }
        {

            final Optional<EmpDto> sum = empList.stream().reduce((e1, e2) -> {
                return EmpDto.builder().ename("sum").salary(e1.getSalary() + e2.getSalary()).build();
            });

            Assertions.assertEquals(empList.stream().mapToLong(EmpDto::getSalary).sum(), sum.get().getSalary());
        }
        {
            final AtomicLong reduce = empList.stream()
                                             .reduce(new AtomicLong(), (longSum, emp) -> {
                                                 longSum.addAndGet(emp.getSalary());
                                                 return longSum;
                                             }, (longSum1, longSum2) -> {
                                                 //not called
                                                 longSum1.addAndGet(longSum2.get());
                                                 return longSum1;
                                             });
            Assertions.assertEquals(empList.stream().mapToLong(EmpDto::getSalary).sum(), reduce.get());
        }
        {
            final EmpDto reduce = empList.stream()
                                         .reduce(EmpDto.builder().build(), (e1, e2) -> {
                                             return EmpDto.builder().ename("sum").salary(
                                                     e1.getSalary() + e2.getSalary()).build();
                                         });
            Assertions.assertEquals("sum", reduce.getEname());
            Assertions.assertEquals(empList.stream().mapToLong(EmpDto::getSalary).sum(), reduce.getSalary());
        }
    }
}