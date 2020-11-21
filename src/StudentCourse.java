

public class StudentCourse {
    private String studentMatricNo;
    private String courseCode;
    private String courseIndex;

    public StudentCourse()
    {

    }
    // constructor for Student Course init
    public StudentCourse(String studentCourse)
    {
        String[] studentCourseArr = studentCourse.split(",");
        studentMatricNo = studentCourseArr[0];
        courseCode = studentCourseArr[1];
        courseIndex = studentCourseArr[2];
    }

    public String getStudentMatricNo() {
        return studentMatricNo;
    }

    public void setStudentMatricNo(String studentMatricNo) {
        this.studentMatricNo = studentMatricNo;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseIndex() {
        return courseIndex;
    }

    public void setCourseIndex(String courseIndex) {
        this.courseIndex = courseIndex;
    }
}
