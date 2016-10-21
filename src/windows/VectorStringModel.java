/*
 * Decompiled with CFR 0_115.
 */
package windows;

import java.util.Vector;
import javax.swing.AbstractListModel;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class VectorStringModel
extends AbstractListModel {
    private Vector<String> data;

    public VectorStringModel(Vector<String> data) {
        this.data = data;
    }

    @Override
    public int getSize() {
        return this.data.size();
    }

    @Override
    public String getElementAt(int index) {
        return this.data.get(index);
    }

    public Vector<String> getData() {
        return this.data;
    }

    public synchronized String set(int index, String element) {
        String res = this.data.set(index, element);
        this.fireContentsChanged(this, index, index);
        return res;
    }

    public synchronized String remove(int index) {
        String res = this.data.remove(index);
        this.fireIntervalRemoved(this, index, index);
        return res;
    }

    public void add(int index, String element) {
        this.data.add(index, element);
        this.fireIntervalAdded(this, index, index);
    }
}

