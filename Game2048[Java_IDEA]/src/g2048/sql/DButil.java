package g2048.sql;

import java.sql.*;

public class DButil {
    public static Connection connection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://your mysql address:port/game2048",
                "game2048", "12233434");
        return connection;
    }

    public static void close(ResultSet resultSet, Statement statement, Connection connection) throws SQLException {
        if (resultSet != null)
            resultSet.close();
        if (statement != null)
            statement.close();
        if (connection != null)
            connection.close();
    }

    public static void find(Connection connection) throws SQLException {
        String sql = "select * from t_test";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.print(resultSet.getInt(1) + "  ");
            System.out.print(resultSet.getString(2) + "  ");
            System.out.println(resultSet.getString(3));
        }
        DButil.close(resultSet, statement, connection);
    }

    public static void delete(Connection connection, String username) throws SQLException {
        String sql = "delete from t_test where a = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.executeUpdate();
        System.out.println("删除" + username);
        DButil.close(null, statement, connection);
    }

    public static void insert(Connection connection, String username, String password) throws SQLException {
        String sql = "insert into t_test(a,b,c) values (null,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        statement.execute();
        System.out.println("插入" + username);
        DButil.close(null, statement, connection);
    }

    public static void update(Connection connection, String username, String password) throws SQLException {
        String sql = "update t_test set a = ? where b = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, password);
        statement.setString(2, username);
        statement.execute();
        System.out.println(username + "改密码");
        DButil.close(null, statement, connection);
    }
}


