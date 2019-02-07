package handler;
import engine.Engine;
import main.MainFrame;
import panels.DrawPanel;
import panels.SettingsPanel;

import javax.swing.*;

public class Handler {
    private Engine engine;

    public Handler(Engine e){
        this.engine = e;
    }

    //GETTERS
    public Engine getEngine() { return engine; }
    public MainFrame getMainFrame(){return engine.getMainFrame();}
    public DrawPanel getDrawPanel(){return engine.getMainFrame().getDrawPanel();}
    public JPanel getSettingsPanel(){return engine.getMainFrame().getSettingsPanel();}
}