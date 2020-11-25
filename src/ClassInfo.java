
/**
 * Class's attributes and functions
 * @author Lee Chun Fei and Mindy Hwang Mei Hui
 * @version 1.0
 * @since 2020-11-13
 */
public class ClassInfo {
    /**
     * class's index number
     */
    private String indexNo;
    /**
     * class's course code
     */
    private String courseCodeReference;
    /**
     * class's group number
     */
    private Integer groupNo;
    /**
     * class's day
     */
    private String day;
    /**
     * class's period
     */
    private String time;
    /**
     * class's venue
     */
    private String venue;
    /**
     * class's remark
     */
    private String remark;
    /**
     * class's vacancy
     */
    private Integer vacancy;
    /**
     * class's waiting list for registration
     */
    private Integer queue;

    /**
     * Create a class object with class information
     * @param classInfo comma delimited string from .txt file and split to attributes respectively
     */
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

    /**
     * Retrieve this class information
     * @return string concatenated class information
     */
    public String getClassInfo()
    {
        return (indexNo + " " + groupNo + " " + day + " " + time + " " + venue + " " + remark + " " +
                vacancy + " " + queue);
    }

    /**
     * Get class's index number
     * @return class's index number
     */
    public String getIndexNo() {
        return indexNo;
    }

    /**
     * Set class's index number
     * @param indexNo index number input
     */
    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    /**
     * Get class's course code
     * @return course code in string
     */
    public String getCourseCodeReference() {
        return courseCodeReference;
    }

    /**
     * Set class's course code
     * @param courseCodeReference
     */
    public void setCourseCodeReference(String courseCodeReference) {
        this.courseCodeReference = courseCodeReference;
    }

    /**
     * Get class's group number
     * @return group number in integer
     */
    public Integer getGroupNo() {
        return groupNo;
    }

    /**
     * Set class's group number
     * @param groupNo
     */
    public void setGroupNo(Integer groupNo) {
        this.groupNo = groupNo;
    }

    /**
     * Get class's day
     * @return day in string
     */
    public String getDay() {
        return day;
    }

    /**
     * Set class's day
     * @param day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * Get class's period
     * @return period in string
     */
    public String getTime() {
        return time;
    }

    /**
     * Set class's period
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Get class's venue
     * @return venue in string
     */
    public String getVenue() {
        return venue;
    }

    /**
     * Set class's venue
     * @param venue
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    /**
     * Get class's remark
     * @return remark in string
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Set class remark
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * Get class's vacancy
     * @return vacancy in integer
     */
    public Integer getVacancy() {
        return vacancy;
    }

    /**
     * Set class's vacancy
     * @param vacancy
     */
    public void setVacancy(Integer vacancy) {
        this.vacancy = vacancy;
    }

    /**
     * Get class's waiting list
     * @return queue number in integer
     */
    public Integer getQueue() {
        return queue;
    }

    /**
     * Set class's queue number
     * @param queue
     */
    public void setQueue(Integer queue) {
        this.queue = queue;
    }

    /**
     * Print class information with format
     * @param leftAlign left alignment offset
     * @param i number of classes
     */
    public void printClassInfo(String leftAlign,int i){
        System.out.format(leftAlign, i+1 + ": " + getIndexNo(),getGroupNo(),getDay(),getTime(),getVenue(),
                getRemark(),getVacancy(),getQueue());
    }

    /**
     * Edit class information
     * @param editOptions string array of options
     */
    public void EditClassInfoOptions(String [] editOptions){
        System.out.println("============");
        for(int k = 0; k < editOptions.length;k++){
            System.out.println((k+1) + ". Edit " +  editOptions[k]);
        }
        System.out.println("9. Back");
        System.out.println("============");
        System.out.println("Enter your input: ");
    }

    /**
     * Retrieve class's information
     * @param data class object
     * @return string concatenated class information
     */
    public String getRecordInDB(ClassInfo data){
        String [] d = new String[] {data.getIndexNo(),data.getCourseCodeReference(),data.getGroupNo().toString(),data.getDay(),
                data.getTime(),data.getVenue(),data.getRemark(),data.getVacancy().toString(),data.getQueue().toString()};
        return String.join(",",d);
    }

}
