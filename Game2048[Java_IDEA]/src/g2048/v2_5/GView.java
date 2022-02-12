package g2048.v2_5;

/**
 * @author Xcl
 * @date 2021/12/1 09:48
 * @package g2048.v3
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GView implements IData {
    private static int score = 0;
    private static int scorem = 0;
    private String user = null;
    private JFrame JFm;
    private JLabel JSnm;
    private JLabel JSn;
    private JLabel Jsm;
    private JLabel Js;
    private GameMb gamemb;
    private String gcbl = null;
    private JLabel JTip;

    GView(String st) {

        user = st;
        init(st);
    }

    public void showView() {
        JFm.setVisible(true);
    }

    @Override
    public void init(String st) {
        JOptionPane.showMessageDialog(null, "您的所有游戏数据将被自动保存在本地以及位于中国北京的服务器中，" +
                "继续游戏默认您同意我的隐私协议。", "隐私申明", JOptionPane.INFORMATION_MESSAGE);
        JFm = new JFrame(st + "的2048小游戏");
        JFm.setSize(450, 580);
        JFm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JFm.setLocationRelativeTo(null);
        JFm.setResizable(false);
        JFm.setLayout(null);

        JSnm = new JLabel("目前你的最高分", JLabel.CENTER);
        JSnm.setFont(IData.scf);
        JSnm.setForeground(Color.WHITE);
        JSnm.setOpaque(true);
        JSnm.setBackground(Color.GRAY);
        JSnm.setBounds(10, 1, 185, 29);
        JFm.add(JSnm);

        Jsm = new JLabel("0", JLabel.CENTER);
        Jsm.setFont(IData.scf);
        Jsm.setForeground(Color.WHITE);
        Jsm.setOpaque(true);
        Jsm.setBackground(Color.GRAY);
        Jsm.setBounds(10, 30, 185, 30);
        JFm.add(Jsm);
        // 分数区
        JSn = new JLabel(st + "的当前得分", JLabel.CENTER);
        JSn.setFont(IData.scf);
        JSn.setForeground(Color.WHITE);
        JSn.setOpaque(true);
        JSn.setBackground(Color.GRAY);
        JSn.setBounds(225, 1, 195, 29);
        JFm.add(JSn);

        Js = new JLabel("0", JLabel.CENTER);
        Js.setFont(IData.scf);
        Js.setForeground(Color.WHITE);
        Js.setOpaque(true);
        Js.setBackground(Color.GRAY);
        Js.setBounds(225, 30, 195, 30);
        JFm.add(Js);

        // 说明：
        JTip = new JLabel("操作: ↑ ↓ ← →, 按esc键重新开始",
                JLabel.CENTER);
        JTip.setFont(IData.nomf);
        JTip.setForeground(Color.DARK_GRAY);
        JTip.setBounds(26, 60, 400, 40);
        JFm.add(JTip);

        gamemb = new GameMb();
        gamemb.setBounds(0, 100, 450, 450);
        gamemb.setBackground(Color.GRAY);
        gamemb.setFocusable(true);
        gamemb.setLayout(new FlowLayout());
        JFm.add(gamemb);
    }


    // 游戏面板需要对键值实现侦听，
    // 这里采用内部类来继承 JPanel 类，
    // 并实现接口 KeyListener 中的 keyPressed 方法，
    // 方格是通过
    class GameMb extends JPanel implements KeyListener {
        private static final long serialVersionUID = -7237908387425382979L;

        private final GBackGroup[][] GBackGroups = new GBackGroup[4][4];
        private boolean added = true;

        GameMb() {
            initGame();
            addKeyListener(this);
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
            // case KeyEvent.VK_SPACE:
            //  System.out.println("空格");
            repaint();
        }

        private void initGame() {
            GView.score = 0;

            /*数据库开始*/
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(SqlMessage.sqlmessage_url,
                        SqlMessage.sqlmessage_user, SqlMessage.sqlmessage_password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select Tscore3 from t_user where Tname='" + user + "';");
                while (rs.next()) {
                    GView.scorem = rs.getInt(1);
                    if (GView.scorem != 0) {
                        break;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "数据库综合错误，如果你看到此弹窗，请立刻联系我。",
                        "ERROR!", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            if (GView.scorem == 1) {
                GView.scorem = 0;
            }
            /*数据库结束*/
            Jsm.setText(GView.scorem + "");
            for (int indexRow = 0; indexRow < 4; indexRow++)
                for (int indexCol = 0; indexCol < 4; indexCol++) GBackGroups[indexRow][indexCol] = new GBackGroup();
            // 生成两个数
            added = true;
            createCheck();
            added = true;
            createCheck();
        }

        private void createCheck() {
            List<Object> list = getEmptyChecks();
            if (!list.isEmpty() && added) {
                Random random = new Random();
                int index = random.nextInt(list.size());
                GBackGroup GBackGroup = (GBackGroup) list.get(index);
                // 2, 4，8出现概率6:4:1
                int sjj = random.nextInt(10);
                if (sjj == 0 || sjj == 1 || sjj == 2 || sjj == 6 || sjj == 8 || sjj == 10) GBackGroup.value = 2;
                else if (sjj == 3 || sjj == 5 || sjj == 7 || sjj == 9) GBackGroup.value = 4;
                else if (sjj == 4) GBackGroup.value = 8;
                else System.out.println("不可能执行到这里...");
                added = false;
            }
        }

        // 获取空白方格
        private List<Object> getEmptyChecks() {
            List<Object> objectList = new ArrayList<>();
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++) if (GBackGroups[i][j].value == 0) objectList.add(GBackGroups[i][j]);
            return objectList;
        }

        private boolean judgeGameOver() {
            //if (GameView.scorem < GameView.score) GameView.scorem = GameView.score;

            if (GView.scorem < GView.score) {
                GView.scorem = GView.score;
                /*数据库开始*/
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(SqlMessage.sqlmessage_url,
                            SqlMessage.sqlmessage_user, SqlMessage.sqlmessage_password);
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("update t_user set Tscore3 = " + GView.scorem + " where Tname = '" + user + "';");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "数据库综合错误，如果你看到此弹窗，请立刻联系我。",
                            "ERROR!", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                /*数据库结束*/
            }
            String datime = new SimpleDateFormat("yyyy-MM-dd|HH-mm-ss").format(new Date());
            gcbl = user + "." + GBackGroups[0][0].value + "." + GBackGroups[0][1].value + "." + GBackGroups[0][2].value + "." +
                    GBackGroups[0][3].value + "." + GBackGroups[1][0].value + "." + GBackGroups[1][1].value + "." + GBackGroups[1][2].value +
                    "." + GBackGroups[1][3].value + "." + GBackGroups[2][0].value + "." + GBackGroups[2][1].value + "." + GBackGroups[2][2].value +
                    "." + GBackGroups[2][3].value + "." + GBackGroups[3][0].value + "." + GBackGroups[3][1].value + "." + GBackGroups[3][2].value +
                    "." + GBackGroups[3][3].value + "." + GView.score + "." + datime + ":";
            try {
                File f1 = new File("D:\\Github\\java\\java-1\\src\\g2048\\db\\2048gd.txt");
                if (!f1.exists()) f1.createNewFile();
                FileWriter fw1 = new FileWriter(f1);
                BufferedWriter bw1 = new BufferedWriter(fw1);
                bw1.write(String.valueOf(gcbl));
                bw1.flush();
                bw1.close();
                fw1.close();
            } catch (IOException e2) {
                JOptionPane.showMessageDialog(null, "文件系统综合错误，如果你看到此弹窗，请立刻联系我。",
                        "ERROR!", JOptionPane.ERROR_MESSAGE);
                e2.printStackTrace();
            }
            Js.setText(GView.score + "");
            Jsm.setText(GView.scorem + "");
            if (!getEmptyChecks().isEmpty()) return false;

            //判断是否存在可合并的方格
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (GBackGroups[i][j].value == GBackGroups[i][j + 1].value
                            || GBackGroups[i][j].value == GBackGroups[i + 1][j].value) return false;

            return true;
        }

        private void moveRight() {
            boolean key = true;
            boolean flag = true;
            boolean mark = true;
            while (key) {
                while (mark) {
                    mark = false;
                    for (int i = 0; i < 4; i++)
                        for (int j = 3; j > 0; j--)
                            if ((GBackGroups[i][j].value == 0) && (GBackGroups[i][j - 1].value != 0)) {
                                mark = true;
                                int temp = GBackGroups[i][j].value;
                                GBackGroups[i][j].value = GBackGroups[i][j - 1].value;
                                GBackGroups[i][j - 1].value = temp;
                            }
                }
                key = false;
                while (flag) {
                    flag = false;
                    for (int i = 0; i < 4; i++)
                        for (int j = 3; j > 0; j--)
                            if ((GBackGroups[i][j].value == GBackGroups[i][j - 1].value) && GBackGroups[i][j].value != 0) {
                                key = true;
                                mark = true;
                                GBackGroups[i][j].value = GBackGroups[i][j].value * 2;
                                GBackGroups[i][j - 1].value = 0;
                                GView.score += GBackGroups[i][j].value;
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
                    for (int i = 0; i < 4; i++)
                        for (int j = 0; j < 3; j++)
                            if ((GBackGroups[i][j].value == 0) && (GBackGroups[i][j + 1].value != 0)) {
                                mark = true;
                                int temp = GBackGroups[i][j].value;
                                GBackGroups[i][j].value = GBackGroups[i][j + 1].value;
                                GBackGroups[i][j + 1].value = temp;
                            }
                }
                key = false;
                while (flag) {
                    flag = false;
                    for (int i = 0; i < 4; i++)
                        for (int j = 0; j < 3; j++)
                            if ((GBackGroups[i][j].value == GBackGroups[i][j + 1].value) && GBackGroups[i][j].value != 0) {
                                key = true;
                                mark = true;
                                GBackGroups[i][j].value = GBackGroups[i][j].value * 2;
                                GBackGroups[i][j + 1].value = 0;
                                GView.score += GBackGroups[i][j].value;
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
                    for (int i = 0; i < 3; i++)
                        for (int j = 0; j < 4; j++)
                            if ((GBackGroups[i][j].value == 0) && (GBackGroups[i + 1][j].value != 0)) {
                                mark = true;
                                int temp = GBackGroups[i][j].value;
                                GBackGroups[i][j].value = GBackGroups[i + 1][j].value;
                                GBackGroups[i + 1][j].value = temp;
                            }
                }
                key = false;
                while (flag) {
                    flag = false;
                    for (int i = 0; i < 3; i++)
                        for (int j = 0; j < 4; j++)
                            if ((GBackGroups[i][j].value == GBackGroups[i + 1][j].value) && GBackGroups[i][j].value != 0) {
                                key = true;
                                mark = true;
                                GBackGroups[i][j].value = GBackGroups[i][j].value * 2;
                                GBackGroups[i + 1][j].value = 0;
                                GView.score += GBackGroups[i][j].value;
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
                    for (int i = 3; i > 0; i--)
                        for (int j = 0; j < 4; j++)
                            if ((GBackGroups[i][j].value == 0) && (GBackGroups[i - 1][j].value != 0)) {
                                mark = true;
                                int temp = GBackGroups[i][j].value;
                                GBackGroups[i][j].value = GBackGroups[i - 1][j].value;
                                GBackGroups[i - 1][j].value = temp;
                            }
                }
                key = false;
                while (flag) {
                    flag = false;
                    for (int i = 3; i > 0; i--)
                        for (int j = 0; j < 4; j++)
                            if ((GBackGroups[i][j].value == GBackGroups[i - 1][j].value) && GBackGroups[i][j].value != 0) {
                                key = true;
                                mark = true;
                                GBackGroups[i][j].value = GBackGroups[i][j].value * 2;
                                GBackGroups[i - 1][j].value = 0;
                                GView.score += GBackGroups[i][j].value;
                            }
                }
            }
            added = true;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (int i = 0; i < 4; i++) for (int j = 0; j < 4; j++) drawCheck(g, i, j);

            // GameOver
            if (judgeGameOver()) {
                JOptionPane.showMessageDialog(null, "游戏结束，游戏数据将自动保存", "提示", JOptionPane.ERROR_MESSAGE);
                g.setColor(Color.darkGray);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.WHITE);
                g.setFont(IData.topf);
                FontMetrics fms = getFontMetrics(IData.topf);
                final String value = "Game Over!";
                g.drawString(value,
                        (getWidth() - fms.stringWidth(value)) / 2,
                        getHeight() / 2);
            }
        }

        // 绘制方格
        // Graphics2D 类扩展了 Graphics 类，
        // 提供了对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
        private void drawCheck(Graphics g, int i, int j) {
            Graphics2D gg = (Graphics2D) g;
            gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            gg.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                    RenderingHints.VALUE_STROKE_NORMALIZE);
            GBackGroup GBackGroup = GBackGroups[i][j];
            gg.setColor(GBackGroup.getBackground());
            // 绘制圆角
            // x - 要填充矩形的 x 坐标。
            // y - 要填充矩形的 y 坐标。
            // width - 要填充矩形的宽度。
            // height - 要填充矩形的高度。
            // arcwidth - 4 个角弧度的水平直径。
            // archeight - 4 个角弧度的垂直直径。
            gg.fillRoundRect(10 + 105 * j,
                    10 + 105 * i,
                    95, 95, 25, 25);
            gg.setColor(GBackGroup.getForeground());
            gg.setFont(GBackGroup.getCheckFont());

            // 对文字的长宽高测量。
            FontMetrics fms = getFontMetrics(GBackGroup.getCheckFont());
            String value = String.valueOf(GBackGroup.value);
            //使用此图形上下文的当前颜色绘制由指定迭代器给定的文本。
            //getAscent()是FontMetrics中的一个方法，
            //它返回某字体的基线(baseline)到该字体中大多数字符的升部(ascender)之间的距离
            //getDescent 为降部
            gg.drawString(value,
                    10 + 105 * j +
                            (95 - fms.stringWidth(value)) / 2,
                    10 + 105 * i +
                            (95 - fms.getAscent() - fms.getDescent()) / 2
                            + fms.getAscent());
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }
}