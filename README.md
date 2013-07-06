adbcj-pool
==========

apache common pool wrapper for adbcj 

用法:

首先下载 adbcj ，推荐使用我修改后的版本: https://github.com/rainerWJY/adbcj ，具体改动请见adbcj的milestone.

mvn install之

然后下载adbcj-pool. 他是使用 apache common pool对adbcj做了简单封装。

ConnectionManager cm = ConnectionManagerProvider.createConnectionManager("adbcj:pooledMysqlnetty://localhost/unit_test",
                                             "root",
                                             "");
                                             
这样就可以创建一个连接池

Connection connection = cm.connect().get();
        DbSessionFuture<Result> result = connection.executeUpdate("delete from type_test");
        Assert.assertTrue(result.get().getAffectedRows() > -1);
        connection.close(true);
        
这样就可以执行一个sql. 
