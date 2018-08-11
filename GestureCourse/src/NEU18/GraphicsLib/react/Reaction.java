/*
 * Copyright by Marlin Eller 2018
 */
package NEU18.GraphicsLib.react;

import NEU18.GraphicsLib.react.Stroke.Shape;
import static NEU18.GraphicsLib.react.Stroke.shapeDB;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Marlin
 */
public abstract class Reaction implements I.React {
  public Shape shape; // reactions all have a single shpae that they bid for
  private static Map reactionMap = new Map(); // map holds Lists organized by Shape
  //public static List initialReactions = new List();

  public static interface Init {
      public void init();
  }
  private static Init DO_NOTHING = new Init(){public void init(){}};
  public static Init init = DO_NOTHING;
  
  @Override
  public abstract int bid(Stroke s);
  @Override
  public abstract void act(Stroke s);
  
  public Reaction(String shapeName){
    shape =shapeDB.get(shapeName);
    if(shape == null){
      System.out.println("Could not find "+shapeName+" in DB!"); 
      return;  // note: we bail early so this reaction is not added to reactionMap
    }
    List list = reactionMap.get(shape);
    if (list == null) {
      list = new List();
      reactionMap.put(shape, list);
    }
    list.add(this);
  }    

  // several static functions relating to the one single ReactionMap
  public static void add(Reaction r){reactionMap.addReaction(r);}
  public static void remove(Reaction r){reactionMap.removeReaction(r);}
  public static void removeList(Reaction.List rl){reactionMap.removeList(rl);}
  public static void clearAll(){reactionMap.clear();}
  public static void addList(Reaction.List rl){for(Reaction r:rl){add(r);}}
  public static Reaction bestReaction(Stroke s){ 
    List list = reactionMap.get(s.shape);
    if(list != null){return list.bestReaction(s);}
    return null;
  }   
  
  //===== List class - allows us to find best reaction.===============  
  public static class List extends ArrayList<Reaction>{
    // we use the default list constructor
    private Reaction bestReaction(Stroke s){ // return reaction from list with best bid.
      Reaction res = null;
      int bestBid = UC.noBid;
      for (Reaction r : this){int curBid = r.bid(s);
        if (curBid < bestBid){bestBid = curBid; res = r;}
      }
      return res;
    }
  }

  //==== Map class ====================================================
  private static class Map extends HashMap<Shape, List>{ // wrapper class
    private void addReaction(Reaction r){  
      List list = get(r.shape); // see if the list already exists..
      if(list == null){list = new List(); put(r.shape, list);} //..and if not, create it.
      list.add(r); // then add the individual reaction.
    }
    private void removeReaction(Reaction r){get(r.shape).remove(r);}
    private void removeList(Reaction.List rl){
      for(Reaction r : rl){removeReaction(r);}
    }
  }
}
