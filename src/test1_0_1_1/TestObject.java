/*
 * Decompiled with CFR 0_115.
 */
package test1_0_1_1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import test1_0_1_1.Constants;

public abstract class TestObject
implements Serializable {
    double norm(int x, int length) {
        return (double)x / (double)length;
    }

    int convert(double x, int length) {
        return (int)(x * (double)length + 0.5);
    }

    public abstract void draw(Graphics2D var1, int var2, int var3);

    public void draw(Graphics2D g, int width, int height, boolean selected) {
        if (selected) {
            g.setColor(Constants.colorSelected);
        } else {
            g.setColor(Constants.colorDefault);
        }
        this.draw(g, width, height);
    }

    public abstract boolean isSelects(int var1, int var2, int var3, int var4);
}

