package decorator.compression;

import java.io.*;
import java.util.zip.ZipInputStream;

import component.Component;
import decorator.CompressDecorator;

public class ZipDecompression extends CompressDecorator {

    public ZipDecompression(Component component) {
        super(component);
    }

    @Override
    public void processFile(InputStream in, OutputStream out) throws Exception {
        ZipInputStream zipIn = new ZipInputStream(in);
        java.util.zip.ZipEntry entry = zipIn.getNextEntry();
        if (entry == null) {
            throw new Exception("Not a valid zip file! It may be encrypted or corrupted.");
        }

        // Pass the unzipped stream to the inner component
        callInner(zipIn, out);

        zipIn.closeEntry();
    }
}
