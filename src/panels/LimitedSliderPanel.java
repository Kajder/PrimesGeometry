package panels;

import handler.Handler;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Hashtable;

public class LimitedSliderPanel extends SliderPanel{
    private Handler handler;
    private Hashtable<Integer, JLabel> LABELS;
    private int i;
    private Integer[] val;
    public LimitedSliderPanel(Handler handler, String text, Integer[] VALUES, int init, ChangeListener listener){
        super(handler, text, VALUES[0], VALUES.length-1, init, listener);
        LABELS = new Hashtable<>();
        val = VALUES;
        this.handler = handler;
        for (i=0; i<VALUES.length; i++){
         LABELS.put(i, new JLabel(VALUES[i].toString()));
         LABELS.get(i).setForeground(Color.WHITE);
        }

        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(LABELS);
        slider.setSnapToTicks(false);
        slider.setForeground(Color.WHITE);
        slider.setBackground(Color.BLACK);


    }
    public void setLabelText(String labelText) {
        label.setText("point size: "+val[slider.getValue()]);
        handler.getEngine().setDotSize(val[slider.getValue()]);
        System.out.println(handler.getEngine().getDotSize());

    }
}
