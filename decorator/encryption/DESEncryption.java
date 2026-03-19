package decorator.encryption;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import component.Component;
import decorator.CompressDecorator;

import java.io.*;

public class DESEncryption extends CompressDecorator {
    private String fileName;

    public DESEncryption(Component component, String fileName) {
        super(component);
        this.fileName = fileName;
    }

    @Override
    public void processFile(InputStream in, OutputStream out) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        SecretKey key = keyGen.generateKey();

        // Save key to file for later decryption
        try (FileOutputStream keyOut = new FileOutputStream(fileName + ".key")) {
            keyOut.write(key.getEncoded());
        }
        System.out.println("DES key saved to: " + fileName + ".key");

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        CipherOutputStream cipherOut = new CipherOutputStream(out, cipher);

        callInner(in, cipherOut);

        cipherOut.flush();
        cipherOut.close();
    }
}
