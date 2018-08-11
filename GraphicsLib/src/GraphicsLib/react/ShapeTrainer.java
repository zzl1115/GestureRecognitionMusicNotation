/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicsLib.react;

import GraphicsLib.G.PL;
import GraphicsLib.G.V;
import GraphicsLib.G.VS;
import GraphicsLib.Window;
import GraphicsLib.react.Ink;
import GraphicsLib.react.Ink.Norm;
import GraphicsLib.react.Stroke;
import GraphicsLib.react.Stroke.Shape;
import GraphicsLib.react.Stroke.Shape.DB;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
/**
 *
 * @author DarKz
 */
public class ShapeTrainer extends Window{
    public static ArrayList<Ink> inkList = new ArrayList<>();
    public static Norm.List normList = new Norm.List();
    //Feb1
        public static final int buttonWidth = 100;
    public static final int buttonHeight = 50;
    public static final int buttonMargin = 10;
    public static VS noButton = new VS(buttonMargin, buttonMargin, buttonWidth, buttonHeight);
    public static VS oopButton = new VS(buttonMargin, buttonMargin * 2 + buttonHeight, buttonWidth, buttonHeight);
    public static boolean buttonClick;
    public static Norm lastNorm;
    public static int nString = 100;
    public static String[] strings = {"N-N", "S-S", "N-E", "N-E", "N-E"};
    
    public void train(String name){
        
    }
    
      public ShapeTrainer() {
      super("ShapeTrainer", 1000, 800);
    }
    
/**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
      PANEL = new ShapeTrainer();
      launch();
    }
    
  
    
    @Override
    protected void paintComponent(Graphics g){
      g.setColor(Color.WHITE); g.fillRect(0, 0, 2000, 2000);
//      g.setColor(Color.RED);
//      g.drawRect(10, 10, 100, 100);

      noButton.showButton(g, Color.PINK, "No");
      oopButton.showButton(g, Color.GRAY, "Oop");
      for(Ink ink : inkList){ink.show(g);}

      
      Ink.buffer.show(g);
//      for(Ink ink : inkList){ink.show(g);}
      

      Ink.buffer.box.show(g);
      if(Ink.buffer.n > 0){
        Norm norm = new Norm();
        VS vs = new VS(10,10,100,100);
        norm.showAt(g, vs);
      }
      

//        normList.showList(g);

        if(nString < strings.length){
            g.drawString("Please draw the shape: " + strings[nString], 100, 100);
            
        }
    }
  
    @Override
    public void mousePressed(MouseEvent e){
//      Ink.buffer.addFirst(e.getX(), e.getY());

//Feb1
        int x = e.getX();
        int y = e.getY();
        if(noButton.hit(x, y)){
            inkList.get(inkList.size() - 1).norm = lastNorm;
            normList.add(lastNorm);
            lastNorm = null;
            System.out.println("No");
            buttonClick = true;
        } else if(oopButton.hit(x, y)){
            System.out.println("Yes");
            buttonClick = true;
        } else {
            if(lastNorm != null) {
                Norm best = normList.bestMatch(lastNorm);
                 best.blend(lastNorm);
            }
            Ink.buffer.addFirst(x, y);
            buttonClick = false;
        }

    }
  
    @Override
    public void mouseDragged(MouseEvent e){
      Ink.buffer.addPoint(e.getX(), e.getY());
      Window.PANEL.repaint();
    }
  
    public void mouseReleased(MouseEvent e){
//      inkList.add(new Ink());
//      System.out.println(Ink.buffer.n);//shows number of points in the drawn stroke
      //in output window below
      //buffer:300
      
      //get last two strokes, print out the total distance of them 
      //sum of the distance between each point on the strokes
//      if(inkList.size() > 1){
//          Ink i1 = inkList.get(inkList.size()-1);
//          Ink i2 = inkList.get(inkList.size()-2);
//          System.out.println(i1.norm.dist(i2.norm));
//      }
//      
//      Norm norm = new Norm();
//      normList.addDiff(norm);

////Feb1
//        if(!buttonClick) {
//           inkList.add(new Ink());
//           int last = inkList.size() - 1;
//           Ink lastInk = inkList.get(last);
//           lastNorm = lastInk.norm;
//           //replace the ink with the best match
//           Norm best = normList.bestMatch(lastNorm);
//           
//           if(best != null) {
//               lastInk.norm = best;
//           } else {
//               normList.add(lastNorm);
//           }
//           Ink.buffer.n = 0;
//        }
//                PANEL.repaint();
                
            if(nString < strings.length) {
                Shape s = new Shape(strings[nString]);
                s.prototypes.add(new Norm());
                nString++;
                
            }  else {
                Stroke stroke = new Stroke();
                System.out.println(stroke.shape.name + " " + Stroke.lastStrokeDistance);
                if(nString == strings.length) {
                    //DB.saveDB();
                    nString ++;
                }
            } 
            PANEL.repaint();

    } 
    
}
