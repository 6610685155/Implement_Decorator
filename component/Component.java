package component;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class Component {
    public abstract void processFile(InputStream in, OutputStream out) throws Exception;
}
