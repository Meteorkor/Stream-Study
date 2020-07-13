package com.meteor.stream.create;

import org.junit.jupiter.api.Test;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class StreamPrimitiveCreateTest {
    @Test
    void intStreamTest() {
        IntStream.range(0, 100).forEach(s -> System.out.println("s1 : " + s));
        //마지막 숫자 포함
        IntStream.rangeClosed(0, 100).forEach(s -> System.out.println("s2 : " + s));

        IntStream.rangeClosed(-100, 0).forEach(s -> System.out.println("s3 : " + s));

        //반대로는 동작 안함
        IntStream.rangeClosed(100, 0).forEach(s -> System.out.println("s4 : " + s));
        IntStream.range(100, 0).forEach(s -> System.out.println("s4 : " + s));
    }

    @Test
    void longStreamTest() {
        LongStream.range(0, 100).forEach(s -> System.out.println("s1 : " + s));

        LongStream.range(0, 100).forEach(s -> System.out.println("s2 : " + s));
    }

    @Test
    void doubleStreamTest() {
        //range가 없음, 정수면 1씩 늘리면 되지만..
        DoubleStream.of(1, 2, 3).forEach(s -> System.out.println("s1 : " + s));
    }

    @Test
    void stringStreamTest() {
        //String의 char는 intStream을 통해 처리
        String text = "abcd1234가나다";
        text.chars().forEach(c -> System.out.println("c : " + c + "] " + (char) c));
    }
}