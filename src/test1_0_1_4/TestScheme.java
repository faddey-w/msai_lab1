/*
 * Decompiled with CFR 0_115.
 */
package test1_0_1_4;

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
import test1_0_1_4.Constants;
import test1_0_1_4.DirectEdge;
import test1_0_1_4.Edge;
import test1_0_1_4.Node;
import test1_0_1_4.Note;
import test1_0_1_4.TestObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class TestScheme
implements Serializable {
    Map<Integer, Node> nodes = new HashMap<Integer, Node>();
    Vector<Note> notes = new Vector();
    Vector<Edge> edges = new Vector();
    Vector<DirectEdge> directEdges = new Vector();
    public double nodeDiameter = 0.1;

    public TestScheme() {
    }

    TestScheme(test1_0_1_3.TestScheme testScheme) {
        for (Map.Entry<Integer, test1_0_1_3.Node> entry : testScheme.getNodes().entrySet()) {
            this.nodes.put(entry.getKey(), new Node(entry.getValue()));
        }
        for (test1_0_1_3.Note note : testScheme.getNotes()) {
            this.notes.add(new Note(note));
        }
        for (test1_0_1_3.Edge edge2 : testScheme.getEdges()) {
            this.edges.add(new Edge(edge2, this.nodes));
        }
        for (test1_0_1_3.DirectEdge edge : testScheme.getDirectEdges()) {
            this.directEdges.add(new DirectEdge(edge, this.nodes));
        }
        this.nodeDiameter = testScheme.nodeDiameter;
    }

    public Edge addEdge(Node i, Node j) {
        if (i == j) {
            return null;
        }
        for (Edge edge : this.edges) {
            if (edge.getFirst() == i && edge.getSecond() == j) {
                return null;
            }
            if (edge.getFirst() != j || edge.getSecond() != i) continue;
            return null;
        }
        Edge res = new Edge(i, j);
        this.edges.add(res);
        return res;
    }

    public DirectEdge addDirectEdge(Node i, Node j) {
        if (i == j) {
            return null;
        }
        for (DirectEdge edge : this.directEdges) {
            if (edge.getFirst() != i || edge.getSecond() != j) continue;
            return null;
        }
        DirectEdge res = new DirectEdge(i, j);
        this.directEdges.add(res);
        return res;
    }

    public Note addNote(int x, int y, int width, int height, String s) {
        Note res = new Note(x, y, width, height, s);
        this.notes.add(res);
        return res;
    }

    public Node addNode(int x, int y, int width, int height, int index) {
        if (this.nodes.containsKey(index)) {
            return this.nodes.get(index);
        }
        Node res = new Node(x, y, width, height, index);
        this.nodes.put(index, res);
        return res;
    }

    public void paintDemonstrate(Graphics2D g, int width, int height, Collection<Integer> choosed, Collection<Integer> right, Collection<Integer> wrong) {
        this.paintInit(g);
        for (Map.Entry<Integer, Node> entry : this.nodes.entrySet()) {
            Node node = entry.getValue();
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
        for (Edge edge2 : this.edges) {
            edge2.draw(g, width, height, false);
        }
        for (DirectEdge edge : this.directEdges) {
            edge.draw(g, width, height, false);
        }
    }

    public void paint(Graphics2D g, int width, int height, TestObject selected, Collection<Integer> choosed) {
        Edge edge;
        this.paintInit(g);
        for (Map.Entry<Integer, Node> entry : this.nodes.entrySet()) {
            Node node = entry.getValue();
            node.draw(g, width, height, (node) == selected, choosed != null && choosed.contains(entry.getKey()) ? Constants.colorChoosed : null);
        }
        Iterator i$ = this.notes.iterator();
        while (i$.hasNext()) {
            Note note = (Note)i$.next();
            note.draw(g, width, height, (note) == selected);
        }
        i$ = this.edges.iterator();
        while (i$.hasNext()) {
            edge = (Edge)i$.next();
            edge.draw(g, width, height, (edge) == selected);
        }
        i$ = this.directEdges.iterator();
        while (i$.hasNext()) {
            edge = (DirectEdge)i$.next();
            edge.draw(g, width, height, (edge) == selected);
        }
    }

    public Node findNode(int x, int y, int width, int height) {
        for (Map.Entry<Integer, Node> entry : this.nodes.entrySet()) {
            if (!entry.getValue().isSelects(x, y, width, height)) continue;
            return entry.getValue();
        }
        return null;
    }

    public TestObject findTestObject(int x, int y, int width, int height) {
        for (Note note : this.notes) {
            if (!note.isSelects(x, y, width, height)) continue;
            return note;
        }
        for (Edge edge2 : this.edges) {
            if (!edge2.isSelects(x, y, width, height)) continue;
            return edge2;
        }
        for (DirectEdge edge : this.directEdges) {
            if (!edge.isSelects(x, y, width, height)) continue;
            return edge;
        }
        return this.findNode(x, y, width, height);
    }

    public Vector<Edge> getEdges() {
        return this.edges;
    }

    public Vector<DirectEdge> getDirectEdges() {
        return this.directEdges;
    }

    public Map<Integer, Node> getNodes() {
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
        if (to instanceof Node) {
            int i;
            this.nodes.remove(((Node)to).getIndex());
            for (i = 0; i < this.edges.size(); ++i) {
                if (this.edges.get(i).getFirst() != to && this.edges.get(i).getSecond() != to) continue;
                this.edges.remove(i);
                --i;
            }
            for (i = 0; i < this.directEdges.size(); ++i) {
                if (this.directEdges.get(i).getFirst() != to && this.directEdges.get(i).getSecond() != to) continue;
                this.directEdges.remove(i);
                --i;
            }
        }
        if (to instanceof Edge) {
            this.edges.remove(to);
        }
        if (to instanceof Note) {
            this.notes.remove(to);
        }
        if (to instanceof DirectEdge) {
            this.directEdges.remove(to);
        }
    }

    private void paintInit(Graphics2D g) {
        g.setStroke(new BasicStroke(Constants.strokeWidth));
        g.setFont(Constants.font);
        g.setColor(Constants.colorDefault);
    }
}

