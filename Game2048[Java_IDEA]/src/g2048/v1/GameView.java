package g2048.v1;

/**
 * @author Xcl
 * @date 2021/12/1 09:48
 * @package g2048.v1
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView implements IBaseData {
    private static int score = 0;
    private static int scorem = 0;
    private JFrame JFm;
    private JLabel JSnm;
    private JLabel JSn;
    private JLabel Jsm;
    private JLabel Js;
    private GameMb gamemb;
    private String gcbl = null;
    private JLabel JTip;

    GameView() {
        init();
    }

    @Override
    public void init() {
        JFm = new JFrame("2048小游戏");
        JFm.setSize(450, 580);
        JFm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFm.setLocationRelativeTo(null);
        JFm.setResizable(false);
        JFm.setLayout(null);

        JSnm = new JLabel("目前最高分", JLabel.CENTER);
        JSnm.setFont(IBaseData.scf);
        JSnm.setForeground(Color.WHITE);
        JSnm.setOpaque(true);
        JSnm.setBackground(Color.GRAY);
        JSnm.setBounds(25, 1, 130, 29);
        JFm.add(JSnm);

        Jsm = new JLabel("0", JLabel.CENTER);
        Jsm.setFont(IBaseData.scf);
        Jsm.setForeground(Color.WHITE);
        Jsm.setOpaque(true);
        Jsm.setBackground(Color.GRAY);
        Jsm.setBounds(25, 30, 130, 30);
        JFm.add(Jsm);
        // 分数区
        JSn = new JLabel("当前得分", JLabel.CENTER);
        JSn.setFont(IBaseData.scf);
        JSn.setForeground(Color.WHITE);
        JSn.setOpaque(true);
        JSn.setBackground(Color.GRAY);
        JSn.setBounds(270, 1, 130, 29);
        JFm.add(JSn);

        Js = new JLabel("0", JLabel.CENTER);
        Js.setFont(IBaseData.scf);
        Js.setForeground(Color.WHITE);
        Js.setOpaque(true);
        Js.setBackground(Color.GRAY);
        Js.setBounds(270, 30, 130, 30);
        JFm.add(Js);

        // 说明：
        JTip = new JLabel("操作: ↑ ↓ ← →, 按esc键重新开始  ",
                JLabel.CENTER);
        JTip.setFont(IBaseData.nomf);
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

    @Override
    public void showView() {
        JFm.setVisible(true);
    }

    // 游戏面板需要对键值实现侦听，
    // 这里采用内部类来继承 JPanel 类，
    // 并实现接口 KeyListener 中的 keyPressed 方法，
    // 方格是通过
    class GameMb extends JPanel implements KeyListener {
        private static final long serialVersionUID = -7237908387425382979L;

        private final Check[][] checks = new Check[4][4];
        private boolean added = true;

        GameMb() {
            initGame();
            addKeyListener(this);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    initGame();
                    break;
                case KeyEvent.VK_LEFT:
                    moveLeft();
                    createCheck();
                    judgeGameOver();
                    break;
                case KeyEvent.VK_RIGHT:
                    moveRight();
                    createCheck();
                    judgeGameOver();
                    break;
                case KeyEvent.VK_UP:
                    moveUp();
                    createCheck();
                    judgeGameOver();
                    break;
                case KeyEvent.VK_DOWN:
                    moveDown();
                    createCheck();
                    judgeGameOver();
                    break;
                default:
                    break;
            }
            repaint();
        }

        private void initGame() {
            GameView.score = 0;
            Jsm.setText(GameView.scorem + "");
            try {
                File file = new File("D:\\Github\\java\\java-1\\src\\g2048\\db\\2048maxcj.txt");
                if (file.isFile() && file.exists()) {
                    InputStreamReader isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                    BufferedReader br = new BufferedReader(isr);
                    String data;
                    data = br.readLine();
                    GameView.scorem = Integer.parseInt(data);
                    br.close();
                } else System.out.println("文件不存在！");
            } catch (Exception e_1) {
                e_1.printStackTrace();
            }
            for (int indexRow = 0; indexRow < 4; indexRow++)
                for (int indexCol = 0; indexCol < 4; indexCol++) checks[indexRow][indexCol] = new Check();
            // 生成两个数
            added = true;
            createCheck();
            added = true;
            createCheck();
        }

        private void createCheck() {
            List<Check> list = getEmptyChecks();
            if (!list.isEmpty() && added) {
                Random random = new Random();
                int index = random.nextInt(list.size());
                Check check = list.get(index);
                // 2, 4，8出现概率6:4:1
                int sjj = random.nextInt(10);
                if (sjj == 0 || sjj == 1 || sjj == 2 || sjj == 6 || sjj == 8 || sjj == 10) check.value = 2;
                else if (sjj == 3 || sjj == 5 || sjj == 7 || sjj == 9) check.value = 4;
                else if (sjj == 4) check.value = 8;
                else System.out.println("不可能！");
                added = false;
            }
        }

        // 获取空白方格
        private List<Check> getEmptyChecks() {
            List<Check> checkList = new ArrayList<>();
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++) if (checks[i][j].value == 0) checkList.add(checks[i][j]);
            return checkList;
        }

        private boolean judgeGameOver() {
            if (GameView.scorem < GameView.score) GameView.scorem = GameView.score;
            gcbl = checks[0][0].value + "." + checks[0][1].value + "." + checks[0][2].value + "." +
                    checks[0][3].value + "." + checks[1][0].value + "." + checks[1][1].value + "." + checks[1][2].value +
                    "." + checks[1][3].value + "." + checks[2][0].value + "." + checks[2][1].value + "." + checks[2][2].value +
                    "." + checks[2][3].value + "." + checks[3][0].value + "." + checks[3][1].value + "." + checks[3][2].value +
                    "." + checks[3][3].value + GameView.score + ":";
            try {
                File f = new File("D:\\Github\\java\\java-1\\src\\g2048\\db\\2048maxcj.txt");
                File f1 = new File("D:\\Github\\java\\java-1\\src\\g2048\\db\\2048gd.txt");
                if (!f.exists()) f.createNewFile();
                if (!f1.exists()) f1.createNewFile();
                FileWriter fw1 = new FileWriter(f1);
                FileWriter fw = new FileWriter(f);
                BufferedWriter bw = new BufferedWriter(fw);
                BufferedWriter bw1 = new BufferedWriter(fw1);
                bw.write(String.valueOf(GameView.scorem));
                bw1.write(String.valueOf(gcbl));
                bw.flush();
                bw1.flush();
                bw.close();
                bw1.close();
                fw.close();
                fw1.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            Js.setText(GameView.score + "");
            Jsm.setText(GameView.scorem + "");
            if (!getEmptyChecks().isEmpty()) return false;

            //判断是否存在可合并的方格
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (checks[i][j].value == checks[i][j + 1].value
                            || checks[i][j].value == checks[i + 1][j].value) return false;

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
                            if ((checks[i][j].value == 0) && (checks[i][j - 1].value != 0)) {
                                mark = true;
                                int temp = checks[i][j].value;
                                checks[i][j].value = checks[i][j - 1].value;
                                checks[i][j - 1].value = temp;
                            }
                }
                key = false;
                while (flag) {
                    flag = false;
                    for (int i = 0; i < 4; i++)
                        for (int j = 3; j > 0; j--)
                            if ((checks[i][j].value == checks[i][j - 1].value) && checks[i][j].value != 0) {
                                key = true;
                                mark = true;
                                checks[i][j].value = checks[i][j].value * 2;
                                checks[i][j - 1].value = 0;
                                GameView.score += checks[i][j].value;
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
                            if ((checks[i][j].value == 0) && (checks[i][j + 1].value != 0)) {
                                mark = true;
                                int temp = checks[i][j].value;
                                checks[i][j].value = checks[i][j + 1].value;
                                checks[i][j + 1].value = temp;
                            }
                }
                key = false;
                while (flag) {
                    flag = false;
                    for (int i = 0; i < 4; i++)
                        for (int j = 0; j < 3; j++)
                            if ((checks[i][j].value == checks[i][j + 1].value) && checks[i][j].value != 0) {
                                key = true;
                                mark = true;
                                checks[i][j].value = checks[i][j].value * 2;
                                checks[i][j + 1].value = 0;
                                GameView.score += checks[i][j].value;
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
                            if ((checks[i][j].value == 0) && (checks[i + 1][j].value != 0)) {
                                mark = true;
                                int temp = checks[i][j].value;
                                checks[i][j].value = checks[i + 1][j].value;
                                checks[i + 1][j].value = temp;
                            }
                }
                key = false;
                while (flag) {
                    flag = false;
                    for (int i = 0; i < 3; i++)
                        for (int j = 0; j < 4; j++)
                            if ((checks[i][j].value == checks[i + 1][j].value) && checks[i][j].value != 0) {
                                key = true;
                                mark = true;
                                checks[i][j].value = checks[i][j].value * 2;
                                checks[i + 1][j].value = 0;
                                GameView.score += checks[i][j].value;
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
                            if ((checks[i][j].value == 0) && (checks[i - 1][j].value != 0)) {
                                mark = true;
                                int temp = checks[i][j].value;
                                checks[i][j].value = checks[i - 1][j].value;
                                checks[i - 1][j].value = temp;
                            }
                }
                key = false;
                while (flag) {
                    flag = false;
                    for (int i = 3; i > 0; i--)
                        for (int j = 0; j < 4; j++)
                            if ((checks[i][j].value == checks[i - 1][j].value) && checks[i][j].value != 0) {
                                key = true;
                                mark = true;
                                checks[i][j].value = checks[i][j].value * 2;
                                checks[i - 1][j].value = 0;
                                GameView.score += checks[i][j].value;
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
                g.setFont(IBaseData.topf);
                FontMetrics fms = getFontMetrics(IBaseData.topf);
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
            Check check = checks[i][j];
            gg.setColor(check.getBackground());
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
            gg.setColor(check.getForeground());
            gg.setFont(check.getCheckFont());

            // 对文字的长宽高测量。
            FontMetrics fms = getFontMetrics(check.getCheckFont());
            String value = String.valueOf(check.value);
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