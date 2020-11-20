
import java.util.*;

public class Student extends User implements IStudent{

    private String matricNo;
    private ArrayList<CourseInfo> courseInfoList;
    private CourseManager courseManager;

    
    public Student(String name, String nationality, String email, String username, String password, String contactNo,
                   EnumHelper.UserRole role, EnumHelper.Gender sex, String matricNo)
    {
        super(name,nationality,email,username,password,contactNo,EnumHelper.UserRole.STUDENT,sex);
        this.matricNo = matricNo;
        courseInfoList = new ArrayList<CourseInfo>();
        courseManager = new CourseManager();
    }

    public ArrayList<CourseInfo> getCourseInfoList()
    {
        return this.courseInfoList;
    }

    public void AddCourse(CourseInfo courseInfo)
    {
        courseManager.AddCourse(this, courseInfo);
    }

    public void DropCourse(CourseInfo courseInfo)
    {
        courseManager.DropCourse(this, courseInfo);
    }

    public void RegisteredCourses()
    {
        courseManager.RegisteredCourses(this);
    }

    public void ChangeCourseIndexNumber(int course_Code)
    {

    }

    public void SwapIndexNumber(int index_ID, int student_ID)
    {
        
    }

    public int CheckCourseVacancy(int indexId)
    {
        return 0;
    } 

    
}