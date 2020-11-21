
import java.io.Console;
import java.util.*;

public class MyStars {

    private  static Console console;
    private static ArrayList<CourseInfo> courseInfoDB;
    private static ArrayList<StudentCourse> studentCourseDB;

    public static void main(String[] args) {


        //Create new user: <<user_2>>
        Account user_2 = new Account("chunfei","abc123", EnumHelper.UserRole.STUDENT);
        user_2.addAccount();

        String username = "chunfei";
        String password = "abc123";

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
        System.out.println("\n=========================================== Course information menu ================================================\n");
        if(courseInfoDB != null && courseInfoDB.size() > 0)
        {
            for (int i = 0; i < courseInfoDB.size(); i++) {
                System.out.println( i+1 + ": " + courseInfoDB.get(i).getCourseInfo());
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

    private static void StudentCourseMenu(ArrayList<CourseInfo> courseInfoDB)
    {
        System.out.println("\n=========================================== Student registered courses ================================================\n");
        if(courseInfoDB != null && courseInfoDB.size() > 0)
        {
            for (int i = 0; i < courseInfoDB.size(); i++) {
                System.out.println( i+1 + ": " + courseInfoDB.get(i).getCourseInfo());
                System.out.println("   " + courseInfoDB.get(i).getClassList().get(0).getClassInfo());
            }
            System.out.println("Please select a course to drop: ");
        }
    }

    private static void StudentSelection(Student student)
    {
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
                    int courseSelection = ChoiceValidation(courseInfoDB.size());
                    ClassMenu(courseInfoDB.get(courseSelection-1));
                    int classSelection = ChoiceValidation(courseInfoDB.size());

                    CourseInfo selectedCourse = new CourseInfo(courseInfoDB.get(courseSelection-1));
                    selectedCourse.addClass(courseInfoDB.get(courseSelection-1).getClassList().get(classSelection-1));

                    studentCourseDB = student.AddCourse(selectedCourse, studentCourseDB);
                    break;
                case 2:
                    StudentCourseMenu(student.getCourseInfoList());
                    int dropSelection = ChoiceValidation(student.getCourseInfoList().size());

                    studentCourseDB = student.DropCourse(student.getCourseInfoList().get(dropSelection-1), studentCourseDB);
                    break;
                case 3:
//                    student.RegisteredCourses();
                    break;
                case 4:
                    break;
                case 5:
//                    StudentCourseMenu(student.getCourseInfoList());
//                    System.out.println("Please enter your current index no");
//                    int oldIndexSelection = ChoiceValidation(student.getCourseInfoList().size());
//                    CourseInfo currentCourse = student.ShowCourses(oldIndexSelection);
//
//                    CourseMenu(courseInfoDB);
//                    System.out.println("Please enter the new index no");
//                    int newIndexSelection = ChoiceValidation(courseInfoDB.size());
//                    CourseInfo newCourse = student.ShowCourses(newIndexSelection);
//
//                    System.out.println("Are you confirm to change index number?Y(1)/N(2)");
//                    int decision = ChoiceValidation(2);
//                    student.ChangeCourseIndexNumber(currentCourse, newCourse);
                    break;
                case 6:
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
        courseInfoDB = new ArrayList<CourseInfo>();

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
                    courseInfoDB.add(course);

                    //<editor-fold desc="read class info">
                    String class_filename = course.getCode() + "_Class.txt";
                    String class_filepath = System.getProperty("user.dir") + "/ClassTable/" + class_filename;
                    IOUtills.ReadFile(class_filepath);

                    ArrayList<String> classInfoDB =  IOUtills.getFileInput();

                    for (int j = 0; j < classInfoDB.size(); j++) {
                        if(classInfoDB.get(j) != "")
                        {
                            ClassInfo classInfo = new ClassInfo(classInfoDB.get(j));
                            courseInfoDB.get(i).addClass(classInfo);
                        }
                    }
                }
                //</editor-fold>
            }
        }
        //</editor-fold>
    }

    private static void InitStudentCourseDB()
    {
        studentCourseDB = new ArrayList<StudentCourse>();

        //<editor-fold desc="read course info">
        String filepath = System.getProperty("user.dir") + "/StudentCourseTable/" + "/StudentCourse.txt";
        IOUtills.ReadFile(filepath);

        ArrayList<String> tempstudentCourseDB =  IOUtills.getFileInput();
        if(tempstudentCourseDB != null && tempstudentCourseDB.size() > 0 ) {
            for (int i = 0; i < tempstudentCourseDB.size(); i++) {
                if(tempstudentCourseDB.get(i) != "")
                {
                    StudentCourse studentCourse = new StudentCourse(tempstudentCourseDB.get(i));
                    studentCourseDB.add(studentCourse);
                }

            }
        }
        //</editor-fold>
    }


    private static void UpdateStudentCourseDB(String matricNo)
    {
        courseInfoDB = new ArrayList<CourseInfo>();
        String filepath = System.getProperty("user.dir") + "/StudentCourseTable/" + "/CourseInfo.txt";
        IOUtills.ReadFile(filepath);

        ArrayList<String> tempcourseInfoDB =  IOUtills.getFileInput();

        if(tempcourseInfoDB != null && tempcourseInfoDB.size() > 0 )
        {
            for (int i = 0; i < tempcourseInfoDB.size(); i++) {
                CourseInfo course = new CourseInfo(tempcourseInfoDB.get(i));
                //courses.GetCourseInfo();
                courseInfoDB.add(course);
            }
        }
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
