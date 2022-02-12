package g2048.v2_5;
/**
 * @author Xcl
 * @date 2021/12/1 09:48
 * @package g2048.v3
 */

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserInfo extends JFrame implements IData {
    private static final long serialVersionUID = -4598519373915560300L;
    int line = 0;
    private JTable table_book;

    UserInfo(String st) {
        init(st);
        setVisible(true);
        setSize(800, 600);
        setTitle("管理员" + st + "的用户信息展示区");
    }

    @Override
    public void init(String st) {
        setLayout(null);
        Object[] title = {"账号", "密码", "姓名", "性别", "身份证号", "注册时间"};//1.3.2.5.4.6

        List<String> list1 = new ArrayList<>();
        /*数据库开始*/
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(SqlMessage.sqlmessage_url,
                    SqlMessage.sqlmessage_user, SqlMessage.sqlmessage_password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from t_user ;");
            while (rs.next()) {
                line = rs.getInt(1);
                if (line != 0) {
                    break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "数据库综合错误，如果你看到此弹窗，请立刻联系我。",
                    "ERROR!", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        /*数据库结束*/
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn1 = DriverManager.getConnection(SqlMessage.sqlmessage_url,
                    SqlMessage.sqlmessage_user, SqlMessage.sqlmessage_password);
            Statement stmt1 = conn1.createStatement();
            ResultSet rs1 = stmt1.executeQuery("select * from t_user ;");
            while (rs1.next()) {
                list1.add(String.valueOf(rs1.getString(1)));
                list1.add(String.valueOf(rs1.getString(3)));
                list1.add(String.valueOf(rs1.getString(2)));
                list1.add(String.valueOf(rs1.getString(5)));
                list1.add(String.valueOf(rs1.getString(4)));
                list1.add(String.valueOf(rs1.getString(6)));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "数据库综合错误，如果你看到此弹窗，请立刻联系我。",
                    "ERROR!", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        /*数据库结束*/
        Object[][] bookList = new String[line][6];
        for (int j = 0; j < 6; j++)
            for (int b = 0, k = 0; b < line; b++, k += 6)
                switch (j) {
                    case 0:
                        bookList[b][j] = list1.get(k);
                        break;
                    case 1:
                        bookList[b][j] = list1.get(k + 1);
                        break;
                    case 2:
                        bookList[b][j] = list1.get(k + 2);
                        break;
                    case 3:
                        bookList[b][j] = list1.get(k + 3);
                        break;
                    case 4:
                        bookList[b][j] = list1.get(k + 4);
                        break;
                    case 5:
                        bookList[b][j] = list1.get(k + 5);
                        break;
                }
        table_book = new JTable(bookList, title);
        JScrollPane pane = new JScrollPane();
        pane.setBounds(20, 20, 750, 500);
        pane.getViewport().add(table_book);
        getContentPane().add(pane);
    }
}

