/*
 * Decompiled with CFR 0_115.
 */
package test1_0_2_0;

import java.awt.Graphics2D;
import java.util.Collection;
import test1_0_2_0.Movable;
import test1_0_2_0.TestObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class ComplexTestObject
extends TestObject
implements Movable {
    private Movable move;
    boolean all = false;

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
        this.startMoving(x, y, width, height, false);
    }

    public void startMoving(int x, int y, int width, int height, boolean all) {
        this.move = null;
        this.all = all;
        for (TestObject to : this.getAll()) {
            if (!to.isSelects(x, y, width, height) && !all || !(to instanceof Movable)) continue;
            this.move = (Movable)((Object)to);
            this.move.startMoving(x, y, width, height);
            if (all) continue;
            return;
        }
    }

    @Override
    public void moveTo(int x, int y) {
        if (this.all) {
            for (TestObject to : this.getAll()) {
                if (!(to instanceof Movable)) continue;
                this.move = (Movable)((Object)to);
                this.move.moveTo(x, y);
            }
        } else {
            if (this.move == null) {
                return;
            }
            this.move.moveTo(x, y);
        }
    }
}

