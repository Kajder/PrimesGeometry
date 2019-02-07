package panels;

import handler.Handler;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class NumberField extends JTextField implements KeyListener {
    private Handler handler;
    private JLabel label;
    private String initialText, parameter;

    public NumberField(Handler handler, JLabel label, String parameter, Integer columns){
        super(Integer.toString(handler.getEngine().getPolygonParametersActual().get(parameter)), columns);
        this.handler = handler;
        this.label = label;
        this.initialText = label.getText();
        this.parameter = parameter;
        this.setFocusable(true);
        addKeyListener(this);
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
        setFont(new Font("SansSerif", Font.BOLD, 12));

        getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                                                  warn();
                                              }
            public void removeUpdate(DocumentEvent e) {
                                                  warn();
                                              }
            public void insertUpdate(DocumentEvent e) {
                                                  warn();
                                              }
        });
/*
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                //setText("");
            }
            @Override
            public void mouseExited(MouseEvent e){
                if (!isNumeric(getText())) label.setText("<html> not a number! <br/> put natural number </html>");
                if (isNumeric(getText())) {
                    label.setText(initialText);
                    handler.getEngine().getPolygonParametersActual().put(parameter, Integer.parseInt(getText()));
                }
                if (getText().isEmpty()) setText(text);
                if (handler.getEngine().getAutomaticRepaint()==1) handler.getDrawPanel().repaint();
            }

        });

        addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                //setText("");
            }
            @Override
            public void focusLost(FocusEvent e){
                if (!isNumeric(getText())) label.setText("<html> not a number! <br/> put natural number </html>");
                if (isNumeric(getText())) {
                    label.setText(initialText);
                    handler.getEngine().getPolygonParametersActual().put(parameter, Integer.parseInt(getText()));
                }
                if (getText().isEmpty()) setText(text);
                if (handler.getEngine().getAutomaticRepaint()==1) handler.getDrawPanel().repaint();
            }
        });
        */
    }

public void warn(){
    //System.out.println("focus set on:   "+handler.getMainFrame().getFocusOwner());
    if (!isNumeric(getText())) label.setText("<html> put natural number! </html>");
    if (isNumeric(getText())) {
        label.setText(initialText);
        handler.getEngine().getPolygonParametersActual().put(parameter, Integer.parseInt(getText()));
    }
    //if (getText().isEmpty()) setText(text);
    if (handler.getEngine().getAutomaticRepaint()==1) handler.getDrawPanel().repaint();
}
    public boolean isNumeric(String s) {
        return s.matches("\\d+");
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("key released");
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP){
            handler.getEngine().getPolygonParametersActual().put(parameter, Integer.parseInt(getText())+1);
            setText(Integer.toString(handler.getEngine().getPolygonParametersActual().get(parameter)));
            if (handler.getEngine().getAutomaticRepaint()==1) handler.getDrawPanel().repaint();
            //System.out.println(parameter+" : "+handler.getEngine().getPolygonParametersActual().get(parameter));
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN){
            handler.getEngine().getPolygonParametersActual().put(parameter, Integer.parseInt(getText())-1);
            setText(Integer.toString(handler.getEngine().getPolygonParametersActual().get(parameter)));
            if (handler.getEngine().getAutomaticRepaint()==1) handler.getDrawPanel().repaint();
            handler.getEngine().getPolygonParametersActual().get(parameter);
            //System.out.println(parameter+" : "+handler.getEngine().getPolygonParametersActual().get(parameter));
        }
}

public String getParameter(){
    return parameter;
}
}