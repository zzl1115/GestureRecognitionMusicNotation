package GraphicsLib;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DarKz
 */
public class Complex {
    public double x;
    public double y;
    
    public Complex(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Complex add(Complex a) {
        return new Complex(this.x + a.x, this.y + a.y);
    }
    
    public Complex mul(Complex a) {
        return new Complex(x*a.x - y*a.y, x*a.y + y*a.x);
    }
    
    public Complex(double theta) {
        x = cos(theta);
        y = sin(theta);
    }
    
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
