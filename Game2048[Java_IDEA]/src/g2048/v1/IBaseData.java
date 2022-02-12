package g2048.v1;

/**
 * @author Xcl
 * @date 2021/12/1 09:48
 * @package g2048.v1
 */

import java.awt.*;

public interface IBaseData {
    Font topf = new Font("", Font.BOLD, 50);
    Font scf = new Font("", Font.BOLD, 23);
    Font nomf = new Font("", Font.PLAIN, 20);
    Font font1 = new Font("", Font.BOLD, 46);
    Font font2 = new Font("", Font.BOLD, 40);
    Font font3 = new Font("", Font.BOLD, 34);
    Font font4 = new Font("", Font.BOLD, 28);
    Font font5 = new Font("", Font.BOLD, 22);
    //int normalFontdata = 20;
    //int topicFontdata = 30;

    void init();

    void showView();
}
