/*
 * Decompiled with CFR 0_115.
 */
package test1_0_1_3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import test1_0_1_3.DirectEdge;
import test1_0_1_3.Edge;
import test1_0_1_3.Node;
import test1_0_1_3.TestScheme;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AnswerFactory {
    int s;
    int t;
    int n;
    Vector<Vector<Integer>> A;
    Map<Integer, Integer> P = new HashMap<Integer, Integer>();
    boolean[] B;
    Vector<Integer> res;

    public AnswerFactory(TestScheme test, int s, int t) {
        int i;
        this.s = s;
        this.t = t;
        this.n = 0;
        for (Map.Entry<Integer, Node> entry : test.getNodes().entrySet()) {
            this.P.put(entry.getKey(), this.n++);
        }
        this.A = new Vector();
        for (i = 0; i < this.n; ++i) {
            this.A.add(new Vector());
        }
        for (Edge edge2 : test.getEdges()) {
            i = edge2.getFirst().getIndex();
            int j = edge2.getSecond().getIndex();
            this.A.get(this.P.get(i)).add(j);
            this.A.get(this.P.get(j)).add(i);
        }
        for (DirectEdge edge : test.getDirectEdges()) {
            i = edge.getFirst().getIndex();
            int j = edge.getSecond().getIndex();
            this.A.get(this.P.get(i)).add(j);
        }
        for (i = 0; i < this.n; ++i) {
            Collections.sort((List)this.A.get(i));
        }
        this.B = new boolean[this.n];
    }

    public void setFirst(int i) {
        this.s = i;
    }

    public void setLast(int i) {
        this.t = i;
    }

    public Vector<Integer> dfs() {
        if (!this.P.containsKey(this.s)) {
            return null;
        }
        for (int i = 0; i < this.n; ++i) {
            this.B[i] = false;
        }
        this.res = new Vector();
        this.dfs(this.s);
        return this.res;
    }

    private boolean dfs(int s) {
        this.B[this.P.get((Object)Integer.valueOf((int)s)).intValue()] = true;
        this.res.add(s);
        if (s == this.t) {
            return true;
        }
        Iterator<Integer> i$ = this.A.get(this.P.get(s)).iterator();
        while (i$.hasNext()) {
            int i = i$.next();
            if (this.B[this.P.get(i)] || !this.dfs(i)) continue;
            return true;
        }
        return false;
    }

    public Vector<Integer> bfs() {
        if (!this.P.containsKey(this.s)) {
            return null;
        }
        for (int i = 0; i < this.n; ++i) {
            this.B[i] = false;
        }
        this.res = new Vector();
        int[] Q = new int[this.n];
        int rq = 0;
        this.res.add(this.s);
        Q[rq++] = this.s;
        this.B[this.P.get((Object)Integer.valueOf((int)this.s)).intValue()] = true;
        if (this.s == this.t) {
            return this.res;
        }
        for (int lq = 0; lq < rq; ++lq) {
            int i2 = Q[lq];
            Iterator<Integer> i$ = this.A.get(this.P.get(i2)).iterator();
            while (i$.hasNext()) {
                int j = i$.next();
                if (this.B[this.P.get(j)]) continue;
                this.res.add(j);
                Q[rq++] = j;
                this.B[this.P.get((Object)Integer.valueOf((int)j)).intValue()] = true;
                if (j != this.t) continue;
                return this.res;
            }
        }
        return this.res;
    }
}

