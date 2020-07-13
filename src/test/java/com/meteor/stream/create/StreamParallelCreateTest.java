package com.meteor.stream.create;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class StreamParallelCreateTest {
    @Test
    void stringStreamParallelTest() {
        StringBuilder stb = new StringBuilder("line0");
//        for (int i = 1; i < 10; i++) {
        for (int i = 1; i < 100000; i++) {
            stb.append(System.lineSeparator());
            stb.append("line").append(i);
        }
        {
            CharArrayReader charArrayReader = new CharArrayReader(stb.toString().toCharArray());
            try (BufferedReader bufferedReader = new BufferedReader(charArrayReader)) {

                long cnt = bufferedReader.lines()
                        .parallel()
//                        .peek(s -> System.out.println("line : " + s))
                        .count();
                System.out.println("cnt : " + cnt);
                Assertions.assertEquals(100000, cnt);
                //100000
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void intPrimitiveStreamParallelTest() {
        //int라 합계가 초과하지만, int 갯수를 늘리기 위해 어쩔수 없음
        int endInt = 100000;
        int asInt = IntStream.rangeClosed(1, endInt)
                .parallel()
                .reduce((num1, num2) -> {
//                    System.out.println(String.format(Thread.currentThread() + "] num1 : %d, num2 : %d", num1, num2));
                    return num1 + num2;
                })
                .orElseThrow(()->new NoSuchElementException(""));

        Assertions.assertEquals((endInt) * (endInt + 1) / 2, asInt);
    }


    @Test
    void intBoxStreamParallelTest() {
        //int라 합계가 초과하지만, int 갯수를 늘리기 위해 어쩔수 없음
        int endInt = 100000;
        int asInt = IntStream.rangeClosed(1, endInt)
                .boxed().parallel()
                .reduce((num1, num2) -> {
//                    System.out.println(String.format(Thread.currentThread() + "] num1 : %d, num2 : %d", num1, num2));
                    return num1 + num2;
                })
                .orElseThrow(()->new NoSuchElementException(""));

        Assertions.assertEquals((endInt) * (endInt + 1) / 2, asInt);
    }
}