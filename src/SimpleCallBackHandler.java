import javax.security.auth.callback.*;
import java.io.IOException;

public class SimpleCallBackHandler implements CallbackHandler {
    String userName;
    char[] password;

    public SimpleCallBackHandler(String userName, char[] password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks){
            if (callback instanceof NameCallback){
                ((NameCallback) callback).setName(userName);
            }
            else if (callback instanceof PasswordCallback){
                ((PasswordCallback) callback).setPassword(password);
            }
        }
    }
}
