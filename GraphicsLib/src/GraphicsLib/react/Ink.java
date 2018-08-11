/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicsLib.react;

import GraphicsLib.G.BBox;
import GraphicsLib.G.LoHi;
import GraphicsLib.G.PL;
import GraphicsLib.G.V;
import GraphicsLib.G.VS;
import java.awt.Graphics;
import GraphicsLib.react.Ink.Norm;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author DarKz
 */
public class Ink implements I.Show{  
    public Norm norm; 
    public VS vs;
    public static final Buffer buffer = new Buffer();
    
    public Ink(){norm = new Norm(); vs = new VS(Ink.buffer.box);}
    
    public static class Norm extends PL implements Serializable{
      public int nBlend = 1;
        
      private static final int N = UC.normSize;
      public static V temp = new V(), prev = new V();
      public Norm(){
        super(N, Ink.buffer, Ink.buffer.n);
        LoHi h = Ink.buffer.box.h, v = Ink.buffer.box.v; // fetch out the two scales
        int s = (h.s > v.s)? h.s : v.s; // select the larger delta as a scale
        for(int i = 0; i<N; i++){
          V p = points[i];
          p.set((p.x - h.m)*UC.normCoord/s,(p.y - v.m)*UC.normCoord/s);
        }
      }
      
      public void blend (Norm n) {
          for(int i = 0; i < N; i++) {
              //update a point with the averaged value of cordinator/
              int newX = (points[i].x * nBlend + n.points[i].x)/(nBlend + 1);
              int newY = (points[i].y * nBlend + n.points[i].y)/(nBlend + 1);
              points[i].set(newX, newY);
          }
          nBlend += 1;
      }
      
      public static class List extends ArrayList<Norm> {
          public void addDif(Norm n) {
              int bestMatch = UC.noMatch;
              for(Norm norm : this) {
                  if(norm.dist(n) < bestMatch) {
                      bestMatch = norm.dist(n);
                  }  
              }
              if(bestMatch == UC.noMatch) {
                  this.add(n);
              }
          }
          
          public void showList(Graphics g) {
              int dx = 100;
              VS vs = new VS(105, 10, dx, dx);
              for(Norm n : this) {
                  n.showAt(g, vs);
                  g.drawString(""+ n.nBlend, vs.loc.x, vs.loc.y);
                  vs.loc.x += dx + 5;
              }
          }
          
          public Norm bestMatch(Norm norm) {
              if(this.size() == 0) {
                  return null;
              }
              Norm result = get(0);
              int bestSoFar = norm.dist(result);
              for(Norm n : this) {
                  int d = n.dist(norm);
                  if(d < bestSoFar) {
                      bestSoFar = d;
                      result = n;
                  }
              }
              return result;
          }
      }
    
      public void showDotsAt(Graphics g, VS vs){
        int s = (vs.size.x > vs.size.y) ? vs.size.x : vs.size.y;
        int mx = vs.loc.x + vs.size.x/2, my = vs.loc.y + vs.size.y/2;
        for(int i = 0; i<N; i++){
          V p = points[i];
          temp.set(p.x*s/UC.normCoord + mx, p.y*s/UC.normCoord + my);
          temp.show(g);
        }
      }
    
      public void showAt(Graphics g, VS vs){
        int s = (vs.size.x > vs.size.y) ? vs.size.x : vs.size.y;
        int mx = vs.loc.x + vs.size.x/2, my = vs.loc.y + vs.size.y/2;
        for(int i = 0; i<N; i++){
          V p = points[i];
          temp.set(p.x*s/UC.normCoord + mx, p.y*s/UC.normCoord + my);
          if(i>0){g.drawLine(prev.x, prev.y, temp.x, temp.y);}
          prev.set(temp);
        }
      }
      
      public int dist(Norm n) {
          int res = 0;
          for(int i = 0; i < N; i++) {
              res += this.points[i].dist(n.points[i]);
          }
          return res;
      }
      
      
    }  
     
    public static class Buffer extends PL implements I.Show{ 
      public final BBox box = new BBox(0,0);
      public int n;
      private static final int N = UC.inkBufferSize;
      private Buffer(){super(N); n = 0;}
       
      @Override
      public void show(Graphics g) {this.show(g,n);}
  
      public void addFirst(int x, int y){
        points[0].x = x; points[0].y = y;
        n=1;
        box.set(x, y);
      }
  
      public void addPoint(int x, int y){
        if(n<N){
          points[n].x = x; points[n++].y = y;
          box.add(x, y);
        }
      }
    }
  
    @Override
    public void show(Graphics g) {norm.showAt(g, vs); }
}
