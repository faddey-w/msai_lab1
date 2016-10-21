/*
 * Decompiled with CFR 0_115.
 */
package makegraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import strings.Names;
import strings.Translatable;
import test1_0_2_0.Constants;

public class Configuration {
    public static void load() throws Exception {
        File f = new File(Names.configFile);
        if (!f.exists()) {
            return;
        }
        Scanner scan = new Scanner(f);
        AnswerFactory.configStart();
        while (scan.hasNext()) {
            int i;
            String s = scan.nextLine();
            int j = (s = s.trim()).indexOf(59);
            if (j == -1) {
                j = s.length();
            }
            if ((s = s.substring(0, j)).equals("")) continue;
            s = s.trim();
            for (i = 0; i < s.length() && s.charAt(i) != '='; ++i) {}
            String p = s.substring(0, i).trim();
            if (i == s.length()) {
                throw new Exception(p + " : = Not Found");
            }
            s = s.substring(++i);
            s = s.trim();
            try {
                if (p.equals(Names.strokeWidth)) {
                    Constants.strokeWidth = Integer.parseInt(s);
                    continue;
                }
                if (p.equals(Names.name)) {
                    AnswerFactory.addName(s);
                    continue;
                }
                if (p.equals(Names.command)) {
                    AnswerFactory.addPath(s);
                    continue;
                }
                if (p.equals(Names.entryName)) {
                    AnswerFactory.addEntryName(s);
                    continue;
                }
                if (p.equals(Names.initValue)) {
                    AnswerFactory.addInitValue(s);
                    continue;
                }
                throw new Exception("Identifier Not Found");
            }
            catch (Exception ex) {
                throw new Exception(p + " : " + ex.toString());
            }
        }
        AnswerFactory.configStop();
    }

    public static void save() {
        File f = new File(Names.configFile);
        if (f.exists()) {
            return;
        }
        try {
            PrintWriter print = new PrintWriter(new File(Names.configFile));
            print.println(Names.strokeWidth + " = " + Constants.strokeWidth + "\t\t; " + Translatable.configStrokeWidth);
            print.println();
            print.println("; " + Translatable.configModulesTitle);
            for (int i = 0; i < AnswerFactory.size(); ++i) {
                AnswerFactory.Entry entry = AnswerFactory.getEntries().get(i);
                print.println("; " + (entry.name != null ? entry.name : entry.path));
                if (entry.name != null) {
                    print.println(Names.name + " = " + entry.name);
                }
                print.println(Names.command + " = " + entry.path);
                for (int j = 0; j < entry.names.size(); ++j) {
                    print.println(Names.entryName + " = " + entry.names.get(j));
                    if (entry.initValues.size() <= j) continue;
                    print.println(Names.initValue + " = " + entry.initValues.get(j));
                }
            }
            print.close();
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}

