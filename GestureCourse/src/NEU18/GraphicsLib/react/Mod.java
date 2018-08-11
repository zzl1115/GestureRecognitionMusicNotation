/*
 * Copyright by Marlin Eller 2018
 */
package NEU18.GraphicsLib.react;

import NEU18.GraphicsLib.G.BBox;
import NEU18.GraphicsLib.G.V;
import NEU18.GraphicsLib.G.VS;
import java.awt.Graphics;

/**
 *
 * @author Marlin
 */
abstract public class Mod implements I.Show, I.Hit{
  BBox range; // bounds on cur
  V cur; // current (x,y) manipulated by handle
  VS h; // handle (x,y) manipulated by cur
  V dMC; // add this to mouseXY to get cur (which must be folded into range)
  V dCH; // add this to cur to get h

  /* Are used to modify existing integer values, in particular x and y coordinates.
   * You start a mod off by telling it the initial x,y Value (xB,yB). You tell it
   * where you want it to draw the little box handle that the user can click on.
   * You do this by supplying (xH,xB) - You may not want the box to
   * sit directly on top of the point (xB,xY) that you are moving around.
   * To prevent you from taking values out of an acceptable range, you also supply
   * limits on how high and low the coordinates of the point you are editing are
   * allowed to go.
   *
   * Note: You can of course limit one of the directions completely like
   * by setting yLo and yHi to a single value. This turns the mod into a handle
   * that can ONLY change the x coordinate. Because this is SO common we
   * supply two subordiante classes Mod.X and Mod.Y that allow you to only
   * modify either an X coordinate or a Y coordinate.
   *
   * Since you almost always have some x or y coordinate stored as an integer 
   * member in some class, and since java does not support pointers we use
   * the following method to construct handles for some member integer.
   *
   * suppose you have an element of type Circle and it has an int radius that
   * you want to adjust. You would create a Mod like this:
   *
   * Mod m = new Mod.X(radius, 5, 200, X, Y){public mod(){radius = val();}
   *
   * if this mod is created in a Circle context the function mod will have
   * access to a circle's radius and can update it.
   * 
   *
   */
  public Mod(
          int xLo, int xHi, int yLo, int yHi, // limits on edited value 
          int xC, int yC, // current value
          int xH, int yH  // location of handle rect
  ){
    range = new BBox(xLo, yLo, xHi, yHi);
    cur = new V(xC,yC); // for drawing Handle
    h = new VS(xH,yH,UC.HandleSize, UC.HandleSize);
    dCH = new V(xH - xC, yH - yC); // converts C to H
    dMC = new V(); // can't initialize this until mouse hit
  }
  
  public void show(Graphics g){h.showInColor(g, UC.HandleColor);}
  
  @Override
  public boolean hit(int x, int y){
    boolean res = h.hit(x,y);
    if(res){
      dMC.set(cur.x - x, cur.y - y);
    }
    return res;
  }
  
  public void move(int x, int y){
    cur.set(x,y).add(dMC);
  }
 
  
  
}
