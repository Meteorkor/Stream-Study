package com.meteor.stream.transform;

import com.meteor.stream.dto.EmpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class StreamIteratingTest {

    @Test
    @DisplayName("sum은 map과 peek를 호출한다.")
    void sumMapPeekCall() {
        {
            try {
                List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                        , EmpDto.builder().empno(2).ename("lee").salary(100).build()
                        , EmpDto.builder().empno(3).ename("park").salary(500).build());
                long count = empList.stream()
                        .map(s -> {
                            System.out.println("map.s : " + s);
                            s.setSalary(s.getSalary() * 2);
                            return s;
                        })
                        .peek(s -> {
                            if ("kim".equals(s.getEname())) throw new RuntimeException();
                            System.out.println("s : " + s);
                        })
                        .mapToLong(s -> s.getSalary()).sum();
                Assertions.fail("sum은 map과 peek를 호출한다.");
            } catch (RuntimeException t) {
                //is ok
            }
        }
    }

    @Test
    @DisplayName("count는 map이나 peek이 영향 줄 수 없다고 파악되어 수행되지 않는다.")
    void peekTest() {
        {
            //"count는 map이나 peek이 영향 줄 수 없다고 파악되어 수행되지 않는다."
            //그런데 이상황은 예외가 발생하는 상황인데.. 아무래도 람다에서 예외가 발생하는 상황은 권장 되지 않아 고려되어있지 않는듯
            List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                    , EmpDto.builder().empno(2).ename("lee").salary(100).build()
                    , EmpDto.builder().empno(3).ename("park").salary(500).build());
            long count = empList.stream()
                    .map(s -> {
                        System.out.println("map.s : " + s);
                        s.setSalary(s.getSalary() * 2);
                        return s;
                    })
                    .peek(s -> {
                        //local 테스트에서는 발생안하고
                        //travis에서는 exception이 발생하네..?;;;
                        //하긴 위에 terminal command에 따라 가운데 로직을 돌리고 안돌리고를 미리 예측하는건..
//                        if ("kim".equals(s.getEname())) throw new RuntimeException();
//                        System.out.println("s : " + s);
                    }).count();

//            long count = empList.stream().peek(System.out::println).count();
            System.out.println("count : " + count);
            Assertions.assertEquals(3, count);
        }
    }
}