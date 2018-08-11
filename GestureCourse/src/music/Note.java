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
public class Note extends Mass{
    public Time time;
    public int yIndex; //index of the y line
    public Staff staff;
    public Stem upStem = null;
    public Stem downStem = null;
    
    public Note(int x, int y, Staff staff){
        super(Music.NOTELAYER);
        this.time = staff.sys.times.getTime(x);
        time.notes.add(this);
        this.staff = staff;
        this.yIndex = (y - staff.y + (staff.dy / 2)) / staff.dy;
        add(new Reaction("S-S") {
            public int bid(Stroke s){
               //how close the stroke to top and bottom line
               int y1 = s.vs.loc.y;
               int y2 = s.vs.by();
               int sx = s.vs.mx();
               if(!(getY() >  y1 || getY() < y2)){
                   return UC.noBid;
               }
               if(time.x - staff.dy > sx ||  sx > time.x + 4 * staff.dy){
                   return UC.noBid;
               }
               return 10;
            }
            public void act(Stroke s){
                int y1 = s.vs.loc.y;
                int y2 = s.vs.by();
                int sx = s.vs.mx();
                new Stem(y1,y2,sx,time);                
            } 
        });
    }
    
    public int getY(){
        return yIndex * staff.dy + staff.y;
    }
    
    public void setUpStem(Stem s){
        this.upStem = s;
    }
    public void setDownStem(Stem s){
        this.downStem = s;
    }

    @Override
    public void show(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(time.x,getY()-staff.dy, 3 * staff.dy, 2 * staff.dy);
    }
    
}
