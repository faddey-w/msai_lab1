/*
 * Decompiled with CFR 0_115.
 */
package test1_0_1_1;

import test1_0_1_1.TestObject;

public abstract class Element
extends TestObject {
    private double x;
    private double y;

    public Element(int x, int y, int width, int height) {
        this.x = this.norm(x, width);
        this.y = this.norm(y, height);
    }

    public int getX(int width) {
        return this.convert(this.x, width);
    }

    public int getY(int height) {
        return this.convert(this.y, height);
    }

    public double getNormX() {
        return this.x;
    }

    public double getNormY() {
        return this.y;
    }
}

