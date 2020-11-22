import java.util.ArrayList;

public class Singleton_CourseInfo
{
    // static variable single_instance of type Singleton
    private static Singleton_CourseInfo single_instance = null;

    public  ArrayList<CourseInfo> courseInfoDB;

    // private constructor restricted to this class itself
    private Singleton_CourseInfo()
    {
        courseInfoDB= new ArrayList<>();
    }

    // static method to create instance of Singleton class
    public static Singleton_CourseInfo getInstance()
    {
        if (single_instance == null)
            single_instance = new Singleton_CourseInfo();

        return single_instance;
    }
}
