package main;

import handler.Handler;
import panels.DrawPanel;
import panels.SettingsPanel;
import panels.SliderPanel;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainFrame extends JFrame  implements KeyListener {
    private Handler handler;
    private Border settingsBorder;
    private JPanel settingsPanel;
    private DrawPanel drawPanel;
    private SliderPanel curveSliderPanel;
    private Font font1 = new Font("SansSerif", Font.BOLD, 12);
    private ChangeListener listener;

    public MainFrame(Handler handler){
        this.handler=handler;
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000,618));
        setTitle("Primes Geometry - demo by £ukasz Kajder");
        settingsPanel = new SettingsPanel(handler);
        settingsBorder = BorderFactory.createTitledBorder(settingsBorder,"parameters",1,0,font1,Color.WHITE);
        settingsPanel.setBorder(settingsBorder);

    drawPanel = new DrawPanel(handler);
    drawPanel.setBorder(BorderFactory.createEtchedBorder());
    settingsPanel.setBackground(Color.BLACK);
    settingsPanel.setPreferredSize(new Dimension(300,618));

    add(settingsPanel, BorderLayout.WEST);
    add(drawPanel, BorderLayout.CENTER);
    pack();

    addComponentListener(new ComponentListener() {
        @Override
        public void componentResized(ComponentEvent e) {
            System.out.println("frame resized");
        handler.getEngine().setFrameResized(true);
            repaint();
        }

        @Override
        public void componentMoved(ComponentEvent e) {

        }

        @Override
        public void componentShown(ComponentEvent e) {

        }

        @Override
        public void componentHidden(ComponentEvent e) {

        }
    });
    }


    public JPanel getSettingsPanel() {
        return settingsPanel;
    }

    public DrawPanel getDrawPanel() {
        return drawPanel;
    }

    public SliderPanel getCurveSliderPanel() {
        return curveSliderPanel;
    }

    public Font getFont() { return font1; }



    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("key released");
    }
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key pressed");
        if (e.getKeyCode() == KeyEvent.VK_UP){
            System.out.println("górna");
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN){
            System.out.println("dolna");
        }
    }
}