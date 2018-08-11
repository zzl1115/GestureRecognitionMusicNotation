/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author nancy
 */
public class ColorSelector {
    
    public static Random rand = new Random();
    
    private int x, y;
    
    private int dx, dy;
    
    public static int size = 20;
    public Color color = Color.BLACK;
    public static ColorSelector.List all = new List();
    
    public ColorSelector(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.dx = rand.nextInt(50);
        this.dy = rand.nextInt(50);
        this.color = color;
        
        all.add(this);
        
        
    }
    
    public static void showAll(Graphics g) {
        all.show(g);
        
    }
    
    public static Color getColor(int x, int y) {
        return all.getColor(x, y);
    }
    
    public static void randomizeColor() {
        for(ColorSelector cs: all) {
            cs.color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        }
    }
    
    
    public static class List extends ArrayList<ColorSelector> {
        public void show(Graphics g) {
            
            for(ColorSelector cs: this) {
                cs.show(g);
            }
        }
        
        public void update() {
            
            for(ColorSelector cs: this) {
                cs.update();
            }
        }
        
        public Color getColor(int x, int y) {
            for(ColorSelector cs: this) {   
                if(cs.hit(x, y)) {
                  return cs.color;
                }
            }
            return null;
        }
        
    }
    
    public void show(Graphics g) {
        g.setColor(this.color);
        g.fillRect(x, y, size, size);
    }
    
    public void update() {
        this.x += this.dx;
        this.y += this.dy;
        
        if(x < 0) {dx = -dx;}
        if(y < 0) {dy = -dy;}
        if(x > 500) {dx = -dx;}
        if(y > 500) {dy = -dy;}
    }
    
    public boolean hit(int x, int y) {
        boolean result = this.x < x && this.x + this.size > x && 
               this.y < y && this.y + this.size > y;
        if(result && this == all.get(0)){
            randomizeColor();
        }
        return result;
    }
}
