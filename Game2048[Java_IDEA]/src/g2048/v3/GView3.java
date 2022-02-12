package g2048.v3;

/**
 * @author Xcl
 * @date 2021/12/3 09:48
 * @package g2048.v3
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GView3 implements IGView {
    static int score = 0;
    static int scorem = 0;
    String user = null;
    String jindu = null;
    String huifu = null;
    String gcbl = null;
    JFrame JFm;
    JLabel JSnm;
    JLabel JSn;
    JLabel Jsm;
    JLabel Js;
    GameMb gamemb;
    JLabel JTip;
    JMenu jm;
    JMenu jm1;
    JMenuBar jmb;
    JMenuItem v3;
    JMenuItem v4;
    JMenuItem v5;
    JMenuItem About;
    JMenuItem chart;
    JMenuItem exit;

    GView3(String st) {
        setUser(st);
        init(st);
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        GView3.score = score;
    }

    public static int getScorem() {
        return scorem;
    }

    public static void setScorem(int scorem) {
        GView3.scorem = scorem;
    }

    public String getJindu() {
        return jindu;
    }

    public void setJindu(String jindu) {
        this.jindu = jindu;
    }

    @Override
    public void showView() {
        getJFm().setVisible(true);
    }

    @Override
    public void init(String st) {
        JOptionPane.showMessageDialog(null, "您的所有游戏数据将被自动保存在位于中国北京的服务器中，" +
                "继续游戏默认您同意我的隐私协议。", "隐私申明", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(null, "注意:请通过菜单栏退出游戏，默认会接着上一次记录，如果想重新开始请按Esc或R。",
                "提示", JOptionPane.CANCEL_OPTION);
        setJFm(new JFrame(st + "的2048小游戏"));
        getJFm().setSize(450, 600);
        getJFm().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getJFm().setLocationRelativeTo(null);
        getJFm().setResizable(false);
        getJFm().setLayout(null);
        setJmb(new JMenuBar());
        setJm(new JMenu("功能"));
        setV3(new JMenuItem("3*3"));
        getV3().addActionListener(this);
        setV4(new JMenuItem("4*4"));
        getV4().addActionListener(this);
        setV5(new JMenuItem("5*5"));
        getV5().addActionListener(this);
        getJm().add(getV3());
        getJm().add(getV4());
        getJm().add(getV5());
        setJm1(new JMenu("其它"));
        setAbout(new JMenuItem("关于"));
        getAbout().addActionListener(this);
        setChart(new JMenuItem("排行榜"));
        getChart().addActionListener(this);
        setExit(new JMenuItem("退出"));
        getExit().addActionListener(this);
        getJm1().add(getAbout());
        getJm1().add(getExit());
        getJmb().add(getJm());
        getJmb().add(getJm1());
        getJFm().setJMenuBar(getJmb());
        setJSnm(new JLabel("目前你的最高分", JLabel.CENTER));
        getJSnm().setFont(IData.scf);
        getJSnm().setForeground(Color.WHITE);
        getJSnm().setOpaque(true);
        getJSnm().setBackground(Color.GRAY);
        getJSnm().setBounds(10, 1, 185, 29);
        getJFm().add(getJSnm());
        setJsm(new JLabel("0", JLabel.CENTER));
        getJsm().setFont(IData.scf);
        getJsm().setForeground(Color.WHITE);
        getJsm().setOpaque(true);
        getJsm().setBackground(Color.GRAY);
        getJsm().setBounds(10, 30, 185, 30);
        getJFm().add(getJsm());
        setJSn(new JLabel(st + "的当前得分", JLabel.CENTER));
        getJSn().setFont(IData.scf);
        getJSn().setForeground(Color.WHITE);
        getJSn().setOpaque(true);
        getJSn().setBackground(Color.GRAY);
        getJSn().setBounds(225, 1, 195, 29);
        getJFm().add(getJSn());
        setJs(new JLabel("0", JLabel.CENTER));
        getJs().setFont(IData.scf);
        getJs().setForeground(Color.WHITE);
        getJs().setOpaque(true);
        getJs().setBackground(Color.GRAY);
        getJs().setBounds(225, 30, 195, 30);
        getJFm().add(getJs());
        setJTip(new JLabel("操作：↑↓←→/WSAD, 按Esc/R重新开始", JLabel.CENTER));
        getJTip().setFont(IData.nomf);
        getJTip().setForeground(Color.DARK_GRAY);
        getJTip().setBounds(26, 60, 400, 40);
        getJFm().add(getJTip());
        setGamemb(new GameMb());
        getGamemb().setBounds(0, 100, 450, 450);
        getGamemb().setBackground(Color.GRAY);
        getGamemb().setFocusable(true);
        getGamemb().setLayout(new FlowLayout());
        getJFm().add(getGamemb());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getV4()) {
            String datime = new SimpleDateFormat("yyyy-MM-dd|HH-mm-ss").format(new Date());
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(SqlMessage.Sqlmessage_URL,
                        SqlMessage.Sqlmessage_USER, SqlMessage.Sqlmessage_PASSWORD);
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("update t_user set Ttime3 = '" + datime + "',Tjindu3=" + getGcbl() + " where Tname = '" + getUser() + "';");
            } catch (Exception e_5) {
                JOptionPane.showMessageDialog(null, "数据库综合错误，如果你看到此弹窗，请立刻联系我。",
                        "ERROR!", JOptionPane.ERROR_MESSAGE);
                e_5.printStackTrace();
            }
            new GView4(getUser()).showView();
            getJFm().dispose();
        } else if (e.getSource() == getV5()) {
            String datime = new SimpleDateFormat("yyyy-MM-dd|HH-mm-ss").format(new Date());
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(SqlMessage.Sqlmessage_URL,
                        SqlMessage.Sqlmessage_USER, SqlMessage.Sqlmessage_PASSWORD);
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("update t_user set Ttime3 = '" + datime + "',Tjindu3=" + getGcbl() + " where Tname = '" + getUser() + "';");
            } catch (Exception e_5) {
                JOptionPane.showMessageDialog(null, "数据库综合错误，如果你看到此弹窗，请立刻联系我。",
                        "ERROR!", JOptionPane.ERROR_MESSAGE);
                e_5.printStackTrace();
            }
            new GView5(getUser()).showView();
            getJFm().dispose();
        } else if (e.getSource() == getAbout()) {
            JOptionPane.showMessageDialog(null, "这是一个简单的2048小游戏\n版本号: V3.2\n通过'W，A，S，" +
                    "D'或'↑，↓，←，→'可以轻松控制\n如发现任何Bug,欢迎联系我\n我的联系方式:xcl@xuegao-tzx.top\n我的主页: xuegao-" +
                    "tzx.top\n我的Github: github.com/xuegao-tzx", "关于", -1);
            geturl("https://www.xuegao-tzx.top/g2048h.html");
        } else if (e.getSource() == getChart()) {
            getJFm().dispose();
            new Charts(user, "3*3的2048小游戏的", 3);
        } else if (e.getSource() == getExit()) {
            String datime = new SimpleDateFormat("yyyy-MM-dd|HH-mm-ss").format(new Date());
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(SqlMessage.Sqlmessage_URL,
                        SqlMessage.Sqlmessage_USER, SqlMessage.Sqlmessage_PASSWORD);
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("update t_user set Ttime3 = '" + datime + "',Tjindu3=" + getGcbl() + " where Tname = '" + getUser() + "';");
            } catch (Exception e_5) {
                JOptionPane.showMessageDialog(null, "数据库综合错误，如果你看到此弹窗，请立刻联系我。",
                        "ERROR!", JOptionPane.ERROR_MESSAGE);
                e_5.printStackTrace();
            }
            getJFm().dispose();
            System.exit(0);
        }
    }

    @Override
    public void geturl(String url) {
        try {
            URI uri = URI.create(url);
            if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
            }
        } catch (NullPointerException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public JFrame getJFm() {
        return JFm;
    }

    @Override
    public void setJFm(JFrame JFm) {
        this.JFm = JFm;
    }

    @Override
    public JLabel getJSnm() {
        return JSnm;
    }

    @Override
    public void setJSnm(JLabel JSnm) {
        this.JSnm = JSnm;
    }

    @Override
    public JLabel getJSn() {
        return JSn;
    }

    @Override
    public void setJSn(JLabel JSn) {
        this.JSn = JSn;
    }

    @Override
    public JLabel getJsm() {
        return Jsm;
    }

    @Override
    public void setJsm(JLabel jsm) {
        Jsm = jsm;
    }

    @Override
    public JLabel getJs() {
        return Js;
    }

    @Override
    public void setJs(JLabel js) {
        Js = js;
    }

    public GameMb getGamemb() {
        return gamemb;
    }

    public void setGamemb(GameMb gamemb) {
        this.gamemb = gamemb;
    }

    @Override
    public String getGcbl() {
        return gcbl;
    }

    @Override
    public void setGcbl(String gcbl) {
        this.gcbl = gcbl;
    }

    @Override
    public JLabel getJTip() {
        return JTip;
    }

    @Override
    public void setJTip(JLabel JTip) {
        this.JTip = JTip;
    }

    @Override
    public String getHuifu() {
        return huifu;
    }

    @Override
    public void setHuifu(String huifu) {
        this.huifu = huifu;
    }

    @Override
    public JMenu getJm() {
        return jm;
    }

    @Override
    public void setJm(JMenu jm) {
        this.jm = jm;
    }

    @Override
    public JMenu getJm1() {
        return jm1;
    }

    @Override
    public void setJm1(JMenu jm1) {
        this.jm1 = jm1;
    }

    @Override
    public JMenuBar getJmb() {
        return jmb;
    }

    @Override
    public void setJmb(JMenuBar jmb) {
        this.jmb = jmb;
    }

    @Override
    public JMenuItem getV3() {
        return v3;
    }

    @Override
    public void setV3(JMenuItem v3) {
        this.v3 = v3;
    }

    @Override
    public JMenuItem getV4() {
        return v4;
    }

    @Override
    public void setV4(JMenuItem v4) {
        this.v4 = v4;
    }

    @Override
    public JMenuItem getV5() {
        return v5;
    }

    @Override
    public void setV5(JMenuItem v5) {
        this.v5 = v5;
    }

    @Override
    public JMenuItem getAbout() {
        return About;
    }

    @Override
    public void setAbout(JMenuItem About) {
        this.About = About;
    }

    @Override
    public JMenuItem getExit() {
        return exit;
    }

    @Override
    public void setExit(JMenuItem exit) {
        this.exit = exit;
    }

    @Override
    public JMenuItem getChart() {
        return chart;
    }

    @Override
    public void setChart(JMenuItem chart) {
        this.chart = chart;
    }

    class GameMb extends JPanel implements KeyListener {
        private static final long serialVersionUID = -7237908387425382979L;
        private final GBackGroup[][] GBackGroups = new GBackGroup[3][3];
        private boolean added = true;

        GameMb() {
            huifu1();
            initGame();
            addKeyListener(this);
        }

        public void huifu1() {
            for (int indexRow = 0; indexRow < 3; indexRow++)
                for (int indexCol = 0; indexCol < 3; indexCol++) GBackGroups[indexRow][indexCol] = new GBackGroup();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn1 = DriverManager.getConnection(SqlMessage.Sqlmessage_URL,
                        SqlMessage.Sqlmessage_USER, SqlMessage.Sqlmessage_PASSWORD);
                Statement stmt1 = conn1.createStatement();
                ResultSet rs1 = stmt1.executeQuery("select Tjindu3 from t_user where Tname='" + getUser() + "';");
                while (rs1.next()) {
                    setJindu(rs1.getString(1));
                    if (getJindu() != null) {
                        break;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "数据库综合错误，如果你看到此弹窗，请立刻联系我。",
                        "ERROR!", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            if (getJindu().equals("333")) {
                added = true;
                createCheck();
                added = true;
                createCheck();
                setHuifu("yhf");
            } else {
                String[] split = getJindu().split(":");
                String[] split2 = split[0].split("\\.");
                GBackGroups[0][0].setValue(Integer.parseInt(split2[0]));
                GBackGroups[0][1].setValue(Integer.parseInt(split2[1]));
                GBackGroups[0][2].setValue(Integer.parseInt(split2[2]));
                GBackGroups[1][0].setValue(Integer.parseInt(split2[3]));
                GBackGroups[1][1].setValue(Integer.parseInt(split2[4]));
                GBackGroups[1][2].setValue(Integer.parseInt(split2[5]));
                GBackGroups[2][0].setValue(Integer.parseInt(split2[6]));
                GBackGroups[2][1].setValue(Integer.parseInt(split2[7]));
                GBackGroups[2][2].setValue(Integer.parseInt(split2[8]));
                setHuifu("fff");
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_R) {
                initGame();
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                moveLeft();
                createCheck();
                judgeGameOver();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                moveRight();
                createCheck();
                judgeGameOver();
            } else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                moveUp();
                createCheck();
                judgeGameOver();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                moveDown();
                createCheck();
                judgeGameOver();
            }
            repaint();
        }

        private void initGame() {
            GView3.setScore(0);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(SqlMessage.Sqlmessage_URL,
                        SqlMessage.Sqlmessage_USER, SqlMessage.Sqlmessage_PASSWORD);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select Tscore3 from t_user where Tname='" + getUser() + "';");
                while (rs.next()) {
                    GView3.setScorem(rs.getInt(1));
                    if (GView3.getScorem() != 0) {
                        break;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "数据库综合错误，如果你看到此弹窗，请立刻联系我。",
                        "ERROR!", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            if (GView3.getScorem() == 1) {
                GView3.setScorem(0);
            }
            getJsm().setText(GView3.getScorem() + "");
            if (getHuifu().equals("fff")) {
                setHuifu("yhf");
            } else {
                for (int indexRow = 0; indexRow < 3; indexRow++) {
                    for (int indexCol = 0; indexCol < 3; indexCol++) {
                        GBackGroups[indexRow][indexCol].setValue(0);
                    }
                }
                added = true;
                createCheck();
                added = true;
                createCheck();
            }
        }

        private void createCheck() {
            List<Object> list = getEmptyChecks();
            if (!list.isEmpty() && added) {
                Random random = new Random();
                int index = random.nextInt(list.size());
                GBackGroup GBackGroup = (g2048.v3.GBackGroup) list.get(index);
                // 2, 4出现概率9:1
                int sjj = random.nextInt(12);
                if (0 <= sjj && sjj <= 11) GBackGroup.setValue(2);
                else if (sjj == 12) GBackGroup.setValue(4);
                else System.out.println("不可能执行到这里...");
                added = false;
            }
        }

        private List<Object> getEmptyChecks() {
            List<Object> objectList = new ArrayList<>();
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++) if (GBackGroups[i][j].getValue() == 0) objectList.add(GBackGroups[i][j]);
            return objectList;
        }

        private boolean judgeGameOver() {
            if (GView3.getScorem() < GView3.getScore()) {
                GView3.setScorem(GView3.getScore());
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(SqlMessage.Sqlmessage_URL,
                            SqlMessage.Sqlmessage_USER, SqlMessage.Sqlmessage_PASSWORD);
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("update t_user set Tscore3 = " + getScorem() + " where Tname = '" + getUser() + "';");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "数据库综合错误，如果你看到此弹窗，请立刻联系我。",
                            "ERROR!", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
            setGcbl("'" + GBackGroups[0][0].getValue() + "." + GBackGroups[0][1].getValue() + "." + GBackGroups[0][2].getValue() +
                    "." + GBackGroups[1][0].getValue() + "." + GBackGroups[1][1].getValue() + "." + GBackGroups[1][2].getValue() +
                    "." + GBackGroups[2][0].getValue() + "." + GBackGroups[2][1].getValue() + "." + GBackGroups[2][2].getValue() +
                    ":'");
            getJs().setText(GView3.getScore() + "");
            getJsm().setText(GView3.getScorem() + "");
            if (!getEmptyChecks().isEmpty()) return false;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    if (GBackGroups[i][j].getValue() == GBackGroups[i][j + 1].getValue()
                            || GBackGroups[i][j].getValue() == GBackGroups[i + 1][j].getValue()) {
                        return false;
                    }
                }
            }
            return true;
        }

        private void moveRight() {
            boolean key = true;
            boolean flag = true;
            boolean mark = true;
            while (key) {
                while (mark) {
                    mark = false;
                    for (int i = 0; i < 3; i++)
                        for (int j = 2; j > 0; j--)
                            if ((GBackGroups[i][j].getValue() == 0) && (GBackGroups[i][j - 1].getValue() != 0)) {
                                mark = true;
                                int temp = GBackGroups[i][j].getValue();
                                GBackGroups[i][j].setValue(GBackGroups[i][j - 1].getValue());
                                GBackGroups[i][j - 1].setValue(temp);
                            }
                }
                key = false;
                while (flag) {
                    flag = false;
                    for (int i = 0; i < 3; i++)
                        for (int j = 2; j > 0; j--)
                            if ((GBackGroups[i][j].getValue() == GBackGroups[i][j - 1].getValue()) && GBackGroups[i][j].getValue() != 0) {
                                key = true;
                                mark = true;
                                GBackGroups[i][j].setValue(GBackGroups[i][j].getValue() * 2);
                                GBackGroups[i][j - 1].setValue(0);
                                GView3.setScore(GView3.getScore() + GBackGroups[i][j].getValue());
                            }
                }
            }
            added = true;
        }

        private void moveLeft() {
            boolean key = true;
            boolean flag = true;
            boolean mark = true;
            while (key) {
                while (mark) {
                    mark = false;
                    for (int i = 0; i < 3; i++)
                        for (int j = 0; j < 2; j++)
                            if ((GBackGroups[i][j].getValue() == 0) && (GBackGroups[i][j + 1].getValue() != 0)) {
                                mark = true;
                                int temp = GBackGroups[i][j].getValue();
                                GBackGroups[i][j].setValue(GBackGroups[i][j + 1].getValue());
                                GBackGroups[i][j + 1].setValue(temp);
                            }
                }
                key = false;
                while (flag) {
                    flag = false;
                    for (int i = 0; i < 3; i++)
                        for (int j = 0; j < 2; j++)
                            if ((GBackGroups[i][j].getValue() == GBackGroups[i][j + 1].getValue()) && GBackGroups[i][j].getValue() != 0) {
                                key = true;
                                mark = true;
                                GBackGroups[i][j].setValue(GBackGroups[i][j].getValue() * 2);
                                GBackGroups[i][j + 1].setValue(0);
                                GView3.setScore(GView3.getScore() + GBackGroups[i][j].getValue());
                            }
                }
            }
            added = true;
        }

        private void moveUp() {
            boolean key = true;
            boolean flag = true;
            boolean mark = true;
            while (key) {
                while (mark) {
                    mark = false;
                    for (int i = 0; i < 2; i++)
                        for (int j = 0; j < 3; j++)
                            if ((GBackGroups[i][j].getValue() == 0) && (GBackGroups[i + 1][j].getValue() != 0)) {
                                mark = true;
                                int temp = GBackGroups[i][j].getValue();
                                GBackGroups[i][j].setValue(GBackGroups[i + 1][j].getValue());
                                GBackGroups[i + 1][j].setValue(temp);
                            }
                }
                key = false;
                while (flag) {
                    flag = false;
                    for (int i = 0; i < 2; i++)
                        for (int j = 0; j < 3; j++)
                            if ((GBackGroups[i][j].getValue() == GBackGroups[i + 1][j].getValue()) && GBackGroups[i][j].getValue() != 0) {
                                key = true;
                                mark = true;
                                GBackGroups[i][j].setValue(GBackGroups[i][j].getValue() * 2);
                                GBackGroups[i + 1][j].setValue(0);
                                GView3.setScore(GView3.getScore() + GBackGroups[i][j].getValue());
                            }
                }
            }
            added = true;
        }

        private void moveDown() {
            boolean key = true;
            boolean flag = true;
            boolean mark = true;
            while (key) {
                while (mark) {
                    mark = false;
                    for (int i = 2; i > 0; i--)
                        for (int j = 0; j < 3; j++)
                            if ((GBackGroups[i][j].getValue() == 0) && (GBackGroups[i - 1][j].getValue() != 0)) {
                                mark = true;
                                int temp = GBackGroups[i][j].getValue();
                                GBackGroups[i][j].setValue(GBackGroups[i - 1][j].getValue());
                                GBackGroups[i - 1][j].setValue(temp);
                            }
                }
                key = false;
                while (flag) {
                    flag = false;
                    for (int i = 2; i > 0; i--)
                        for (int j = 0; j < 3; j++)
                            if ((GBackGroups[i][j].getValue() == GBackGroups[i - 1][j].getValue()) && GBackGroups[i][j].getValue() != 0) {
                                key = true;
                                mark = true;
                                GBackGroups[i][j].setValue(GBackGroups[i][j].getValue() * 2);
                                GBackGroups[i - 1][j].setValue(0);
                                GView3.setScore(GView3.getScore() + GBackGroups[i][j].getValue());
                            }
                }
            }
            added = true;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) drawCheck(g, i, j);
            if (judgeGameOver()) {
                String datime = new SimpleDateFormat("yyyy-MM-dd|HH-mm-ss").format(new Date());
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(SqlMessage.Sqlmessage_URL,
                            SqlMessage.Sqlmessage_USER, SqlMessage.Sqlmessage_PASSWORD);
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("update t_user set Ttime3 = '" + datime + "',Tjindu3='333' where Tname = '" + getUser() + "';");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "数据库综合错误，如果你看到此弹窗，请立刻联系我。",
                            "ERROR!", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "游戏结束，游戏数据将自动保存", "提示", JOptionPane.ERROR_MESSAGE);
                g.setColor(Color.darkGray);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.WHITE);
                g.setFont(IData.topf);
                FontMetrics fms = getFontMetrics(IData.topf);
                final String value = "Game Over!";
                g.drawString(value, (getWidth() - fms.stringWidth(value)) / 2, getHeight() / 2);
            }
        }

        private void drawCheck(Graphics g, int i, int j) {
            Graphics2D gg = (Graphics2D) g;
            gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            gg.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                    RenderingHints.VALUE_STROKE_NORMALIZE);
            GBackGroup GBackGroup = GBackGroups[i][j];
            gg.setColor(GBackGroup.getBackground());
            gg.fillRoundRect(30 + 136 * j, 30 + 136 * i, 106, 106, 35, 35);
            gg.setColor(GBackGroup.getForeground());
            gg.setFont(GBackGroup.getCheckFont());
            FontMetrics fms = getFontMetrics(GBackGroup.getCheckFont());
            String value = String.valueOf(GBackGroup.getValue());
            gg.drawString(value, 30 + 136 * j + (106 - fms.stringWidth(value)) / 2, 30 + 136 * i +
                    (106 - fms.getAscent() - fms.getDescent()) / 2 + fms.getAscent());
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }
}