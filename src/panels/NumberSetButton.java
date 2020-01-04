package panels;

import javax.swing.*;
import java.awt.*;

public class NumberSetButton extends JButton {
    private JButton button;
    private ImageIcon icon1, icon2;
    private Integer blinks = 0, blinksLimit = 4;

    public NumberSetButton(ImageIcon FirstIcon, ImageIcon SecondIcon) {
        button = new JButton();
        icon1 = FirstIcon;
        icon2 = SecondIcon;
        setIcon(icon1);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setBackground(Color.BLACK);
        setModel(new DefaultButtonModel() {
            @Override
            public boolean isPressed() {
                return false;
            }

            @Override
            public boolean isRollover() {
                return false;
            }

            @Override
            public void setRollover(boolean b) {
            }
        });

    }

    public void changeBackground() {
        if (getIcon() == icon1) {
            setIcon(icon2);
        } else {
            setIcon(icon1);
        }
    }

    public Integer getBlinksLimit() {
        return blinksLimit;
    }

    public Integer getBlinks() {
        return blinks;
    }

    public void setBlinks(Integer val) {
        blinks = val;
    }

    ;
}
