package NEU18.GraphicsLib;

import NEU18.GraphicsLib.G.V;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author Marlin Eller <meller at uw.edu>
 */
public class G{
  public static int dotSize = 4;
  public static Color dotColor = Color.RED;
  public static Color lineColor = Color.BLACK;
  
  public static Random RND = new Random(); // a shared random number generator
  public static class V implements Serializable{
    public int x,y;
    public V(int x, int y){this.x = x; this.y = y;}
    public V(){x=0;y=0;}
    public V(V v){x = v.x; y = v.y;}
    public V set(int x, int y){this.x = x; this.y = y; return this;}
    public V set(V v){x = v.x; y = v.y; return this;}
    public V constrain(BBox bb){x = bb.h.constrain(x); y = bb.v.constrain(y); return this;}
    public V add(V delta){x += delta.x; y += delta.y; return this;}
    public void show(Graphics g){
      int hd = dotSize/2, d = dotSize;
      g.setColor(dotColor); 
      g.drawOval(x-hd, y-hd, d, d);
    }
    public int dist(V v){return (x-v.x)*(x-v.x) + (y-v.y)*(y-v.y);}
    public void blend(int na, V b, int nb){ // blend na copies of this with nb of b 
      x = ((x*na) + (b.x*nb))/(na+nb); 
      y = ((y*na) + (b.y*nb))/(na+nb);
    }
  }
  public static class VS implements Serializable{
    public V loc, size;
    public VS(int x, int y, int dx, int dy){
      loc = new V(x,y); size = new V(dx, dy);
    }
    public VS(BBox b){loc = new V(b.h.lo, b.v.lo); size = new V(b.h.s, b.v.s);}
    public void showInColor(Graphics g, Color c){g.setColor(c); g.fillRect(loc.x,loc.y,size.x,size.y);}
    public void showAsButton(Graphics g, String text, Color c){
      showInColor(g, c);
      g.setColor(Color.BLACK);
      FontMetrics fm = g.getFontMetrics();
      int w = fm.stringWidth(text);
      int a = fm.getAscent();
      int d = fm.getDescent();
      g.drawString(text, loc.x + (size.x - w)/2, loc.y + (size.y -a-d)/2 +a);
    }
    public boolean hit(int x, int y){
      return x>=loc.x && x <=(loc.x+size.x) && y>=loc.y && y<=(loc.y + size.y);
    }
    public int mx(){return loc.x + size.x/2;} // return mid x
    public int bx(){return loc.x + size.x;} // return big x
    public int my(){return loc.y + size.y/2;}
    public int by(){return loc.y + size.y;}
  }
  public static class LoHi implements Serializable{ 
    public int lo, hi, s, m; // size (never zero!) & midpoint
    private void sm(){m = (hi+lo)/2; s = hi-lo; if(s==0){s=1;}}
    public LoHi(int min, int max){this.lo = min; this.hi = max; sm();}
    public LoHi(int v){this.lo = v; this.hi = v; sm();}
    public LoHi(LoHi x){lo = x.lo; hi = x.hi; sm();} // clone a LoHi
    public void set(int x){lo = x; hi = x; sm();}
    public void add(int v){if(lo>v){lo = v;} if(hi<v){hi = v;} sm(); } 
    public String toString(){return "["+lo+".."+hi+"]";} 
    public int constrain(int v){if(v<lo){return lo;}; return (v>hi)?hi:v;}
  }
  
  public static class BBox implements Serializable{
    public LoHi h, v; // horizontal and vertical ranges
    public BBox(int x, int y){h = new LoHi(x,x); v = new LoHi(y,y);}
    public BBox(int x1, int y1, int x2, int y2){h = new LoHi(x1,x2); v = new LoHi(y1,y2);}
    public BBox(BBox b){h = new LoHi(b.h); v = new LoHi(b.v);}
    public void show(Graphics g){g.drawRect(h.lo, v.lo, h.s, v.s);}
    public void show(Graphics g, Color c){g.setColor(c); this.show(g);}
    public void draw(Graphics g, Color c){this.show(g,c);}
    public void set(int x, int y){h.set(x); v.set(y);}
    public void add(int x, int y){h.add(x); v.add(y);}
    public void add(V vec){h.add(vec.x); v.add(vec.y);}
    public String toString(){return "H:"+ h + " V:" + v;}
  }
  
  public static class PL implements Serializable{
    public V[] points;
    public PL(int n){points = new V[n]; for(int i=0;i<n;i++){points[i]=new V();}}
    public PL(int N, PL pl, int n){
      points = new V[N];
      int N1 = N-1; int n1 = n-1; // the last points in the two PLs
      for(int i = 0; i<N; i++){
        points[i] = new V(pl.points[i*n1/N1]);
      }
    }
    public void show(Graphics g, int n){
      g.setColor(lineColor);
      for(int i = 1; i<n; i++){
        g.drawLine(points[i-1].x, points[i-1].y, points[i].x, points[i].y);
      }
    }
    public void show(Graphics g){show(g, points.length);}
    public void showDots(Graphics g, int n){
      for(int i = 0; i<n; i++){points[i].show(g);}            
    }
  }
}
