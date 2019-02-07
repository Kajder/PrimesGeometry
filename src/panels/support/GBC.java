package panels.support;

import java.awt.*;

public class GBC extends GridBagConstraints {
    public GBC (int gridx, int gridy){
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }
    public GBC (int gridx, int gridy, int gridwidth, int gridheight){
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
        this.anchor = anchor;
        this.fill = fill;
        this.insets = insets;
        this.ipadx = ipadx;
        this.ipady = ipady;
    }

    public GBC setWeight(int weightx, int weighty){
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    public GBC setAnchor(int anchor){
        this.anchor = anchor;
        return this;
    }
    public GBC setFill(int fill){
        this.fill = fill;
        return this;
    }
    public GBC setInsets(int top, int left, int bottom, int right){
        this.insets = new Insets(top, left, bottom,  right);
        return this;
    }
    public GBC setIpad(int ipdax, int ipady){
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }



}
