package org.adbcj.mysql.pool;

import java.util.List;
import java.util.Map;

import org.adbcj.DbFuture;
import org.adbcj.PreparedStatement;
import org.adbcj.Result;
import org.adbcj.ResultSet;


public class WrappedMysqlPreparedStatement implements PreparedStatement{
    private final PreparedStatement targetStatment;
    private final WrappedMysqlConnection conn ;
    
    public WrappedMysqlPreparedStatement(PreparedStatement targetStatment, WrappedMysqlConnection conn){
        super();
        this.targetStatment = targetStatment;
        this.conn = conn;
    }
    public List<Object> getParameterKeys() {
        return targetStatment.getParameterKeys();
    }
    public String getNativeSQL() {
        return targetStatment.getNativeSQL();
    }
    public DbFuture<ResultSet> executeQuery(Object... params) {
        
        return new WrappedDBFuture<ResultSet>(targetStatment.executeQuery(params), conn);
    }
    public int hashCode() {
        return targetStatment.hashCode();
    }
    public boolean equals(Object obj) {
        return targetStatment.equals(obj);
    }
    public <T extends ResultSet> DbFuture<T> executeQuery(Map<Object, Object> params) {
        throw new IllegalArgumentException("not supported yet");
    }
    public DbFuture<Result> executeUpdate(Object... params) {
        return new WrappedDBFuture<Result>(targetStatment.executeUpdate(params), conn);
    }
    public <T extends ResultSet> DbFuture<T> executeUpdate(Map<Object, Object> params) {
        throw new IllegalArgumentException("not supported yet");
    }
    public String toString() {
        return targetStatment.toString();
    }
    
}
