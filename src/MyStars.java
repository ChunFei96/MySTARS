package src;

import java.io.Console;
import java.util.*;

import static src.EnumHelper.*;

public class MyStars {

    private  static Console console;

    public static void main(String[] args) {


        //Create new user: <<user_2>>
        // Account user_2 = new Account("chunfei","abc123", UserRole.Student);
        // user_2.addAccount();

        String username = "chunfei";
        String password = "abc123";
        UserRole userRole = UserRole.Student;

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

        Login login = new Login(username,new String(password), userRole);
        if(login.validateLogin())
            System.out.println("\nWelcome back! " + login.getMyUsername());
        User user = login.getUserProfile();

        // Init course info db
        ArrayList<CourseInfo> courseInfoList = new ArrayList<CourseInfo>();
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

        

        // user course selection
        int choice = 0;
        do
        {
            UserMenu();
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();

            switch(choice)
            {
                case 1:
                    CourseMenu(courseInfoList);
                    choice = sc.nextInt();
                    user.AddCourse(courseInfoList.get(choice-1));
                    break;
                case 2:
                    CourseMenu(courseInfoList);
                    choice = sc.nextInt();
                    user.DropCourse(courseInfoList.get(choice-1));
                    break;
                case 3:
                    user.RegisteredCourses();
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                default:
                    System.out.println("See you again!");
                    break;
            }
        }
        while(choice < 8);
    }

    private static void UserMenu()
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
}
