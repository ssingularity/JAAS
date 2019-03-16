import com.sun.security.auth.callback.TextCallbackHandler;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.PrivilegedAction;

public class Main {
    public static void main(String[] args){
        System.setProperty("java.security.auth.login.config", "Jaas.config");
        System.setProperty("java.security.policy", "Jaas.policy");
        System.setProperty("book.catogeryA", "publicBook");
        System.setProperty("book.catogeryB", "privateBook");
        System.setProperty("book.catogeryC", "privateBook");
        System.setSecurityManager(new SecurityManager());
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true){
                System.out.print("请输入用户名（q退出）：");
                String userName = br.readLine();
                if (userName.equals("q")) return;
                System.out.print("请输入密码：");
                String password = br.readLine();
                LoginContext context = new LoginContext("Login1", new SimpleCallBackHandler(userName, password.toCharArray()));
                context.login();
                Subject subject = context.getSubject();
                System.out.println("subject=" + subject);
                try {
                    while (true){
                        System.out.print("请输入Property（q退出）：");
                        String property = br.readLine();
                        if (property.equals("q")) break;
                        PrivilegedAction action = new SysPropAction(property);
                        Object result = Subject.doAsPrivileged(subject, action, null);
                        System.out.println(result);
                    }
                }
                catch (Throwable e){
                    System.out.println(e.getMessage());
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
