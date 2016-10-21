/*
 * Decompiled with CFR 0_115.
 */
package test1_0_1_2;

import java.awt.Color;
import java.awt.Graphics2D;
import test1_0_1_2.Constants;
import test1_0_1_2.Element;
import test1_0_1_2.Note;

public class Node
extends Element {
    private int index;
    private int x;
    private int y;
    private int d;

    public Node(int x, int y, int width, int height, int index) {
        super(x, y, width, height);
        this.index = index;
    }

    Node(test1_0_1_1.Node val) {
        super(val);
        this.index = val.getIndex();
    }

    public void draw(Graphics2D g, int width, int height) {
        this.calculateRealCoordinates(width, height);
        g.drawOval(this.x, this.y, this.d, this.d);
        Note note = new Note(this.getX(width), this.getY(height), width, height, "" + this.index + "");
        note.draw(g, width, height);
    }

    public void draw(Graphics2D g, int width, int height, boolean selected, Color innerColor) {
        if (innerColor != null) {
            this.calculateRealCoordinates(width, height);
            g.setColor(innerColor);
            g.fillOval(this.x, this.y, this.d, this.d);
        }
        this.draw(g, width, height, selected);
    }

    int getDiameter(int width, int height) {
        int l = Math.min(width, height);
        l = this.convert(Constants.nodeDiameter, l);
        return l;
    }

    public boolean isSelects(int x, int y, int width, int height) {
        int r = this.getDiameter(width, height) / 2;
        return r * r >= (x - this.getX(width)) * (x - this.getX(width)) + (y - this.getY(height)) * (y - this.getY(height));
    }

    private void calculateRealCoordinates(int width, int height) {
        this.x = this.getX(width);
        this.y = this.getY(height);
        this.d = this.getDiameter(width, height);
        this.x -= this.d / 2;
        this.y -= this.d / 2;
    }

    public int getIndex() {
        return this.index;
    }
}

