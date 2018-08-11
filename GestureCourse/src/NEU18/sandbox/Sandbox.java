/*
 * Copyright by Marlin Eller 2018
 */
package NEU18.sandbox;

import NEU18.GraphicsLib.Window;
import NEU18.GraphicsLib.react.Ink;
import NEU18.GraphicsLib.react.Mass.Layer;
import NEU18.GraphicsLib.react.Reaction;
import NEU18.GraphicsLib.react.Stroke;
import NEU18.GraphicsLib.react.Stroke.Shape;
import static NEU18.GraphicsLib.react.Stroke.shapeDB;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 *
 * @author Marlin
 */
public class Sandbox extends Window{
  public static Layer CIRC = Circle.circleLayer;

  public static void main(String[] args) {
    PANEL = new Sandbox();
    launch();
    
    Delta d = new Delta(Delta.ZERO, 10);
    Delta g = new Delta(d, 10);
    System.out.println(g.val());
    d.off += 3;
    System.out.println(g.val());
  }
  
  public Sandbox() {
    super("Sandbox", 1000, 800);
  }
  @Override
  protected void paintComponent(Graphics g){
    g.setColor(Color.WHITE); g.fillRect(0, 0, 2000, 2000);
    Ink.buffer.show(g);
    Layer.showAll(g);
  }
  
  @Override
  public void mousePressed(MouseEvent e){
    int x = e.getX(), y = e.getY();
    Ink.buffer.addFirst(e.getX(), e.getY());    
  }
  
  @Override
  public void mouseDragged(MouseEvent e){
    Ink.buffer.addPoint(e.getX(), e.getY());
    Window.PANEL.repaint();
  }
  
  public void mouseReleased(MouseEvent e){
    Stroke s = new Stroke();
    if(s.doit()){Ink.buffer.n = 0;}
    
    System.out.println(s.shape.name);

    //Reaction r = Reaction.bestReaction(s);
    //if(r != null){r.act(s); Ink.buffer.n = 0;}
    Window.PANEL.repaint();
  } 
  
  private interface Val{int val();}
  public static class Delta implements Val{
    public static Val ZERO = new Val(){public int val(){return 0;}};
    public Val root;
    public int off;
    public Delta(Val root, int off){this.root = root; this.off = off;}
    public int val(){return root.val() + off;}
  }
}
