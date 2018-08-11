/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicsLib;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author DarKz
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
      public void set(int x, int y){this.x = x; this.y = y;}
      public void set(V v){x = v.x; y = v.y;}
      public void show(Graphics g){
        int hd = dotSize/2, d = dotSize;
        g.setColor(dotColor); 
        g.drawOval(x-hd, y-hd, d, d);
      }
      
      public int dist(V v) {
          return (this.x - v.x) * (this.x - v.x) + (this.y - v.y) * (this.y - v.y);
      }
    }
      
    public static class VS{
      public V loc, size;
      public VS(int x, int y, int dx, int dy){
        loc = new V(x,y); size = new V(dx, dy);
      }
      public VS(BBox b){loc = new V(b.h.lo, b.v.lo); size = new V(b.h.s, b.v.s);}
      public void showButton(Graphics g, Color c, String text) {
          
          g.setColor(c);
          g.fillRect(loc.x, loc.y, size.x, size.y);
           FontMetrics fm = g.getFontMetrics();     
            int w = fm.stringWidth(text);     
            int a = fm.getAscent();     
            int d = fm.getDescent();   
          g.setColor(Color.BLACK);
          g.drawString(text, loc.x + (size.x - w)/2, loc.y + (size.y - (a+d))/2 + a);
      }
      public boolean hit(int x, int y) {
          return (loc.x <= x) && (loc.y <= y) && (x <= (loc.x + size.x)) &&(y<=(size.y + loc.y));
      }
    }
  
    public static class LoHi{ 
      public int lo, hi, s, m; // size (never zero!) & midpoint
      private void sm(){m = (hi+lo)/2; s = hi-lo; if(s==0){s=1;}}
      public LoHi(int min, int max){this.lo = min; this.hi = max; sm();}
      public LoHi(int v){this.lo = v; this.hi = v; sm();}
      public LoHi(LoHi x){lo = x.lo; hi = x.hi; sm();} // clone a LoHi
      public void set(int x){lo = x; hi = x; sm();}
      public void add(int v){if(lo>v){lo = v;} if(hi<v){hi = v;} sm(); } 
      public String toString(){return "["+lo+".."+hi+"]";} 
    }
    
    public static class BBox{
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
