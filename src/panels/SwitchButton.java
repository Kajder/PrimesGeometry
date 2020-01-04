package panels;

import handler.Handler;

import javax.swing.*;
import java.awt.*;

public class SwitchButton extends JButton {
    private Handler handler;
    private JButton button;
    private Color buttonColor, color1, color2;
    private int count;
    private String labelActual, label1, label2;

    public SwitchButton(Handler handler, String label1, String label2, Color color1, Color color2) {
        this.label1 = label1;
        this.label2 = label2;
        this.color1 = color1;
        this.color2 = color2;
        button = new JButton();
        this.setForeground(Color.WHITE);
        this.setFont(new Font("SansSerif", Font.BOLD, 12));

        this.setModel(new DefaultButtonModel() {
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

    public void changeColor(Graphics g) {
        /*if (handler.getEngine().getLinesVisibility()) {
            buttonColor = offColor;
        } else buttonColor = onColor;*/
        if (count == 0) {
            labelActual = (buttonColor == color1) ? label2 : label1;
            buttonColor = (buttonColor == color1) ? color2 : color1;
            setBackground(buttonColor);
            this.setText(labelActual);
            this.setFocusPainted(false);
        }
        count = 1;
        //g.setColor(buttonColor);
        //g.fillRect(getX(), getY()+10, getWidth(), getHeight());
    }


    public void setCount(int val) {
        count = val;
    }
}
