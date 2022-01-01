package com.meteor.stream.create;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.concurrent.ListenableFutureTask;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.NoSuchElementException;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class StreamSubCreateTest {
    @Test
    void streamBuilderTest() {
        Stream.Builder<String> builder = Stream.<String>builder().add("A");
        builder.accept("B");
        builder.accept("C");
        {
            Stream<String> stream = builder.build();
            Assertions.assertEquals("ABC", stream.reduce((val1, val2) -> val1 + val2)
                    .orElseThrow(() -> new NoSuchElementException("No value present")));
        }
        {
            try {
                builder.add("A");
                Assertions.fail("build 이후 builder의 내부 cnt를 음수로 변경해서 add, build 등 모두 동작하지 않음");
                builder.accept("B");
                builder.accept("C");
                Stream<String> stream = builder.build();
                Assertions.assertEquals("ABC", stream.reduce((val1, val2) -> val1 + val2).get());
            } catch (IllegalStateException illegalStateException) {
                //ok
            }
        }
    }

    @Test
    void streamOf() {
        {
            String s = Stream.of("A", "B", "C").reduce((val1, val2) -> val1 + val2).orElse("B");
            Assertions.assertEquals("ABC", s);
        }
        {
            String b = Stream.<String>empty().reduce((s1, s2) -> (s1 + s2)).orElse("B");
            Assertions.assertEquals("B", b);
        }
        {
            String s1 = Stream.of("A", "B", "C").reduce((val1, val2) -> "")
                    .orElse("B");
            //orElse는 값이 없을때만, EmptyString도 값은 있는것
            Assertions.assertEquals("", s1);
        }
    }

    @Test
    void streamEmpty() {
        SettableListenableFuture future = new SettableListenableFuture<Boolean>();
        //empty 이후에는 transform은 동작하지 않음
        Stream.empty().forEach(s -> future.set(true));
        Assertions.assertFalse(future.isDone());
    }

    @Test
    void streamEmptyCase() {
        long count = Stream.of("a", "b", "c").flatMap((ch) -> {
            //Stream to EmptyStream
            return Stream.empty();
        }).count();
        Assertions.assertEquals(count, 0);
    }

    @Test
    void streamGenerate() {

        int limit = 15;
        Supplier<Integer> supplier = new Supplier<Integer>() {
            private int cnt = 0;

            @Override
            public Integer get() {
                if (cnt == limit) return null;
                return cnt++;
            }
        };

        //generate에 지정된 supplier는 null을 리턴하더라도 계속 반환
        //결국 limit으로 제어를 해야함
        Stream.generate(supplier)
                .limit(limit)
                .forEach(s -> System.out.println("s : " + s));
    }

    @Test
    void streamIterate() {
        //(final T seed, final UnaryOperator<T> f)
        //limit 없으면 null을 리턴하더라도 반복됨
        int limitCnt = 10;
        Stream.iterate(0, val -> {
            if (val == null || val == limitCnt) return null;
            return val + 1;
        })
                .limit(limitCnt)
                .forEach(s -> System.out.println("s : " + s));

        //JDK9, limit 없어도 정상 동작
        //(T seed, Predicate<? super T> hasNext, UnaryOperator<T> next)
//        Stream.iterate(0, (val) -> val != null && val != limitCnt, val -> val + 1)
//                .forEach(s -> System.out.println("s : " + s));
    }

    @Test
    void streamConcatTest() {
        Stream<Integer> first = Stream.of(1, 2, 3);
        Stream<Integer> second = Stream.of(4, 5, 6);
        Stream<Integer> third = Stream.of(7, 8, 9);
        Stream<Integer> concatStream = Stream.concat(Stream.concat(first, second), third);
        Assertions.assertEquals(9, concatStream.count());

        try {
            concatStream.forEach(s -> System.out.println("s : " + s));
            //java.lang.IllegalStateException: stream has already been operated upon or closed
            Assertions.fail("java.lang.IllegalStateException: stream has already been operated upon or closed");
        } catch (IllegalStateException illegalStateException) {
            //is ok
        }
    }
}