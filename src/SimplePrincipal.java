import javax.security.auth.Subject;
import java.security.Principal;

public class SimplePrincipal implements Principal {
    String descr;
    String value;

    public SimplePrincipal(String descr, String value) {
        this.descr = descr;
        this.value = value;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;

        if (otherObject instanceof Principal){
            Principal other = (Principal) otherObject;
            return other.getName().equals(getName());
        }
        return false;
    }

    @Override
    public String getName() {
        return descr + "=" + value;
    }
}
