/*
 * copyright Marlin Eller 2018
 */

package NEU18.GraphicsLib.react;

import NEU18.GraphicsLib.G.VS;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Marlin Eller <meller at uw.edu>
 */
public class Area implements I.Hit, I.Show{Color color; VS vs;

  @Override
  public boolean hit(int x, int y) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void show(Graphics g) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  public class Gesture{}
}
