

public class ClassInfo {
    private String indexNo;
    private String courseCodeReference;
    private String groupNo;
    private String day;
    private String time;
    private String venue;
    private String remark;
    private String vacancy;
    private String queue;

    public ClassInfo(String classInfo)
    {
        String[] courseInfoArr = classInfo.split(",");
        indexNo = courseInfoArr[0];
        courseCodeReference = courseInfoArr[1];
        groupNo = courseInfoArr[2];
        day = courseInfoArr[3];
        time = courseInfoArr[4];
        venue = courseInfoArr[5];
        remark = courseInfoArr[6];
        vacancy = courseInfoArr[7];
        queue = courseInfoArr[8];
    }

    public void setIndexNo(String indexNo)
    {
        this.indexNo = indexNo;
    }

    public String getIndexNo()
    {
        return indexNo;
    }

    public String getClassInfo()
    {
        return (indexNo + " " + groupNo + " " + day + " " + time + " " + venue + " " + remark + " " +
                vacancy + " " + queue);
    }
}
