package org.adbcj.mysql.netty;

import junit.framework.Assert;

import org.adbcj.Connection;
import org.adbcj.ConnectionManager;
import org.adbcj.ConnectionManagerProvider;
import org.adbcj.DbFuture;
import org.adbcj.DbSessionFuture;
import org.adbcj.PreparedStatement;
import org.adbcj.Result;
import org.adbcj.ResultSet;
import org.adbcj.Row;
import org.adbcj.Value;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class SimpleTest {

    // public static ConnectionManager cm =
    // ConnectionManagerProvider.createConnectionManager("adbcj:mysqlnetty://10.232.31.25:3309/unitTest",
    // "test",
    // "test");

    public static ConnectionManager cm   = ConnectionManagerProvider.createConnectionManager("adbcj:pooledMysqlnetty://localhost/unit_test",
                                             "root",
                                             "");
    public static final String      DATE = "'1986-03-22 05:33:12'";

    public void prepare_notNull() throws Exception {
        Connection connection = cm.connect().get();
        DbSessionFuture<Result> result = connection.executeUpdate("delete from type_test");
        Assert.assertTrue(result.get().getAffectedRows() > -1);
        String sql = "insert into type_test (" + "pk,varcharr,charr,blobr,integerr,integerr_unsigned,tinyintr,tinyintr_unsigned,"
                     + "smallintr,smallintr_unsigned,mediumintr,mediumintr_unsigned,bitr,bigintr,bigintr_unsigned,floatr,doubler,"
                     + "decimalr,dater,timer,datetimer,timestampr,yearr) values (" + "0,'varch','char','lob',100,100,4,4,"
                     + "1,1,100,100,b'0',1000000,1000000,1.1,2.2,1000.1," + DATE + "," + DATE + "," + DATE + "," + DATE + "," + DATE
                     + ")";
        result = connection.executeUpdate(sql);
        Assert.assertTrue(result.get().getAffectedRows() > -1);
        connection.close(true);
    }
    
   
    @AfterMethod
    public void tear() throws Exception {
        Connection connection = cm.connect().get();
        DbSessionFuture<Result> result = connection.executeUpdate("delete from type_test");
        Assert.assertTrue(result.get().getAffectedRows() > -1);
        connection.close(true);
    }
    
    

    @Test
    public void testSimple() throws Exception {
        prepare_notNull();
        Connection connection = cm.connect().get();
        DbSessionFuture<PreparedStatement> ps = connection.prepareStatement("select pk,varcharr from type_test where pk = ?");
        DbFuture<ResultSet> result = ps.get().executeQuery(0);
        ResultSet r = result.get();
        Row row = r.iterator().next();
        Value[] values = row.getValues();
        // pk
        Assert.assertEquals(0, values[0].getValue());
        // varcharr varch
        Assert.assertEquals("varch", values[1].getValue());
    }
}
