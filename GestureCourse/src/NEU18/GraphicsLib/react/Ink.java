package NEU18.GraphicsLib.react;

import NEU18.GraphicsLib.G.BBox;
import NEU18.GraphicsLib.G.LoHi;
import NEU18.GraphicsLib.G.PL;
import NEU18.GraphicsLib.G.V;
import NEU18.GraphicsLib.G.VS;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
  
public class Ink implements I.Show{ 
  public Norm norm; 
  public VS vs;
  public static final Buffer buffer = new Buffer();
  
  public Ink(){norm = new Norm(); vs = new VS(Ink.buffer.box);}
  
  public static class Norm extends PL implements Serializable{
    public int nBlend = 1; // number of shapes blended to create this Norm
    
    private static final int N = UC.normSize;
    
    public static V temp = new V(), prev = new V();

    public static Norm DOT = new Norm(true);
    private Norm(boolean t){ super(N);} // this constructor only for DOT
    
    public static Norm getNewNorm(){
      BBox b = buffer.box;
      if(b.h.s < UC.dotSize && b.v.s < UC.dotSize){return DOT;}
      return new Norm(); // otherwise call the private constructor.
    }
    
    private Norm(){
      super(N, Ink.buffer, Ink.buffer.n);
      LoHi h = Ink.buffer.box.h, v = Ink.buffer.box.v; // fetch out the two scales
      int s = (h.s > v.s)? h.s : v.s; // select the larger delta as a scale
      for(int i = 0; i<N; i++){
        V p = points[i];
        p.set((p.x - h.m)*UC.normCoord/s,(p.y - v.m)*UC.normCoord/s);
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
    
    public int dist(Norm norm){
      if(this == DOT && norm == DOT){return 0;}
      if(this == DOT || norm == DOT){return UC.differentNormDist;}
      int res = 0;
      for(int i = 0; i<N; i++){res += points[i].dist(norm.points[i]);}
      return res;
    }

    public void blend(Norm n){
      if(this == DOT && n == DOT){return;}
      for(int i = 0; i<N; i++){
        points[i].blend(nBlend, n.points[i], n.nBlend);
      }
      nBlend += n.nBlend; // updated the blend to reflect the size of the blend
    }
    
    public static class List extends ArrayList<Norm> implements Serializable{
      public static int bestMatchDistance;
      public void show(Graphics g){
        int dx = 40;
        VS vs = new VS(10,10,dx,dx);
        for(Norm n : this){
          g.drawString(""+n.nBlend, vs.loc.x, 10);
          n.showAt(g,vs); 
          vs.loc.x += dx + 5;
        }
      }
      
      public Norm bestMatch(Norm norm){ // side effect: calculates bestMatchDistance
        bestMatchDistance = Integer.MAX_VALUE;
        Norm bestNorm = null;
        for(Norm n : this){
          int d = norm.dist(n);
          if(d < bestMatchDistance){bestMatchDistance = d; bestNorm = n;}
        }
        return bestNorm;
      }
      
      /* if distance is far from every element on list this
         this routine will add, otherwise it will blend with
         the closest match.
      */
      public void addOrBlend(Norm norm){
        if(norm == DOT){return;} // neither add nor blend DOT
        Norm best = bestMatch(norm);
        if(best != null && bestMatchDistance < UC.differentNormDist){
          best.blend(norm);
        } else {
          add(norm);
        }
      }
    } // end Norm.List
  }//end Norm  
     
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
  }//end Buffer
  
  @Override
  public void show(Graphics g) {norm.showAt(g, vs); }
}

