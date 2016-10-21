/*
 * Decompiled with CFR 0_115.
 */
package makegraph;

import java.awt.Frame;
import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import strings.Names;
import test1_0_2_0.AdditionalInfo;
import test1_0_2_0.Constants;
import test1_0_2_0.Test;
import windows.InputEntries;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Module {
    private String[] command;
    private String name;
    private Vector<String> entryNames;
    private Vector<String> initValues;

    public Module(String command, String name, Vector<String> entryNames, Vector<String> initValues) throws Exception {

        StringTokenizer tokenizer = new StringTokenizer(command);
        this.command = new String[tokenizer.countTokens()];
        for(int idx = 0; tokenizer.hasMoreTokens(); ++idx) {
            this.command[idx] = tokenizer.nextToken();
        }
        if (name == null) {
            name = command;
        }
        if (entryNames == null) {
            entryNames = new Vector();
        }
        if (initValues == null) {
            initValues = new Vector();
        }
        this.name = name;
        this.entryNames = entryNames;
        this.initValues = initValues;
    }

    public String getName() {
        String res = this.name;
        if (this.entryNames.size() > 0) {
            res = res + "...";
        }
        return res;
    }

    public String getInnerName() {
        return this.name;
    }

    public Vector<Integer> exec(Test test, int index, Frame parent) throws Exception {
        if (this.entryNames.size() > 0) {
            InputEntries ie = new InputEntries(parent, true, this.name, this.entryNames, this.getInitValues(test, index));
            ie.setVisible(true);
            if (!ie.result) {
                throw new Cancel(this);
            }
            test.additionalInfo.getData().set(index, ie.res);
        }
        File modulesRoot = new File(Names.appRoot + "/" + Names.dirModules);

        ProcessBuilder proc_builder = new ProcessBuilder(this.command).directory(modulesRoot);
        Process proc = proc_builder.start();
        
        PrintWriter print = new PrintWriter(proc.getOutputStream());
        print.print(test.toInputString(index));
        print.close();
        proc.waitFor();
        if (proc.exitValue() != 0) {
            Scanner err_scan = new Scanner(proc.getErrorStream()).useDelimiter("\\A");
            String err_msg;
            if (err_scan.hasNext()) {
                err_msg = err_scan.next();
            } else {
                err_msg = "exited with " + Integer.toString(proc.exitValue());
            }
            throw new MessageReceived(this, "Module run failed:\n" + err_msg);
        }
        Scanner scan = new Scanner(proc.getInputStream());
        Vector<Integer> res = new Vector<Integer>();
        String s = "<html>";
        boolean suc = true;
        while (scan.hasNext()) {
            if (scan.hasNextInt() && suc) {
                int a = scan.nextInt();
                res.add(a);
                s = s + a + " ";
                continue;
            }
            s = s + scan.nextLine() + "<br>";
            suc = false;
        }
        if (!suc) {
            throw new MessageReceived(this, s);
        }
        return res;
    }

    private Vector<String> getInitValues(Test test, int index) {
        Vector<String> res = new Vector<String>();
        Vector<String> a = test.additionalInfo.getData(index);
        Vector<String> b = this.initValues;
        for (int i = 0; i < a.size() || i < b.size(); ++i) {
            if (i < a.size()) {
                res.add(a.get(i));
                continue;
            }
            res.add(b.get(i));
        }
        return res;
    }

    public class Cancel extends Exception {
        final /* synthetic */ Module this$0;

        public Cancel(Module module, String message) {
            super(message);
            this.this$0 = module;
        }

        public Cancel(Module module) {
            this.this$0 = module;
        }
    }

    public class MessageReceived extends Exception {
        final /* synthetic */ Module this$0;

        public MessageReceived(Module module, String message) {
            super(message);
            this.this$0 = module;
        }

        public MessageReceived(Module module) {
            this.this$0 = module;
        }
    }

}

