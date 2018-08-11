/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skunkgame;
import GraphicsLib.Window;
  import java.awt.Color;
  import java.awt.Graphics;
  import java.awt.Point;
  import java.awt.event.MouseEvent;
import static java.lang.Math.PI;
  import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author DarKz
 */
public class SkunkGame extends Window{

    public static Random rand = new Random();
    public static ColorMap cm;
    public static int margin = 10;
    public static int cw = 8;
    public static double[][][] meh = new double[100][100][100] ;
    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        Window.PANEL = new SkunkGame();
        Window.launch();
        // TODO code application logic here
    }
    
    public SkunkGame() {
        super("skunk", 1000, 1000);
        cm = new ColorMap();
        randomize();
    }
    @Override
    public void paintComponent(Graphics g) {
        cm.show(g, 2000);
        drawMatrix(g);
        drawGrid(g);
    }
    private void drawGrid(Graphics g) {
        
        g.setColor(Color.BLACK);
        
        for(int i = 0; i <= 10; i++){
            g.drawLine(margin, margin + i * 10 * cw, margin + 100 * cw, margin + i * 10 * cw); //horizontal
            g.drawLine(margin + i * 10 * cw, margin, margin + i * 10 * cw, margin + 100 * cw); //vertical
        }
    }
    
    private void drawMatrix(Graphics g) {
        update();
        for(int m = 0; m < 100; m++){
            for(int e = 0; e < 100; e++) {
                g.setColor(stopColor(m, e));
                g.fillRect(margin + m * cw, margin + e * cw, cw, cw);
            }
        }
    }
    
    private Color stopColor(int m, int e) {
        for(int h = 0; h < 100; h++){
            
            if(shouldStop(m, e, h)) {
                if(h < ColorMap.n) {
                return cm.colorBar[h];
                }
                else {
                    return cm.colorBar[ColorMap.n - 1];
                }
            }
        }
        return cm.colorBar[0];
    }
    
    
    public static class ColorMap {
        static int n = 48;
        Color[] colorBar = new Color[n];
        
        public ColorMap() {
            for(int i = 0; i < n; i++) {  
                colorBar[i] = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            }
        }
        
        public void show(Graphics g, int x) {
            int dy = 30;
            for(int i = 0; i < n; i++) {
                int y = 70 + i * dy;
                g.setColor(colorBar[i]);
                g.fillRect(x, 50 + i * dy, 60, dy - 2);
                g.setColor(Color.WHITE);
                g.drawString("" + i, x + 25, y);
            }
            
        }
    }
    
    public static void randomize() {
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j ++) {
                for(int k = 0; k < 100; k ++){
                    meh[i][j][k] = rand.nextDouble();
                }
            }
        }
    }
    
    public double prob(int m, int e, int h) {
        if(m + h >= 100) {
            return 1.0;
        }
        if(e >= 100) {
            return 0.0;
        }
        return meh[m][e][h];
    }
    public double noRoll(int m, int e, int h) {
        return 1.0 - prob(e, m+h,0);
    }
    public static double[] mul = {0, 0, 0, 0, 1.0/36, 2.0/36, 3.0/36, 4.0/36, 5.0/36, 4.0/36, 3.0/36, 2.0/36, 1.0/36};
    public double roll(int m, int e, int h) {
        double result = (1.0 - prob(e, 0, 0)) / 36;
        result += (1.0 - prob(e, m, 0)) * 10 / 36;
        
        for(int i = 4; i <= 12; i++) {
            result += prob(m, e, h+i) * mul[i];
        }
        return result;
    }
    
    public boolean shouldStop(int m, int e, int h) {
        double r = roll(m, e, h);
        double nr = noRoll(m, e, h);
        meh[m][e][h] = r <=nr? nr : r;//update meh array
        return r <= nr;
    }
    
    public void update(){
        for(int i = 0; i < 5000000; i++) {
            shouldStop(rand.nextInt(100),rand.nextInt(100), rand.nextInt(100));
        }
    }
}
