
/**
 * Student course class to store the relationship between students and courses
 * @author Krystal
 * @version 1.0
 * @since 2020-11-12
 */
public class StudentCourse {
    /**
     * student's matriculation number
     */
    private String studentMatricNo;
    /**
     * course's code
     */
    private String courseCode;
    /**
     * class's index
     */
    private String classIndex;

    /**
     * Create a empty StudentCourse object
     */
    public StudentCourse()
    {

    }

    /**
     * Create a StudentCourse object with attributes below
     * @param studentCourse
     */
    public StudentCourse(String studentCourse)
    {
        String[] studentCourseArr = studentCourse.split(",");
        studentMatricNo = studentCourseArr[0];
        courseCode = studentCourseArr[1];
        classIndex = studentCourseArr[2];
    }

    /**
     * Get student matriculation number
     * @return matriculation number in string
     */
    public String getStudentMatricNo() {
        return studentMatricNo;
    }

    /**
     * Set student matriculation number
     * @param studentMatricNo
     */
    public void setStudentMatricNo(String studentMatricNo) {
        this.studentMatricNo = studentMatricNo;
    }

    /**
     * Get course's code
     * @return course's code in string
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Set course's code
     * @param courseCode
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     *  Get class's index
     * @return class's index in string
     */
    public String getClassIndex() {
        return classIndex;
    }

    /**
     * Set class's index
     * @param classIndex
     */
    public void setClassIndex(String classIndex) {
        this.classIndex = classIndex;
    }
}
