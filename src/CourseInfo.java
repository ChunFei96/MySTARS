import java.util.ArrayList;

public class CourseInfo
{

    private String code;
    private String name;
    private String type;

    private ArrayList<ClassInfo> classArrayList;


    // constructor for add course
    public CourseInfo(CourseInfo courseInfo)
    {
        this.code = courseInfo.code;
        this.name = courseInfo.name;
        this.type = courseInfo.type;

        classArrayList = new ArrayList<ClassInfo>();
    }

    // constructor for DB init
    public CourseInfo(String courseInfo)
    {
        String[] courseInfoArr = courseInfo.split(",");
        code = courseInfoArr[0];
        name = courseInfoArr[1];
        type = courseInfoArr[2];

        classArrayList = new ArrayList<ClassInfo>();
    }

    public String getCourseInfo()
    {
        return (code + " " + name + " " + type);
    }

    public String getName() {return name;}

    public String getCode() {return code;}

    public void addClass(ClassInfo classInfo)
    {
        this.classArrayList.add(classInfo);
    }

    public ArrayList<ClassInfo> getClassList()
    {
        return this.classArrayList;
    }
}