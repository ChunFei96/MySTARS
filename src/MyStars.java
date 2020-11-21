
import java.io.Console;
import java.util.*;

public class MyStars {

    private  static Console console;
    private static ArrayList<CourseInfo> courseInfoList;

    public static void main(String[] args) {


        //Create new user: <<user_2>>
        // Account user_2 = new Account("chunfei","abc123", UserRole.Student);
        // user_2.addAccount();

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


        // Init course info db
        InitCourseDB();

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

//                Login login2 = new Login("tst","tst");
//                login2.validateLogin();
//                Student student = login2.getStudentProfile();

                //admin.EditStudentAccessPeriod();
                //admin.AddStudent();  //Task  2

                admin.PrintStudentListByIndex("00318");
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

    private static void CourseMenu(ArrayList<CourseInfo> courseInfoList)
    {
        System.out.println("\n=========================================== Course information menu ================================================\n");
        if(courseInfoList != null && courseInfoList.size() > 0)
        {
            for (int i = 0; i < courseInfoList.size(); i++) {
                System.out.println( i+1 + ": " + courseInfoList.get(i).GetCourseInfo());
            }
            System.out.println("Please select a course to register: ");
        }
    }

    private static void StudentCourseMenu(ArrayList<CourseInfo> courseInfoList)
    {
        System.out.println("\n=========================================== Student registered courses ================================================\n");
        if(courseInfoList != null && courseInfoList.size() > 0)
        {
            for (int i = 0; i < courseInfoList.size(); i++) {
                System.out.println( i+1 + ": " + courseInfoList.get(i).GetCourseInfo());
            }
            System.out.println("Please select a course to drop: ");
        }
    }

    private static void InitCourseDB()
    {
        courseInfoList = new ArrayList<CourseInfo>();
        String filepath = System.getProperty("user.dir") + "/CourseInfo/" + "/CourseInfo.txt";
        IOUtills.ReadFile(filepath);

        ArrayList<String> courseInfoDB =  IOUtills.getFileInput();

        if(courseInfoDB != null && courseInfoDB.size() > 0 )
        {
            for (int i = 0; i < courseInfoDB.size(); i++) {
                CourseInfo course = new CourseInfo(courseInfoDB.get(i));
                //courses.GetCourseInfo();
                courseInfoList.add(course);
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
                    CourseMenu(courseInfoList);
                    int addSelection = ChoiceValidation(courseInfoList.size());
                    student.AddCourse(courseInfoList.get(addSelection-1));
                    break;
                case 2:

                    StudentCourseMenu(student.getCourseInfoList());
                    int dropSelection = ChoiceValidation(student.getCourseInfoList().size());
                    student.DropCourse(student.getCourseInfoList().get(dropSelection-1));
                    break;
                case 3:
                    student.RegisteredCourses();
                    break;
                case 4:
                    break;
                case 5:
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
}
