/*
 * Decompiled with CFR 0_115.
 */
package test1_0_2_0;

import java.util.Collection;
import java.util.Vector;
import test1_0_2_0.ComplexTestObject;
import test1_0_2_0.Note;
import test1_0_2_0.TestObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class WeightedObject
extends ComplexTestObject {
    private TestObject main;
    private Vector<Note> notes;

    public WeightedObject(TestObject main) {
        this.main = main;
        this.notes = new Vector();
    }

    @Override
    public Collection<TestObject> getAll() {
        Vector v = (Vector)this.notes.clone();
        v.add(this.main);
        return v;
    }

    public TestObject getMain() {
        return this.main;
    }

    public Vector<Note> getNotes() {
        return this.notes;
    }

    public Note addNote(int x, int y, int width, int height, String s) {
        Note res = new Note(x, y, width, height, s);
        this.notes.add(res);
        return res;
    }

    public abstract Note addNote(int var1, int var2, String var3);

    public void removeNote() {
        this.notes.remove(this.notes.size() - 1);
    }

    public void setNotesStrings(int width, int height, Vector<String> v) {
        int i;
        for (i = 0; i < this.notes.size() && i < v.size() && this.notes.get(i).getText().equals(v.get(i)); ++i) {
        }
        while (i < this.notes.size()) {
            this.removeNote();
        }
        while (i < v.size()) {
            this.addNote(width, height, v.get(i));
            ++i;
        }
    }

    public Vector<String> getNotesStrings() {
        Vector<String> res = new Vector<String>();
        for (Note note : this.notes) {
            res.add(note.getText());
        }
        return res;
    }

    @Override
    public void startMoving(int x, int y, int width, int height) {
        super.startMoving(x, y, width, height, this.main.isSelects(x, y, width, height) || x < 0 || y < 0);
    }
}

