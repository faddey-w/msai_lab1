/*
 * Decompiled with CFR 0_115.
 */
package test1_0_2_0;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import test1_0_2_0.Constants;
import test1_0_2_0.DirectEdge;
import test1_0_2_0.Edge;
import test1_0_2_0.Node;
import test1_0_2_0.Note;
import test1_0_2_0.TestObject;
import test1_0_2_0.WeightedEdge;
import test1_0_2_0.WeightedNode;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class TestScheme
implements Serializable {
    Map<Integer, WeightedNode> nodes = new HashMap<Integer, WeightedNode>();
    Vector<Note> notes = new Vector();
    Vector<WeightedEdge> edges = new Vector();
    public double nodeDiameter = 0.1;

    public TestScheme() {
    }

    TestScheme(test1_0_1_4.TestScheme testScheme) {
        for (Map.Entry<Integer, test1_0_1_4.Node> entry : testScheme.getNodes().entrySet()) {
            this.nodes.put(entry.getKey(), new WeightedNode(new Node(entry.getValue())));
        }
        for (test1_0_1_4.Note note : testScheme.getNotes()) {
            this.notes.add(new Note(note));
        }
        for (test1_0_1_4.Edge edge2 : testScheme.getEdges()) {
            this.edges.add(new WeightedEdge(new Edge(edge2, this.nodes)));
        }
        for (test1_0_1_4.DirectEdge edge : testScheme.getDirectEdges()) {
            this.edges.add(new WeightedEdge(new DirectEdge(edge, this.nodes)));
        }
        this.nodeDiameter = testScheme.nodeDiameter;
    }

    public WeightedEdge addEdge(WeightedNode i, WeightedNode j) {
        if (i == j) {
            return null;
        }
        for (WeightedEdge edge : this.edges) {
            if (edge.getMain().getFirst() == i.getMain() && edge.getMain().getSecond() == j.getMain()) {
                return null;
            }
            if (edge.getMain().getFirst() != j.getMain() || edge.getMain().getSecond() != i.getMain()) continue;
            return null;
        }
        WeightedEdge res = new WeightedEdge(new Edge(i.getMain(), j.getMain()));
        this.edges.add(res);
        return res;
    }

    public WeightedEdge addDirectEdge(WeightedNode i, WeightedNode j) {
        if (i == j) {
            return null;
        }
        for (WeightedEdge edge : this.edges) {
            if (edge.getMain().getFirst() == i.getMain() && edge.getMain().getSecond() == j.getMain()) {
                return null;
            }
            if (edge.getMain() instanceof DirectEdge || edge.getMain().getFirst() != j.getMain() || edge.getMain().getSecond() != i.getMain()) continue;
            return null;
        }
        WeightedEdge res = new WeightedEdge(new DirectEdge(i.getMain(), j.getMain()));
        this.edges.add(res);
        return res;
    }

    public Note addNote(int x, int y, int width, int height, String s) {
        Note res = new Note(x, y, width, height, s);
        this.notes.add(res);
        return res;
    }

    public WeightedNode addNode(int x, int y, int width, int height, int index) {
        if (this.nodes.containsKey(index)) {
            return this.nodes.get(index);
        }
        WeightedNode res = new WeightedNode(new Node(x, y, width, height, index));
        this.nodes.put(index, res);
        return res;
    }

    public void paintDemonstrate(Graphics2D g, int width, int height, Collection<Integer> choosed, Collection<Integer> right, Collection<Integer> wrong) {
        this.paintInit(g);
        for (Map.Entry<Integer, WeightedNode> entry : this.nodes.entrySet()) {
            WeightedNode node = entry.getValue();
            int key = entry.getKey();
            Color col = null;
            if (choosed != null && choosed.contains(key)) {
                col = Constants.colorChoosed;
            }
            if (right != null && right.contains(key)) {
                col = Constants.colorRight;
            }
            if (wrong != null && wrong.contains(key)) {
                col = Constants.colorWrong;
            }
            node.draw(g, width, height, false, col);
        }
        for (Note note : this.notes) {
            note.draw(g, width, height, false);
        }
        for (WeightedEdge edge : this.edges) {
            edge.draw(g, width, height, false);
        }
    }

    public void paint(Graphics2D g, int width, int height, TestObject selected, Collection<Integer> choosed) {
        this.paintInit(g);
        for (Map.Entry<Integer, WeightedNode> entry : this.nodes.entrySet()) {
            WeightedNode node= entry.getValue();
            node.draw(g, width, height, (node ) == selected, choosed != null && choosed.contains(entry.getKey()) ? Constants.colorChoosed : null);
        }
        Iterator i$ = this.notes.iterator();
        while (i$.hasNext()) {
            Note note = (Note)i$.next();
            note.draw(g, width, height, (note) == selected);
        }
        i$ = this.edges.iterator();
        while (i$.hasNext()) {
            WeightedEdge edge = (WeightedEdge)i$.next();
            edge.draw(g, width, height, (edge) == selected);
        }
    }

    public TestObject findTestObject(int x, int y, int width, int height, TestObject betterNotThis) {
        for (Note note : this.notes) {
            if (!note.isSelects(x, y, width, height) || note == betterNotThis) continue;
            return note;
        }
        for (WeightedEdge edge : this.edges) {
            if (!edge.isSelects(x, y, width, height) || edge == betterNotThis) continue;
            return edge;
        }
        for (Map.Entry entry : this.nodes.entrySet()) {
            if (!((WeightedNode)entry.getValue()).isSelects(x, y, width, height) || entry.getValue() == betterNotThis) continue;
            return (TestObject)entry.getValue();
        }
        return this.findTestObject(x, y, width, height);
    }

    public TestObject findTestObject(int x, int y, int width, int height) {
        for (Note note : this.notes) {
            if (!note.isSelects(x, y, width, height)) continue;
            return note;
        }
        for (WeightedEdge edge : this.edges) {
            if (!edge.isSelects(x, y, width, height)) continue;
            return edge;
        }
        for (Map.Entry entry : this.nodes.entrySet()) {
            if (!((WeightedNode)entry.getValue()).isSelects(x, y, width, height)) continue;
            return (TestObject)entry.getValue();
        }
        return null;
    }

    public Vector<WeightedEdge> getEdges() {
        return this.edges;
    }

    public Map<Integer, WeightedNode> getNodes() {
        return this.nodes;
    }

    public Vector<Note> getNotes() {
        return this.notes;
    }

    public int getNextIndex() {
        int res = 1;
        while (this.nodes.containsKey(res)) {
            ++res;
        }
        return res;
    }

    public void deleteObject(TestObject to) {
        if (to instanceof WeightedNode) {
            this.nodes.remove(((WeightedNode)to).getMain().getIndex());
            for (int i = 0; i < this.edges.size(); ++i) {
                if (this.edges.get(i).getMain().getFirst() != to && this.edges.get(i).getMain().getSecond() != to) continue;
                this.edges.remove(i);
                --i;
            }
        }
        if (to instanceof WeightedEdge) {
            this.edges.remove(to);
        }
        if (to instanceof Note) {
            this.notes.remove(to);
        }
    }

    private void paintInit(Graphics2D g) {
        g.setStroke(new BasicStroke(Constants.strokeWidth));
        g.setFont(Constants.font);
        g.setColor(Constants.colorDefault);
    }
}

