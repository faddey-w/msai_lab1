/*
 * Decompiled with CFR 0_115.
 */
package test1_0_2_0;

import java.awt.Graphics2D;
import java.util.Map;
import test1_0_2_0.Constants;
import test1_0_2_0.Edge;
import test1_0_2_0.Node;
import test1_0_2_0.WeightedNode;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DirectEdge
extends Edge {
    public DirectEdge(Node i, Node j) {
        super(i, j);
    }

    public DirectEdge(test1_0_1_4.Edge edge, Map<Integer, WeightedNode> nodes) {
        super(edge, nodes);
    }

    @Override
    public void draw(Graphics2D g, int width, int height) {
        super.draw(g, width, height);
        int x = this.x2;
        int y = this.y2;
        int xx = this.x1;
        int yy = this.y1;
        int d = this.convert(Constants.nodeDiameter / Constants.nodeSizeDivBySizeArrow, Math.min(width, height));
        double a = Math.atan2(yy - y, xx - x);
        g.drawLine(x, y, x + (int)((double)d * Math.cos(a)), y + (int)((double)d * Math.sin(a += Constants.arrowAngle)));
        g.drawLine(x, y, x + (int)((double)d * Math.cos(a)), y + (int)((double)d * Math.sin(a -= 2.0 * Constants.arrowAngle)));
    }
}

