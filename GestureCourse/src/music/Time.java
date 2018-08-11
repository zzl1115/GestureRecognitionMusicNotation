/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import NEU18.GraphicsLib.react.UC;
import java.util.ArrayList;

/**
 *
 * @author Jessielee
 */
public class Time {
    
    public int x;
    public ArrayList<Note> notes = new ArrayList<>();
    
    public Time(int x){
        this.x = x;
    }
    
    public static class List extends ArrayList<Time>{
    
        public Time getTime(int x){
            Time result = null;
            int bestDistance;
            if(this.isEmpty()){
                Time t = new Time(x);
                this.add(t);
                return t;
            }else{
            result = this.get(0);
            bestDistance = Math.abs(x - result.x);
            for(Time t: this){
                int d = Math.abs(t.x - x);
                if(d < bestDistance){
                    bestDistance = d;
                    result = t;
                }
            }
            }
            // bestDistance between each time set
            
            if (bestDistance < UC.timeTreshold){
                return result;
            } else{
                Time t = new Time(x);
                this.add(t);
                return t;
            }
        
        }
    }
}
