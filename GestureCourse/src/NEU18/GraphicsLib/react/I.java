package NEU18.GraphicsLib.react;

import java.awt.Graphics;

public interface I {
  public interface Act{public void act(Stroke s);}
  public interface React extends Act{public int bid(Stroke s);}
  public interface Show{public void show(Graphics g);}
  public interface Hit{public boolean hit(int x, int y);}
}
