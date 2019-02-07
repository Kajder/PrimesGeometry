package engine;
import handler.*;
import main.MainFrame;
import mathTools.Point2D;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Engine {
    private Handler handler;
    private MainFrame mainFrame;

    //editable for user
    private Integer N; //last number to draw
    private Integer n; //number to start drawing with
    private Integer S; //type of spiral; 3->triangle, 4->square, 5->pentagon etc.
    private Integer L; //number of pixels to draw between points
    private Integer dotSize=1;
    private Integer linesVisible=1;
    private Integer nonPrimesVisible=1;
    private Integer automaticRepaint=1;
    private boolean automaticRepaintTempFlag;
    private boolean frameResized;

    //to calculate basing on above or during drawing
    private int M; //number of moves between rotations (moves in a given direction)
    private double alpha; //change of rotation angle
    private double beta; //absolute rotation angle
    private double xC, yC, xN, yN; //coordinates of current and next point to draw
    private int k=1,l=0,p=0,i=0, m=0, t=0, r=0,s=0, breakMark=0, turns=0; //counters
    private int moveX, moveY; //pixels to move to have the center of the dot where the coordinates indicate

    //navigational - always available
    private int zoom, moveLR, moveUD, width=0+zoom+dotSize, height=0+zoom+dotSize;

    //other
    private Point2D activePoint;
    int[] primes = new int[65535];
    private int allNumbers[][]; //table with coordinates of each number
    private Map<String, Integer> polygonPar, polygonParametersActual, polygonParametersObsolete;
    //Get file from resources folder
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("primes.txt");
    BufferedReader reader = null;

    public Engine(){
        polygonParametersActual = new HashMap<>();
        polygonParametersObsolete = new HashMap<>();
        initializeParametersMaps();
        handler = new Handler(this);
        activePoint = new Point2D(0,0);
        mainFrame = new MainFrame(handler);
        readPrimes(inputStream);
        calculate();
    }

    private void initializeParametersMaps(){ //later to be populated from Settings
        polygonParametersActual.put("N",2000);
        polygonParametersActual.put("n",0);
        polygonParametersActual.put("S",4);
        polygonParametersActual.put("L",7);
        polygonParametersActual.put("dotSize",1);
        polygonParametersActual.put("linesVisible",1);
        polygonParametersActual.put("automaticRepaint",1);
        polygonParametersActual.put("nonPrimesVisible",1);
        updateOldParametersMap();
    }

    public void updateOldParametersMap(){
        //reminder - actual parameters map get updated with every set-method that is called
        //reminder - old parameters get updated with every RepaintButton click (not with every repaint() method call).
        //And during initialization
        polygonParametersObsolete.putAll(polygonParametersActual);
    }

    public void calculate(){}

    public void print(Graphics g){
        if (!frameResized) {
            updateOldParametersMap();
        }
        if (automaticRepaint==1) printPolygonSpiral(g);
    }

    public void printPolygonSpiral(Graphics g){
        if (automaticRepaintTempFlag) {
            setAutomaticRepaint(0);
            setAutomaticRepaintTempFlag(false);
        }
        if (frameResized){
            frameResized=false;
            polygonPar=polygonParametersObsolete;
            System.out.println("frame resized - "+frameResized);
        }else{ polygonPar=polygonParametersActual; }

        N = polygonPar.get("N");
        n = polygonPar.get("n");
        S = polygonPar.get("S");
        L = polygonPar.get("L");
        dotSize = polygonPar.get("dotSize");
        linesVisible = polygonPar.get("linesVisible");
        nonPrimesVisible = polygonPar.get("nonPrimesVisible");

        allNumbers = new int[mainFrame.getDrawPanel().getWidth()][mainFrame.getDrawPanel().getHeight()];

        M = 1;
        alpha = -360 / S;//(-2*Math.PI/S);
        beta = 0;
        xC = handler.getDrawPanel().getWidth()/2 + moveLR;
        yC = handler.getDrawPanel().getHeight()/2 + moveUD;
        k = 1;
        p = 0;
        width=1+zoom+dotSize;
        height=1+zoom+dotSize;
        breakMark = 0;
        turns = 0;
        moveX = (int) (Math.floor(width / 2));
        moveY = (int) (Math.floor(height / 2));
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, mainFrame.getDrawPanel().getWidth(), mainFrame.getDrawPanel().getHeight());
        g2d.setColor(Color.YELLOW);
        g2d.fillRect((int) (xC - moveX), (int) (yC - moveY), width, height);
        allNumbers[(int)(Math.round(xC))][(int)(Math.round(yC))]=n;
        n = n + 1;
        p=0;
        m=1;
        for (i = n; i <= N; i++) {
            if (i<=primes[p]) {
                //turns navigation
                if (m <= M) {
                    if (S == 4) xN = xC + (int) (L * Math.cos(Math.toRadians(beta)));
                    if (S == 4) yN = yC + (int) (L * Math.sin(Math.toRadians(beta)));
                    if (S != 4) xN = xC + (L * Math.cos(Math.toRadians(beta)));
                    if (S != 4) yN = yC + (L * Math.sin(Math.toRadians(beta)));
                    m++;
                } else {//make turn
                    m = 1;
                    turns++;
                    beta = turns * alpha;
                    if (k == (Math.floor(S / 2))) {
                        M++; //increase number of moves in given direction every time for triangle, every second time for square etc.
                        k = 0;
                    }
                    k++;

                    if (S == 4) xN = xC + (int) (L * Math.cos(Math.toRadians(beta)));
                    if (S == 4) yN = yC + (int) (L * Math.sin(Math.toRadians(beta)));
                    if (S != 4) xN = xC + (L * Math.cos(Math.toRadians(beta)));
                    if (S != 4) yN = yC + (L * Math.sin(Math.toRadians(beta)));
                    m++;
                }
            }

            //points and lines painting
            if(i==primes[p]){
                g2d.setColor(Color.BLACK);
                if (linesVisible==1) g2d.drawLine((int) xC, (int) yC, (int) xN, (int) yN);
                g2d.setColor(Color.BLUE);
                g2d.fillRect((int) (xN - moveX), (int) (yN - moveY), width, height);
                //System.out.println(i);
                if((xN>=0)&&(xN<mainFrame.getDrawPanel().getWidth()-1)&&(yN>=0)&&(yN<mainFrame.getDrawPanel().getHeight()-1)) {
                    allNumbers[(int) (Math.round(xN))][(int) (Math.round(yN))] = i;
                }
                    p++;
                    xC = xN;
                    yC = yN;

            }else if(i<primes[p]){
                g2d.setColor(Color.BLACK);
                if (linesVisible==1) g2d.drawLine((int) xC, (int) yC, (int) xN, (int) yN);
                if (nonPrimesVisible==1)g2d.fillRect((int) (xN - moveX), (int) (yN - moveY), width, height);
                //System.out.println(i);
                if((xN>=0)&&(xN<mainFrame.getDrawPanel().getWidth()-1)&&(yN>=0)&&(yN<mainFrame.getDrawPanel().getHeight()-1)) {
                    allNumbers[(int) (Math.round(xN))][(int) (Math.round(yN))] = i;
                }
                    xC = xN;
                    yC = yN;

            }else if(i>primes[p]){p++;i--;}

            }


    }

    public void readPrimes(InputStream inputStream){
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String text = null;

            while ((text = reader.readLine()) != null) {
                //list.add(Integer.parseInt(text));
                primes[l]=(int)(Integer.parseInt(text));
                //System.out.println(primes[l]);
                l++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }

    }

    public void displayNumber (MouseEvent e) {
        //[mainFrame.getDrawPanel().getWidth()][mainFrame.getDrawPanel().getHeight()];
        if ((e.getX() > t) && (e.getX() + t < mainFrame.getDrawPanel().getWidth()) &&
                (e.getY() > t) && (e.getY() + t < mainFrame.getDrawPanel().getHeight())) {

            t = (int) (Math.floor(dotSize));
            for (r = e.getX() - t; r <= e.getX() + t; r++) {
                for (s = e.getY() - t; s <= e.getY() + t; s++) {
                    if (allNumbers[r][s] != 0) {
                        if ((activePoint.getX() == r) && (activePoint.getY() == s)) {
                            //pokoloruj na biało analizowany punkt
                            handler.getDrawPanel().setColor(Color.WHITE);
                            handler.getDrawPanel().setRectangle((int) (r - moveX), (int) (s - moveY), width, height);
                            handler.getDrawPanel().setPrintPointFlag(true);
                            handler.getDrawPanel().repaint();
                            System.out.println("hovering over: " + allNumbers[r][s]);
                        } else {
                            //pokoloruj na biało analizowany punkt
                            handler.getDrawPanel().setColor(Color.WHITE);
                            handler.getDrawPanel().setRectangle((int) (r - moveX), (int) (s - moveY), width, height);
                            handler.getDrawPanel().setPrintPointFlag(true);
                            handler.getDrawPanel().repaint();
                            //pokoloruj aktywny punkt z białego na jego oryginalny kolor
                            handler.getDrawPanel().setColor(Color.BLACK);
                            for (i = 0; i < primes.length; i++) {
                                if (allNumbers[(int) (activePoint.getX())][(int) (activePoint.getY())] == primes[i]) {
                                    handler.getDrawPanel().setColor(Color.BLUE);
                                    break;
                                }
                                if (allNumbers[(int) (activePoint.getX())][(int) (activePoint.getY())] < primes[i])
                                    break;
                            }
                            if (allNumbers[(int) (activePoint.getX())][(int) (activePoint.getY())] == n)
                                handler.getDrawPanel().setColor(Color.YELLOW);
                            handler.getDrawPanel().setRectangle((int) (activePoint.getX() - moveX), (int) (activePoint.getY() - moveY), width, height);
                            handler.getDrawPanel().setPrintPointFlag(true);
                            handler.getDrawPanel().repaint();
                            //ustaw analizowany punkt jako aktywny
                            activePoint.setPoint(r, s);
                            System.out.println("hovering over: " + allNumbers[r][s]);
                            return;
                        }
                    }
                }
            }

        }
    }




    //GETTERS

    public MainFrame getMainFrame(){return mainFrame;}
    public int getS(){return S;}
    public int getDotSize(){return dotSize;}
    public Integer getLinesVisibility(){return polygonParametersActual.get("linesVisible");}
    public Integer getNonPrimesVisible(){return polygonParametersActual.get("nonPrimesVisible");}
    public Integer getAutomaticRepaint(){return polygonParametersActual.get("automaticRepaint");}
    public Map<String, Integer> getPolygonParametersActual(){return polygonParametersActual;}
    public boolean getFrameResized() {return frameResized;}
    public int getNumberFromCoordinates(int x, int y){return allNumbers[x][y];}

    //SETTERS
    public void setS(int s) {polygonParametersActual.put("S",s);}
    public void setDotSize(int dotSize) {polygonParametersActual.put("dotSize",dotSize);}
    public void setLinesVisible(Integer linesVisible) {polygonParametersActual.put("linesVisible",linesVisible);}
    public void setAutomaticRepaint(Integer val){polygonParametersActual.put("automaticRepaint",val);}
    public void setNonPrimesVisible(Integer val){polygonParametersActual.put("nonPrimesVisible",val);}
    public void setAutomaticRepaintTempFlag(boolean flag){automaticRepaintTempFlag=flag;}
    public void setFrameResized(boolean flag) {frameResized=flag;}
}