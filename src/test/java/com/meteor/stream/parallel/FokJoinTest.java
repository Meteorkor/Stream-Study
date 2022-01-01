package com.meteor.stream.parallel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FokJoinTest {
    @DisplayName("기본적으로 parallel()은 ForkJoinPool.commonPool()을 사용하기 때문에 장애를 유발할수도 있음")
    @Test
    void parallelStreamThreadCountTest() throws InterruptedException {
        final AtomicInteger threadCount = new AtomicInteger();
        final AtomicInteger keepThread = new AtomicInteger();
        final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap<String, Boolean>();

        IntStream.range(1, 20).parallel().forEach((n) -> {
            try {
                threadCount.incrementAndGet();
                concurrentHashMap.put(Thread.currentThread(), true);
                Thread.sleep(300);
            } catch (Exception ignore) {
            }
            keepThread.compareAndSet(0, threadCount.get());
        });

        Assertions.assertNotEquals(0, concurrentHashMap.size());
        Assertions.assertEquals(concurrentHashMap.size(), ForkJoinPool.commonPool().getPoolSize() + 1);
    }

    @DisplayName("ForkJoinPool을 생성하여 submit을 사용하는 경우 ForkJoinPool.commonPool()을 사용하지 않음")
    @Test
    void customParallelStreamThreadCountTest() throws InterruptedException, ExecutionException {
        final AtomicInteger threadCount = new AtomicInteger();
        final AtomicInteger keepThread = new AtomicInteger();
        final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap<String, Boolean>();
        final int FORK_JOIN_POOL_CNT = 5;

        ForkJoinPool forkJoinPool = new ForkJoinPool(FORK_JOIN_POOL_CNT);
        ForkJoinTask<?> submit = forkJoinPool.submit(() -> {
            IntStream.range(1, 20).parallel().forEach((n) -> {
                try {
                    threadCount.incrementAndGet();
                    concurrentHashMap.put(Thread.currentThread(), true);
                    Thread.sleep(300);
                } catch (Exception ignore) {
                }
                keepThread.compareAndSet(0, threadCount.get());
            });
        });

        submit.get();//complete wait
        Assertions.assertNotEquals(0, concurrentHashMap.size());
        Assertions.assertEquals(concurrentHashMap.size(), FORK_JOIN_POOL_CNT);
    }

    @DisplayName("ExecutorService을 생성하여 submit을 사용하는 경우에도 Stream내부는 ForkJoinPool.commonPool()을 사용")
    @Test
    void misParallelStreamThreadCountTest() throws InterruptedException, ExecutionException {
        final AtomicInteger threadCount = new AtomicInteger();
        final AtomicInteger keepThread = new AtomicInteger();
        final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap<String, Boolean>();
        final int POOL_CNT = 5;

        ExecutorService executorService = Executors.newFixedThreadPool(POOL_CNT);
        Future<?> submit = executorService.submit(() -> {
            IntStream.range(1, 20).parallel().forEach((n) -> {
                try {
                    threadCount.incrementAndGet();
                    concurrentHashMap.put(Thread.currentThread(), true);
                    Thread.sleep(300);
                } catch (Exception ignore) {
                }
                keepThread.compareAndSet(0, threadCount.get());
            });
        });

        submit.get();//complete wait
        Assertions.assertNotEquals(0, concurrentHashMap.size());
        //생성한 executorService 스레드풀에서 동작할것이라 생각할수 있지만, 실제로는 stream 내부 동작은 ForkJoinPool.commonPool()에서 동작하게 됨
        Assertions.assertNotEquals(concurrentHashMap.size(), POOL_CNT);
        Assertions.assertEquals(concurrentHashMap.size(), ForkJoinPool.commonPool().getPoolSize() + 1);
    }
}
