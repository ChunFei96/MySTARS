
import java.io.Console;
import java.util.*;

public class MyStars {

    private  static Console console;

    public static void main(String[] args) {


        //Create new user: <<user_2>>
//        Account user_2 = new Account("chunfei","abc123");
//        user_2.setUserRole(EnumHelper.UserRole.STUDENT);
//        user_2.addAccount();


        //Test Admin Acc
//        String username = "chunfei";
//        String password = "abc123";

        //Test Student Acc
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
        InitUserDB();
        InitStudentProfileDB();
        InitCourseDB();
        InitStudentCourseDB();

        boolean testing = false;

        if(!testing){
            Login login = new Login(username,new String(password));
            if(login.validateLogin()) {
                System.out.println("\nWelcome back! " + login.getMyUsername());

                if(login.getUser().getUserRole() == EnumHelper.UserRole.STUDENT){
                    Student student = login.getStudentProfile();
                    StudentSelection(student);
                }
                else if(login.getUser().getUserRole() == EnumHelper.UserRole.ADMIN){
                    Admin admin = login.getAdminProfile();
                    AdminMenu();


                    //admin.EditStudentAccessPeriod(); //Task 1 DONE
                    //admin.AddStudent();  //Task  2 DONE
                    //admin.AddCourse();  Task 3
                    //admin.CheckCourseVacancy( "00192"); //Task 4
                    //admin.PrintStudentListByIndex("00318");  //Task 5 DONE
                    admin.PrintStudentListByCourse("CZ2003"); //Task 6
                }
            }
        }
    }

    private static void StudentMenu()
    {
        System.out.println("Please select option below: ");
			
        System.out.println("1: Add Course");
        System.out.println("2: Drop Course");
        System.out.println("3: Check/Print Courses Registered");
        System.out.println("4: Check Vacancies Available");
        System.out.println("5: Change Index Number of Course");
        System.out.println("6: Swop Index Number with Another Student");
        System.out.println("7: Log out");
    }

    private static void AdminMenu(){
        System.out.println("====== Welcome Admin ======");
        System.out.println("1. Add Student");
        System.out.println("2. Edit Student");
        System.out.println("3. Add Course");
        System.out.println("4. Update Course");
        System.out.println("5. Edit Student Access");
        System.out.println("6. Print StudentList By Index Number");
        System.out.println("7. Print StudentList By Course");
        System.out.println("8. Retrieve Class Vacancy");
        System.out.println("9. Logout");
        System.out.println("=====================");
    }

    private static void CourseMenu()
    {
        Singleton_CourseInfo singleton_courseInfo = Singleton_CourseInfo.getInstance();

        System.out.println("\n=========================================== Course information menu ================================================\n");
        if(singleton_courseInfo.courseInfoDB != null && singleton_courseInfo.courseInfoDB.size() > 0)
        {
            for (int i = 0; i < singleton_courseInfo.courseInfoDB.size(); i++) {
                System.out.println( i+1 + ": " + singleton_courseInfo.courseInfoDB.get(i).getCourseInfo());
            }
            System.out.println("Please select a course: ");
        }
    }

    private  static void ClassMenu(CourseInfo course)
    {
        String menuTitle = course.getCode() + " " + course.getName();
        System.out.println("\n=========================================== " + menuTitle + " ================================================\n");
        if(course != null)
        {
            for (int i = 0; i < course.getClassList().size(); i++) {
                System.out.println( i+1 + ": " + course.getClassList().get(i).getClassInfo());
            }
            System.out.println("Please select a class to register: ");
        }
    }

    private  static void ShowClassVacancyMenu(CourseInfo course)
    {
        String menuTitle = course.getCode() + " " + course.getName();
        System.out.println("\n=========================================== " + menuTitle + " ================================================\n");
        if(course != null)
        {
            for (int i = 0; i < course.getClassList().size(); i++) {
                ClassInfo classInfo = course.getClassList().get(i);
                System.out.println( i+1 + ": " + classInfo.getIndexNo() + " class has left " + classInfo.getVacancy() + " slots");
            }
            System.out.println("\n");
        }
    }

    private  static int ChangeClassMenu(CourseInfo course)
    {
        Singleton_CourseInfo singleton_courseInfo = Singleton_CourseInfo.getInstance();
        int size=0;
        String menuTitle = course.getCode() + " " + course.getName();
        System.out.println("\n=========================================== " + menuTitle + " ================================================\n");
        if(course != null)
        {
            for (int i = 0; i < course.getClassList().size(); i++) {
                System.out.println( i+1 + ": " + course.getClassList().get(i).getClassInfo());
            }
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
            for (int i = 0; i < courseInfoDB.size(); i++) {
                System.out.println( i+1 + ": " + courseInfoDB.get(i).getCourseInfo());
                System.out.println("   " + courseInfoDB.get(i).getClassList().get(0).getClassInfo());
            }
            System.out.println("Please select a course to drop: ");
        }
    }

    private static void StudentChangeClassMenu(ArrayList<CourseInfo> courseInfoDB, String studentName)
    {
        String menuTitle = studentName + " registered courses";
        System.out.println("\n=========================================== " + menuTitle + " ================================================\n");
        if(courseInfoDB != null && courseInfoDB.size() > 0)
        {
            for (int i = 0; i < courseInfoDB.size(); i++) {
                System.out.println( i+1 + ": " + courseInfoDB.get(i).getCourseInfo());
                System.out.println("   " + courseInfoDB.get(i).getClassList().get(0).getClassInfo());
            }
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
                    CourseMenu();
                    int courseSelection = ChoiceValidation(singleton_courseInfo.courseInfoDB.size());
                    ClassMenu(singleton_courseInfo.courseInfoDB.get(courseSelection-1));
                    int classSelection = ChoiceValidation(singleton_courseInfo.courseInfoDB.get(courseSelection-1).getClassList().size());

                    CourseInfo selectedCourse = new CourseInfo(singleton_courseInfo.courseInfoDB.get(courseSelection-1));
                    selectedCourse.addClass(singleton_courseInfo.courseInfoDB.get(courseSelection-1).getClassList().get(classSelection-1));

                    student.AddCourse(selectedCourse);
                    break;
                case 2:
                    StudentCourseMenu(student.getCourseInfoList(), student.getName());
                    int dropSelection = ChoiceValidation(student.getCourseInfoList().size());

                    student.DropCourse(student.getCourseInfoList().get(dropSelection-1));
                    break;
                case 3:
                    student.RegisteredCourses();
                    break;
                case 4:
                    CourseMenu();
                    int courseSelection2 = ChoiceValidation(singleton_courseInfo.courseInfoDB.size());
                    ShowClassVacancyMenu(singleton_courseInfo.courseInfoDB.get(courseSelection2-1));


                    break;
                case 5:
                    // remove old student course class
                    StudentChangeClassMenu(student.getCourseInfoList(), student.getName());
                    int dropSelection5 = ChoiceValidation(student.getCourseInfoList().size());


                    for (int i = 0; i < singleton_courseInfo.courseInfoDB.size(); i++) {
                        if(student.getCourseInfoList().get(dropSelection5-1).getCode() == singleton_courseInfo.courseInfoDB.get(i).getCode()) {
                            // add new student course class
                            ChangeClassMenu(singleton_courseInfo.courseInfoDB.get(i));
                            int classSelection5 = ChoiceValidation(singleton_courseInfo.courseInfoDB.get(i).getClassList().size());

                            CourseInfo selectedCourse5 = new CourseInfo(singleton_courseInfo.courseInfoDB.get(i));
                            selectedCourse5.addClass(singleton_courseInfo.courseInfoDB.get(i).getClassList().get(classSelection5-1));

                            student.AddCourse(selectedCourse5);

                            student.DropCourse(student.getCourseInfoList().get(dropSelection5-1));
                            break;
                        }
                    }
                    break;
                case 6:
                    StudentChangeClassMenu(student.getCourseInfoList(), student.getName());
                    int dropSelection6 = ChoiceValidation(student.getCourseInfoList().size());

                    CourseInfo courseInfo6 = student.getCourseInfoList().get(dropSelection6-1);


                    System.out.println("Student 2: ");
                    System.out.println("Username: ");
                    sc.nextLine();
                    String username = sc.nextLine();

                    System.out.println("Password: ");
                    String password = sc.nextLine();
                    Login login = new Login(username,new String(password));
                    if(login.validateLogin()) {
                        Student student2 = login.getStudentProfile();

                        for(int i =0; i < student2.getCourseInfoList().size(); i++)
                        {
                            if(courseInfo6.getCode() == student2.getCourseInfoList().get(i).getCode())
                            {
                                ClassMenu(courseInfo6);
                                ClassMenu(student2.getCourseInfoList().get(i));
                                System.out.println("Are you sure to swap with " + student2.getName() + " ?");
                                System.out.println("Y(1)/N(2)");
                                int decision6 = ChoiceValidation(2);
                                if(decision6 ==1)
                                {
                                    student.addCourse(student2.getCourseInfoList().get(i));
                                    student.dropCourse(courseInfo6);

                                    student2.addCourse(courseInfo6);
                                    student2.dropCourse(student2.getCourseInfoList().get(i));
                                }
                            }
                        }


                    }
                    break;
                default:
                    System.out.println("See you again!");
                    System.exit(0);
                    break;
            }
        }while (choice < 8);
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
                    IOUtills.ReadFile(class_filepath);

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
        String filepath = System.getProperty("user.dir") + "/StudentCourseTable/chunfei.txt";
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
        String filepath = System.getProperty("user.dir") + "/StudentProfile/" + "/chunfei.txt";
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
        while(choice > size)
        {
            System.out.println("Invalid input! Please select again.");
            choice = sc.nextInt();
        }
        return choice;
    }


}
