package decorator;

import java.io.InputStream;
import java.io.OutputStream;

import component.Component;

public abstract class CompressDecorator extends Component {
    protected Component inner;

    public CompressDecorator(Component component) {
        this.inner = component;
    }

    protected void callInner(InputStream in, OutputStream out) throws Exception {
        if (inner != null) {
            inner.processFile(in, out);
        }
    }
}
