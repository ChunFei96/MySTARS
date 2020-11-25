import java.util.ArrayList;
/**
 * Singleton for User database
 * @author Lee Chun Fei and Mindy Hwang Mei Hui
 * @version 1.0
 * @since 2020-11-15
 */
public class Singleton_UserTable {

    private static Singleton_UserTable single_instance = null;

    public ArrayList<Login> userDB;

    // private constructor restricted to this class itself
    private Singleton_UserTable()
    {
        userDB = new ArrayList<>();
    }

    // static method to create instance of Singleton class
    public static Singleton_UserTable getInstance()
    {
        if (single_instance == null)
            single_instance = new Singleton_UserTable();

        return single_instance;
    }
}
