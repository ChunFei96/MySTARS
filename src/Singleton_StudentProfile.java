

import java.util.ArrayList;

public class Singleton_StudentProfile {

    private static Singleton_StudentProfile single_instance = null;

    public ArrayList<Student> studentProfileDB;

    // private constructor restricted to this class itself
    private Singleton_StudentProfile()
    {
        studentProfileDB = new ArrayList<>();
    }

    // static method to create instance of Singleton class
    public static Singleton_StudentProfile getInstance()
    {
        if (single_instance == null)
            single_instance = new Singleton_StudentProfile();

        return single_instance;
    }
}
