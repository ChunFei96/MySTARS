
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Student concrete class that implements student interface for mandatory function and extends User class for common attributes
 * @author Lee Chun Fei and Mindy Hwang Mei Hui
 * @version 1.0
 * @since 2020-11-15
 */
public class Student extends User implements IStudent{

    /**
     * student matriculation number
     */
    private String matricNo;
    /**
     * student access period start date
     */
    private String accessPeriodStart;
    /**
     * student access period start end
     */
    private String accessPeriodEnd;
    /**
     * student's list of courses
     */
    private ArrayList<CourseInfo> courseInfoList;
    /**
     * student uses CourseManager reference
     */
    private CourseManager courseManager;

    /**
     * Create a student object reference with attributes below
     * @param name
     * @param nationality
     * @param email
     * @param username
     * @param password
     * @param contactNo
     * @param role
     * @param sex
     * @param matricNo
     * @param accessPeriodStart
     * @param accessPeriodEnd
     */
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

    /**
     * Get student access period start date
     * @return start date in string
     */
    public String getAccessPeriodStart() {
        return accessPeriodStart;
    }

    /**
     * Set student access period start date
     * @param accessPeriodStart
     */
    public void setAccessPeriodStart(String accessPeriodStart) {
        this.accessPeriodStart = accessPeriodStart;
    }

    /**
     * Get student access period end date
     * @return end date in string
     */
    public String getAccessPeriodEnd() {
        return accessPeriodEnd;
    }

    /**
     * Set student access period end date
     * @param accessPeriodEnd
     */
    public void setAccessPeriodEnd(String accessPeriodEnd) {
        this.accessPeriodEnd = accessPeriodEnd;
    }

    /**
     * Get student list of courses
     * @return list of course object
     */
    public ArrayList<CourseInfo> getCourseInfoList()
    {
        return this.courseInfoList;
    }

    /**
     * Allow student to register course with vacancy available
     * @param courseInfo The course selected by student
     * @param isSwapIndex Flag to check if student performs the swap index with other student, bypass reduce vacancy or queue process
     */
    public void AddCourse(CourseInfo courseInfo, Boolean isSwapIndex)
    {
        courseManager.AddCourse(this, courseInfo, isSwapIndex);
    }
    /**
     * Allow student to drop their registered course
     * @param courseInfo The course dropped by student
     * @param isSwapIndex Flag to check if student performs the swap index with other student, bypass reduce vacancy or queue process
     */
    public void DropCourse(CourseInfo courseInfo, Boolean isSwapIndex)
    {
        courseManager.DropCourse(this, courseInfo, isSwapIndex);
    }
    /**
     * Allow student to print their registered courses
     */
    public void RegisteredCourses()
    {
        courseManager.RegisteredCourses(this);
    }
    /**
     * Allow student to change course's class index
     * @param oldClass The course's class index dropped by student
     * @param newClass The course's class index added by student
     * @param isSwapIndex Flag to check if student performs the swap index with other student, bypass reduce vacancy or queue process
     */
    public void ChangeCourseIndexNumber(CourseInfo oldClass, CourseInfo newClass, Boolean isSwapIndex)
    {
        courseManager.ChangeCourseIndexNumber(this,oldClass,newClass, isSwapIndex);
    }
    /**
     * Allow student to swap course's class index with another student
     * @param secondStudent Student 2 that agreed to swap course's class index with student 1
     * @param firstStudentCourse Student 1 course class's index that swap to student 2
     * @param secondStudentCourse Student 2 course class's index that swap to student 1
     * @param isSwapIndex Flag to check if student performs the swap index with other student, bypass reduce vacancy or queue process
     */
    public void SwapIndexNumber(Student secondStudent, CourseInfo firstStudentCourse, CourseInfo secondStudentCourse, Boolean isSwapIndex)
    {
        courseManager.SwapIndexNumber(this,secondStudent, firstStudentCourse,secondStudentCourse, isSwapIndex);
    }
    /**
     * Check course vacancy
     * @return the course's vacancy
     */
    public int CheckCourseVacancy()
    {
        int result = new CourseManager().CheckCourseVacancy();
        return result;
    }

    @Override
    public String ToStr() {
        String[] raw = new String[]{matricNo,accessPeriodStart,accessPeriodEnd};
        return super.ToStr() + "," + String.join(",",raw);
    }

    /**
     * Get student matriculation number
     * @return matriculation number in string
     */
    public String getMatricNo()
    {
        return matricNo;
    }

    /**
     * student add course to their course list
     * @param courseInfo
     */
    public void addCourse(CourseInfo courseInfo)
    {
        this.courseInfoList.add(courseInfo);
    }

    /**
     * student drop course fromt their course list
     * @param courseInfo
     */
    public void dropCourse(CourseInfo courseInfo)
    {
        this.courseInfoList.remove(courseInfo);
    }

    /**
     * Retrieve student information from StudentProfile.txt
     * @param skipByColumnName column name to skip when retrieving
     * @return student information in string
     */
    public String getAllRecordInDB(String skipByColumnName){
        ArrayList<String> d = new ArrayList<String>();
        for(var i :  Singleton_StudentProfile.getInstance().studentProfileDB){
            if(!(i.getMatricNo().equals(skipByColumnName))){
                d.add(getRecordInDB(i));
            }
        }
        return String.join("\r\n",d);
    }
    /**
     * Get student information
     * @param data student object reference
     * @return student information in string
     */
    public String getRecordInDB(Student data){
        String [] d = new String[] {data.getName(),data.getNationality(),data.getEmail(),data.getUsername()
                ,data.getPassword(),data.getContactNo(),data.getUserRole().toString(),
                data.getGender().toString(),data.getMatricNo(),data.getAccessPeriodStart(),data.getAccessPeriodEnd()};
        return String.join(",",d);
    }

    /**
     * Print list of student details
     * @param students
     */
    public void PrintStudents(ArrayList<Student> students){

        if(students.size() > 0){

        String leftAlign = "| %-11s | %-43s |%-9s | %-11s | %-7s |%-9s | %-12s | %-10s | %-14s | %-8s |%-14s |%n";

        System.out.format("+-------------+-------------+--------------+-------------+------------+-----------+------------+-------+-------------+----------------------------+%n");
        System.out.format("|    Name     | NATIONALITY |     EMAIL    |   USERNAME  |  PASSWORD  | CONTACTNO |    ROLE    |  SEX  |   MATRIC NO | ACCESS PERIOD START - END  |%n");
        System.out.format("+-------------+-------------+--------------+-------------+------------+-----------+------------+-------+-------------+----------------------------+%n");

        for (int i = 0; i < students.size(); i++){
            System.out.format(leftAlign, i+1 + ": " + students.get(i).getName(),students.get(i).getNationality(),students.get(i).getEmail(),
                    students.get(i).getUsername(),students.get(i).getPassword(),students.get(i).getContactNo(),
                    students.get(i).getUserRole(),students.get(i).getGender(),(students.get(i).getAccessPeriodStart() + "-" + students.get(i).getAccessPeriodEnd()));
        }
        System.out.format("+-------------+-------------+--------------+-------------+------------+-----------+------------+-------+-------------+----------------------------+%n");
        }
    }

    /**
     * Check if student duplicated
     * @param newStudent add new student object reference
     * @return if duplication true of false
     */
    public boolean checkDuplicateStudent(Student newStudent){
        boolean output = true;
        var students = Singleton_StudentProfile.getInstance().studentProfileDB;
        var getClassInfo = students.stream().filter(x -> x.getMatricNo().equals(newStudent.getMatricNo()) || x.getEmail().equals(newStudent.getEmail())).collect(Collectors.toList());  // && x.getUsername().equals(newStudent.getUsername()

        if(getClassInfo.size() == 0){
            output = false;
        }
        return output;
    }

}