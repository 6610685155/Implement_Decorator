package decorator.checksum;

import java.io.*;
import java.security.MessageDigest;

import component.Component;
import decorator.CompressDecorator;

public class MD5Verify extends CompressDecorator {
    private String fileName;

    public MD5Verify(Component component, String fileName) {
        super(component);
        this.fileName = fileName;
    }

    @Override
    public void processFile(InputStream in, OutputStream out) throws Exception {
        // Read all raw input data first
        ByteArrayOutputStream rawBuffer = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int len;
        while ((len = in.read(buf)) != -1) {
            rawBuffer.write(buf, 0, len);
        }
        byte[] rawData = rawBuffer.toByteArray();

        // Compute MD5 on raw input (matches what compress computed)
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(rawData);
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        String computedHash = sb.toString();

        // Read expected hash from file
        String md5File = fileName + ".md5";
        String expectedHash;
        try (BufferedReader br = new BufferedReader(new FileReader(md5File))) {
            expectedHash = br.readLine().trim();
        }

        // Compare
        if (computedHash.equals(expectedHash)) {
            System.out.println("MD5 VERIFY: PASS (" + computedHash + ")");
        } else {
            System.out.println("MD5 VERIFY: FAIL");
            System.out.println("  Expected: " + expectedHash);
            System.out.println("  Computed: " + computedHash);
        }

        // Pass raw data to inner decorators for further processing (decrypt,
        // decompress)
        ByteArrayInputStream rawIn = new ByteArrayInputStream(rawData);
        callInner(rawIn, out);
    }
}
