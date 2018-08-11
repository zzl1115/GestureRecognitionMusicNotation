/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicsLib.react;

import GraphicsLib.G.VS;
import GraphicsLib.react.Ink.Norm;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

/**
 *
 * @author yunzhejiao
 */
public class Stroke implements Serializable{
    public static Shape.DB theShapeDB = new Shape.DB.load();
    public Shape shape;
    public VS vs;
    public static int lastStrokeDistance;
    
    public Stroke(){
        Norm norm = new Norm();
        shape = theShapeDB.find(norm);
        vs = new VS(Ink.buffer.box);
    }
    
    public static class Shape {
        public String name;
        public Norm.List prototypes;
        
        public Shape(String name) {
            this.name = name;
            prototypes = new Norm.List();
            theShapeDB.add(this);
            
        }
        
        private int bestMatch(Norm norm){
            int bestNumber = Integer.MAX_VALUE;
            for (Norm n : prototypes) {
                int d = norm.dist(n);
                if (d < bestNumber) {
                    bestNumber = d;
                }
            }
            
            return bestNumber;
        }
        
        public static class DB extends ArrayList<Shape> implements Serializable{
            public static String FNAME = "ShapeDB.dat";
            //map here : name -> shape
            public Map<String, Shape> byName = new HashMap<>();//diamond notation<>
            
            //serach in database and return the best match
            Shape find(Norm norm) {
                int bestNumber = Integer.MAX_VALUE;
                Shape result = null;
                for (Shape s : this) {
                    int matchScore = s.bestMatch(norm);                
                    if (matchScore < bestNumber) {
                        bestNumber = matchScore;
                        result = s;
                    }                                      
                }
                lastStrokeDistance = bestNumber;
                return result;
            }
            
            @Override
            public boolean add(Shape s){
                super.add(s);
                byName.put(s.name, s);
                return true;
            }

          public static DB load(){
            DB result;
            try {
              FileInputStream fin = new FileInputStream(FNAME);
              ObjectInputStream oin = new ObjectInputStream(fin);
              result = (DB) oin.readObject();
             oin.close();
             fin.close();
            }catch(Exception e) {
             System.out.println("Couldn't find file "+ FNAME + " so used default values.");
                result = new DB(); 
              }
           return result;
           }
    
    // here is the routine to write out a single person list to FNAME
    public static void saveDB(){
      try {
        FileOutputStream fout =new FileOutputStream(FNAME);
        ObjectOutputStream oout = new ObjectOutputStream(fout);
        oout.writeObject(theShapeDB);
        oout.close();
        fout.close();
        }catch(Exception e) {
          System.out.println("WTF? Saving file: "+ FNAME);
          e.printStackTrace();
        }   
            
        }
        
    }
}
}
