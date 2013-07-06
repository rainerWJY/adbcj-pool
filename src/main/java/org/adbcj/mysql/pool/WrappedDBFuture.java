package org.adbcj.mysql.pool;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.adbcj.DbException;
import org.adbcj.DbFuture;
import org.adbcj.DbListener;


public class WrappedDBFuture<T> implements DbFuture<T>{
    private final DbFuture<T> target;
    private final WrappedMysqlConnection targetConn;
    public WrappedDBFuture(DbFuture<T> target,WrappedMysqlConnection targetConn){
        super();
        this.target = target;
        this.targetConn = targetConn;
    }

    public boolean removeListener(DbListener<T> listener) {
        return target.removeListener(listener);
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return target.cancel(mayInterruptIfRunning);
    }

    public T get() throws DbException, InterruptedException {
        try {
            return target.get();
        } catch (DbException e) {
            invalidConnIfNeed(e);
            throw e;
        }
    }

    public T get(long timeout, TimeUnit unit) throws DbException, InterruptedException, TimeoutException {
        try {
            return target.get(timeout, unit);
        } catch (DbException e) {
            invalidConnIfNeed(e);
            throw e;
        }
    }

    protected void invalidConnIfNeed(DbException e) {
        Throwable throwable = e;
        for(int i = 0 ; i < 10 ; i ++)
        {//try 10 times to get Non DbException
            if(throwable instanceof DbException)
            {
                throwable = throwable.getCause();
            }
            else
            {
                break;
            }
        }
        if(throwable instanceof DbException)
        {
            throw new IllegalArgumentException("we have try to get real Exception ,"
                    + "more than 10 times,should not be here..");
        }
        if(throwable instanceof IOException)
        {//remove this Connection because It has broken.
            targetConn.invalidThisConn();
        }
    }

    public boolean isCancelled() {
        return target.isCancelled();
    }

    public T getUninterruptably() throws DbException {
        return target.getUninterruptably();
    }

    public boolean isDone() {
        return target.isDone();
    }

    @Override
    public DbFuture<T> addListener(DbListener<T> listener) {
        return target.addListener(listener);
    }
    
}
