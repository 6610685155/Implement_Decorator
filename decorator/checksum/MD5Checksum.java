package decorator.checksum;

import java.io.*;
import java.security.MessageDigest;

import component.Component;
import decorator.CompressDecorator;

public class MD5Checksum extends CompressDecorator {
    private String fileName;

    public MD5Checksum(Component component, String fileName) {
        super(component);
        this.fileName = fileName;
    }

    @Override
    public void processFile(InputStream in, OutputStream out) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        callInner(in, buffer);

        byte[] data = buffer.toByteArray();

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(data);

        // Build hex string
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        String hexHash = sb.toString();

        // Save MD5 hash to file for later verification
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName + ".md5"))) {
            pw.print(hexHash);
        }

        // Write original data to output
        out.write(data);

        // Print checksum
        System.out.println("MD5: " + hexHash);
        System.out.println("MD5 saved to: " + fileName + ".md5");
    }
}
