/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import NEU18.GraphicsLib.react.Mass;
import NEU18.GraphicsLib.react.Reaction;
import NEU18.GraphicsLib.react.Stroke;
import NEU18.GraphicsLib.react.UC;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Jessielee
 */
public class Measure extends Mass {
    private int x;
    private Sys sys;
    private int style = Reg;//three dif styles of measure
    private static int Reg = 1;
    private static int Two = 2;
    private static int Fine = 4;
    private static int RepeatLeft = 8;
    private static int RepeatRight = 16;
    
    public Measure(Sys sys,int x){
        super(Music.BACK);
        this.sys = sys;
        this.x = x;
        
        add(new Reaction("S-S"){
            public int bid(Stroke s){
               int y1 = s.vs.loc.y;
               int y2 = s.vs.by();
               int sX = s.vs.mx();
            
               if(Math.abs(x - sX) > 20){
                   return UC.noBid;
               }
               if(y2 < sys.getY() || y2 > sys.getYBot()){
                   return UC.noBid;
               }
               return Math.abs(x - sX);
            }
            
            public void act(Stroke s){
                cycleStyle();
            }
        });
        
        add(new Reaction("DOT"){
            public int bid(Stroke s){
               int y1 = s.vs.loc.y;
               int y2 = s.vs.by();
               int sX = s.vs.mx();
            
               if(Math.abs(x - sX) > 20){
                   return UC.noBid;
               }
               if(y2 < sys.getY() || y2 > sys.getYBot()){
                   return UC.noBid;
               }
               return Math.abs(x - sX);
            }
            
            public void act(Stroke s){
                if(s.vs.mx() < x){
                    toggleLeft();
                }else{
                    toggleRight();
                }
            }
        });
    }

    public void cycleStyle(){
        if(style <= 4){
           style *= 2;
           if(style == RepeatLeft){
               style = Reg;
           }
           System.out.println("the style is " + style);
        }
    }
    
    public void toggleLeft(){
        style ^= RepeatLeft;
    }
    
    public void toggleRight(){
        style ^= RepeatRight;
    }

    @Override
    public void show(Graphics g) {
        Staff.List staffList = sys.staffs;
        g.setColor(Color.BLACK);
        int dy = sys.getDy();
        for(Staff s: staffList){
            int y1 = s.y;
            int y2 = s.yBot();
            if(style <= Fine){
                if(style == Reg){
                    g.drawLine(x, y1, x,y2);
                }else if(style == Two){
                    g.drawLine(x,y1,x,y2);
                    g.drawLine(x-dy, y1, x-dy, y2);
                }else if(style == Fine){
                    g.drawLine(x-dy,y1,x-dy,y2);
                    g.fillRect(x, y1, dy, y2-y1);
                }
            }else {
                g.fillRect(x, y1, dy, y2-y1);
                int my = (y1 + y2)/2;
                if((RepeatLeft & style) != 0){
                    g.drawLine(x-dy,y1,x-dy,y2);
                    g.fillOval(x-2*dy, my-dy, 3, 3);
                    g.fillOval(x-2*dy, my+dy, 3, 3);
                }
                if((RepeatRight & style) != 0){
                    g.drawLine(x+2*dy,y1,x+2*dy,y2);
                    g.fillOval(x+3*dy, my-dy, 3, 3);
                    g.fillOval(x+3*dy, my+dy, 3, 3);
                }
            }   
            
        }
    }
    
    
}
