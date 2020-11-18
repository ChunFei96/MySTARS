package src;
import java.util.*;

public class Student extends User{

    private String matricNo;
    private ArrayList<CourseInfo> courseInfoList;

    
    public Student(String name, String nationality, String email, String username, String password, String contactNo, String matricNo)
    {
        super(name,nationality,email,username,password,contactNo);
        this.matricNo = matricNo;
        courseInfoList = new ArrayList<CourseInfo>();
    }

    public void AddCourse(CourseInfo courseInfo)
    {
        if(!IsCourseDuplicate(courseInfo))
            this.courseInfoList.add(courseInfo);

    }

    public void DropCourse(CourseInfo courseInfo)
    {
        if(IsCourseExist(courseInfo))
            this.courseInfoList.remove(courseInfo);

    }

    public void RegisteredCourses()
    {
        if(courseInfoList != null && courseInfoList.size() > 0)
        {
            for(int i=0; i < courseInfoList.size() ; i++)
            {
                System.out.println(i+1 + ": " + courseInfoList.get(i).GetCourseInfo());
            }
        }
        else
            System.out.println("There is no registerd course(s) for " + super.getName() + "!");
    }

    public void ChangeCourseIndexNumber(int course_Code)
    {

    }



    public void SwapIndexNumber(int index_ID, int student_ID)
    {
        
    }

    private Boolean IsCourseDuplicate(CourseInfo courseInfo)
    {
        if(courseInfoList != null && courseInfoList.size() > 0)
        {
            for(int i=0; i < courseInfoList.size() ; i++)
            {
                if(courseInfoList.get(i).getIndexNo() == courseInfo.getIndexNo()){
                    System.out.println( super.getName() + " already registered " + courseInfo.getIndexNo() );
                    return true;
                }
            }
        }
        return false;
    }

    private Boolean IsCourseExist(CourseInfo courseInfo)
    {
        if(courseInfoList != null && courseInfoList.size() > 0)
        {
            for(int i=0; i < courseInfoList.size() ; i++)
            {
                if(courseInfoList.get(i).getIndexNo() == courseInfo.getIndexNo()){
                    return true;
                }
            }
        }
        return false;
    }
}