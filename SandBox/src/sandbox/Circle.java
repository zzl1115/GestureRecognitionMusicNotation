/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox;

import GraphicsLib.G.V;
import GraphicsLib.react.I;
import java.awt.Graphics;

/**
 *
 * @author DarKz
 */
public class Circle implements I.Show{
    public V center;
    public int radius;
    public Circle(int x, int y, int r) {
        center = new V(x, y);
        radius = r;
    }

    @Override
    public void show(Graphics g) {
        g.setColor(Color)
        g.fillOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
    }
}
