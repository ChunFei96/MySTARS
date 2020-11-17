
public class CourseInfo
{
    private String indexNo;
    private int groupNo;
    private String day;
    private String time;
    private String venue;
    private String remark;
    private String courseType;
    private String courseCode;


    public CourseInfo(String indexNo, int groupNo, String day, String time, String venue, String remark, String courseType, String courseCode)
    {
        this.indexNo = indexNo;
        this.groupNo = groupNo;
        this.day = day;
        this.time = time;
        this.venue = venue;
        this.remark = remark;
        this.courseType = courseType;
        this.courseCode = courseCode;
    }

    public static void GetCourseInfo(CourseInfo course)
    {
        System.out.println(course.indexNo);
        System.out.println(course.groupNo);
        System.out.println(course.day);
        System.out.println(course.time);
        System.out.println(course.venue);
        System.out.println(course.remark);
        System.out.println(course.courseType);
        System.out.println(course.courseCode);
    }

    public void AddCourseInfo()
    {

    }

    public void EditCourseInfo()
    {

    }

    public void RemoveCourseInfo()
    {

    }
}