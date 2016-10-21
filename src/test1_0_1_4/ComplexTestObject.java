/*
 * Decompiled with CFR 0_115.
 */
package test1_0_1_4;

import java.awt.Graphics2D;
import java.util.Collection;
import test1_0_1_4.Movable;
import test1_0_1_4.TestObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class ComplexTestObject
extends TestObject
implements Movable {
    private Movable move;

    public abstract Collection<TestObject> getAll();

    @Override
    public void draw(Graphics2D g, int width, int height) {
        for (TestObject to : this.getAll()) {
            to.draw(g, width, height);
        }
    }

    @Override
    public boolean isSelects(int x, int y, int width, int height) {
        for (TestObject to : this.getAll()) {
            if (!to.isSelects(x, y, width, height)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void startMoving(int x, int y, int width, int height) {
        this.move = null;
        for (TestObject to : this.getAll()) {
            if (!to.isSelects(x, y, width, height) || !(to instanceof Movable)) continue;
            this.move = (Movable)((Object)to);
            this.move.startMoving(x, y, width, height);
            return;
        }
    }

    @Override
    public void moveTo(int x, int y) {
        if (this.move == null) {
            return;
        }
        this.move.moveTo(x, y);
    }
}

