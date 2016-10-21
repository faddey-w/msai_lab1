/*
 * Decompiled with CFR 0_115.
 */
package strings;


import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

public class Names {
    public static String appRoot;
    public static String testGraph = "TestGraph";
    public static String makeGraph = "System of Search in State Space";
    public static String makeGraphVersion = "1.0.2.0 RUSSIAN";
    public static String about = "About";
    public static String nameFilesTests = "graph files";
    public static String dirTests = "tests";
    public static String configFile = "config.ini";
    public static String strokeWidth = "StrokeWidth";
    public static String dirModules = "modules";
    public static String name = "name";
    public static String command = "command";
    public static String entryName = "entry name";
    public static String initValue = "init value";

    static {
        try {
            if (Names.class.getResource("Names.class").toString().startsWith("jar:")) {
                // we are running from jar
                String jarFile = Names.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                jarFile = URLDecoder.decode(jarFile, "UTF-8");
                appRoot = new File(jarFile).getParentFile().getPath();
            } else {
                // there are no Jar file, use current working directory
                appRoot = new File(".").getCanonicalPath();
            }
        } catch (IOException e) {
            appRoot = null; // actually, this should never happen
        }

    }
}

