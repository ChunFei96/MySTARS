
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Student extends User implements IStudent{

    private String matricNo;
    private String accessPeriodStart;
    private String accessPeriodEnd;
    private ArrayList<CourseInfo> courseInfoList;
    private CourseManager courseManager;

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

    public String getAccessPeriodStart() {
        return accessPeriodStart;
    }

    public void setAccessPeriodStart(String accessPeriodStart) {
        this.accessPeriodStart = accessPeriodStart;
    }

    public String getAccessPeriodEnd() {
        return accessPeriodEnd;
    }

    public void setAccessPeriodEnd(String accessPeriodEnd) {
        this.accessPeriodEnd = accessPeriodEnd;
    }

    public ArrayList<CourseInfo> getCourseInfoList()
    {
        return this.courseInfoList;
    }

    public void AddCourse(CourseInfo courseInfo, Boolean isSwapIndex)
    {
        courseManager.AddCourse(this, courseInfo, isSwapIndex);
    }

    public void DropCourse(CourseInfo courseInfo, Boolean isSwapIndex)
    {
        courseManager.DropCourse(this, courseInfo, isSwapIndex);
    }

    public void RegisteredCourses()
    {
        courseManager.RegisteredCourses(this);
    }


    public void ChangeCourseIndexNumber(CourseInfo oldClass, CourseInfo newClass)
    {
        courseManager.ChangeCourseIndexNumber(this,oldClass,newClass);
    }

    public void SwapIndexNumber(int index_ID, int student_ID)
    {
        
    }

    public int CheckCourseVacancy()
    {
        return 0;
    }

    @Override
    public String ToStr() {
        String[] raw = new String[]{matricNo,accessPeriodStart,accessPeriodEnd};
        return super.ToStr() + "," + String.join(",",raw);
    }

    public String getMatricNo()
    {
        return matricNo;
    }

    public void addCourse(CourseInfo courseInfo)
    {
        this.courseInfoList.add(courseInfo);
    }

    public void dropCourse(CourseInfo courseInfo)
    {
        this.courseInfoList.remove(courseInfo);
    }

    public String getAllRecordInDB(String skipByColumnName){
        ArrayList<String> d = new ArrayList<String>();
        for(var i :  Singleton_StudentProfile.getInstance().studentProfileDB){
            if(!(i.getMatricNo().equals(skipByColumnName))){
                d.add(getRecordInDB(i));
            }
        }
        return String.join("\r\n",d);
    }

    public String getRecordInDB(Student data){
        String [] d = new String[] {data.getName(),data.getNationality(),data.getEmail(),data.getUsername()
                ,data.getPassword(),data.getContactNo(),data.getUserRole().toString(),
                data.getGender().toString(),data.getMatricNo(),data.getAccessPeriodStart(),data.getAccessPeriodEnd()};
        return String.join(",",d);
    }

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

    public boolean checkDuplicateStudent(Student newStudent){
        boolean output = true;

        var students = Singleton_StudentProfile.getInstance().studentProfileDB;

        var getClassInfo = students.stream().filter(x -> x.getMatricNo().equals(newStudent.getMatricNo())
                && x.getEmail().equals(newStudent.getEmail()) && x.getUsername().equals(newStudent.getUsername()))
                .collect(Collectors.toList());

        if(getClassInfo.size() == 0){
            output = false;
        }

        return output;
    }

}