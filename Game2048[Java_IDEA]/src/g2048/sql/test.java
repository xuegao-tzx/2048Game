package g2048.sql;

import java.sql.Connection;
import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection;
        //查找
        connection = DButil.connection();
        DButil.find(connection);
        //删除
        connection = DButil.connection();
        DButil.delete(connection, "张三");
        connection = DButil.connection();
        DButil.find(connection);
        //增加
        connection = DButil.connection();
        DButil.insert(connection, "张三", "123456");
        connection = DButil.connection();
        DButil.find(connection);
        //修改
        connection = DButil.connection();
        DButil.update(connection, "张三", "123");
        connection = DButil.connection();
        DButil.find(connection);
    }
}

