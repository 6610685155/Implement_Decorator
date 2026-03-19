package decorator.compression;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import component.Component;
import decorator.CompressDecorator;

public class ZipCompression extends CompressDecorator {
    private String fileName;

    public ZipCompression(Component component, String fileName) {
        super(component);
        this.fileName = fileName;
    }

    @Override
    public void processFile(InputStream in, OutputStream out) throws Exception {
        ZipOutputStream zipOut = new ZipOutputStream(out);
        zipOut.putNextEntry(new ZipEntry(fileName));

        callInner(in, zipOut);

        zipOut.closeEntry();
        zipOut.finish();
    }
}
