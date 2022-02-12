package g2048.v3;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xcl
 * @date 2021/12/5 14:50
 * @package g2048.v3
 */
public class Charts extends JFrame implements IData {
    static final long serialVersionUID = -4598519373915560300L;
    int line = 0;
    int panda = 0;
    JTable table_book;

    Charts(String st, String st1, int st2) {
        setPanda(st2);
        init(st);
        setVisible(true);
        setSize(800, 600);
        setTitle("用户" + st + ",正在查看" + st1 + "排行榜");
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public void init(String st) {
        setLayout(null);
        Object[] title = {"账号", "最高分", "游戏时间"};//1.3.2.5.4.6

        List<String> list1 = new ArrayList<>();
        /*数据库开始*/
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(SqlMessage.Sqlmessage_URL,
                    SqlMessage.Sqlmessage_USER, SqlMessage.Sqlmessage_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from t_user ;");
            while (rs.next()) {
                setLine(rs.getInt(1));
                if (getLine() != 0) {
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
            Connection conn1 = DriverManager.getConnection(SqlMessage.Sqlmessage_URL,
                    SqlMessage.Sqlmessage_USER, SqlMessage.Sqlmessage_PASSWORD);
            Statement stmt1 = conn1.createStatement();
            String aa = null, bb = null, cc = null;
            if (getPanda() == 3) {
                aa = "Tscore3";
                bb = "Ttime3";
                cc = "Tscore3";
            } else if (getPanda() == 4) {
                aa = "Tscore4";
                bb = "Ttime4";
                cc = "Tscore4";
            } else if (getPanda() == 5) {
                aa = "Tscore5";
                bb = "Ttime5";
                cc = "Tscore5";
            } else {
                System.out.println("这是不可能的...");
            }
            ResultSet rs1 = stmt1.executeQuery("select Tname," + aa + "," + bb + " from t_user order by " + cc + " asc;");
            while (rs1.next()) {
                list1.add(String.valueOf(rs1.getString(1)));
                list1.add(String.valueOf(rs1.getString(2)));
                list1.add(String.valueOf(rs1.getString(3)));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "数据库综合错误，如果你看到此弹窗，请立刻联系我。",
                    "ERROR!", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        /*数据库结束*/
        Object[][] bookList = new String[getLine()][6];
        for (int j = 0; j < 3; j++)
            for (int b = 0, k = 0; b < getLine(); b++, k += 3)
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
                }
        setTable_book(new JTable(bookList, title));
        JScrollPane pane = new JScrollPane();
        pane.setBounds(20, 20, 750, 500);
        pane.getViewport().add(getTable_book());
        getContentPane().add(pane);
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public JTable getTable_book() {
        return table_book;
    }

    public void setTable_book(JTable table_book) {
        this.table_book = table_book;
    }

    public int getPanda() {
        return panda;
    }

    public void setPanda(int panda) {
        this.panda = panda;
    }
}
