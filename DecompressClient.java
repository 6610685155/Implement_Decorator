import java.io.*;
import java.util.*;

import component.Component;
import factory.DecompressFactory;

public class DecompressClient {
    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            System.out.println("Usage: jdecompress <file.jc|file.zip> [-zip] [-des] [-md5]");
            return;
        }

        String inputName = args[0];
        List<String> options = Arrays.asList(args).subList(1, args.length);

        // Strip .jc or .zip extension to get the original file name
        String outputName;
        if (inputName.endsWith(".jc")) {
            outputName = inputName.substring(0, inputName.length() - 3);
        } else if (inputName.endsWith(".zip")) {
            outputName = inputName.substring(0, inputName.length() - 4);
        } else {
            outputName = inputName + ".out";
        }

        // The original fileName (without .jc) is used for finding .key and .md5 files
        String originalName = outputName;

        DecompressFactory factory = new DecompressFactory();
        Component processor = factory.create(options, originalName);

        try (
                InputStream in = new FileInputStream(inputName);
                OutputStream out = new FileOutputStream(outputName)) {
            processor.processFile(in, out);
        }

        System.out.println("Decompressed to: " + outputName);
        System.out.println("Done.");
    }
}
