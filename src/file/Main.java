package file;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
import java.io.File;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Encryptor en = Encryptor.getEncrypter(true);
        Decryptor de = Decryptor.getDecrypter(true);
        File src = new File("C:/Users/knwer/Desktop/crypt/src");
        en.encrypt(src, src);
    }
}

