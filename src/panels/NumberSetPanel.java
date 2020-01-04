package panels;

import graphics.Assets;
import handler.Handler;
import panels.support.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.GridBagConstraints.HORIZONTAL;

public class NumberSetPanel extends JPanel {

    private Handler handler;
    private NumberSetButton upButton, downButton;
    private NumberField textField;
    private JLabel textLabel;
    private String parameter;
    private Timer timerUP, timerDOWN;
    private ActionListener actionUP, actionDOWN;

    public NumberSetPanel(Handler hand, NumberField tField, JLabel tLabel) {
        handler = hand;
        init();
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        textField = tField;
        parameter = textField.getParameter();
        textLabel = tLabel;
        textLabel.setForeground(Color.WHITE);

        Assets assets = new Assets();
        upButton = new NumberSetButton(new ImageIcon(assets.getArrowUPw()), new ImageIcon(assets.getArrowUPb()));
        upButton.addActionListener(actionUP);
        downButton = new NumberSetButton(new ImageIcon(assets.getArrowDOWNw()), new ImageIcon(assets.getArrowDOWNb()));
        downButton.addActionListener(actionDOWN);

        add(textLabel, new GBC(0, 0, 8, 1).setInsets(0, 5, 0, 0).setFill(HORIZONTAL).setWeight(1, 1));
        add(textField, new GBC(10, 0, 10, 1).setFill(HORIZONTAL).setWeight(100, 100));
        add(upButton, new GBC(8, 0, 1, 1).setWeight(0, 0).setInsets(1, 1, 1, 1));
        add(downButton, new GBC(9, 0, 1, 1).setWeight(0, 0).setInsets(1, 1, 1, 1));

        textLabel.setPreferredSize(new Dimension(100, 50));
        textField.setPreferredSize(new Dimension(70, 20));
    }

    private void init() {
        actionUP = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerUP.start();
                if (upButton.getBlinks() < upButton.getBlinksLimit()) {
                    upButton.changeBackground();
                }
                if (upButton.getBlinks() == 0) {
                    handler.getEngine().getPolygonParametersActual().put(parameter, Integer.parseInt(textField.getText()) + 1);
                    textField.setText(Integer.toString(handler.getEngine().getPolygonParametersActual().get(parameter)));
                    if (handler.getEngine().getAutomaticRepaint() == 1) handler.getDrawPanel().repaint();
                }
                upButton.setBlinks(upButton.getBlinks() + 1);
                if (upButton.getBlinks() == upButton.getBlinksLimit()) {
                    upButton.setBlinks(0);
                    timerUP.stop();
                }

            }
        };
        actionDOWN = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerDOWN.start();
                if (downButton.getBlinks() < downButton.getBlinksLimit()) {
                    downButton.changeBackground();
                }
                if (downButton.getBlinks() == 0) {
                    handler.getEngine().getPolygonParametersActual().put(parameter, Integer.parseInt(textField.getText()) - 1);
                    textField.setText(Integer.toString(handler.getEngine().getPolygonParametersActual().get(parameter)));
                    if (handler.getEngine().getAutomaticRepaint() == 1) handler.getDrawPanel().repaint();
                }
                downButton.setBlinks(downButton.getBlinks() + 1);
                if (downButton.getBlinks() == downButton.getBlinksLimit()) {
                    downButton.setBlinks(0);
                    timerDOWN.stop();
                }

            }
        };


        timerUP = new Timer(100, actionUP);
        timerDOWN = new Timer(100, actionDOWN);

    }
}
