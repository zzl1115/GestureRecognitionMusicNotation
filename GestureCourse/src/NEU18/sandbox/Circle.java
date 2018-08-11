/*
 * Copyright by Marlin Eller 2018
 */
package NEU18.sandbox;

import NEU18.GraphicsLib.G.V;
import NEU18.GraphicsLib.react.Mass;
import NEU18.GraphicsLib.react.Reaction;
import NEU18.GraphicsLib.react.Stroke;
import NEU18.GraphicsLib.react.UC;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Marlin
 */
public class Circle extends Mass{
  public static Layer circleLayer = new Layer();
  
  public static Reaction newCircle = new Reaction("O"){
    public int bid(Stroke s){
      return 100;
    }
    public void act(Stroke s){
      int dx = (s.vs.size.x + s.vs.size.y)/2; // diameter
      new Circle(s.vs.mx(), s.vs.my(), dx/2);
    }
  };
  static{ Reaction.add(newCircle);}
  
  public V center;
  public int radius;
  public boolean blue;
  
  public Circle(int x, int y, int r){
    super(circleLayer);
    center = new V(x,y); radius = r;
    
    add(new Reaction("S-S"){ // resize
      public int bid(Stroke s){return xDist(s.vs.loc.x);}
      public void act(Stroke s){radius = xDist(s.vs.loc.x);}
    });
    add(new Reaction("S-N"){ // delete
      public int bid(Stroke s){
        int sx = s.vs.loc.x; int sy = s.vs.loc.y;
        int t = Math.abs(center.x-sx) + Math.abs(center.y-sy);
        return t < 100? t : UC.noBid;
      }
      public void act(Stroke s){Circle.this.removeFromLayers();}    
    });
  
    add(new Reaction("DOT"){
      public int bid(Stroke s){
        int sx = s.vs.loc.x; int sy = s.vs.loc.y;
        int d = Math.abs(center.x-sx) + Math.abs(center.y-sy);
        return (d>radius) ? UC.noBid : d;
      }
      public void act(Stroke s){blue ^= true;} // toggle boolean blue    
    }); 
  }
   
  public int xDist(int x){return Math.abs(x - center.x);}
  
  @Override
  public void show(Graphics g) {
    g.setColor(blue?Color.blue:Color.red);
    g.fillOval(center.x-radius, center.y-radius, 2*radius, 2*radius);
  }
}
