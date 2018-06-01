/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author j-knakagami2
 */
public class SqlMain {
    
    public static Connection makeConnection(String jdbcUrl) throws SQLException, ClassNotFoundException{
        Connection wcon = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        wcon = DriverManager.getConnection(jdbcUrl);
        return wcon;
    }
}
