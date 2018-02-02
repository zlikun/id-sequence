package com.zlikun.tools.flickr;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-02-02 17:34
 */
public class IdGeneratorTest {

    @Test
    public void get() {
        System.out.println(IdGenerator.get());
    }

    /**
     * 单线程测试
     */
    @Test
    public void benchmark_single() {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10_000; i++) {
            IdGenerator.get();
        }
        // 10,527 | 9,707 | 9,260 毫秒
        System.out.println(String.format("程序执行耗时：%d 毫秒!", System.currentTimeMillis() - time));
    }

    /**
     * 50个线程测试
     */
    @Test
    public void benchmark_concurrency() {
        ExecutorService exec = Executors.newFixedThreadPool(50);
        long time = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            exec.execute(() -> {
                IdGenerator.get();
            });
        }
        exec.shutdown();
        while (!exec.isTerminated());
        // 42,938 | 45,242 | 43,319 毫秒
        System.out.println(String.format("程序执行耗时：%d 毫秒!", System.currentTimeMillis() - time));
    }

    @Test
    public void benchmark_concurrency_200() {
        ExecutorService exec = Executors.newFixedThreadPool(200);
        long time = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            exec.execute(() -> {
                IdGenerator.get();
            });
        }
        exec.shutdown();
        while (!exec.isTerminated());
        // 33,584 | 33,798 | 35,258 毫秒
        System.out.println(String.format("程序执行耗时：%d 毫秒!", System.currentTimeMillis() - time));
    }

}