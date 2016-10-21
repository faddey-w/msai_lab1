/*
 * Decompiled with CFR 0_115.
 */
package test1_0_2_0;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;
import test1_0_2_0.Constants;
import test1_0_2_0.Node;
import test1_0_2_0.Note;
import test1_0_2_0.TestObject;
import test1_0_2_0.WeightedObject;

public class WeightedNode
extends WeightedObject {
    public WeightedNode(Node main) {
        super(main);
    }

    public Node getMain() {
        return (Node)super.getMain();
    }

    public Note addNote(int width, int height, String s) {
        int n = this.getNotes().size();
        int m = Constants.weightedNodeAngles.length;
        return this.addNote(Constants.weightedNodeAngles[n % m], width, height, s);
    }

    public Note addNote(double angle, int width, int height, String s) {
        Node main = this.getMain();
        int x = main.getX(width);
        int y = main.getY(height);
        double d = main.getDiameter(width, height);
        d = d / 2.0 + (double)Constants.distanceFromNodeToNote;
        return this.addNote((int)((double)x + d * Math.cos(angle)), (int)((double)y + d * Math.sin(angle)), width, height, s);
    }

    public void draw(Graphics2D g, int width, int height, boolean selected, Color innerColor) {
        this.draw(g, width, height, selected);
        this.getMain().draw(g, width, height, selected, innerColor);
    }
}

