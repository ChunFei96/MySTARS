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
        ArrayList<String> output = new ArrayList<>();

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
                    Student studentInfo = stds.get(0);
                    String name = studentInfo.getName();
                    String nationality = studentInfo.getNationality();
                    String sex = studentInfo.getGender().toString();
                    String [] reportContent = new String[] {name,nationality,sex};
                    String print = (counter+1) + ". " + String.join(" | ",reportContent);
                    System.out.println(print);

                    output.add(print);
                    counter++;
                }
            }
            System.out.println("=====END=====");

            Scanner sr = new Scanner(System.in);
            boolean valid = false;
            do{
                System.out.println("Do you want to output the report? (Y/N)");
                String generateTxt = sr.nextLine().toUpperCase();

                if(generateTxt.equals("Y")){
                    Date currentTime = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    String strDate = formatter.format(currentTime);
                    IOUtills.setFilename("StudentList_" + indexNo + "_" + strDate);
                    IOUtills.setDirectoryName(System.getProperty("user.dir"));
                    IOUtills.setFiletype("txt");
                    IOUtills.setContent(String.join("\r\n",output));
                    IOUtills.WriteFile();
                    valid = true;
                }
                else if(generateTxt.equals("N")){
                    valid = true;
                    System.out.println("Pending message for NOT printing the report.");
                }
            }while(!valid);
        }
    }

    public void PrintStudentListByCourse(String courseCode)
    {
        ArrayList<StudentCourse> data = Singleton_StudentCourse.getInstance().studentCourseDB;
        List<StudentCourse> _StudentProfile = data.stream().filter(c -> c.getCourseCode().equals(courseCode)).collect(Collectors.toList());

        ArrayList<Student> students = Singleton_StudentProfile.getInstance().studentProfileDB;
        ArrayList<String> output = new ArrayList<>();

        int counter = 0;
        if(_StudentProfile.size() > 0){
            System.out.println("\r\n===============");
            System.out.println("Print Student List by Course Name: " + _StudentProfile.get(0).getCourseCode());
            System.out.println("===============");

            for(var j : _StudentProfile){

                List<Student> stds = students.stream().filter(m -> m.getMatricNo().
                        equals(j.getStudentMatricNo()))
                        .collect(Collectors.toList());

                if(stds.stream().count() > 0){
                    Student studentInfo = stds.get(0);
                    String name = studentInfo.getName();
                    String nationality = studentInfo.getNationality();
                    String sex = studentInfo.getGender().toString();
                    String [] reportContent = new String[] {name,nationality,sex};

                    String print = (counter+1) + ". " + String.join(" | ",reportContent);
                    System.out.println(print);

                    output.add(print);
                    counter++;
                }
            }
            System.out.println("=====END=====");

            Scanner sr = new Scanner(System.in);
            boolean valid = false;
            do{
                System.out.println("Do you want to output the report? (Y/N)");
                String generateTxt = sr.nextLine().toUpperCase();

                if(generateTxt.equals("Y")){
                    Date currentTime = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    String strDate = formatter.format(currentTime);
                    IOUtills.setFilename("StudentList_" + _StudentProfile.get(0).getCourseCode() + "_" + strDate);
                    IOUtills.setDirectoryName(System.getProperty("user.dir"));
                    IOUtills.setFiletype("txt");
                    IOUtills.setContent(String.join("\r\n",output));
                    IOUtills.WriteFile();
                    valid = true;
                }
                else if(generateTxt.equals("N")){
                    valid = true;
                    System.out.println("Pending message for NOT printing the report.");
                }
            }while(!valid);
        }
    }

    public int CheckCourseVacancy()
    {
        int counter = 0;
        int c = 0;
        boolean isCourseValid = false;
        boolean isClassValid = false;
        Scanner sr = new Scanner(System.in);

        ArrayList<CourseInfo> CourseInfo = Singleton_CourseInfo.getInstance().courseInfoDB;
        for(CourseInfo Course : CourseInfo){
            System.out.println((counter + 1) + ". " + Course.getCode() + " " + Course.getName());
            counter++;
        }

        do{
            System.out.println("Select A Course: " );
            int courseOption = sr.nextInt();

            if(courseOption > 0 && courseOption <= CourseInfo.size()){
                CourseInfo selectedCourse = CourseInfo.get(courseOption-1);

                for(var classInfo : selectedCourse.getClassList()){
                    System.out.println((c+1)  + ". " + classInfo.getCourseCodeReference() + "'s index number: " + classInfo.getIndexNo());
                    c++;
                }

                do{
                    System.out.println("Select A Class Index: ");
                    int classOption = sr.nextInt();

                    if(classOption > 0 && classOption <= selectedCourse.getClassList().size()){
                        var selectedClassIndex = selectedCourse.getClassList().get(classOption-1);
                        System.out.println("Total available vacancies: " + selectedClassIndex.getVacancy());
                        isClassValid = true;
                    }
                }while(!isClassValid);

                if(isClassValid){
                    isCourseValid = true;
                }
            }
        }while(!isCourseValid);
        return 0;
    }
}
