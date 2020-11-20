
public class Admin extends User implements IAdmin{

    public Admin(String name, String nationality, String email, String username, String password, String contactNo,
                 EnumHelper.UserRole role, EnumHelper.Gender sex) {
        super(name, nationality, email, username, password, contactNo, EnumHelper.UserRole.ADMIN, sex);
    }

    private int getClassVacancy(int indexNumber){
        return 0;
    }

    public void EditStudentAccessPeriod(Student student)
    {

    }

    public void AddStudent(Student student)
    {

    }

    public void AddCourse(CourseInfo course)
    {

    }

    public void PrintStudentListByIndex(String indexNo)
    {

    }

    public void PrintStudentListByCourse(CourseInfo courseInfo)
    {

    }

    public int CheckCourseVacancy(int indexId)
    {
        return 0;
    } 
}
