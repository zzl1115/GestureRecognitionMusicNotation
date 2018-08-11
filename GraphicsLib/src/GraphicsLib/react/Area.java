/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicsLib.react;

import GraphicsLib.G.VS;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author DarKz
 */
public class Area implements I.Hit{
    public VS vs;
    public Color color;
    public static List all = new List();
    public static Area active;
    public static final Area DEFAULT = null; 
    public static boolean inking;
    
    
    public Area(VS vs, Color color) {
        this.vs = vs;
        this.color = color;
    }

    @Override
    public boolean hit(int x, int y) {
   //     return vs.contains(x, y);
    return false;
    }
    
    public static class List extends ArrayList<Area> {
        public void setActive(int x, int y) {
            active = DEFAULT;
            for(Area a: this) {
                if(a.hit(x, y)) {
                    active = a;
                }
            }
        }
    }
    
}
