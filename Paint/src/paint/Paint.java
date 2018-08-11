/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;
  
  import GraphicsLib.Window;
  import java.awt.Color;
  import java.awt.Graphics;
  import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
  import java.awt.event.MouseEvent;
  import java.util.ArrayList;
import javax.swing.Timer;
  
  
/**
   *
   * @author meller
   */

  public class Paint extends Window implements ActionListener{
    private ArrayList<Line> lines = new ArrayList<>();
    private boolean draging = false;
    private Color currentColor = Color.BLACK;
    
/**
     * @param args the command line arguments
     */

    public static void main(String[] args){
      Window.PANEL = new Paint();
      Timer timer = new Timer(100, (ActionListener)PANEL);
      timer.setInitialDelay(2000);
      timer.start();
      Window.launch();
    }
  
    public Paint(){
      super("Paint", 700,600);
      new ColorSelector(10, 10, Color.RED);
      new ColorSelector(10, 40, Color.GREEN);
      new ColorSelector(10, 70, Color.BLUE);
    }
    
    public static class Line extends ArrayList<Point>{
        private Color color;
        
        public Line(Color color) {
            this.color = color;
        }
        
        public void draw(Graphics g){
            g.setColor(this.color);
            
          for(int i = 1; i<size(); i++){
            Point a = get(i-1); Point b = get(i);
            g.drawLine(a.x,a.y,b.x,b.y);
          }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g){
      
        
      g.setColor(Color.WHITE); g.fillRect(0, 0, 2000, 2000);
      
      /*g.setColor(Color.RED); g.fillRect(50, 50, 100, 100);
      g.setColor(Color.GREEN); g.fillRect(50, 200, 100, 100);
      g.setColor(Color.BLUE); g.fillRect(50, 350, 100, 100);
      */
      ColorSelector.showAll(g);
    

      for(int i = 0; i<lines.size(); i++){
        g.setColor(lines.get(i).color);
        lines.get(i).draw(g);
      }
    }
    
    /*public Color getColor(MouseEvent e) {
        Color curColor = Color.BLACK;
        if (e.getX() >= 50 && e.getX() <= 150 && e.getY() >= 50 && e.getY() <= 150) {
            curColor  = Color.RED;
        }
        if (e.getX() >= 50 && e.getX() <= 150 && e.getY() >= 200 && e.getY() <= 300) {
            curColor  = Color.GREEN;
        }
        if (e.getX() >= 50 && e.getX() <= 150 && e.getY() >= 350 && e.getY() <= 450) {
            curColor  = Color.BLUE;
        }
        return curColor;
    }
    */
    //@Override
    /* public void mouseClicked(MouseEvent e) {
        currentColor = getColor(e);
    }
    */

    
    @Override
    public void mousePressed(MouseEvent e){
      Color c = ColorSelector.getColor(e.getX(), e.getY());
      
      if(c != null) {
          currentColor = c;
      }
      Line t = new Line(currentColor);
      t.add(new Point(e.getX(), e.getY()));
      lines.add(t);
      Window.PANEL.repaint();
    }
     
    @Override
    public void mouseDragged(MouseEvent e){
      lines.get(lines.size() - 1).add(new Point(e.getX(), e.getY()));
      
    }
  
    public void mouseReleased(MouseEvent e){
      System.out.println(lines.get(lines.size() -1).size());
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        ColorSelector.all.update();
      this.repaint();
    }
    
  }