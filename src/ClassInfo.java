

public class ClassInfo {
    private String indexNo;
    private String courseCodeReference;
    private Integer groupNo;
    private String day;
    private String time;
    private String venue;
    private String remark;
    private Integer vacancy;
    private Integer queue;

    public ClassInfo(String classInfo)
    {
        String[] courseInfoArr = classInfo.split(",");
        indexNo = courseInfoArr[0];
        courseCodeReference = courseInfoArr[1];
        groupNo = Integer.valueOf(courseInfoArr[2]);
        day = courseInfoArr[3];
        time = courseInfoArr[4];
        venue = courseInfoArr[5];
        remark = courseInfoArr[6];
        vacancy = Integer.valueOf(courseInfoArr[7]);
        queue = Integer.valueOf(courseInfoArr[8]);
    }

    public String getClassInfo()
    {
        return (indexNo + " " + groupNo + " " + day + " " + time + " " + venue + " " + remark + " " +
                vacancy + " " + queue);
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public String getCourseCodeReference() {
        return courseCodeReference;
    }

    public void setCourseCodeReference(String courseCodeReference) {
        this.courseCodeReference = courseCodeReference;
    }

    public Integer getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(Integer groupNo) {
        this.groupNo = groupNo;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getVacancy() {
        return vacancy;
    }

    public void setVacancy(Integer vacancy) {
        this.vacancy = vacancy;
    }

    public Integer getQueue() {
        return queue;
    }

    public void setQueue(Integer queue) {
        this.queue = queue;
    }
}