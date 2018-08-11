/*
 * Copyright by Marlin Eller 2018
 */
package NEU18.GraphicsLib.react;

import NEU18.GraphicsLib.G;
import NEU18.GraphicsLib.Window;
import static NEU18.GraphicsLib.Window.PANEL;
import static NEU18.GraphicsLib.Window.launch;
import static NEU18.GraphicsLib.react.Ink.Norm.getNewNorm;
import NEU18.GraphicsLib.react.Stroke.Shape;
import NEU18.GraphicsLib.react.Stroke.Shape.DB;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Scanner;

/**
 *
 * @author Marlin
 */
public class ShapeTrainer extends Window {
  public static boolean buttonAction = false;
  public static boolean selectingName = false;
  public static G.VS nameButton = new G.VS(10,100, 80,40); 
  public static G.VS saveButton = new G.VS(10,170, 80,40); 
  public static String curName = "N-N";
  public static Shape curShape;
  public static Scanner scanner = new Scanner(System.in);
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    PANEL = new ShapeTrainer();
    setCurShape("N-N");
    launch();
  }
  
  public ShapeTrainer() {
    super("Shape Trainer", 1000, 800);
  }
  
  public static void setCurShape(String name){
    curName = name;
    curShape = Stroke.shapeDB.get(curName);
    if(curShape == null){curShape = new Shape(curName);} 
  }
  
  @Override
  protected void paintComponent(Graphics g){
    g.setColor(Color.WHITE); g.fillRect(0, 0, 2000, 2000);
   
    nameButton.showAsButton(g, "Enter Name", Color.pink);
    saveButton.showAsButton(g, "Save DB", Color.pink);
    
    Ink.buffer.show(g);
    
    if(selectingName){
      g.drawString("Please TYPE Name of Shape: ",50,250);
    } else {
      g.drawString("Please DRAW: " + curName,50,250);      
    }
    
    curShape.prototypes.show(g);
  }
  
  @Override
  public void mousePressed(MouseEvent e){
    int x = e.getX(), y = e.getY();
    if(nameButton.hit(x,y)){
      buttonAction = true;
      selectingName = true;
      PANEL.repaint();
      setCurShape(scanner.next()); // fetch string from input
      selectingName = false;
    } else if(saveButton.hit(x,y)){
      DB.saveDB(); 
      buttonAction = true;
    } else {
      Ink.buffer.addFirst(e.getX(), e.getY());
    }    
  }
  
  @Override
  public void mouseDragged(MouseEvent e){
    if(!buttonAction){
      Ink.buffer.addPoint(e.getX(), e.getY());
    }
    Window.PANEL.repaint();
  }
  
  public void mouseReleased(MouseEvent e){
    if(buttonAction){
      buttonAction = false;
    } else {
      setCurShape(curName);
      curShape.prototypes.addOrBlend(getNewNorm());
    }
    //Ink.buffer.n = 0; // clear the ink buffer at end of stroke

    Window.PANEL.repaint();
  }   
}
