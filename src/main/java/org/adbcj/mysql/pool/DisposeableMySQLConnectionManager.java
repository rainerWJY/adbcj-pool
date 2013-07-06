package org.adbcj.mysql.pool;

import java.util.Properties;

import org.adbcj.mysql.netty.MysqlConnectionManager;

public class DisposeableMySQLConnectionManager extends MysqlConnectionManager {

    public DisposeableMySQLConnectionManager(String host, int port, String username, String password, String schema,
                                             Properties properties){
        super(host, port, username, password, schema, properties);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
