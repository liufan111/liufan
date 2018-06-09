package sss;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by zxw on 17-11-8.
 */
public class ConnectionManager {
    private static ConnectionManager instance;
    private static DataSource ds;
    static{
        try{
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mydb");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public synchronized static final ConnectionManager getInstance(){
        if(instance == null){
            try {
                instance = new ConnectionManager();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return instance;
    }

    @SuppressWarnings("finally")
    public synchronized Connection getConnection(){
        Connection conn = null;
        try {
            conn = ds.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return  conn;
        }
    }

    //关闭数据库链接
    public static void close (ResultSet rs, Statement stmt,Connection con){
        try{
            if(rs != null)
                rs.close();
            if(stmt != null)
                stmt.close();
            if(con != null)
                con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
