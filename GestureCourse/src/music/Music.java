/*
 * Copyright by Marlin Eller 2018
 */
package music;

import NEU18.GraphicsLib.Window;
import static NEU18.GraphicsLib.Window.PANEL;
import static NEU18.GraphicsLib.Window.launch;
import NEU18.GraphicsLib.react.Ink;
import NEU18.GraphicsLib.react.Mass.Layer;
import NEU18.GraphicsLib.react.Reaction;
import NEU18.GraphicsLib.react.Reaction.Init;
import NEU18.GraphicsLib.react.Stroke;
import NEU18.GraphicsLib.react.Stroke.Shape;
import static NEU18.GraphicsLib.react.Stroke.shapeDB;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import static music.Sys.createSystem;
import static music.Sys.systemDefBack;

/**
 *
 * @author Marlin
 */
public class Music extends Window implements Init{
  public static Layer BACK = new Layer();
  public static Layer NOTELAYER = new Layer();
  public static Layer sysDef = new Layer();
  public Init init;

  public static void main(String[] args) {
    Music music = new Music();
    PANEL = music;
    Reaction.init = music;
    music.init();
    //Reaction r = Staff.createStaff;
    Reaction r = Sys.systemDefBack;
    launch();
  }
  
  public Music() {
    super("Music", 1000, 800);
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

    @Override
    public void init() {
        Sys.systemDef = new Sys.Def();
        Reaction.add(systemDefBack);
        Reaction.add(createSystem);
        
    }
}

