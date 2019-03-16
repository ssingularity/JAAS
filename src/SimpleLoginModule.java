import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.FileReader;
import java.security.Principal;
import java.util.*;

public class SimpleLoginModule implements LoginModule {
    Subject subject;
    CallbackHandler callbackHandler;
    Map<String, ?> sharedState;
    Map<String, ?> options;
    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
    }

    @Override
    public boolean login() throws LoginException {
        if (callbackHandler == null) throw  new LoginException("handler is null");
        NameCallback nameCallback = new NameCallback("username:");
        PasswordCallback passwordCallback = new PasswordCallback("password:", false);
        try {
            callbackHandler.handle(new Callback[] {nameCallback, passwordCallback});
        }catch (Exception e){
            e.printStackTrace();
        }
        return checkLogin(nameCallback.getName(), passwordCallback.getPassword());
    }

    private boolean checkLogin(String name, char[] password){
        try{
            Scanner in = new Scanner(new FileReader(""+options.get("pwfile")));
            while (in.hasNextLine()){
                String[] input = in.nextLine().split("\\|");
                if (input[0].equals(name) && Arrays.equals(input[1].toCharArray(), password)){
                    String role = input[2];
                    Set<Principal> principals = subject.getPrincipals();
                    principals.add(new SimplePrincipal("username", name));
                    principals.add(new SimplePrincipal("role", role));
                    return true;
                }
            }
            in.close();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean commit() throws LoginException {
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        return true;
    }
}
