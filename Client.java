import java.io.*;
import java.util.*;

import component.Component;
import factory.ProcessFactory;

public class Client {
    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            System.out.println("Usage: jcompress <file> [-zip] [-des] [-md5]");
            return;
        }

        String fileName = args[0];
        List<String> options = Arrays.asList(args).subList(1, args.length);

        ProcessFactory factory = new ProcessFactory();
        Component processor = factory.create(options, fileName);

        boolean hasZip = options.stream().anyMatch(opt -> opt.equalsIgnoreCase("-zip"));
        String outputName = fileName + (hasZip ? ".zip" : ".jc");

        try (
                InputStream in = new FileInputStream(fileName);
                OutputStream out = new FileOutputStream(outputName)) {
            processor.processFile(in, out);
        }

        System.out.println("Compressed to: " + outputName);
        System.out.println("Done.");
    }
}
