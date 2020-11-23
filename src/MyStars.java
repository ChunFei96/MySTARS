
import java.io.Console;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MyStars {

    private  static Console console;
    private final static int MAXCOURSELIMIT = 8;

    public static void main(String[] args) throws Exception {


        //Create new user: <<user_2>>
//        Account user_2 = new Account("chunfei","abc123");
//        user_2.setUserRole(EnumHelper.UserRole.STUDENT);
//        user_2.addAccount();


        //Test Student Acc
        //String username = "chunfei";
        //String password = "abc123";

        //String username = "Mike";
        //String password = "[C@368239c8";

        //Test Admin Acc
        String username = "TS";
        String password = "[C@244038d0";

        //Run by Console
        /*
        console = System.console();

        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        String username = console.readLine("Enter your username: ");
        System.out.println("Welcome " + username);

        char[] password = console.readPassword("Enter your password: ");
        //console.printf("Password entered was: %s%n", new String(passwordArray));
        */



        // Init all db
        InitCourseDB();
        InitStudentCourseDB();
        InitUserDB();
        InitStudentProfileDB();




        Login login = new Login(username,new String(password));
        if(login.validateLogin()) {
            System.out.println("\nWelcome back! " + login.getMyUsername());

            if(login.getUser().getUserRole() == EnumHelper.UserRole.STUDENT){
                Student student = login.getStudentProfile();
                StudentSelection(student);
            }
            else if(login.getUser().getUserRole() == EnumHelper.UserRole.ADMIN){
                Admin admin = login.getAdminProfile();
                AdminSelection(admin);
            }
            else
                System.out.println("Invalid Username or Password! Please try again.");
        }

    }

    private static void StudentMenu()
    {
        System.out.println("\nPlease select option below: ");
			
        System.out.println("1: Add Course");
        System.out.println("2: Drop Course");
        System.out.println("3: Check/Print Courses Registered");
        System.out.println("4: Check Vacancies Available");
        System.out.println("5: Change Index Number of Course");
        System.out.println("6: Swap Index Number with Another Student");
        System.out.println("7: Log out");
    }

    private static void AdminMenu(){
        System.out.println("====== Welcome Admin ======");
        System.out.println("1. Add Student");
//        System.out.println("2. Edit Student");
        System.out.println("2. Add Course");
        System.out.println("3. Update Course");
        System.out.println("4. Edit Student Access");
        System.out.println("5. Print StudentList By Index Number");
        System.out.println("6. Print StudentList By Course");
        System.out.println("7. Retrieve Class Vacancy");
        System.out.println("8. Logout");
        System.out.println("=====================");
    }

    private static void CourseMenu()
    {
        Singleton_CourseInfo singleton_courseInfo = Singleton_CourseInfo.getInstance();

        System.out.println("\n====================== Course information menu =========================");
        if(singleton_courseInfo.courseInfoDB != null && singleton_courseInfo.courseInfoDB.size() > 0)
        {
            int i;
            String leftAlign = "| %-11s | %-43s |%-9s |%n";

            System.out.format("+-------------+---------------------------------------------+----------+%n");
            System.out.format("| Course Code |                  Course Name                |   Type   |%n");
            System.out.format("+-------------+---------------------------------------------+----------+%n");
            for (i = 0; i < singleton_courseInfo.courseInfoDB.size(); i++) {
                System.out.format(leftAlign, i+1 + ": " + singleton_courseInfo.courseInfoDB.get(i).getCode(), singleton_courseInfo.courseInfoDB.get(i).getName(), singleton_courseInfo.courseInfoDB.get(i).getType());
            }
            System.out.format("+-------------+---------------------------------------------+----------+%n");
            System.out.println(i+1 + ": Back to main menu");
            System.out.println("Please select a course: ");
        }
    }

    private  static void ClassMenu(CourseInfo course)
    {
        String menuTitle = course.getCode() + " " + course.getName();
        System.out.println("\n=========================================== " + menuTitle + " ================================================");
        if(course != null)
        {
            int i;
            String leftAlign = "| %-11s | %-7s |%-9s | %-12s | %-10s | %-14s | %-8s |%-14s |%n";

            System.out.format("+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");
            System.out.format("| Class Index |  Group  |    Day   |    Period    |    Venue   |     Remark     |  Vacancy | Waiting list  |%n");
            System.out.format("+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");

            for (i = 0; i < course.getClassList().size(); i++) {
                System.out.format(leftAlign, i+1 + ": " + course.getClassList().get(i).getIndexNo(),course.getClassList().get(i).getGroupNo(),course.getClassList().get(i).getDay(),course.getClassList().get(i).getTime(),course.getClassList().get(i).getVenue(),
                        course.getClassList().get(i).getRemark(),course.getClassList().get(i).getVacancy(),course.getClassList().get(i).getQueue());
            }
            System.out.format("+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");
            System.out.println(i+1 + ": Back to main menu");
            System.out.println("Please select a class to register: ");
        }
    }

    private  static void SwapClassMenu(CourseInfo course, Student student)
    {
        String menuTitle = student.getName() + " " +  course.getCode() + " " + course.getName();
        System.out.println("\n========================================= " + menuTitle + " ==============================================");
        if(course != null)
        {
            String leftAlign = "| %-11s | %-7s |%-9s | %-12s | %-10s | %-14s | %-8s |%-14s |%n";

            System.out.format("+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");
            System.out.format("| Class Index |  Group  |    Day   |    Period    |    Venue   |     Remark     |  Vacancy | Waiting list  |%n");
            System.out.format("+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");
            for (int i = 0; i < course.getClassList().size(); i++) {
                System.out.format(leftAlign, i+1 + ": " + course.getClassList().get(i).getIndexNo(),course.getClassList().get(i).getGroupNo(),course.getClassList().get(i).getDay(),course.getClassList().get(i).getTime(),course.getClassList().get(i).getVenue(),
                        course.getClassList().get(i).getRemark(),course.getClassList().get(i).getVacancy(),course.getClassList().get(i).getQueue());
            }
            System.out.format("+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");
        }
    }

    private  static void ShowClassVacancyMenu(CourseInfo course)
    {
        String menuTitle = course.getCode() + " " + course.getName();
        System.out.println("\n===================== " + menuTitle + " ========================");
        if(course != null)
        {
            String leftAlign = "| %-11s | %-23s | %-13s | %n";

            System.out.format("+-------------+-------------------------+---------------+%n");
            System.out.format("| Class Index |    Vacancy Available    | Waiting list  |%n");
            System.out.format("+-------------+-------------------------+---------------+%n");

            for (int i = 0; i < course.getClassList().size(); i++) {
                ClassInfo classInfo = course.getClassList().get(i);
                System.out.format(leftAlign, classInfo.getIndexNo() , classInfo.getVacancy(), classInfo.getQueue());
            }
            System.out.format("+-------------+-------------------------+---------------+%n");
        }
    }

    private  static int ChangeClassMenu(CourseInfo course)
    {
        Singleton_CourseInfo singleton_courseInfo = Singleton_CourseInfo.getInstance();
        int size=0;
        String menuTitle = course.getCode() + " " + course.getName();
        System.out.println("\n============================================= " + menuTitle + " ==================================================");
        if(course != null)
        {
            int i;
            String leftAlign = "| %-11s | %-7s |%-9s | %-12s | %-10s | %-14s | %-8s |%-14s |%n";

            System.out.format("+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");
            System.out.format("| Class Index |  Group  |    Day   |    Period    |    Venue   |     Remark     |  Vacancy | Waiting list  |%n");
            System.out.format("+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");
            for (i = 0; i < course.getClassList().size(); i++) {
                System.out.format(leftAlign, i+1 + ": " + course.getClassList().get(i).getIndexNo(),course.getClassList().get(i).getGroupNo(),course.getClassList().get(i).getDay(),course.getClassList().get(i).getTime(),course.getClassList().get(i).getVenue(),
                        course.getClassList().get(i).getRemark(),course.getClassList().get(i).getVacancy(),course.getClassList().get(i).getQueue());
            }
            System.out.format("+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");
            System.out.println(i+1 + ": Back to main menu");
            System.out.println("Please select a new class to swap: ");
        }

        return size;
    }

    private static void StudentCourseMenu(ArrayList<CourseInfo> courseInfoDB, String studentName)
    {
        String menuTitle = studentName + " registered courses";
        System.out.println("\n=========================================== " + menuTitle + " ================================================\n");
        if(courseInfoDB != null && courseInfoDB.size() > 0)
        {
            int i;
            String leftAlign = "| %-11s | %-43s |%-9s | %-11s | %-7s |%-9s | %-12s | %-10s | %-14s | %-8s |%-14s |%n";

            System.out.format("+-------------+---------------------------------------------+----------+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");
            System.out.format("| Course Code |                  Course Name                |   Type   | Class Index |  Group  |    Day   |    Period    |    Venue   |     Remark     |  Vacancy | Waiting list  |%n");
            System.out.format("+-------------+---------------------------------------------+----------+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");

            for (i = 0; i < courseInfoDB.size(); i++) {
                System.out.format(leftAlign, i+1 + ": " + courseInfoDB.get(i).getCode(), courseInfoDB.get(i).getName(), courseInfoDB.get(i).getType(),courseInfoDB.get(i).getClassList().get(0).getIndexNo(),courseInfoDB.get(i).getClassList().get(0).getGroupNo(),courseInfoDB.get(i).getClassList().get(0).getDay(),courseInfoDB.get(i).getClassList().get(0).getTime(),courseInfoDB.get(i).getClassList().get(0).getVenue(),
                        courseInfoDB.get(i).getClassList().get(0).getRemark(),courseInfoDB.get(i).getClassList().get(0).getVacancy(),courseInfoDB.get(i).getClassList().get(0).getQueue());
            }
            System.out.format("+-------------+---------------------------------------------+----------+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");
            System.out.println(i+1 + ": Back to main menu");
            System.out.println("Please select a course to drop: ");
        }
    }

    private static void StudentChangeClassMenu(ArrayList<CourseInfo> courseInfoDB, String studentName)
    {
        String menuTitle = studentName + " registered courses";
        System.out.println("\n======================================================================= " + menuTitle + " ===========================================================================");
        if(courseInfoDB != null && courseInfoDB.size() > 0)
        {
            int i;
            String leftAlign = "| %-11s | %-43s |%-9s | %-11s | %-7s |%-9s | %-12s | %-10s | %-14s | %-8s |%-14s |%n";

            System.out.format("+-------------+---------------------------------------------+----------+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");
            System.out.format("| Course Code |                  Course Name                |   Type   | Class Index |  Group  |    Day   |    Period    |    Venue   |     Remark     |  Vacancy | Waiting list  |%n");
            System.out.format("+-------------+---------------------------------------------+----------+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");

            for (i = 0; i < courseInfoDB.size(); i++) {
                System.out.format(leftAlign, i+1 + ": " + courseInfoDB.get(i).getCode(), courseInfoDB.get(i).getName(), courseInfoDB.get(i).getType(),courseInfoDB.get(i).getClassList().get(0).getIndexNo(),courseInfoDB.get(i).getClassList().get(0).getGroupNo(),courseInfoDB.get(i).getClassList().get(0).getDay(),courseInfoDB.get(i).getClassList().get(0).getTime(),courseInfoDB.get(i).getClassList().get(0).getVenue(),
                        courseInfoDB.get(i).getClassList().get(0).getRemark(),courseInfoDB.get(i).getClassList().get(0).getVacancy(),courseInfoDB.get(i).getClassList().get(0).getQueue());
            }
            System.out.format("+-------------+---------------------------------------------+----------+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");
            System.out.println(i+1 + ": Back to main menu");
            System.out.println("Please select a registered course you wish to change: ");
        }
    }

    private static void StudentSelection(Student student)
    {
        Singleton_CourseInfo singleton_courseInfo = Singleton_CourseInfo.getInstance();

        // student selection
        int choice = 0;
        do{
            StudentMenu();
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();

            switch(choice)
            {
                case 1:
                    if(student.getCourseInfoList().size() >= MAXCOURSELIMIT)
                    {
                        System.out.println("Error! You cannot register for more than 8 courses. Please contact your school administrator for more assistance.");
                        break;
                    }

                    CourseMenu();

                    int courseSelection = ChoiceValidation(singleton_courseInfo.courseInfoDB.size());
                    if(courseSelection == singleton_courseInfo.courseInfoDB.size()+1)
                        break;

                    ClassMenu(singleton_courseInfo.courseInfoDB.get(courseSelection-1));

                    int classSelection = ChoiceValidation(singleton_courseInfo.courseInfoDB.get(courseSelection-1).getClassList().size());
                    if(classSelection == singleton_courseInfo.courseInfoDB.get(courseSelection-1).getClassList().size()+1)
                        break;

                    CourseInfo selectedCourse = new CourseInfo(singleton_courseInfo.courseInfoDB.get(courseSelection-1));
                    selectedCourse.addClass(singleton_courseInfo.courseInfoDB.get(courseSelection-1).getClassList().get(classSelection-1));

                    student.AddCourse(selectedCourse,false);
                    break;
                case 2:
                    StudentCourseMenu(student.getCourseInfoList(), student.getName());
                    int dropSelection = ChoiceValidation(student.getCourseInfoList().size());
                    if(dropSelection == student.getCourseInfoList().size()+1)
                        break;

                    student.DropCourse(student.getCourseInfoList().get(dropSelection-1),false);
                    break;
                case 3:
                    student.RegisteredCourses();
                    break;
                case 4:
                    CourseMenu();
                    int courseSelection2 = ChoiceValidation(singleton_courseInfo.courseInfoDB.size());
                    if(courseSelection2 == singleton_courseInfo.courseInfoDB.size()+1)
                        break;

                    ShowClassVacancyMenu(singleton_courseInfo.courseInfoDB.get(courseSelection2-1));


                    break;
                case 5:
                    // remove old student course class
                    StudentChangeClassMenu(student.getCourseInfoList(), student.getName());
                    int dropSelection5 = ChoiceValidation(student.getCourseInfoList().size());
                    if(dropSelection5 == student.getCourseInfoList().size()+1)
                        break;


                    for (int i = 0; i < singleton_courseInfo.courseInfoDB.size(); i++) {
                        if(student.getCourseInfoList().get(dropSelection5-1).getCode() == singleton_courseInfo.courseInfoDB.get(i).getCode()) {
                            // add new student course class
                            ChangeClassMenu(singleton_courseInfo.courseInfoDB.get(i));
                            int classSelection5 = ChoiceValidation(singleton_courseInfo.courseInfoDB.get(i).getClassList().size());
                            if(classSelection5 == singleton_courseInfo.courseInfoDB.get(i).getClassList().size()+1)
                                break;

                            CourseInfo selectedCourse5 = new CourseInfo(singleton_courseInfo.courseInfoDB.get(i));
                            selectedCourse5.addClass(singleton_courseInfo.courseInfoDB.get(i).getClassList().get(classSelection5-1));

                            System.out.println("You have successfully changed from the course index " + student.getCourseInfoList().get(dropSelection5-1).getClassList().get(0).getIndexNo() + " to the course index " + selectedCourse5.getClassList().get(0).getIndexNo());

                            student.AddCourse(selectedCourse5,false);

                            student.DropCourse(student.getCourseInfoList().get(dropSelection5-1),false);

                            break;
                        }
                    }


                    break;
                case 6:
                    StudentChangeClassMenu(student.getCourseInfoList(), student.getName());
                    int dropSelection6 = ChoiceValidation(student.getCourseInfoList().size());
                    if(dropSelection6 == student.getCourseInfoList().size()+1)
                        break;

                    CourseInfo courseInfo6 = student.getCourseInfoList().get(dropSelection6-1);

                    System.out.println("Please allow student 2 to verify his account : ");
                    System.out.println("Student 2 Username: ");
                    sc.nextLine();
                    String username = sc.nextLine();

                    System.out.println("Student 2 Password: ");
                    String password = sc.nextLine();
                    Login login = new Login(username,new String(password));
                    if(login.validateLogin()) {
                        Student student2 = login.getStudentProfile();
                        Boolean isCourseFound = false;
                        for(int i =0; i < student2.getCourseInfoList().size(); i++)
                        {
                            if(courseInfo6.getCode() == student2.getCourseInfoList().get(i).getCode())
                            {
                                isCourseFound = true;
                                SwapClassMenu(courseInfo6, student);
                                SwapClassMenu(student2.getCourseInfoList().get(i), student2);
                                System.out.println("Are you sure to swap with " + student2.getName() + " ?");
                                System.out.println("Y(1)/N(2)");
                                int decision6 = ChoiceValidation(2);
                                if(decision6 ==1)
                                {
                                    System.out.println("You have successfully changed from the course index "+ courseInfo6.getClassList().get(0).getIndexNo() + " to the course index "+ student2.getCourseInfoList().get(i).getClassList().get(0).getIndexNo()  +
                                                        " with Student " + student2.getName());
                                    student.AddCourse(student2.getCourseInfoList().get(i),true);
                                    student2.AddCourse(courseInfo6,true);

                                    student.DropCourse(courseInfo6,true);
                                    student2.DropCourse(student2.getCourseInfoList().get(i),true);
                                    break;
                                }
                                else if(decision6==2)
                                    break;
                            }
                        }
                        if(!isCourseFound)
                            System.out.println(student2.getName() + " doesn't register " + courseInfo6.getName() + " !");

                    }
                    else
                        System.out.println("Invalid Username or Password! Please try again.");
                    break;
                default:
                    System.out.println("See you again!");
                    System.exit(0);
                    break;
            }
        }while (choice < 8);
    }

    private static void AdminSelection(Admin admin) throws Exception {
        int choice = 0;
        int optionSize = 7;

        do{
            AdminMenu();
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();

            switch (choice){
                case 1:
                    admin.AddStudent();    //Update DONE
                    break;
                case 2:
                    admin.AddCourse();  //Update DONE
                    break;
                case 3:
                    admin.UpdateCourse();  //Update DONE
                    break;
                case 4:
                    admin.EditStudentAccessPeriod(); //Update DONE
                    break;
                case 5:
                    admin.PrintStudentListByIndex();   //No need UpdateDB
                    break;
                case 6:
                    admin.PrintStudentListByCourse(); //No need UpdateDB
                    break;
                case 7:
                    admin.CheckCourseVacancy(); //No need UpdateDB
                    break;
                case 8:
                    System.out.println("See you again!");
                    System.exit(0);
                    break;
            }

        }while(choice != -1);
    }

    private static void InitCourseDB()
    {
        Singleton_CourseInfo singleton_courseInfo = Singleton_CourseInfo.getInstance();

        //<editor-fold desc="read course info">
        String filepath = System.getProperty("user.dir") + "/CourseTable/" + "/CourseInfo.txt";
        IOUtills.ReadFile(filepath);

        ArrayList<String> tempcourseInfoDB =  IOUtills.getFileInput();

        if(tempcourseInfoDB != null && tempcourseInfoDB.size() > 0 )
        {
            for (int i = 0; i < tempcourseInfoDB.size(); i++) {
                if(tempcourseInfoDB.get(i) != "")
                {
                    CourseInfo course = new CourseInfo(tempcourseInfoDB.get(i));
                    singleton_courseInfo.courseInfoDB.add(course);

                    //<editor-fold desc="read class info">
                    String class_filename = course.getCode() + "_Class.txt";
                    String class_filepath = System.getProperty("user.dir") + "/ClassTable/" + class_filename;

                    if(Files.exists(Path.of(class_filepath))){
                        IOUtills.ReadFile(class_filepath);
                    }

                    ArrayList<String> classInfoDB =  IOUtills.getFileInput();

                    for (int j = 0; j < classInfoDB.size(); j++) {
                        if(classInfoDB.get(j) != "")
                        {
                            ClassInfo classInfo = new ClassInfo(classInfoDB.get(j));
                            singleton_courseInfo.courseInfoDB.get(i).addClass(classInfo);
                        }
                    }
                }
                //</editor-fold>
            }
        }
        //</editor-fold>
    }

    private static void InitStudentCourseDB(){
        Singleton_StudentCourse singleton_studentCourse = Singleton_StudentCourse.getInstance();

        //<editor-fold desc="read course info">
        String filepath = System.getProperty("user.dir") + "/StudentCourseTable/StudentCourse.txt";
        IOUtills.ReadFile(filepath);

        ArrayList<String> tempstudentCourseDB =  IOUtills.getFileInput();
        if(tempstudentCourseDB != null && tempstudentCourseDB.size() > 0 ) {
            for (int i = 0; i < tempstudentCourseDB.size(); i++) {
                if(tempstudentCourseDB.get(i) != "")
                {
                    StudentCourse studentCourse = new StudentCourse(tempstudentCourseDB.get(i));
                    singleton_studentCourse.studentCourseDB.add(studentCourse);
                }

            }
        }
        //</editor-fold>
    }

    private static void InitUserDB(){
        Singleton_UserTable singleton_userTable = Singleton_UserTable.getInstance();

        //<editor-fold desc="read UserTable info">
        String filepath = System.getProperty("user.dir") + "/UserTable/" + "/user.txt";
        IOUtills.ReadFile(filepath);

        ArrayList<String> temp =  IOUtills.getFileInput();
        if(temp != null && temp.size() > 0 ) {
            for (int i = 0; i < temp.size(); i++) {
                if(temp.get(i) != "")
                {
                    String[] data = temp.get(i).split(",");

                    String myUsername = data[0];
                    String myPassword = data[1];
                    String salt = data[2];
                    String securePassword = data[3];
                    EnumHelper.UserRole myRole = EnumHelper.UserRole.valueOf(data[4]);

                    Login _userLogin = new Login(myUsername,myPassword,salt,securePassword,myRole);
                    singleton_userTable.userDB.add(_userLogin);
                }
            }
        }
        //</editor-fold>
    }

    private static void InitStudentProfileDB(){
        Singleton_StudentProfile singleton_studentProfile = Singleton_StudentProfile.getInstance();

        //<editor-fold desc="read StudentProfile info">
        String filepath = System.getProperty("user.dir") + "/StudentProfile/StudentProfile.txt";
        IOUtills.ReadFile(filepath);

        ArrayList<String> temp =  IOUtills.getFileInput();
        if(temp != null && temp.size() > 0 ) {
            for (int i = 0; i < temp.size(); i++) {
                if(temp.get(i) != "")
                {
                    String[] data = temp.get(i).split(",");

                    String name = data[0];
                    String nationality = data[1];
                    String email = data[2];
                    String username = data[3];
                    String password = data[4];
                    String contactNo = data[5];
                    EnumHelper.UserRole role = EnumHelper.UserRole.valueOf(data[6]);
                    EnumHelper.Gender gender = EnumHelper.Gender.valueOf(data[7]);
                    String matricNo = data[8];
                    String accessPeriodStart = data[9];
                    String accessPeriodEnd = data[10];

                    Student _student = new Student(name,nationality,email,
                            username,password,contactNo,role,
                            gender,matricNo,accessPeriodStart,accessPeriodEnd);

                    if(_student != null){

                        // retrieve all the registered course
                        Singleton_StudentCourse studentCourse = Singleton_StudentCourse.getInstance();
                        Singleton_CourseInfo courseInfo = Singleton_CourseInfo.getInstance();

                        ArrayList<StudentCourse> studentCourseArrayList = new ArrayList<>();

                        for(int z=0; z < studentCourse.studentCourseDB.size(); z++)
                        {
                            if(studentCourse.studentCourseDB.get(z).getStudentMatricNo().toUpperCase().equals(_student.getMatricNo().toUpperCase()))
                            {
                                studentCourseArrayList.add(studentCourse.studentCourseDB.get(z));
                            }
                        }

                        ArrayList<CourseInfo> courseInfoArrayList = new ArrayList<>();

                        for(int j=0; j < courseInfo.courseInfoDB.size(); j++)
                        {
                            for(int k=0; k < studentCourseArrayList.size(); k++)
                            {
                                if(courseInfo.courseInfoDB.get(j).getCode().equals(studentCourseArrayList.get(k).getCourseCode()))
                                {
                                    CourseInfo courseInfo1 = new CourseInfo(courseInfo.courseInfoDB.get(j));


                                    for(int z=0; z< courseInfo.courseInfoDB.get(j).getClassList().size() ; z++)
                                    {
                                        if(courseInfo.courseInfoDB.get(j).getClassList().get(z).getIndexNo().equals(studentCourseArrayList.get(k).getClassIndex()))
                                        {
                                            courseInfo1.addClass(courseInfo.courseInfoDB.get(j).getClassList().get(z));
                                        }
                                    }
                                    _student.addCourse(courseInfo1);
                                }
                            }
                        }

                    }

                    singleton_studentProfile.studentProfileDB.add(_student);
                }
            }
        }
        //</editor-fold>
    }

    private static int ChoiceValidation(int size)
    {
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        while(choice > size+1)
        {
            System.out.println("Invalid input! Please select again.");
            choice = sc.nextInt();
        }
        return choice;
    }

}
