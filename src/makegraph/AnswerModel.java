/*
 * Decompiled with CFR 0_115.
 */
package makegraph;

import java.util.Vector;
import javax.swing.AbstractListModel;
import makegraph.Main;
import test1_0_2_0.AdditionalInfo;
import test1_0_2_0.Test;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AnswerModel
extends AbstractListModel {
    private Main main;

    public AnswerModel(Main main) {
        this.main = main;
    }

    @Override
    public int getSize() {
        return this.main.test.additionalInfo.size();
    }

    @Override
    public Vector<Integer> getElementAt(int index) {
        return this.main.test.additionalInfo.getAnswer(index);
    }
}

