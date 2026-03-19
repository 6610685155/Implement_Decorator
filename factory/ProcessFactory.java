package factory;

import java.util.List;

import component.Component;
import component.CompressFile;
import decorator.checksum.MD5Checksum;
import decorator.compression.ZipCompression;
import decorator.encryption.DESEncryption;

public class ProcessFactory {
    public Component create(List<String> options, String fileName) {

        Component comp = new CompressFile();

        for (String opt : options) {
            switch (opt.toLowerCase()) {

                case "-zip":
                    comp = new ZipCompression(comp, fileName);
                    break;

                case "-des":
                    comp = new DESEncryption(comp, fileName);
                    break;

                case "-md5":
                    comp = new MD5Checksum(comp, fileName);
                    break;
            }
        }

        return comp;
    }
}
