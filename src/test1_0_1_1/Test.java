/*
 * Decompiled with CFR 0_115.
 */
package test1_0_1_1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import test1_0_1_1.AdditionalInfo;
import test1_0_1_1.TestScheme;

public class Test
implements Serializable {
    public AdditionalInfo additionalInfo = new AdditionalInfo();
    public TestScheme testScheme = new TestScheme();
    public String task = "";

    public void save(File dest) throws IOException {
        if (!dest.exists()) {
            dest.createNewFile();
        }
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dest));
        out.writeObject(this);
        out.close();
    }

    public static Test load(File source) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(source));
        Test res = (Test)in.readObject();
        in.close();
        return res;
    }
}

