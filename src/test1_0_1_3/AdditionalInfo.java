/*
 * Decompiled with CFR 0_115.
 */
package test1_0_1_3;

import java.io.Serializable;
import java.util.Vector;

public class AdditionalInfo
implements Serializable {
    public int points = 1;
    public Vector<Vector<Integer>> answers = new Vector();

    public AdditionalInfo() {
        this.answers.add(new Vector());
    }

    AdditionalInfo(test1_0_1_2.AdditionalInfo ai) {
        this.points = ai.points;
        this.answers = new Vector();
        this.answers.add(ai.rightAnswer);
    }
}

