package panels;
import handler.Handler;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import static java.awt.SystemColor.text;
import static javax.swing.SwingConstants.HORIZONTAL;

public class SliderPanel extends JPanel{
    private Handler handler;
    private int fromVal, toVal, initial;
    private String labelText;
    public JSlider slider;
    public JLabel label;
    private ChangeListener sliderListener;
    private GridBagConstraints constraints;

    public SliderPanel(Handler handler, String text, int from, int to, int init, ChangeListener listener){
        this.handler=handler;
        this.fromVal=from;
        this.toVal=to;
        this.labelText=text;
        this.initial=init;
        slider = new JSlider(fromVal,toVal,initial);
        label = new JLabel(labelText);

        constraints=new GridBagConstraints();
        sliderListener = listener;
        organize();
    }
    public void organize(){
        //setPreferredSize(new Dimension(200,100));
        //setLayout(new GridLayout(20,20));
        setBackground(Color.BLACK);
        //setBorder(BorderFactory.createEtchedBorder());

        slider.setPreferredSize(new Dimension(180,50));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing((toVal-fromVal)/5);
        slider.setBackground(Color.BLACK);
        slider.setForeground(Color.WHITE);
        slider.addChangeListener(sliderListener);

        label.setPreferredSize(new Dimension(90,80));
        label.setBackground(Color.PINK);
        label.setForeground(Color.WHITE);

        constraints.gridx=5;
        constraints.gridy=5;
        constraints.gridwidth=5;
        constraints.gridheight=5;
        add(label);
        add(slider);
    }
    public void setLabelText(String labelText) {
        label.setText(labelText);
    }
}