import java.util.ArrayList;

public class Singleton_StudentCourse
{
    // static variable single_instance of type Singleton
    private static Singleton_StudentCourse single_instance = null;

    public  ArrayList<StudentCourse> studentCourseDB;

    // private constructor restricted to this class itself
    private Singleton_StudentCourse()
    {
        studentCourseDB= new ArrayList<>();
    }

    // static method to create instance of Singleton class
    public static Singleton_StudentCourse getInstance()
    {
        if (single_instance == null)
            single_instance = new Singleton_StudentCourse();

        return single_instance;
    }
}
