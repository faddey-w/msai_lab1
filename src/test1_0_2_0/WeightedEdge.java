/*
 * Decompiled with CFR 0_115.
 */
package test1_0_2_0;

import java.awt.Point;
import java.util.Vector;
import test1_0_2_0.Constants;
import test1_0_2_0.Edge;
import test1_0_2_0.Note;
import test1_0_2_0.TestObject;
import test1_0_2_0.WeightedObject;

public class WeightedEdge
extends WeightedObject {
    public WeightedEdge(Edge main) {
        super(main);
    }

    public Edge getMain() {
        return (Edge)super.getMain();
    }

    public Note addNote(int width, int height, String s) {
        int n = this.getNotes().size();
        int m = Constants.weightedEdgeDistances.length;
        return this.addNote(Constants.weightedEdgeDistances[n % m], width, height, s);
    }

    public Note addNote(double d, int width, int height, String s) {
        d = Math.max(d, 0.0);
        d = Math.min(d, 1.0);
        Point p1 = this.getMain().getFirstPoint(width, height);
        Point p2 = this.getMain().getSecondPoint(width, height);
        int x = p2.x - p1.x;
        int y = p2.y - p1.y;
        double xx = - y;
        double yy = x;
        double dd = Math.hypot(xx, yy);
        xx /= dd;
        yy /= dd;
        return this.addNote((int)((double)p1.x + (double)x * d + (xx *= (double)Constants.distanceFromEdgeToNote)), (int)((double)p1.y + (double)y * d + (yy *= (double)Constants.distanceFromEdgeToNote)), width, height, s);
    }
}

