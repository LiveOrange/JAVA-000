### Week02 作业题目

#### 使用 GCLogAnalysis.java 演练串行/并行/CMS/G1

- 串行 GC  

  ```
  java -Xms256M -Xmx256m -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis 
  执行结束!共生成对象次数:3960
  java -Xms512M -Xmx512m -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis 
  执行结束!共生成对象次数:7659
  java -Xms1G -Xmx1G -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis 
  执行结束!共生成对象次数:10387
  java -Xms2G -Xmx2G -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis 
  执行结束!共生成对象次数:12729
  ```

   单线程进行垃圾收集且会触发(Stop-The-World)，不能充分利用多核CPU。

   年轻代使用复制算法，老年代使用标记整理算法。

   内存过小会导致GC次数增多且存在内存溢出。

- 并行 GC 

  ```
  java -Xms256M -Xmx256m -XX:+UseParallelGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis 
  执行结束!共生成对象次数:3173
  java -Xms512M -Xmx512m -XX:+UseParallelGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis 
  执行结束!共生成对象次数:7099
  java -Xms1G -Xmx1G -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis 
  执行结束!共生成对象次数:10387
  java -Xms2G -Xmx2G -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis 
  执行结束!共生成对象次数:12729
  ```

   年轻代和老年代的垃圾回收都会触发 (Stop-The-World)。

   年轻代使用复制算法，老年代使用标记整理算法。

   GC 时所有 CPU 内核都在并行清理垃圾，系统的吞吐量优先。

- CMS GC

  ```
  java -Xms256M -Xmx256m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
  执行结束!共生成对象次数:4090
  java -Xms512M -Xmx512m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
  执行结束!共生成对象次数:8528
  java -Xms1g -Xmx1g -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
  执行结束!共生成对象次数:11499
  java -Xms2g -Xmx2g -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
  执行结束!共生成对象次数:13015
  ```

   CMS是正对老年代进行垃圾收集，年轻代需要配合串行GC或ParNewGC使用。

   CMS关注尽可能缩短垃圾收集时用户线程的停顿时间，使用标记清除算法。

   无垃圾整理阶段使用空闲列表管理内存的回收空间。

- G1 GC  

  ```
  java -Xms256M -Xmx256m -XX：+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
  Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
  java -Xms512M -Xmx512m -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
  执行结束!共生成对象次数:8428
  java -Xms1g -Xmx1g -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
  执行结束!共生成对象次数:11808
  java -Xms2g -Xmx2g -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
  执行结束!共生成对象次数:14084
  ```

   GC停顿时间变成可预期和可配置的，既满足低延迟又兼顾吞吐量。

   堆空间划分为多个可以存放对象区域Region。

- GC日志 

  ```
  [GC (Allocation Failure) [DefNew: 69952K->8704K(78656K), 0.0107288 secs] 69952K->25835K(253440K), 0.0110548 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
  69952K/78656K=88%(GC前年轻代使用率)，8704K/78656K=10%(GC后年轻代使用率)，大约10ms。
  
  [Full GC (Allocation Failure) [Tenured: 174737K->174718K(174784K), 0.0313628 secs] 252832K->252813K(253440K), [Metaspace: 2667K->2667K(1056768K)], 0.0320858 secs] [Times: user=0.03 sys=0.00, real=0.03 secs]
  Full GC：整个堆空间的垃圾回收；Allocation Failure：触发GC原因。
  堆内存分配越小GC的频率越高，出现OOM的概率会提前，堆内存分配越多GC的频率就会降低，耗时可能会增加。
  ```



#### 使用压测工具，演练gateway-server-0.0.1-SNAPSHOT.jar 示例。

- 串行 GC

  ```
  java -jar -XX:+UseSerialGC -Xms512m -Xmx512m -XX:+PrintGCDetails  gateway-server-0.0.1-SNAPSHOT.jar
  Starting at 2020/10/27 22:59:47
  [Press C to stop the test]
  633117  (RPS: 9933.4)
  ---------------Finished!----------------
  Finished at 2020/10/27 23:00:51 (took 00:01:03.7695245)
  Status 200:    633122
  RPS: 10372.3 (requests/second)
  Max: 128ms
  Min: 0ms
  Avg: 0.1ms
  
  java -jar -XX:+UseSerialGC -Xms4G -Xmx4G -XX:+PrintGCDetails  gateway-server-0.0.1-SNAPSHOT.jar
  Starting at 2020/10/27 23:18:32
  [Press C to stop the test]
  647233  (RPS: 10157.1)
  ---------------Finished!----------------
  Finished at 2020/10/27 23:19:35 (took 00:01:03.7480785)
  Status 200:    647233
  RPS: 10603.3 (requests/second)
  Max: 132ms
  Min: 0ms
  Avg: 0.1ms
  ```

- 并行 GC  

  ```
  java -jar -XX:+UseParallelGC -Xms512m -Xmx512m -XX:+PrintGCDetails  gateway-server-0.0.1-SNAPSHOT.jar
  Starting at 2020/10/27 23:10:11
  [Press C to stop the test]
  663323  (RPS: 10408)
  ---------------Finished!----------------
  Finished at 2020/10/27 23:11:15 (took 00:01:04.0265016)
  Status 200:    663324
  RPS: 10836.3 (requests/second)
  Max: 40ms
  Min: 0ms
  Avg: 0ms
  
  java -jar -XX:+UseParallelGC -Xms4g -Xmx4g -XX:+PrintGCDetails  gateway-server-0.0.1-SNAPSHOT.jar
  Starting at 2020/10/27 23:23:59
  [Press C to stop the test]
  644891  (RPS: 10126.4)
  ---------------Finished!----------------
  Finished at 2020/10/27 23:25:03 (took 00:01:04.1900415)
  Status 200:    644891
  
  RPS: 10500.1 (requests/second)
  Max: 134ms
  Min: 0ms
  Avg: 0.1ms
  ```

- CMS GC  

  ```
    java -jar -XX:+UseConcMarkSweepGC -Xms512m -Xmx512m -XX:+PrintGCDetails  gateway-server-0.0.1-SNAPSHOT.jar
    [Press C to stop the test]
    640696  (RPS: 10059.8)
    ---------------Finished!----------------
    Finished at 2020/10/27 23:14:18 (took 00:01:03.9111156)
    Status 200:    640716
    RPS: 10463 (requests/second)
    Max: 191ms
    Min: 0ms
    Avg: 0.1ms
    
    java -jar -XX:+UseConcMarkSweepGC -Xms4g -Xmx4g -XX:+PrintGCDetails  gateway-server-0.0.1-SNAPSHOT.jar
    Starting at 2020/10/27 23:27:20
    [Press C to stop the test]
    645542  (RPS: 10129.2)
    ---------------Finished!----------------
    Finished at 2020/10/27 23:28:24 (took 00:01:03.9464649)
    Status 200:    645542
    RPS: 10543 (requests/second)
    Max: 138ms
    Min: 0ms
    Avg: 0.1ms
  ```

- G1 GC 

  ```
    java -jar -XX:+UseG1GC -Xms512m -Xmx512m -XX:+PrintGCDetails  gateway-server-0.0.1-SNAPSHOT.jar
    Starting at 2020/10/27 23:30:42
    [Press C to stop the test]
    639147  (RPS: 10038.4)
    ---------------Finished!----------------
    Finished at 2020/10/27 23:31:46 (took 00:01:03.8971442)
    Status 200:    639155
    RPS: 10437 (requests/second)
    Max: 175ms
    Min: 0ms
    Avg: 0.1ms
    
    java -jar -XX:+UseG1GC -Xms4g -Xmx4g -XX:+PrintGCDetails  gateway-server-0.0.1-SNAPSHOT.jar
    Starting at 2020/10/27 23:32:55
    [Press C to stop the test]
    626260  (RPS: 9837.7)
    ---------------Finished!----------------
    Finished at 2020/10/27 23:33:58 (took 00:01:03.7802181)
    Status 200:    626260
    RPS: 10215.5 (requests/second)
    Max: 565ms
    Min: 0ms
    Avg: 0.1ms
  ```

  

