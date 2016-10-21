/*
 * Decompiled with CFR 0_115.
 */
package test1_0_1_2;

import java.io.Serializable;
import java.util.Vector;

public class AdditionalInfo
implements Serializable {
    public int points = 1;
    public Vector<Integer> rightAnswer = new Vector();

    public AdditionalInfo() {
    }

    AdditionalInfo(test1_0_1_1.AdditionalInfo ai) {
        this.points = ai.points;
        this.rightAnswer = ai.rightAnswer;
    }
}

