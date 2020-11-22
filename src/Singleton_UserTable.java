import java.util.ArrayList;

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
