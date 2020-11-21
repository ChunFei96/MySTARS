import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.function.Predicate;

public class Admin extends User implements IAdmin{

    public Admin(String name, String nationality, String email, String username, String password, String contactNo,
                 EnumHelper.UserRole role, EnumHelper.Gender sex) {
        super(name, nationality, email, username, password, contactNo, EnumHelper.UserRole.ADMIN, sex);
    }

    private int getClassVacancy(int indexNumber){
        return 0;
    }

    public void EditStudentAccessPeriod(Student student, String accessPeriod)
    {
        student.setAccessPeriod(accessPeriod);
    }

    public void AddStudent()
    {
        String filepath = System.getProperty("user.dir") + "/StudentProfile";
        IOUtills.ReadFile(filepath + "/chunfei.txt");

        ArrayList<String> studentList = IOUtills.getFileInput();

        //<editor-fold desc="<User inputs>">
        Scanner sr = new Scanner(System.in);

        System.out.println("Enter name: ");
        String name = sr.nextLine();
        System.out.println("Student name: " + name);

        System.out.println("Enter nationality: ");
        String nationality = sr.nextLine();
        System.out.println("Student nationality: " + nationality);

        System.out.println("Enter email: ");
        String email = sr.nextLine();
        System.out.println("Student email: " + email);

        System.out.println("Enter contactNo: ");
        String contactNo = sr.nextLine();
        System.out.println("Student contactNo: " + contactNo);

        System.out.println("Enter sex (M/F): ");
        String sex = sr.nextLine();
        System.out.println("Student sex: " + sex);

        System.out.println("Enter matricNo: ");
        String matricNo = sr.nextLine();
        System.out.println("Student matricNo: " + matricNo);
        //</editor-fold>

        String username = name;  //Set username as name
        String password = PasswordUtils.generatePassword(8).toString(); //Generate Random password
        Enum gender = sex.equals("M") ? EnumHelper.Gender.MALE : EnumHelper.Gender.FEMALE;

        Student newStudent = new Student(name,nationality,email,username,
                password,contactNo, EnumHelper.UserRole.STUDENT, (EnumHelper.Gender) gender,
                matricNo,new Date().toString());

        String formatStudent = newStudent.ToStr();
        formatStudent = String.join("\r\n",studentList) + "\r\n" + formatStudent;


        //<editor-fold desc="<Add into StudentProfile>">
        new IOUtills("chunfei","txt",formatStudent,filepath);
        IOUtills.WriteFile();
        //</editor-fold>

        //<editor-fold desc="<Add into UserTable>">
         Account user_2 = new Account(username,password);
         user_2.setUserRole(EnumHelper.UserRole.STUDENT);
         user_2.addAccount();
        //</editor-fold>




        //<editor-fold desc="">
        //</editor-fold>






    }

    public void AddCourse(CourseInfo course)
    {

    }

    public void PrintStudentListByIndex(String indexNo)
    {
        String studentCourseFile = System.getProperty("user.dir") + "/StudentCourseTable/chunfei.txt";
        IOUtills.ReadFile(studentCourseFile);
        ArrayList<String> _StudentCourse = IOUtills.getFileInput();
        ArrayList<String> getStudents = new ArrayList<>();

        int found = 0;
        for(int i = 0; i < _StudentCourse.size();i++){
            String[] studentCourse = _StudentCourse.get(i).split(",");
            System.out.println("Course Index [" + (i+1) + "] = " + studentCourse[1]);

            if(indexNo.equals(studentCourse[1])){
                getStudents.add(studentCourse[0]);
                System.out.println("matched");
                found++;
            }
        }
        System.out.println("Total students: " + found);

        String studentProfileFile = System.getProperty("user.dir") + "/StudentCourseTable/chunfei.txt";
        IOUtills.ReadFile(studentProfileFile);
        ArrayList<String> _StudentProfile = IOUtills.getFileInput();


    }



    //private void Search

    public void PrintStudentListByCourse(CourseInfo courseInfo)
    {

    }

    public int CheckCourseVacancy(int indexId)
    {
        return 0;
    } 
}
