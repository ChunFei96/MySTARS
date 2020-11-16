
import java.io.Console;
public class MyStars {

    private  static Console console;


    public static void main(String[] args) {


        //Create new user: <<user_2>>
        //Account user_2 = new Account("tst","tst");
        //user_2.addAccount();



        String username = "tst2";
        String password = "tst";

        //Run by Console
        /*
        console = System.console();

        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        String username = console.readLine("Enter your username: ");
        System.out.println("Welcome " + username);

        char[] password = console.readPassword("Enter your password: ");
        //console.printf("Password entered was: %s%n", new String(passwordArray));
        */

        Login login = new Login(username,new String(password));
        login.validateLogin();
    }


}
