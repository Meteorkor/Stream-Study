package com.meteor.stream.terminal;

import com.meteor.stream.dto.EmpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.LongSummaryStatistics;

public class StreamCalcTest {
    @Test
    void calcTestSum() {
        List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                , EmpDto.builder().empno(2).ename("lee").salary(300).build()
                , EmpDto.builder().empno(3).ename("park").salary(500).build());

        Assertions.assertEquals(100 + 300 + 500, empList.stream().mapToLong(EmpDto::getSalary).sum());
        Assertions.assertEquals(3, empList.stream().mapToLong(EmpDto::getSalary).count());
        Assertions.assertEquals(300, empList.stream().mapToLong(EmpDto::getSalary).average().getAsDouble());
        Assertions.assertEquals(100, empList.stream().mapToLong(EmpDto::getSalary).min().getAsLong());
        Assertions.assertEquals(500, empList.stream().mapToLong(EmpDto::getSalary).max().getAsLong());

        {
            //sum min max 등 다 필요하다면 statistics를 통해 처리
            LongSummaryStatistics longSummaryStatistics = empList.stream().mapToLong(EmpDto::getSalary).summaryStatistics();
            Assertions.assertEquals(100 + 300 + 500, longSummaryStatistics.getSum());
            Assertions.assertEquals(3, longSummaryStatistics.getCount());
            Assertions.assertEquals(300, longSummaryStatistics.getAverage());
            Assertions.assertEquals(100, longSummaryStatistics.getMin());
            Assertions.assertEquals(500, longSummaryStatistics.getMax());
        }
    }
}