/*
 * Decompiled with CFR 0_115.
 */
package makegraph;

import java.awt.Frame;
import java.util.Vector;

import strings.Names;
import strings.Translatable;
import test1_0_2_0.Test;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AnswerFactory {
    private static Vector<Module> modules = new Vector();
    private static Vector<Entry> entries = new Vector();
    private static Entry entry;

    private static void add(Entry entry) throws Exception {
        modules.add(new Module(entry.path, entry.name, entry.names, entry.initValues));
        entries.add(entry);
    }

    public static int size() {
        return modules.size();
    }

    public static String getName(int i) {
        return modules.get(i).getName();
    }

    public static String getInnerName(int i) {
        return modules.get(i).getInnerName();
    }

    public static Vector<Integer> exec(int i, Test test, int index, Frame parent) throws Exception {
        return modules.get(i).exec(test, index, parent);
    }

    public static Vector<Entry> getEntries() {
        return entries;
    }

    public static void configStart() {
        modules.clear();
        entries.clear();
        entry = null;
    }

    private static void configStopEntry() throws Exception {
        if (entry == null) {
            return;
        }
        if (AnswerFactory.entry.path == null) {
            throw new Exception("Error adding a module: " + Names.command + " not defined");
        }
        AnswerFactory.add(entry);
        entry = null;
    }

    public static void configStop() throws Exception {
        AnswerFactory.configStopEntry();
    }

    public static void addPath(String path) throws Exception {
        if (entry != null && AnswerFactory.entry.path != null) {
            AnswerFactory.configStopEntry();
        }
        if (entry == null) {
            entry = new Entry();
        }
        AnswerFactory.entry.path = path;
    }

    public static void addName(String name) throws Exception {
        if (entry != null && AnswerFactory.entry.name != null) {
            AnswerFactory.configStopEntry();
        }
        if (entry == null) {
            entry = new Entry();
        }
        AnswerFactory.entry.name = name;
    }

    public static void addEntryName(String name) throws Exception {
        if (entry == null) {
            entry = new Entry();
        }
        AnswerFactory.entry.names.add(name);
    }

    public static void addInitValue(String value) throws Exception {
        if (entry == null) {
            entry = new Entry();
        }
        while (AnswerFactory.entry.names.size() > AnswerFactory.entry.initValues.size() + 1) {
            AnswerFactory.entry.initValues.add("");
        }
        AnswerFactory.entry.initValues.add(value);
    }

    static {
        Vector<String> names = new Vector<String>();
        Vector<String> initValues = new Vector<String>();
        names.add(Translatable.firstNode);
        initValues.add("1");
        names.add(Translatable.lastNode);
        initValues.add("-1");
        try {
            AnswerFactory.add(new Entry("dfs.exe", Translatable.dfs, names, initValues));
            AnswerFactory.add(new Entry("bfs.exe", Translatable.bfs, names, initValues));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Entry {
        public String path;
        public String name;
        Vector<String> names;
        Vector<String> initValues;

        public Entry(String path, String name, Vector<String> names, Vector<String> initValues) {
            this.path = path;
            this.name = name;
            this.names = names;
            this.initValues = initValues;
        }

        public Entry() {
            this.names = new Vector();
            this.initValues = new Vector();
        }
    }

}

