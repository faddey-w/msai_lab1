/*
 * Decompiled with CFR 0_115.
 */
package test1_0_1_4;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import test1_0_1_4.AdditionalInfo;
import test1_0_1_4.Constants;
import test1_0_1_4.TestScheme;

public class Test
implements Serializable {
    public AdditionalInfo additionalInfo = new AdditionalInfo();
    public TestScheme testScheme = new TestScheme();
    public String task = "";

    public Test() {
    }

    public Test(test1_0_1_3.Test test, Component parent) {
        this.additionalInfo = new AdditionalInfo(test.additionalInfo);
        this.testScheme = new TestScheme(test.testScheme);
        this.task = test.task;
    }

    private void presave() {
        this.testScheme.nodeDiameter = Constants.nodeDiameter;
    }

    private static Test postload(Test t) {
        Constants.nodeDiameter = t.testScheme.nodeDiameter;
        return t;
    }

    public void save(File dest) throws IOException {
        this.presave();
        if (!dest.exists()) {
            dest.createNewFile();
        }
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dest));
        out.writeObject(this);
        out.close();
    }

    public static Test loadFromObject(Object o, Component parent) throws IOException, ClassNotFoundException {
        Test res = o instanceof Test ? (Test)o : new Test(test1_0_1_3.Test.loadFromObject(o, parent), parent);
        return res;
    }

    public static Test load(File source, Component parent) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(source));
        Object o = in.readObject();
        in.close();
        return Test.postload(Test.loadFromObject(o, parent));
    }
}

