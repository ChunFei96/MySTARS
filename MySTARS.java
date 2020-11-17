import java.util;

public class MySTARS{

    public static void main(String args[]){
        
        CourseInfo course1 = new CourseInfo("14803", 5, "Wednesday", "1530 - 1730", "HWLAB3", "Lab work", "Core", "CZ2001");
        CourseInfo course2 = new CourseInfo("15803", 2, "Tuesday", "1130 - 1230", "LT5", "Lecture", "Core", "CZ2004");
        CourseInfo course3 = new CourseInfo("16803", 9, "Friday", "930 - 1130", "SWLAB1", "Lab work", "Core", "CZ2002");
        CourseInfo course4 = new CourseInfo("17803", 4, "Thursday", "1700 - 1830", "LT4", "Tutorial", "Core", "CZ2003");
        CourseInfo[] courses = new CourseInfo[] { course1, course2, course3, course4 };




        CourseInfo.GetCourseInfo(course);
    }
}