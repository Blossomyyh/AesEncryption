package file;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class Encryptor {
    private static Encryptor encrypter = new Encryptor();
    private static boolean deleteOriginal;

    private Encryptor() {
    }

    public static Encryptor getEncrypter(boolean originalFileDeleted) {
        deleteOriginal = originalFileDeleted;
        return encrypter;
    }

    public void encrypt(File src, File dst) {
        if (!dst.exists()) {
            dst.mkdir();
        }

        if (dst.isDirectory()) {
            try {
                if (!src.isDirectory()) {
                    this.copyEncrypted(src, dst);
                } else {
                    File[] files = src.listFiles();
                    System.out.println("Encrypting...");
                    File[] var7 = files;
                    int var6 = files.length;

                    for(int var5 = 0; var5 < var6; ++var5) {
                        File f = var7[var5];
                        this.copyEncrypted(f, dst);
                        if (deleteOriginal) {
                            f.delete();
                        }
                    }

                    System.out.println(files.length + " files are encrypted");
                }
            } catch (IOException var8) {
                var8.printStackTrace();
            }

        }
    }

    public void copyEncrypted(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        dest = new File(dest.getPath().concat("/").concat(this.getRandomName(10, "sopiro")));

        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            os.write(new byte[]{(byte)source.getName().length()});
            os.write(this.stringToByte(source.getName()));
            byte[] buffer = new byte[1024];

            int length;
            while((length = is.read(buffer)) > 0) {
                this.encryptBytes(buffer);
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }

    }

    private void encryptBytes(byte[] data) {
        for(int i = 0; i < data.length; ++i) {
            data[i] = (byte)(~data[i]);
        }

    }

    public byte[] stringToByte(String data) {
        char[] ca = data.toCharArray();
        byte[] res = new byte[ca.length * 2];

        for(int i = 0; i < res.length; ++i) {
            res[i] = (byte)(ca[i / 2] >> 8 - i % 2 * 8 & 255);
        }

        return res;
    }

    public String getRandomName(int length, String extend) {
        Random r = new Random();
        StringBuilder res = new StringBuilder();

        for(int i = 0; i < length; ++i) {
            char c = 97;
            int width = 25;
            if (r.nextInt(3) == 0) {
                c = 65;
                width = 25;
            }

            if (r.nextInt(3) == 1) {
                c = 48;
                width = 9;
            }

            res.append((char)(c + r.nextInt(width)));
        }

        res.append(".").append(extend);
        return res.toString();
    }

    public void copy(File source, File dest) throws IOException {
        InputStream is = null;
        FileOutputStream os = null;

        try {
            dest = new File(dest.getPath().concat("/").concat(source.getName()));
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int tl = 0;

            int length;
            while((length = is.read(buffer)) > 0) {
                tl += length;
                os.write(buffer, 0, length);
            }

            System.out.println(tl + " bytes");
        } finally {
            is.close();
            os.close();
        }
    }
}
