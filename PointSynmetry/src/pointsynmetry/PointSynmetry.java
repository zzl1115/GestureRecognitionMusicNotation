/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointsynmetry;

import GraphicsLib.Complex;
  import GraphicsLib.Window;
  import java.awt.Color;
  import java.awt.Graphics;
  import java.awt.Point;
  import java.awt.event.MouseEvent;
import static java.lang.Math.PI;
  import java.util.ArrayList;

  
/**
   *
   * @author meller
   */

  public class PointSynmetry extends Window{
    public ArrayList<Line> lines = new ArrayList<>();
    public boolean draging = false;
    public static Complex center = new Complex(350, 300);
    public static Complex mcenter = new Complex(-350, -300);
    public static Complex rot = new Complex(PI);
/**
     * @param args the command line arguments
     */

    public static void main(String[] args){
      Window.PANEL = new PointSynmetry();
      Window.launch();
    }
  
    public PointSynmetry(){
      super("Paint", 700,600);
    }
    
    public static class Line extends ArrayList<Point>{
      public void draw(Graphics g){
        for(int i = 1; i<size(); i++){
          Point a = get(i-1); Point b = get(i);
          g.drawLine(a.x,a.y,b.x,b.y);
          Complex ca = new Complex(a.x, a.y);
          Complex cb = new Complex(b.x, b.y);

             ca = ca.add(mcenter).mul(rot).add(center);
            cb = cb.add(mcenter).mul(rot).add(center);
            g.drawLine((int)ca.x,(int)ca.y,(int)cb.x,(int)cb.y);

        }
        
      }
    }
    
    @Override
    protected void paintComponent(Graphics g){
      g.setColor(Color.WHITE); g.fillRect(0, 0, 2000, 2000);
      g.setColor(Color.BLACK);
      for(int i = 0; i<lines.size(); i++){
        lines.get(i).draw(g);
      }
    }
    
    @Override
    public void mousePressed(MouseEvent e){
      Line t = new Line();
      t.add(new Point(e.getX(), e.getY()));
      lines.add(t);
    }
     
    @Override
    public void mouseDragged(MouseEvent e){
      lines.get(lines.size() - 1).add(new Point(e.getX(), e.getY()));
      Window.PANEL.repaint();
    }
  
    public void mouseReleased(MouseEvent e){
      System.out.println(lines.get(lines.size() -1).size());
    }
    
  }