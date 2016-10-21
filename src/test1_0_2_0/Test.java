/*
 * Decompiled with CFR 0_115.
 */
package test1_0_2_0;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import test1_0_2_0.AdditionalInfo;
import test1_0_2_0.Constants;
import test1_0_2_0.DirectEdge;
import test1_0_2_0.Edge;
import test1_0_2_0.Node;
import test1_0_2_0.Note;
import test1_0_2_0.TestScheme;
import test1_0_2_0.WeightedEdge;
import test1_0_2_0.WeightedNode;

public class Test
implements Serializable {
    public AdditionalInfo additionalInfo = new AdditionalInfo();
    public TestScheme testScheme = new TestScheme();
    public String task = "";

    public Test() {
    }

    public Test(test1_0_1_4.Test test, Component parent) {
        this.additionalInfo = new AdditionalInfo(test.additionalInfo);
        this.testScheme = new TestScheme(test.testScheme);
        this.task = test.task;
    }

    private void presave() {
        this.testScheme.nodeDiameter = Constants.nodeDiameter;
    }

    private static Test postload(Test t) {
        Constants.nodeDiameter = t.testScheme.nodeDiameter;
        return t;
    }

    public void save(File dest) throws IOException {
        this.presave();
        if (!dest.exists()) {
            dest.createNewFile();
        }
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dest));
        out.writeObject(this);
        out.close();
    }

    public static Test loadFromObject(Object o, Component parent) throws IOException, ClassNotFoundException {
        Test res = o instanceof Test ? (Test)o : new Test(test1_0_1_4.Test.loadFromObject(o, parent), parent);
        return res;
    }

    public static Test load(File source, Component parent) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(source));
        Object o = in.readObject();
        in.close();
        return Test.postload(Test.loadFromObject(o, parent));
    }

    public String toInputString(int index) {
        int k;
        String res = "";
        int n = this.testScheme.nodes.size();
        int m = this.testScheme.edges.size();
        int q = this.additionalInfo.getData(index).size();
        res = res + n + " " + m + " " + q + "\n";
        for (Map.Entry<Integer, WeightedNode> entry : this.testScheme.nodes.entrySet()) {
            res = res + entry.getKey() + " ";
            WeightedNode a = entry.getValue();
            k = a.getNotes().size();
            res = res + k + "\n";
            for (Note note : a.getNotes()) {
                res = res + note.getText() + "\n";
            }
        }
        for (WeightedEdge edge : this.testScheme.getEdges()) {
            int a = 0;
            if (edge.getMain() instanceof DirectEdge) {
                a = 1;
            }
            res = res + a + " ";
            res = res + edge.getMain().getFirst().getIndex() + " ";
            res = res + edge.getMain().getSecond().getIndex() + " ";
            k = edge.getNotes().size();
            res = res + k + "\n";
            for (Note note : edge.getNotes()) {
                res = res + note.getText() + "\n";
            }
        }
        for (String s : this.additionalInfo.getData(index)) {
            res = res + s + "\n";
        }
        return res;
    }
}

