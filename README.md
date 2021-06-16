  # Java程序连接Mysql报异常原因  
配置文件jdbc.properties中要指明characterEncoding，useSSL，和serverTimezone

# Mysql 8.0.15 root用户给普通用户授权访问权限时报错，
```MySQL
mysql> grant select,insert,update,delete on test.* to 'Tom'@'%';
```
ERROR 1410 (42000): You are not allowed to create a user with GRANT
#### 给root用户自身授权可以,
```MySQL
mysql> grant all privileges on *.* to 'root'@'%';
```
Query OK, 0 rows affected (0.01 sec)
####  原因是当前user中没有 'Tom'@'%'， 因为创建时指定的是'Tom'@'localhost'，因此下列代码允许的。
```MySQL
grant select,insert,update,delete on test.* to 'Tom'@'localhost';
```
# 数据库连接池
##  C3P0 
### 文档中的Index.html打不开  
在网上找了文档[C3P0-v0.9.5.5](https://www.mchange.com/projects/c3p0/)。
### 用配置文件c3p0-config.xml，获得连接出错
```
六月 16, 2021 2:55:29 下午 com.mchange.v2.log.MLog <clinit>
信息: MLog clients using java 1.4+ standard logging.
六月 16, 2021 2:55:29 下午 com.mchange.v2.c3p0.cfg.C3P0Config warnOnUnknownProperties
警告: Unknown c3p0-config property: jdbcurl
六月 16, 2021 2:55:29 下午 com.mchange.v2.c3p0.cfg.C3P0Config warnOnUnknownProperties
警告: Unknown c3p0-config property: driverclass
六月 16, 2021 2:55:30 下午 com.mchange.v2.c3p0.C3P0Registry banner
信息: Initializing c3p0-0.9.1.2 [built 21-May-2007 15:04:56; debug? true; trace: 10]
六月 16, 2021 2:55:30 下午 com.mchange.v2.c3p0.impl.AbstractPoolBackedDataSource getPoolManager
信息: Initializing c3p0 pool... com.mchange.v2.c3p0.ComboPooledDataSource [ acquireIncrement -> 5, acquireRetryAttempts -> 30, acquireRetryDelay -> 1000, autoCommitOnClose -> false, automaticTestTable -> null, breakAfterAcquireFailure -> false, checkoutTimeout -> 0, connectionCustomizerClassName -> null, connectionTesterClassName -> com.mchange.v2.c3p0.impl.DefaultConnectionTester, dataSourceName -> myC3p0, debugUnreturnedConnectionStackTraces -> false, description -> null, driverClass -> null, factoryClassLocation -> null, forceIgnoreUnresolvedTransactions -> false, identityToken -> 1hge138ai5ftajf1evevxg|3c679bde, idleConnectionTestPeriod -> 0, initialPoolSize -> 10, jdbcUrl -> null, maxAdministrativeTaskTime -> 0, maxConnectionAge -> 0, maxIdleTime -> 0, maxIdleTimeExcessConnections -> 0, maxPoolSize -> 100, maxStatements -> 50, maxStatementsPerConnection -> 5, minPoolSize -> 10, numHelperThreads -> 3, numThreadsAwaitingCheckoutDefaultUser -> 0, preferredTestQuery -> null, properties -> {user=******, password=******}, propertyCycle -> 0, testConnectionOnCheckin -> false, testConnectionOnCheckout -> false, unreturnedConnectionTimeout -> 0, usesTraditionalReflectiveProxies -> false ]
六月 16, 2021 2:55:50 下午 com.mchange.v2.async.ThreadPoolAsynchronousRunner$DeadlockDetector run
警告: com.mchange.v2.async.ThreadPoolAsynchronousRunner$DeadlockDetector@24a12ff -- APPARENT DEADLOCK!!! Creating emergency threads for unassigned pending tasks!
六月 16, 2021 2:55:50 下午 com.mchange.v2.async.ThreadPoolAsynchronousRunner$DeadlockDetector run
警告: com.mchange.v2.async.ThreadPoolAsynchronousRunner$DeadlockDetector@24a12ff -- APPARENT DEADLOCK!!! Complete Status:
Managed Threads: 3
Active Threads: 3
Active Tasks:
com.mchange.v2.resourcepool.BasicResourcePool$AcquireTask@4db0e6fd (com.mchange.v2.async.ThreadPoolAsynchronousRunner$PoolThread-#0)
com.mchange.v2.resourcepool.BasicResourcePool$AcquireTask@1f3fc85e (com.mchange.v2.async.ThreadPoolAsynchronousRunner$PoolThread-#1)
com.mchange.v2.resourcepool.BasicResourcePool$AcquireTask@7c37eca3 (com.mchange.v2.async.ThreadPoolAsynchronousRunner$PoolThread-#2)
Pending Tasks:
com.mchange.v2.resourcepool.BasicResourcePool$AcquireTask@67529cab
com.mchange.v2.resourcepool.BasicResourcePool$AcquireTask@2d9f19b2
com.mchange.v2.resourcepool.BasicResourcePool$AcquireTask@4c835967
com.mchange.v2.resourcepool.BasicResourcePool$AcquireTask@f12f6a0
com.mchange.v2.resourcepool.BasicResourcePool$AcquireTask@7c56942d
com.mchange.v2.resourcepool.BasicResourcePool$AcquireTask@29d1409c
com.mchange.v2.resourcepool.BasicResourcePool$AcquireTask@7fcf7ca0
Pool thread stack traces:
Thread[com.mchange.v2.async.ThreadPoolAsynchronousRunner$PoolThread-#0,5,main]
java.lang.Thread.sleep(Native Method)
com.mchange.v2.resourcepool.BasicResourcePool$AcquireTask.run(BasicResourcePool.java:1805)
com.mchange.v2.async.ThreadPoolAsynchronousRunner$PoolThread.run(ThreadPoolAsynchronousRunner.java:547)
Thread[com.mchange.v2.async.ThreadPoolAsynchronousRunner$PoolThread-#1,5,main]
java.lang.Thread.sleep(Native Method)
com.mchange.v2.resourcepool.BasicResourcePool$AcquireTask.run(BasicResourcePool.java:1805)
com.mchange.v2.async.ThreadPoolAsynchronousRunner$PoolThread.run(ThreadPoolAsynchronousRunner.java:547)
Thread[com.mchange.v2.async.ThreadPoolAsynchronousRunner$PoolThread-#2,5,main]
java.lang.Thread.sleep(Native Method)
com.mchange.v2.resourcepool.BasicResourcePool$AcquireTask.run(BasicResourcePool.java:1805)
com.mchange.v2.async.ThreadPoolAsynchronousRunner$PoolThread.run(ThreadPoolAsynchronousRunner.java:547)

Process finished with exit code 130 (interrupted by signal 2: SIGINT)
```
解决：
配置xml属性时，属性名大小写也必须一致，不能写成driverclass和jdbcurl
``` xml
      <property name="driverClass">com.mysql.cj.jdbc.Driver</property>
      <property name="jdbcUrl">jdbc:mysql://localhost:3306/test?useUnicode=true&amp;useSSL=false&amp;serverTimezone=Asia/Shanghai&amp;rewriteBatchedStatements=true</property>
```