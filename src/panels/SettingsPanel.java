package panels;

//import com.sun.java.util.jar.pack.Attribute;

import handler.Handler;
import panels.support.GBC;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.HORIZONTAL;

public class SettingsPanel extends JPanel {
    private Handler handler;
    private SliderPanel curveSliderPanel, pointSizePanel;
    private SwitchButton linesVisibilityButton, fakeButton, nonPrimesVisibilityButton, repaintButton, automaticRepaintButton;
    private NumberField startingNumber, finalNumber;
    private JLabel startingNumberLabel, finalNumberLabel;
    private NumberSetPanel startingNumberPanel, finalNumberPanel;

    public SettingsPanel(Handler handler) {
        this.handler = handler;
        this.setLayout(new GridBagLayout());
        this.setFocusable(true);

        automaticRepaintButton = new SwitchButton(handler, "automatic repaint on", "automatic repaint off", Color.BLACK, Color.DARK_GRAY);
        automaticRepaintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                automaticRepaintButton.setCount(0);
                if (handler.getEngine().getAutomaticRepaint() == 1) {
                    handler.getEngine().setAutomaticRepaint(0);
                } else {
                    handler.getEngine().setAutomaticRepaint(1);
                }
                repaint();
            }
        });
        add(automaticRepaintButton, new GBC(0, 0, 1, 1).setFill(HORIZONTAL).setWeight(100, 100));
        repaintButton = new SwitchButton(handler, "          repaint          ", "          repaint          ", Color.BLACK, Color.BLACK);
        repaintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaintButton.setCount(0);

                if (handler.getEngine().getAutomaticRepaint() == 1) {
                    handler.getDrawPanel().repaint();
                } else {
                    handler.getEngine().setAutomaticRepaintTempFlag(true);
                    handler.getEngine().setAutomaticRepaint(1);
                    handler.getDrawPanel().repaint();
                    //handler.getEngine().setAutomaticRepaint(false);
                    /*
                    cannot use handler.getEngine().setAutomaticRepaint(false)
                    here, as for some reason compiler moved to
                    method handler.getDrawPanel().repaint() only after whole actionPerformed was completed!
                    So, another flag (automaticRepaintClicked) had to be introduced and
                    handler.getEngine().setAutomaticRepaint(false) has been moved to Engine...
                    */
                }
                repaint();
            }
        });
        add(repaintButton, new GBC(1, 0, 1, 1).setFill(HORIZONTAL).setWeight(1, 1));
        //fake button to control layout
        fakeButton = new SwitchButton(handler, "", "", Color.BLACK, Color.BLACK);
        add(fakeButton, new GBC(1, 1, 1, 1).setFill(BOTH).setWeight(1, 1));
        fakeButton.setBorderPainted(false);

        startingNumberLabel = new JLabel("<html>starting number<br/>limit: 65000 </html>");
        startingNumber = new NumberField(handler, startingNumberLabel, "n", 1);
        startingNumberPanel = new NumberSetPanel(handler, startingNumber, startingNumberLabel);
        add(startingNumberPanel, new GBC(0, 2, 2, 1).setFill(HORIZONTAL).setWeight(100, 100));

        finalNumberLabel = new JLabel("<html>final number<br/>limit: 65000 </html>");
        finalNumber = new NumberField(handler, finalNumberLabel, "N", 1);
        finalNumberPanel = new NumberSetPanel(handler, finalNumber, finalNumberLabel);
        add(finalNumberPanel, new GBC(0, 3, 2, 1).setFill(HORIZONTAL).setWeight(100, 100));

        linesVisibilityButton = new SwitchButton(handler, "lines on", "lines off", Color.BLACK, Color.DARK_GRAY);
        linesVisibilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                linesVisibilityButton.setCount(0);
                if (handler.getEngine().getLinesVisibility() == 0) {
                    handler.getEngine().setLinesVisible(1);
                } else {
                    handler.getEngine().setLinesVisible(0);
                }

                if (handler.getEngine().getAutomaticRepaint() == 1) handler.getDrawPanel().repaint();
                repaint();
            }
        });
        add(linesVisibilityButton, new GBC(0, 4, 1, 1).setFill(HORIZONTAL).setWeight(100, 100));

        nonPrimesVisibilityButton = new SwitchButton(handler, "non-primes on", "non-primes off", Color.BLACK, Color.DARK_GRAY);
        nonPrimesVisibilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nonPrimesVisibilityButton.setCount(0);
                if (handler.getEngine().getNonPrimesVisible() == 0) {
                    handler.getEngine().setNonPrimesVisible(1);
                } else {
                    handler.getEngine().setNonPrimesVisible(0);
                }

                if (handler.getEngine().getAutomaticRepaint() == 1) handler.getDrawPanel().repaint();
                repaint();
            }
        });
        add(nonPrimesVisibilityButton, new GBC(1, 4, 1, 1).setFill(HORIZONTAL).setWeight(1, 1));

        pointSizePanel = new LimitedSliderPanel(handler, "<html>point size: 1</html>", new Integer[]{1, 1, 3, 5, 7, 9}, 1, new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                changePointSizeText("point size: " + source.getValue());
                handler.getEngine().setDotSize(source.getValue());
                if (handler.getEngine().getAutomaticRepaint() == 1) handler.getDrawPanel().repaint();
            }
        });
        add(pointSizePanel, new GBC(0, 5, 2, 2).setFill(BOTH).setWeight(100, 100));

        curveSliderPanel = new SliderPanel(handler, "<html>curve grade: 4<br/>(Ulam spiral)</html>", 3, 18, 4, new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (source.getValue() != 4) changeSliderPanelText("curve grade: " + source.getValue());
                if (source.getValue() == 4)
                    changeSliderPanelText("<html>curve grade: " + source.getValue() + "<br/>(Ulam spiral)</html>");
                handler.getEngine().setS(source.getValue());
                if (handler.getEngine().getAutomaticRepaint() == 1) handler.getDrawPanel().repaint();
            }
        });
        add(curveSliderPanel, new GBC(0, 7, 2, 2).setFill(BOTH).setWeight(100, 100));

    } //end of constructor

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        linesVisibilityButton.changeColor(g);
        nonPrimesVisibilityButton.changeColor(g);
        automaticRepaintButton.changeColor(g);
        repaintButton.changeColor(g);
        fakeButton.changeColor(g);
    }

    public void changeSliderPanelText(String text) {
        curveSliderPanel.setLabelText(text);
    }

    public void changePointSizeText(String text) {
        pointSizePanel.setLabelText(text);
    }
}
