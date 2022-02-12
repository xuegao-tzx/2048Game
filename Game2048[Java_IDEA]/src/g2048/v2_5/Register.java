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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static g2048.v2_5.SqlMessage.*;

public class Register extends JFrame implements IData {
    private static final long serialVersionUID = -3035113424256798327L;
    private static final Register pre_u2 = null;
    String sss = null;
    JFrame jfm;
    String panduancf = null;
    private ButtonGroup bg;
    private JLabel l_id, l_name, l_password1, l_password2, l_sex, l_card;
    private JTextField t_id, t_name;
    private JPasswordField p_password1, p_password2, p_card;
    private JRadioButton r_male, r_female;
    private JButton b_reg, b_reset;
    private String sex1;

    Register() {
        init(sss);
        setTitle("2048游戏注册");
        setSize(300, 450);
        setLocation(100, 150);
    }

    @Override
    public void init(String sss) {
        setLayout(new GridLayout(7, 2, 5, 5));
        l_id = new JLabel("用户名", JLabel.CENTER);
        l_name = new JLabel("真实姓名", JLabel.CENTER);
        l_password1 = new JLabel("密码", JLabel.CENTER);
        l_password2 = new JLabel("确认密码", JLabel.CENTER);
        l_card = new JLabel("身份证号", JLabel.CENTER);
        l_sex = new JLabel("性别", JLabel.CENTER);
        t_id = new JTextField();
        t_name = new JTextField();
        p_password1 = new JPasswordField();
        p_password2 = new JPasswordField();
        p_card = new JPasswordField();
        JPanel p = new JPanel();
        bg = new ButtonGroup();
        r_male = new JRadioButton("男");
        r_female = new JRadioButton("女");
        bg.add(r_male);
        bg.add(r_female);
        b_reg = new JButton("注册");
        b_reg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "务必牢记自己的用户信息，一旦注册成功后只有管理员可以更改密码。", "提示", JOptionPane.CANCEL_OPTION);
                if (Register.pre_u2 != null) Register.pre_u2.dispose();
                if (r_female.isSelected()) sex1 = "女";
                else if (r_male.isSelected()) sex1 = "男";
                /*数据库开始*/
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(sqlmessage_url,
                            sqlmessage_user, sqlmessage_password);
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("select count(*) from t_user where Tname='" + t_id.getText() + "';");
                    while (rs.next()) {
                        int panduan = rs.getInt(1);
                        if (panduan == 1) {
                            panduancf = "CF";
                            break;
                        }
                    }
                } catch (Exception e_2) {
                    JOptionPane.showMessageDialog(null, "数据库综合错误，如果你看到此弹窗，请立刻联系我。",
                            "ERROR!", JOptionPane.ERROR_MESSAGE);
                    e_2.printStackTrace();
                }
                /*数据库结束*/
                if (panduancf.equals("CF")) {
                    JOptionPane.showMessageDialog(null, "用户名重复，请重新注册！",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    t_id.setText("");
                    t_name.setText("");
                    p_password1.setText("");
                    p_password2.setText("");
                    p_card.setText("");
                } else {
                    if (String.valueOf(p_password1.getPassword()).equals(String.valueOf((p_password2.getPassword())))) {
                        User u = new User(String.valueOf(t_id.getText()), String.valueOf(t_name.getText()),
                                String.valueOf(p_password1.getPassword()), String.valueOf(sex1).charAt(0),
                                String.valueOf(p_card.getPassword()));
                        String datime = new SimpleDateFormat("yyyy-MM-dd|HH-mm-ss").format(new Date());
                        List<String> listuser = new ArrayList<>();
                        listuser.add(u.getId());
                        listuser.add(u.getName());
                        listuser.add(u.getPassword());
                        listuser.add(u.getCard());
                        listuser.add(String.valueOf(u.getSex()));
                        listuser.add(datime);
                        String data = "insert into t_user(Tname, Trname, Tpasswd, Tcard, Tsex, Ttime, Tscore3, " +
                                "Tjindu3, Ttime3) values ('" + listuser.get(0) + "','" + listuser.get(1) + "','" + listuser.get(2)
                                + "','" + listuser.get(3) + "','" + listuser.get(4) + "','" + listuser.get(5) + "','1','0.0.0.0.0.0.0.0.0" +
                                ".0.0.0.0.0.0.0.0','2002-04-12|01-01-00');";
                        /*数据库开始*/
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            Connection conn1 = DriverManager.getConnection(sqlmessage_url,
                                    sqlmessage_user, sqlmessage_password);
                            Statement stmt1 = conn1.createStatement();
                            stmt1.execute(data);
                            JOptionPane.showMessageDialog(null, "注册成功！", " ", -1);
                        } catch (Exception e_3) {
                            JOptionPane.showMessageDialog(null, "数据库综合错误，如果你看到此弹窗，请立刻联系我。",
                                    "ERROR!", JOptionPane.ERROR_MESSAGE);
                            e_3.printStackTrace();
                        }
                        /*数据库结束*/
                        jfm.dispose();
                        new GView(listuser.get(0)).showView();
                    } else {
                        p_password1.setText("");
                        p_password2.setText("");
                    }
                }
            }
        });
        b_reset = new JButton("重置");
        b_reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t_id.setText("");
                t_name.setText("");
                p_password1.setText("");
                p_password2.setText("");
                p_card.setText("");
                JOptionPane.showMessageDialog(null, "重置成功！", " ", 1);
            }
        });
        add(l_id);
        add(t_id);
        add(l_name);
        add(t_name);
        add(l_password1);
        add(p_password1);
        add(l_password2);
        add(p_password2);
        add(l_card);
        add(p_card);
        add(l_sex);
        p.add(r_male);
        p.add(r_female);
        add(p);
        add(b_reg);
        add(b_reset);
    }
}
