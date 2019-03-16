import java.security.PrivilegedAction;

public class SysPropAction implements PrivilegedAction {
    String property;

    public SysPropAction(String property) {
        this.property = property;
    }

    @Override
    public Object run() {
        return System.getProperty(property);
    }
}
