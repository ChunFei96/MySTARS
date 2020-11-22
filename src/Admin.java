import java.io.File;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Admin extends User implements IAdmin{

    public Admin(String name, String nationality, String email, String username, String password, String contactNo,
                 EnumHelper.UserRole role, EnumHelper.Gender sex) {
        super(name, nationality, email, username, password, contactNo, EnumHelper.UserRole.ADMIN, sex);
    }

    private int getClassVacancy(int indexNumber){
        return 0;
    }

    public void EditStudentAccessPeriod()
    {
        Scanner sr = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter Student Matrix No: ");
        String studentMatrixNo = sr.nextLine();

        Student student = null;

        for(Student j : Singleton_StudentProfile.getInstance().studentProfileDB){
            if(j.getMatricNo().equals(studentMatrixNo)){
                student = j;  //Assign the student obj

                String [] info = new String[]{
                        j.getName(),j.getMatricNo(),j.getAccessPeriodStart(),j.getAccessPeriodEnd()
                };

                System.out.println("Retrieving Student Info: ");
                System.out.println(String.join(" | ",info));
                break;
            }
        }

        boolean valid = false;
        do{
            try{
                System.out.println("Enter New Access Period (dd/MM/yyyy): ");
                String _accessPeriod = sr.nextLine();

                new SimpleDateFormat("dd/MM/yyyy").parse(_accessPeriod);
                student.setAccessPeriodEnd(_accessPeriod);
                valid = true;
            }
            catch(Exception e){
                System.out.println("Invalid access period, please try again!");
            }
        }
        while(!valid);
        System.out.println("New Access Period: " + student.getAccessPeriodEnd());
    }

    public void AddStudent()
    {
        String filepath = System.getProperty("user.dir") + "/StudentProfile";
        IOUtills.ReadFile(filepath + "/user.txt");

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

        //<editor-fold desc="<Get Current Date>">
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String getTodayDate = myDateObj.format(myFormatObj);
        //</editor-fold>

        //TODO: set Period End - today + 3months
        String accessPeriodEnd = myDateObj.plusMonths(3).format(myFormatObj);
        Student newStudent = new Student(name,nationality,email,username,
                password,contactNo, EnumHelper.UserRole.STUDENT, (EnumHelper.Gender) gender,
                matricNo,getTodayDate,accessPeriodEnd);

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
    }

    public void AddCourse(CourseInfo course)
    {

        //Requirment Check registred courses max - 8; return error msg > 8
    }

    public void PrintStudentListByIndex(String indexNo)
    {
        ArrayList<StudentCourse> data = Singleton_StudentCourse.getInstance().studentCourseDB;
        List<StudentCourse> _StudentProfile = data.stream().filter(c -> c.getClassIndex().equals(indexNo)).collect(Collectors.toList());
        ArrayList<Student> students = Singleton_StudentProfile.getInstance().studentProfileDB;

        int counter = 0;
        if(_StudentProfile.size() > 0){
            System.out.println("\r\n===============");
            System.out.println("Print Student List by Index ID: " + indexNo);
            System.out.println("===============");
            for(var j : _StudentProfile){

                List<Student> stds = students.stream().filter(m -> m.getMatricNo().
                        equals(j.getStudentMatricNo()))
                        .collect(Collectors.toList());

                if(stds.stream().count() > 0){
                    var studentInfo = stds.get(0);
                    String name = studentInfo.getName();
                    String nationality = studentInfo.getNationality();
                    String sex = studentInfo.getGender().toString();
                    String [] reportContent = new String[] {name,nationality,sex};
                    System.out.println((counter+1) + ". " + String.join(" | ",reportContent));
                    counter++;
                }
            }
            System.out.println("=====END=====");
        }
    }

    public void PrintStudentListByCourse(String courseIndex)
    {

        ArrayList<StudentCourse> data = Singleton_StudentCourse.getInstance().studentCourseDB;
        List<StudentCourse> _StudentProfile = data.stream().filter(c -> c.getCourseCode().equals(courseIndex)).collect(Collectors.toList());


        int k = 0;
        /*
        String studentCourseFile = System.getProperty("user.dir") + "/StudentCourseTable/user.txt";
        IOUtills.ReadFile(studentCourseFile);
        ArrayList<String> _StudentCourse = IOUtills.getFileInput();
        ArrayList<String> getStudents = new ArrayList<>();

        for(int j = 0; j < _StudentCourse.size();j++){
            String[] studentInfo = _StudentCourse.get(j).split(",");
            String _courseName = studentInfo[2];
            if(_courseName.equals(courseName)){
                String name = studentInfo[0];
                getStudents.add(name);
            }
        }

        if(getStudents.size() > 0){
            String studentProfileFile = System.getProperty("user.dir") + "/StudentProfile/user.txt";
            IOUtills.ReadFile(studentProfileFile);
            ArrayList<String> _StudentProfile = IOUtills.getFileInput();

            System.out.println("\r\n===============");
            System.out.println("Print Student List by Course Name: " + courseName);
            System.out.println("===============");

            for(int j = 0; j < _StudentProfile.size();j++){
                String[] studentInfo = _StudentProfile.get(j).split(",");
                String name = studentInfo[0];

                if(getStudents.contains(name)){
                    String nationality = studentInfo[1];
                    String sex = studentInfo[7];
                    String [] reportContent = new String[] {name,nationality,sex};
                    System.out.println((j+1) + ". " + String.join(" | ",reportContent));
                }
            }
            System.out.println("=====END=====");
        }
         */
    }

    public int CheckCourseVacancy(String indexId)
    {
        //Check from ClassTable and Compare last 2 param
        //Need to default a MAX vacancy for class
        System.out.println("hello");
        return 0;
    }
}
