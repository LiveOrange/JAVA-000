#### 1、写代码实现Spring Bean的装配，方式越多越好（XML、Annotation都可以）。 

##### 	XML

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <context:component-scan base-package="io.spring01"/>
    <bean class="io.spring01.bean.Student" id="student01">
        <property value="1" name="id"/>
        <property value="KK01" name="name"/>
    </bean>

</beans>
```

##### 	Configuration

```
@Configuration
@ImportResource("classpath:applicationContext.xml")
public class StudentConfiguration {
    @Bean(name = "student02")
    public Student student02() {
        Student student = new Student();
        student.setId(2);
        student.setName("KK02");
        return student;
    }
}
```

##### 	Component

```
@Component(value = "student03")
public class Student implements Serializable {

    private int id;
    private String name;
    public void init() {
        System.out.println("hello...........");
    }

    public Student create() {
        return new Student(00, "KK101");
    }
}
```

##### 测试类

```
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StudentConfiguration.class)
public class SpringDemo01 {

    @Autowired
    private ApplicationContext context;

    @Autowired
    @Qualifier("student03")
    private Student student03;

    @Test
    public void test() {
        Student student = (Student) context.getBean("student01");
        System.out.println(student.toString());

        Student student02 = (Student) context.getBean("student02");
        System.out.println(student02.toString());

        System.out.println(student03.toString());
    }
}
```



#### 2、给前面课程提供的Student/Klass/School实现自动配置和Starter。 

##### 	spring.factories

```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
io.spring02.config.AutoConfiguration
```

##### 	Configuration

```
@Configuration
public class AutoConfiguration {
    @Bean
    public Student student02() {
        return new Student();
    }
}
```

##### 	测试类

```
@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
public class SpringStarter {
    @Autowired
    private Student student;

    @Test
    public void test() throws InterruptedException {
        student.init();
    }
}
```

#### 3、研究一下JDBC接口和数据库连接池，掌握它们的设计和用法。 

​	1）使用JDBC原生接口，实现数据库的增删改查操作。 
​	2）使用事务，PrepareStatement方式，批处理方式，改进上述操作。
​	3）配置Hikari连接池，改进上述操作。提交代码到Github。

```
public class SpringMySqlStarter {
    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_URL = "jdbc:mysql://192.168.249.71:3306/manager";
    private final static String USER_NAME = "root";
    private final static String PASSWORD = "000000";

    private static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection hikari() throws Exception {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setUsername(USER_NAME);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(8);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "300");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource.getConnection();
    }

    @Test
    public void insert() throws SQLException {
        String insert = "insert into t_oder(id,data) values(?,?)";
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(insert);
        pstmt.setInt(1, 100);
        pstmt.setString(2, "data1");
        pstmt.execute();
        pstmt.close();
        conn.close();
    }

    @Test
    public void delete() throws SQLException {
        String delete = "delete from t_oder where id=?";
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(delete);
        pstmt.setInt(1, 100);
        pstmt.execute();
        pstmt.close();
        conn.close();
    }

    @Test
    public void update() throws SQLException {
        Connection conn = getConnection();
        String sql = "update t_oder set data=? where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "data2");
        pstmt.setInt(2, 100);
        pstmt.execute();
        pstmt.close();
        conn.close();
    }

    @Test
    public void query() throws SQLException {
        Connection conn = getConnection();
        String sql = "select * from t_oder where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, 100);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            System.out.println("id:" + rs.getInt("id") + ",data:" + rs.getString("data"));
        }
        pstmt.close();
        conn.close();
    }

    @Test
    public void transactions() {
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("insert into t_oder(id,data) values(102,'data1')");
            pstmt.execute();
            pstmt = conn.prepareStatement("insert into t_oder(id,data) values(103,'data2')");
            pstmt.execute();
            conn.commit();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Test
    public void hikariQuery() throws Exception {
        Connection conn = hikari();
        String sql = "select * from t_oder";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            System.out.println("id:" + rs.getInt("id") + ",data:" + rs.getString("data"));
        }
        pstmt.close();
        conn.close();
    }
}
```

#### 4、基于AOP和自定义注解，实现@MyCache(60)对于指定方法返回值缓存60秒。

​	实现思路：

​		1）将请求入参作为key，返回值value，存储在ConcurrentHashMap。

​		2）将存储的key添加到延时队列，创建线程删除过期对象。

##### 		@MyCache

```
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyCache {
    int value() default 60;
}
```

##### 		Configuration

```
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = "io.spring02")
public class CacheConfiguration {
}
```

##### 		延时队列

```
@Data
public class DelayTask implements Delayed {
    private String key;
    private LocalDateTime taskTime;

    DelayTask(String key, LocalDateTime taskTime) {
        this.key = key;
        this.taskTime = taskTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(Duration.between(LocalDateTime.now(), taskTime).toMillis(), TimeUnit.MILLISECONDS);
    }
    @Override
    public int compareTo(Delayed d) {
        DelayTask delayTask = (DelayTask) d;
        long diff = Duration.between(delayTask.getTaskTime(), taskTime).toMillis();
        return diff > 0 ? 1 : diff == 0 ? 0 : -1;
    }
}

```

##### 	AOP实现缓存

```
@Aspect
@Component
public class MyAspect implements InitializingBean {
    private final static DelayQueue<DelayTask> queue = new DelayQueue<>();
    private final static ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();

    @Pointcut("@annotation(io.spring02.aop.MyCache)")
    private void pointCut() {
    }

    @Around("pointCut() && @annotation(myCache)")
    public Object begin(ProceedingJoinPoint joinPoint, MyCache myCache) throws Throwable {
        Object[] args = joinPoint.getArgs();
        StringBuilder key = new StringBuilder();
        for (Object object : args) {
            key.append(object);
        }
        Object value = cache.get(key.toString());
        if (value != null) {
            return value;
        }
        Object proceed = joinPoint.proceed();
        LocalDateTime dateTime = LocalDateTime.now().plusSeconds(myCache.value());
        cache.put(key.toString(), proceed);
        queue.add(new DelayTask(key.toString(), dateTime));
        return proceed;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(new ConsumeTask()).start();
    }
    
    static class ConsumeTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    DelayTask take = queue.take();
                    cache.remove(take.getKey());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```

##### 		Service

```
@Service
public class HelloService {
    @MyCache(2)
    public String queryByName(String name) {
        System.out.println("queryByName query" + name);
        return "query" + name;
    }
    
    @MyCache
    public String queryById(int id) {
        System.out.println("queryById query" + id);
        return "query" + id;
    }
}
```

##### 		测试类

```
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CacheConfiguration.class)
public class SpringMyCache {
    @Autowired
    private HelloService helloService;

    @Test
    public void test() throws InterruptedException {
        System.out.println(helloService.queryByName("xmw1"));
        System.out.println(helloService.queryByName("xmw1"));
        Thread.sleep(500);
        System.out.println(helloService.queryById(15));
        System.out.println(helloService.queryById(15));
        Thread.sleep(2000);
        System.out.println(helloService.queryByName("xmw1"));
    }
}
```

