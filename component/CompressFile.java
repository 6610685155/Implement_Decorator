package component;

import java.io.InputStream;
import java.io.OutputStream;

public class CompressFile extends Component {
    @Override
    public void processFile(InputStream in, OutputStream out) throws Exception {
        byte[] buffer = new byte[4096];
        int len;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
    }
}
