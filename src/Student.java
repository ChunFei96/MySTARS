
import java.util.ArrayList;

public class Student extends User implements IStudent{

    private String matricNo;
    private String accessPeriodStart;
    private String accessPeriodEnd;
    private ArrayList<CourseInfo> courseInfoList;
    private CourseManager courseManager;



    public Student(String name, String nationality, String email, String username, String password, String contactNo,
                   EnumHelper.UserRole role, EnumHelper.Gender sex, String matricNo, String accessPeriodStart,String accessPeriodEnd)
    {
        super(name,nationality,email,username,password,contactNo,EnumHelper.UserRole.STUDENT,sex);
        this.matricNo = matricNo;
        this.setAccessPeriodStart(accessPeriodStart);
        this.setAccessPeriodEnd(accessPeriodEnd);
        courseInfoList = new ArrayList<CourseInfo>();
        courseManager = new CourseManager();
    }

    public String getAccessPeriodStart() {
        return accessPeriodStart;
    }

    public void setAccessPeriodStart(String accessPeriodStart) {
        this.accessPeriodStart = accessPeriodStart;
    }

    public String getAccessPeriodEnd() {
        return accessPeriodEnd;
    }

    public void setAccessPeriodEnd(String accessPeriodEnd) {
        this.accessPeriodEnd = accessPeriodEnd;
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

    public int CheckCourseVacancy(String indexId)
    {
        return 0;
    }

    @Override
    public String ToStr() {
        String[] raw = new String[]{matricNo,accessPeriodStart,accessPeriodEnd};
        return super.ToStr() + "," + String.join(",",raw);
    }
    public String getMatricNo()
    {
        return matricNo;
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