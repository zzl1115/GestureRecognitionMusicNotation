/*
 * Copyright by Marlin Eller 2018
 */
package NEU18.GraphicsLib.react;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Marlin
 */
public abstract class Mass extends Reaction.List implements I.Show{
  public Layer layer; // everything that Shows itself lives in a layer.
  public Mass(Layer layer){this.layer = layer; layer.add(this);}
  // uses the default constructor for the Reaction.List because it is just an ArrayList<Reaction> in a wrapper.
  @Override
  abstract public void show(Graphics g);
  public void removeFromLayers(){
    layer.remove(this); // remove visible mass from layers..
    Reaction.removeList(this); // .. and remove all the reactions as well
  }
  public void addToLayers(){
      layer.add(this);
  }
          
  public static class Layer extends ArrayList<I.Show> implements I.Show{
    public static ArrayList<Layer> all = new ArrayList<>();
    public static void clearAll(){for(Layer lay : all){lay.clear();}}
    public static void showAll(Graphics g){for(Layer lay: all){lay.show(g);}}
  
    public Layer(){all.add(this);} // returns this, but adds it to all as well
    public void show(Graphics g){for(I.Show s : this){s.show(g);}}
  }
}
