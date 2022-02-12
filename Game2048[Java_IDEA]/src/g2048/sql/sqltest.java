package g2048.sql;

import java.sql.*;

public class sqltest {
    public static void main(String[] args) throws Exception {
        Connection conn = null;
        try {
            // 加载驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");
            long start = System.currentTimeMillis();
            // 建立连接
            conn = DriverManager.getConnection("jdbc:mysql://your mysql address:port/game2048",
                    "game2048", "12233434");
            long end = System.currentTimeMillis();
            //System.out.println(conn);
            System.out.println("建立连接耗时： " + (end - start) + "ms 毫秒");
            // 创建Statement对象
            Statement stmt = conn.createStatement();
            // 执行SQL语句
            ResultSet rs = stmt.executeQuery("select * from t_test");
            System.out.println("查询");
            System.out.println("a\tb\tc\td\te");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3)
                        + "\t" + rs.getInt(4) + "\t" + rs.getInt(5));
            }
            System.out.println("插入");
            ResultSet rs1 = stmt.executeQuery("insert * from t_test");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}