/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;


import NEU18.GraphicsLib.react.Mass;
import NEU18.GraphicsLib.react.Reaction;
import NEU18.GraphicsLib.react.Stroke;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Jessielee
 */
public class Sys {
    public Staff.List staffs = new Staff.List();
    public Time.List times = new Time.List();
    public static Sys.Def systemDef = null;
    public static Reaction systemDefBack = new Reaction("E-W"){
        public int bid(Stroke s){
            return 100;
        }
        public void act(Stroke s){
            systemDef.addToLayers();
        }
    };
    public static Reaction createSystem = new Reaction("E-E"){
        public int bid(Stroke s){
            return 100;
        }
        public void act(Stroke s){
            new Sys(s.vs.my());
        }
    };
    
    public Sys(int y){
        if(systemDef.yStaff.size() == 0){
            return;
        }
        int bias = systemDef.yStaff.get(0);
        int index = 0;
        for(Integer sy: systemDef.yStaff){
            staffs.add(new Staff(sy + y - bias,this,index));
            index++;
        }
    }
    
    public int getDy(){
        return staffs.get(0).dy;
    }
    
    public int getYBot(){
        return staffs.get(0).yBot();
    }
    
    public int getY(){
        return staffs.get(0).y;
    }
    public static class Def extends Mass{
        //list of y coords for each staff
        public ArrayList<Integer> yStaff = new ArrayList<>();

        public Def() {
            super(Music.sysDef);
            //add new reactions
            add(new Reaction("E-E"){
                public int bid(Stroke s){
                    return 100;
                }
                public void act(Stroke s){
                    yStaff.add(s.vs.my());
                }
            });
            
            add(new Reaction("DOT") {
                public int bid(Stroke s){
                    return 10;
                }
                public void act(Stroke s){
                    Def.this.removeFromLayers();
                }
            });
            
        }

        @Override
        public void show(Graphics g) {
            g.setColor(Color.PINK);
            g.fillRect(0, 0, 2000, 4000);
            g.setColor(Color.BLACK);
            g.drawString("Define your systems.",120,20);
            for(Integer y: yStaff){
                Staff.showAt(g, y, 5, 10);
            }
        }
           
    }
}
