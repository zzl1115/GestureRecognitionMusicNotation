/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import NEU18.GraphicsLib.react.Mass;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Jessielee
 */
public class Stem extends Mass{
    private ArrayList<Note> notes = new ArrayList<>();
    private boolean isUpStem;
    
    public Stem(int y1, int y2,int x, Time time){
        super(Music.NOTELAYER);
        for(Note note : time.notes){
            if(y1 < note.getY() && y2 > note.getY()){
                notes.add(note);
                if(time.x < x){
                    note.setUpStem(this);
                    this.isUpStem = true;
                }else{
                    note.setDownStem(this);
                    this.isUpStem = false;
                }
            };
        }
        
    }

    @Override
    public void show(Graphics g) {
        int yLow = notes.get(0).getY();
        int yHigh = notes.get(0).getY();
        Time time = notes.get(0).time;
        int x = time.x;
        int dy = notes.get(0).staff.dy;
        
        for(Note note : notes){
            int y = note.getY();
            if(y < yLow){
                yLow = y;
            }
            if(y > yHigh){
                yHigh = y;
            }
        }
        
        g.setColor(Color.BLACK);
        if(isUpStem){
            g.drawLine(x + 3 * dy, yHigh, x + 3 * dy, yLow - 7 * dy);
        }else{
            g.drawLine(x, yLow, x, yHigh + 7 * dy);
        }
    }
}
