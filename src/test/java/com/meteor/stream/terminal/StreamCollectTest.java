package com.meteor.stream.terminal;

import com.meteor.stream.dto.EmpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class StreamCollectTest {
    @Test
    void streamCollectListTest() {
        List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                , EmpDto.builder().empno(2).ename("lee").salary(300).build()
                , EmpDto.builder().empno(3).ename("park").salary(500).build()
                , EmpDto.builder().empno(4).ename("choi").salary(500).build()
        );
        {
            //List
            List<EmpDto> collect1 = empList.stream().filter(s -> s.getSalary() > 200).collect(Collectors.toList());
            Assertions.assertEquals(3, collect1.size());
            //jdk 10
//            List<EmpDto> collect2 = empList.stream().filter(s -> s.getSalary() > 200).collect(Collectors.toUnmodifiableList());
//            Assertions.assertEquals(3, collect1.size());
        }
    }

    @Test
    void streamCollectSetTest() {
        List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                , EmpDto.builder().empno(2).ename("lee").salary(300).build()
                , EmpDto.builder().empno(3).ename("park").salary(500).build()
                , EmpDto.builder().empno(4).ename("choi").salary(500).build()
        );
        //LongStream to Set
        HashSet<Long> collect = empList.stream().mapToLong(EmpDto::getSalary).collect(HashSet::new, HashSet::add,
                AbstractCollection::addAll
        );

        Assertions.assertEquals(3, collect.size());
        //collect to Set
        Assertions.assertEquals(3, empList.stream().map(EmpDto::getSalary).collect(Collectors.toSet()).size());
    }

    @Test
    void streamCollectMapTest() {
        List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                , EmpDto.builder().empno(2).ename("lee").salary(300).build()
                , EmpDto.builder().empno(3).ename("park").salary(500).build()
                , EmpDto.builder().empno(4).ename("choi").salary(500).build()
        );
        {
            //LongStream to Map
            HashMap<Long, Integer> collect = empList.stream().mapToLong(EmpDto::getSalary).collect(HashMap<Long, Integer>::new,
                    (map, value) -> {
                        Integer o = map.putIfAbsent(value, 1);
                        if (o != null) {
                            map.put(value, o + 1);
                        }
                    },
                    (set1, set2) -> set1.forEach((k, v) -> {
                        Integer o = set2.putIfAbsent(k, 1);
                        if (o != null) {
                            set2.put(k, v + 1);
                        }
                    })
            );
            Assertions.assertEquals(2, collect.get(500L));
            Assertions.assertEquals(3, collect.size());
        }
        {

            //LongStream to Map
            Map<Long, Integer> collect = empList.stream().mapToLong(EmpDto::getSalary).collect(ConcurrentHashMap<Long, Integer>::new,
                    (map, value) -> {
                        Integer o = map.putIfAbsent(value, 1);
                        if (o != null) {
                            map.put(value, o + 1);
                        }
                    },
                    (set1, set2) -> set1.forEach((k, v) -> {
                        Integer o = set2.putIfAbsent(k, 1);
                        if (o != null) {
                            set2.put(k, v + 1);
                        }
                    })
            );
            Assertions.assertEquals(2, collect.get(500L));
            Assertions.assertEquals(3, collect.size());
        }
        {
            //Stream to Map
            //keyMapper, valueMapper, mergeFunction
            Map<Long, Integer> collect = empList.stream().collect(Collectors.toMap(EmpDto::getSalary, s -> 1, Integer::sum));

            Assertions.assertEquals(2, collect.get(500L));
            Assertions.assertEquals(3, collect.size());
        }
    }


    @Test
    void streamCollectEtcTest() {
        List<EmpDto> empList = Arrays.asList(EmpDto.builder().empno(1).ename("kim").salary(100).build()
                , EmpDto.builder().empno(2).ename("lee").salary(300).build()
                , EmpDto.builder().empno(2).ename("lee").salary(300).build()
                , EmpDto.builder().empno(3).ename("park").salary(500).build()
                , EmpDto.builder().empno(4).ename("choi").salary(500).build()
        );
        {
            //Stream to Map
            //keyMapper, valueMapper, mergeFunction
            Map<Long, Integer> collect = empList.stream().collect(Collectors.toConcurrentMap(EmpDto::getSalary, s -> 1, Integer::sum));

            Assertions.assertEquals(2, collect.get(500L));
            Assertions.assertEquals(3, collect.size());
            Long collect1 = empList.stream().collect(Collectors.counting());
            Assertions.assertEquals(empList.size(), collect1);
        }

        {
            //groupingBy
            Map<Long, List<EmpDto>> mapList = empList.stream().collect(Collectors.groupingBy(EmpDto::getSalary));
            mapList.forEach((k, v) -> {
                System.out.println("k : " + k + ", v: " + v);
            });

            Assertions.assertEquals(2, mapList.get(500L).size());
            Assertions.assertEquals(2, mapList.get(300L).size());

            //group을 하고, value의 타입을 지정
            Map<Long, Set<EmpDto>> mapSet = empList.stream().collect(Collectors.groupingBy(EmpDto::getSalary, Collectors.toSet()));
            Assertions.assertEquals(1, mapSet.get(300L).size());
        }
    }
}