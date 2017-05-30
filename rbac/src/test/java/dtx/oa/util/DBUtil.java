/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author datouxi
 */
public class DBUtil {
    public static Connection getConnection() throws SQLException{
        Connection conn=null;
        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/testsshdb??useUnicode=true&characterEncoding=utf-8", "root", "449449");
        return conn;
    }
    
    public void closeConnection(Connection conn){
        if(conn==null)return;
        try {
            conn.close();
        } catch (SQLException ex) {
            LogUtil.getLogger().error(ex);
        }
    }
    
    public void close(Statement st){
        if(st==null)return;
        try {
            st.close();
        } catch (SQLException ex) {
            LogUtil.getLogger().error(ex);
        }
    }
    
    public void close(ResultSet rs){
        if(rs==null)return;
        try {
            rs.close();
        } catch (SQLException ex) {
            LogUtil.getLogger().error(ex);
        }
    }
}
