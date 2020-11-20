

public class CourseInfo
{
    private String indexNo;
    private String code;
    private String name;
    private String type;
    private String groupNo;
    private String day;
    private String time;
    private String venue;
    private String remark;
    private String vacancy;
    private String queue;

    public CourseInfo(String courseInfo)
    {
        String[] courseInfoArr = courseInfo.split(",");
        indexNo = courseInfoArr[0];
        code = courseInfoArr[1];
        name = courseInfoArr[2];
        type = courseInfoArr[3];
        groupNo = courseInfoArr[4];
        day = courseInfoArr[5];
        time = courseInfoArr[6];
        venue = courseInfoArr[7];
        remark = courseInfoArr[8];
        vacancy = courseInfoArr[9];
        queue = courseInfoArr[10];
    }

    public String GetCourseInfo()
    {
        return (indexNo + " " + code + " " + name + " " + type + " " + groupNo + " " + day + " " + time + " " + venue + " " + remark + " " + vacancy + " " + queue );
    }

    public void setIndexNo(String indexNo)
    {
        this.indexNo = indexNo;
    }

    public String getIndexNo()
    {
        return indexNo;
    }

    public String getName() {return name;}
}