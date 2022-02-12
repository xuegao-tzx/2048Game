package g2048.v1;

/**
 * @author Xcl
 * @date 2021/12/1 09:50
 * @package g2048.v1
 */

import java.awt.*;

public class Check {
    public int value;

    Check() {
        clear();
    }

    public void clear() {
        value = 0;
    }

    Color getForeground() {
        switch (value) {
            case 0:
                return new Color(0xcdc1b4);
            case 2:
                return Color.BLACK;
            case 4:
                return Color.BLACK;
            default:
                return Color.WHITE;
        }
    }

    Color getBackground() {
        switch (value) {
            case 0:
                return new Color(0xcdc1b4);
            case 2:
                return new Color(0xeee4da);
            case 4:
                return new Color(0xede0c8);
            case 8:
                return new Color(0xf2b179);
            case 16:
                return new Color(0xf59563);
            case 32:
                return new Color(0xf67c5f);
            case 64:
                return new Color(0xf65e3b);
            case 128:
                return new Color(0xedcf72);
            case 256:
                return new Color(0xedcc61);
            case 512:
                return new Color(0xedc850);
            case 1024:
                return new Color(0xedc53f);
            case 2048:
                return new Color(0xedc22e);
            case 4096:
                return new Color(0x65da92);
            case 8192:
                return new Color(0x5abc65);
            case 16384:
                return new Color(0x248c51);
            default:
                return new Color(0x09572A);
        }
    }

    Font getCheckFont() {
        if (value < 10) return IBaseData.font1;
        if (value < 100) return IBaseData.font2;
        if (value < 1000) return IBaseData.font3;
        if (value < 10000) return IBaseData.font4;
        return IBaseData.font5;
    }
}
