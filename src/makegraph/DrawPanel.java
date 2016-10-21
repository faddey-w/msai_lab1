/*
 * Decompiled with CFR 0_115.
 */
package makegraph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import test1_0_2_0.Note;
import test1_0_2_0.TestObject;
import test1_0_2_0.TestScheme;
import test1_0_2_0.WeightedEdge;
import test1_0_2_0.WeightedNode;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DrawPanel
extends JPanel {
    TestScheme testScheme;
    TestObject selected;
    public boolean demonstrate = false;
    public Collection<Integer> choosed;
    public Collection<Integer> right;
    public Collection<Integer> wrong;

    public DrawPanel() {
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.addMouseListener(new MouseAdapter(){

            public void mousePressed(MouseEvent e) {
                DrawPanel.this.requestFocusInWindow();
            }
        });
    }

    public void setTestScheme(TestScheme testScheme) {
        this.testScheme = testScheme;
    }

    public WeightedEdge addEdge(WeightedNode i, WeightedNode j) {
        WeightedEdge res = this.testScheme.addEdge(i, j);
        return res;
    }

    public WeightedEdge addDirectEdge(WeightedNode i, WeightedNode j) {
        WeightedEdge res = this.testScheme.addDirectEdge(i, j);
        return res;
    }

    public Note addNote(int x, int y, String s) {
        Note res = this.testScheme.addNote(x, y, this.getWidth(), this.getHeight(), s);
        return res;
    }

    public WeightedNode addNode(int x, int y, int index) {
        WeightedNode res = this.testScheme.addNode(x, y, this.getWidth(), this.getHeight(), index);
        return res;
    }

    public TestObject findTestObject(int x, int y, TestObject betterNotThis) {
        return this.testScheme.findTestObject(x, y, this.getWidth(), this.getHeight(), betterNotThis);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.testScheme != null) {
            if (this.demonstrate) {
                this.testScheme.paintDemonstrate((Graphics2D)g, this.getWidth(), this.getHeight(), this.choosed, this.right, this.wrong);
            } else {
                this.testScheme.paint((Graphics2D)g, this.getWidth(), this.getHeight(), this.selected, this.choosed);
            }
        }
    }

    public void setChoosed(Collection<Integer> choosed) {
        this.choosed = choosed;
    }

    public void setSelected(TestObject selected) {
        this.selected = selected;
    }

}

