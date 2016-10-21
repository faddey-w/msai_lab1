/*
 * Decompiled with CFR 0_115.
 */
package test1_0_1_4;

import java.awt.Graphics2D;
import java.util.Map;
import test1_0_1_4.Constants;
import test1_0_1_4.Node;
import test1_0_1_4.TestObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Edge
extends TestObject {
    Node i;
    Node j;
    protected int x1;
    protected int y1;
    protected int x2;
    protected int y2;

    public Edge(Node i, Node j) {
        this.i = i;
        this.j = j;
    }

    Edge(test1_0_1_3.Edge edge, Map<Integer, Node> nodes) {
        this.i = nodes.get(edge.getFirst().getIndex());
        this.j = nodes.get(edge.getSecond().getIndex());
    }

    @Override
    public void draw(Graphics2D g, int width, int height) {
        this.calculateRealCoordinates(width, height);
        g.drawLine(this.x1, this.y1, this.x2, this.y2);
    }

    public Node getFirst() {
        return this.i;
    }

    public Node getSecond() {
        return this.j;
    }

    @Override
    public boolean isSelects(int x, int y, int width, int height) {
        this.calculateRealCoordinates(width, height);
        double a = this.y2 - this.y1;
        double b = this.x1 - this.x2;
        double c = (- (double)this.y1) * (double)this.x2 + (double)this.y2 * (double)this.x1;
        double d = Math.sqrt(a * a + b * b);
        if (Math.abs(d) < 1.0E-9) {
            return false;
        }
        if (Math.abs(d = (a /= d) * (double)x + (b /= d) * (double)y - (c /= d)) > (double)Constants.edgeDelta) {
            return false;
        }
        double xx = (double)x - a * d;
        double yy = (double)y - b * d;
        d = a * xx + b * yy - c;
        if (Math.abs(d) > 1.0E-4) {
            throw new ArithmeticException("Edge:isSelects works wrong");
        }
        int l = Math.min(this.x1, this.x2);
        int r = Math.max(this.x1, this.x2);
        if ((double)l > xx || xx > (double)r) {
            return false;
        }
        l = Math.min(this.y1, this.y2);
        r = Math.max(this.y1, this.y2);
        if ((double)l > yy || yy > (double)r) {
            return false;
        }
        return true;
    }

    private void calculateRealCoordinates(int width, int height) {
        this.x1 = this.i.getX(width);
        this.x2 = this.j.getX(width);
        this.y1 = this.i.getY(height);
        this.y2 = this.j.getY(height);
        double dx = this.x2 - this.x1;
        double dy = this.y2 - this.y1;
        double d = Math.sqrt(dx * dx + dy * dy);
        double r = (double)this.i.getDiameter(width, height) / 2.0;
        this.x1 += (int)((dx /= d) * r + 0.5);
        this.y1 += (int)((dy /= d) * r + 0.5);
        this.x2 -= (int)(dx * r + 0.5);
        this.y2 -= (int)(dy * r + 0.5);
    }
}

