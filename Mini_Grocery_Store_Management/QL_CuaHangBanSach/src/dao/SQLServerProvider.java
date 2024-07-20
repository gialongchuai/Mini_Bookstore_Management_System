package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerProvider {

    public static Connection getConnection() {
        Connection connection = null;
        String url = "jdbc:sqlserver://localhost:1433;databaseName=QL_CuaHangBanSach";
        String user = "sa";
        String password = "123";
        
        try {
            // Đăng ký trình điều khiển JDBC
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Mở kết nối
            connection = DriverManager.getConnection(url, user, password);
            //System.out.println("Đã kết nối đến cơ sở dữ liệu");
        } catch (ClassNotFoundException | SQLException e) {
            //System.out.println("Không thể kết nối đến cơ sở dữ liệu: " + e.getMessage());
        }
        
        return connection;
    }
}
