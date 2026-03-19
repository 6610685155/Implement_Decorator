package factory;

import java.util.List;

import component.Component;
import component.CompressFile;
import decorator.checksum.MD5Verify;
import decorator.compression.ZipDecompression;
import decorator.encryption.DESDecryption;

public class DecompressFactory {
    public Component create(List<String> options, String fileName) {

        Component comp = new CompressFile();

        // Same order as compress: the outermost decorator processes raw file data first
        for (String opt : options) {
            switch (opt.toLowerCase()) {

                case "-zip":
                    comp = new ZipDecompression(comp);
                    break;

                case "-des":
                    comp = new DESDecryption(comp, fileName);
                    break;

                case "-md5":
                    comp = new MD5Verify(comp, fileName);
                    break;
            }
        }

        return comp;
    }
}
