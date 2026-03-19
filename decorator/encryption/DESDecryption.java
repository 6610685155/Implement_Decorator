package decorator.encryption;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import component.Component;
import decorator.CompressDecorator;

import java.io.*;

public class DESDecryption extends CompressDecorator {
    private String fileName;

    public DESDecryption(Component component, String fileName) {
        super(component);
        this.fileName = fileName;
    }

    @Override
    public void processFile(InputStream in, OutputStream out) throws Exception {
        // Read key from file
        String keyFile = fileName + ".key";
        byte[] keyBytes;
        try (FileInputStream keyIn = new FileInputStream(keyFile)) {
            keyBytes = keyIn.readAllBytes();
        }
        SecretKey key = new SecretKeySpec(keyBytes, "DES");

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        CipherInputStream cipherIn = new CipherInputStream(in, cipher);

        // Pass decrypted stream to inner component
        callInner(cipherIn, out);

        System.out.println("DES decrypted using key: " + keyFile);
    }
}
