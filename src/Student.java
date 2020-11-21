
import java.util.ArrayList;

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


    public ArrayList<StudentCourse> AddCourse(CourseInfo courseInfo, ArrayList<StudentCourse> studentCourseList)
    {
        return courseManager.AddCourse(this, courseInfo, studentCourseList);
    }

    public ArrayList<StudentCourse> DropCourse(CourseInfo courseInfo, ArrayList<StudentCourse> studentCourseList)
    {
        return courseManager.DropCourse(this, courseInfo, studentCourseList);
    }

    public void RegisteredCourses()
    {
        courseManager.RegisteredCourses(this);
    }

//    public CourseInfo ShowCourses(int indexNo)
//    {
//        return courseManager.RegisteredCourses(this, indexNo);
//    }

    public void ChangeCourseIndexNumber(CourseInfo oldClass, CourseInfo newClass)
    {
        courseManager.ChangeCourseIndexNumber(this,oldClass,newClass);
    }

    public void SwapIndexNumber(int index_ID, int student_ID)
    {
        
    }

    public int CheckCourseVacancy(int indexId)
    {
        return 0;
    } 

    public String getMatricNo()
    {
        return matricNo;
    }

    public ArrayList<CourseInfo> getCourseInfoList()
    {
        return this.courseInfoList;
    }

    public void addCourse(CourseInfo courseInfo)
    {
        this.courseInfoList.add(courseInfo);
    }

    public void dropCourse(CourseInfo courseInfo)
    {
        this.courseInfoList.remove(courseInfo);
    }

}