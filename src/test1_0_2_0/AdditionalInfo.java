/*
 * Decompiled with CFR 0_115.
 */
package test1_0_2_0;

import java.io.Serializable;
import java.util.Vector;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AdditionalInfo
implements Serializable {
    public int points = 1;
    private Vector<Vector<Integer>> answers = new Vector();
    private Vector<Vector<String>> data = new Vector();

    public AdditionalInfo() {
        this.answers.add(new Vector());
        this.data.add(new Vector());
    }

    AdditionalInfo(test1_0_1_4.AdditionalInfo ai) {
        this.points = ai.points;
        this.answers = ai.getAnswers();
        this.data = new Vector();
        for (Vector<Integer> v : ai.getData()) {
            Vector<String> r = new Vector<String>();
            for (Integer a : v) {
                r.add(a + "");
            }
            this.data.add(r);
        }
    }

    public Vector<Vector<Integer>> getAnswers() {
        return this.answers;
    }

    public Vector<Vector<String>> getData() {
        return this.data;
    }

    public synchronized int size() {
        return this.answers.size();
    }

    public synchronized void remove(int index) {
        this.answers.remove(index);
        this.data.remove(index);
    }

    public synchronized Vector<Integer> getAnswer(int index) {
        return this.answers.get(index);
    }

    public synchronized Vector<String> getData(int index) {
        return this.data.get(index);
    }

    public void add(int index) {
        this.answers.add(index, new Vector());
        this.data.add(index, new Vector());
    }
}

