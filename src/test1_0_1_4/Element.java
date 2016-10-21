/*
 * Decompiled with CFR 0_115.
 */
package test1_0_1_4;

import test1_0_1_4.Movable;
import test1_0_1_4.TestObject;

public abstract class Element
extends TestObject
implements Movable {
    private double x;
    private double y;
    private int movingDX;
    private int movingDY;
    private int movingWidth;
    private int movingHeight;

    public Element(int x, int y, int width, int height) {
        this.setX(x, width);
        this.setY(y, height);
    }

    Element(test1_0_1_3.Element val) {
        this.x = val.getNormX();
        this.y = val.getNormY();
    }

    public int getX(int width) {
        return this.convert(this.x, width);
    }

    public int getY(int height) {
        return this.convert(this.y, height);
    }

    public void setX(int x, int width) {
        this.x = this.norm(x, width);
    }

    public void setY(int y, int height) {
        this.y = this.norm(y, height);
    }

    public double getNormX() {
        return this.x;
    }

    public double getNormY() {
        return this.y;
    }

    public void startMoving(int x, int y, int width, int height) {
        this.movingDX = x - this.getX(width);
        this.movingDY = y - this.getY(height);
        this.movingWidth = width;
        this.movingHeight = height;
    }

    public void moveTo(int x, int y) {
        this.setX(x - this.movingDX, this.movingWidth);
        this.setY(y - this.movingDY, this.movingHeight);
    }
}

