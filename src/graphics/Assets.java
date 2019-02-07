package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Assets {
    public BufferedImage sheet, arrowUPb, arrowUPw, arrowDOWNb, arrowDOWNw, arrowLEFTb, arrowLEFTw, arrowRIGHTb, arrowRIGHTw;
    private int len=20;

    public Assets() {
        init();
    }


    public void init() {
        try {
            sheet = ImageIO.read(getClass().getClassLoader().getResourceAsStream("sheet.png"));
        } catch (IOException ex) {
            System.out.println("io exception - image not found!");
        }
        arrowUPb = sheet.getSubimage(0,0,len,len);
        arrowUPw = sheet.getSubimage(0,len,len,len);
        arrowDOWNb = sheet.getSubimage(len,0,len,len);
        arrowDOWNw = sheet.getSubimage(len,len,len,len);
        arrowRIGHTb = sheet.getSubimage(2*len,0,len,len);
        arrowRIGHTw = sheet.getSubimage(2*len,len,len,len);
        arrowLEFTb = sheet.getSubimage(3*len,0,len,len);
        arrowLEFTw = sheet.getSubimage(3*len,len,len,len);
    }

    public BufferedImage getArrowUPb() {
        return arrowUPb;
    }

    public BufferedImage getArrowUPw() {
        return arrowUPw;
    }

    public BufferedImage getArrowDOWNb() {
        return arrowDOWNb;
    }

    public BufferedImage getArrowDOWNw() {
        return arrowDOWNw;
    }

    public BufferedImage getArrowLEFTb() {
        return arrowLEFTb;
    }

    public BufferedImage getArrowLEFTw() {
        return arrowLEFTw;
    }

    public BufferedImage getArrowRIGHTb() {
        return arrowRIGHTb;
    }

    public BufferedImage getArrowRIGHTw() {
        return arrowRIGHTw;
    }
}