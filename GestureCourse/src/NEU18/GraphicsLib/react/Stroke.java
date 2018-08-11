package NEU18.GraphicsLib.react;
import NEU18.GraphicsLib.G.VS;
import NEU18.GraphicsLib.react.Ink.Norm;
import static NEU18.GraphicsLib.react.Ink.Norm.getNewNorm;
import NEU18.GraphicsLib.react.Mass.Layer;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Stroke {
  public static Shape.DB shapeDB = Shape.DB.loadDB(); 
  
  public Shape shape; 
  public VS vs;
  
  public Stroke(){
    shape = shapeDB.find(getNewNorm());
    vs = new VS(Ink.buffer.box);
  }
  
  public boolean doit(){ // see if Stoke.shape is UNDO - true if stroke recognized
    return Undo.list.process(this);
  }
  
  boolean redo(){ // does one simple stroke that is NOT UNDO - true if recognized
    Reaction r = Reaction.bestReaction(this);
    if(r != null){r.act(this);}
    return (r != null); // so we can add to undo list if non-null and first time
  }
  
  //==========Undo============================
  public static class Undo extends ArrayList<Stroke>{
    public static Shape UNDO = shapeDB.get("N-N");
    public static Undo list = new Undo();
    
    boolean process(Stroke s){ // return true if recognized.
      if(s.shape != UNDO){if(s.redo()){list.add(s); return true;} return false;}
      if(list.size() > 0){
        list.remove(list.size() - 1);
        Layer.clearAll(); // remove all visible things
        Reaction.clearAll(); // remove all reactions
        Reaction.init.init();
      //  Reaction.addList(Reaction.initialReactions);
        for(Stroke k:list){k.redo();} // redo the list
      }
      return true;
    }
  } 
  
  //===========Shape=========================================
  public static class Shape implements Serializable{
    public String name; 
    public Norm.List prototypes = new Norm.List();
    
    public static Shape DOT = new Shape(true);
    private Shape(boolean t){this.name = "DOT"; prototypes.add(Norm.DOT);}
    public Shape(String name){this.name = name; shapeDB.add(this);}
    
    int dist(Norm norm){ 
      prototypes.bestMatch(norm);
      return Norm.List.bestMatchDistance;
    }
    //===============DB====================================
    public static class DB extends ArrayList<Shape> implements Serializable{ 
      public static String FNAME = "C:\\Users\\DarKz\\Documents\\NetBeansProjects\\GestureCourse\\ShapeDB.dat";
      private Map<String, Shape> byName = new HashMap<>();
      
      public Shape get(String name){
        if(name.equals("DOT")){return Shape.DOT;}
        return byName.get(name);
      }
      
      public Shape find(Norm norm){
        if(norm == Norm.DOT){return Shape.DOT;}
        Shape res = null; int bestDist = Integer.MAX_VALUE;
        for(Shape s : this){
          int d = s.dist(norm);
          if(d <bestDist){bestDist = d; res = s;}
        }
        return res;
      }
      
      @Override
      public boolean add(Shape s){
        super.add(s);
        byName.put(s.name, s);
        return true;
      }
      
      public static DB loadDB(){
        DB res;
        try {
          FileInputStream fin = new FileInputStream(FNAME);
          ObjectInputStream oin = new ObjectInputStream(fin);
          res = (DB) oin.readObject();
          oin.close();
          fin.close();
          System.out.println("Loaded "+FNAME);
          System.out.println("DB contains: "+res.byName.keySet());
        }catch(Exception e) {
          System.out.println(e);
          System.out.println("Couldn't find file "+ FNAME + " created new DB.");
          res = new DB();
        }
        System.out.println("Recognize shapes " + res.byName.keySet());
        return res;
      }

      // here is the routine to write out a single person list to FNAME
      public static void saveDB(){
        try {
          FileOutputStream fout =new FileOutputStream(FNAME);
          ObjectOutputStream oout = new ObjectOutputStream(fout);
          oout.writeObject(Stroke.shapeDB);
          System.out.println("Saved "+FNAME);
          oout.close();
          fout.close();
        }catch(Exception e) {
          System.out.println("WTF? Exception when Saving file: "+ FNAME);
          e.printStackTrace();
        }   
      }
    }// end DB
  }// end Shape
}// end Stroke
