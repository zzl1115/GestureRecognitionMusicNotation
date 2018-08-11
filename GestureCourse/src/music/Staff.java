/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import NEU18.GraphicsLib.react.I;
import NEU18.GraphicsLib.react.Mass;
import NEU18.GraphicsLib.react.Reaction;
import NEU18.GraphicsLib.react.Stroke;
import NEU18.GraphicsLib.react.UC;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Jessielee
 */
public class Staff extends Mass {
    
    private int nLines = UC.defaultStaffLineCount;
    public int dy = 10; //half distance between each line
    private static int rightMargin = 1500;
    private static int leftMargin = 50;
    public int y;
    public Sys sys;
    
    private boolean measureContinues = false;
    private int index;
    
    
//    public static Reaction createStaff = new Reaction("E-E"){
//        public int bid(Stroke s){
//            return 100;
//        }
//        public void act(Stroke s){
//            new Staff(s.vs.my());          
//        }
//    };
    //static {Reaction.initialReactions.add(createStaff);}
            
    
    public Staff(int y,Sys sys,int index){
        super(Music.BACK);
        this.y = y;
        this.sys = sys;
        this.index = index;
        
        add(new Reaction("S-S") {
            public int bid(Stroke s){
               //how close the stroke to top and bottom line
               int y1 = s.vs.loc.y;
               int y2 = s.vs.by();
               
               if(Math.abs(y1-y) > 20){
                   return UC.noBid;
               }
               if(Math.abs(y2-yBot()) > 20){
                   return UC.noBid;
               }
               return Math.abs(y1-y) + Math.abs(y2-yBot()) + 100;
            }
            public void act(Stroke s){
                new Measure(sys,s.vs.mx());
                
            } 
        });
        
        add(new Reaction("SW-SW"){
            public int bid(Stroke s){
                int yStroke = s.vs.my();
                int yTop = y;
                int yBot = yBot();
                if(yStroke < yTop - 2 * dy){
                    return UC.noBid;
                }
                if(yStroke > yBot + 2 * dy){
                    return UC.noBid;
                }
                if(s.vs.mx() < leftMargin || s.vs.mx() > rightMargin){
                    return UC.noBid;
                }
                return 100;
                
            }
            
            public void act(Stroke s){
                new Note(s.vs.mx(),s.vs.my(),Staff.this);
            }
            
        });
        
    }
    
    @Override
    public void show(Graphics g) {
        g.setColor(Color.black);
        for(int i = 0; i < nLines; i++){
          g.drawLine(leftMargin, y + i * 2 * dy, rightMargin, y + 2 * i * dy);
        }
    }
    
    public static void showAt(Graphics g,int y, int nLines, int dy){
        g.setColor(Color.black);
        for(int i = 0; i < nLines; i++){
            g.drawLine(leftMargin, y + i * 2 * dy, rightMargin, y + 2 * i * dy);
        }
    }
    
    public int yBot(){
        return y + 2 * (nLines-1)*dy;
    }
    
    public static class List extends ArrayList<Staff> {
    
}
}
