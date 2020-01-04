package panels;

import handler.Handler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


public class DrawPanel extends JPanel implements MouseMotionListener {
    private Handler handler;
    private boolean printPointFlag;
    private Rectangle rectangle;
    private Color color;

    public DrawPanel(Handler handler) {
        addMouseMotionListener(this);
        this.handler = handler;
        rectangle = new Rectangle(0, 0, 0, 0);
    }

    public void paintComponent(Graphics g) {
        if (printPointFlag) {
            printPoint(g);
        } else {
            super.paintComponent(g);
            handler.getEngine().print(g);
        }
    }

    private void printPoint(Graphics g) {
        g.setColor(color);
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        repaint();
        printPointFlag = false;

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        handler.getEngine().displayNumber(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    public void setColor(Color c) {
        color = c;
    }

    public void setRectangle(int x, int y, int width, int height) {
        rectangle.setBounds(x, y, width, height);
    }

    public void setPrintPointFlag(boolean flag) {
        printPointFlag = flag;
    }

}
