package g2048.v2_5;
/**
 * @author Xcl
 * @date 2021/12/1 09:48
 * @package g2048.v3
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends JFrame implements Runnable, IData, ILogin {
    private static final long serialVersionUID = 3878971623272611814L;
    public static String stt1 = null;
    JFrame jfmm = this;
    private JLabel l_name, l_password;
    private JTextField t_name;
    private JPasswordField t_password;
    private JButton b_login, b_reset, b_register;
    private Register rf_1 = null;

    Login() {
        setTitle(stt1 + "登录窗体");
        setVisible(stt1 != null);
        init(stt1);
        setSize(380, 300);
        setLocation(200, 100);
    }


    @Override
    public void init(String st) {
        setLayout(null);
        l_name = new JLabel("用户名", JLabel.CENTER);
        l_password = new JLabel("用户密码", JLabel.CENTER);
        t_name = new JTextField();
        t_password = new JPasswordField();
        b_login = new JButton("登录");
        b_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "登录成功会跳转！", " ", -1);
                if (st.equals("用户")) {
                    /*数据库结束*/
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn1 = DriverManager.getConnection(SqlMessage.sqlmessage_url,
                                SqlMessage.sqlmessage_user, SqlMessage.sqlmessage_password);
                        Statement stmt1 = conn1.createStatement();
                        ResultSet rs1 = stmt1.executeQuery("select Tname,Tpasswd from t_user ;");
                        while (rs1.next()) {
                            if (String.valueOf(t_name.getText()).equals(rs1.getString(1))) {
                                if (String.valueOf(t_password.getPassword()).equals(rs1.getString(2))) {
                                    jfmm.dispose();
                                    new GView(t_name.getText()).showView();
                                    break;
                                }
                            }
                        }
                    } catch (Exception e_1) {
                        JOptionPane.showMessageDialog(null, "数据库综合错误，如果你看到此弹窗，请立刻联系我。",
                                "ERROR!", JOptionPane.ERROR_MESSAGE);
                        e_1.printStackTrace();
                    }
                    /*数据库结束*/
                } else if (st.equals("管理员")) {
                    if (String.valueOf(t_name.getText()).equals("admin123")) {
                        if (String.valueOf(t_password.getPassword()).equals("111111")) {
                            jfmm.dispose();
                            new UserInfo(String.valueOf(t_name.getText()));
                        }
                    }
                } else {
                    System.out.println("不可能执行到这里...");
                }
            }
        });
        b_reset = new JButton("重置");
        b_reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t_name.setText("");
                t_password.setText("");
            }
        });
        b_register = new JButton("点我注册");
        b_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (st.equals("管理员"))
                    JOptionPane.showMessageDialog(null, "注意：管理员模式登录禁止注册！",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                else {
                    jfmm.dispose();
                    if (rf_1 != null) rf_1.dispose();
                    Register rf = new Register();
                    rf_1 = rf;
                    rf.setVisible(true);
                }
            }
        });
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 2, 5, 5));
        p.add(l_name);
        p.add(t_name);
        p.add(l_password);
        p.add(t_password);
        p.setBounds(5, 5, 350, 185);
        add(p);
        p = new JPanel();
        p.setLayout(new GridLayout(1, 3, 5, 5));
        p.add(b_login);
        p.add(b_reset);
        p.add(b_register);
        p.setBounds(5, 205, 350, 50);
        add(p);
    }


    @Override
    public void run() {
        new Login();
    }
}
