package com.Week_01.java0.conc0303;

import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class Homework03 {
    private final static CountDownLatch countDownLatch = new CountDownLatch(5);

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        long start = System.currentTimeMillis();

        //方法1-JDK8
        Future<Integer> result1 = executorService.submit(Homework03::sum);
        System.out.println("异步计算结果为：" + result1.get());

        //方法2-JDK8
        Future<Integer> result2 = executorService.submit(() -> {
            return sum();
        });
        System.out.println("异步计算结果为：" + result2.get());

        //方法3-匿名内部类
        Future<Integer> result3 = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() {
                return sum();
            }
        });
        System.out.println("异步计算结果为：" + result3.get());

        //方法4-JDK8异步计算
        CompletableFuture<Integer> result4 = CompletableFuture.supplyAsync(Homework03::sum);
        System.out.println("异步计算结果为：" + result4.get());

        //方法5-JDK8指定线程池
        CompletableFuture<Integer> result5 = CompletableFuture.supplyAsync(Homework03::sum, executorService);
        System.out.println("异步计算结果为：" + result5.get());

        //方法6-FutureTask
        FutureTask<Integer> futureTask1 = new FutureTask<Integer>(Homework03::sum);
        new Thread(futureTask1).start();
        System.out.println("异步计算结果为：" + futureTask1.get());

        //方法7-线程池执行Task
        FutureTask<Integer> futureTask2 = new FutureTask<Integer>(Homework03::sum);
        executorService.execute(futureTask2);
        System.out.println("异步计算结果为：" + futureTask2.get());

        countDownLatch.await();
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
        executorService.shutdown();
    }

    private static int sum() {
        try {
            return fibo(36);
        } finally {
            countDownLatch.countDown();
        }
    }


    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }

}
