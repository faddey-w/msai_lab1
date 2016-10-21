/*
 * Decompiled with CFR 0_115.
 */
package test1_0_1_2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import test1_0_1_2.AdditionalInfo;
import test1_0_1_2.Constants;
import test1_0_1_2.TestScheme;

public class Test
implements Serializable {
    public AdditionalInfo additionalInfo = new AdditionalInfo();
    public TestScheme testScheme = new TestScheme();
    public String task = "";

    public Test() {
    }

    public Test(test1_0_1_1.Test test) {
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

    public static Test load(File source) throws IOException, ClassNotFoundException {
        Test res;
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(source));
        Object o = in.readObject();
        in.close();
        if (o instanceof Test) {
            res = (Test)o;
        } else {
            test1_0_1_1.Test test = (test1_0_1_1.Test)o;
            res = new Test(test);
        }
        return Test.postload(res);
    }
}

