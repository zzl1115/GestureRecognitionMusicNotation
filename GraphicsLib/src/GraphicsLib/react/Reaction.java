/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicsLib.react;

import GraphicsLib.react.Stroke.Shape.DB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author DarKz
 */
public abstract class Reaction implements I.React{
    
    public Map<Shape, List> byShape = new HashMap<>();

    public Reaction(String shapeName) {
        Stroke.Shape s = Stroke.theShapeDB.byName.get(shapeName);
        List list = byShape.get(s);
        if(list == null) {
            list = new List();
            byShape.put(s, list);
            
        }
        list.add(this);
    }
    
    public static Reaction bestReaction(Stroke s) {
        return byShape.get(s.shape).bestReaction(s);
    }
    
    @Override
    public abstract int bid(Stroke s);
    
    @Override
    public abstract void act(Stroke s);
    
    public static class List extends ArrayList<Reaction> {
        public Reaction bestReaction(Stroke s) {
            Reaction res = null;
            int bestBid =UC.noBid;
            for(Reaction r : this) {
                int curBid = r.bid(s);
                if(curBid < bestBid) {
                    bestBid = curBid;
                    res = r;
                }
            }
            return res;
        }
    }
    
}
