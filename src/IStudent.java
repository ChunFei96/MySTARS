import java.util.ArrayList;

public interface IStudent extends IUser {
    ArrayList<StudentCourse> AddCourse(CourseInfo courseInfo, ArrayList<StudentCourse> studentCourseList);

    ArrayList<StudentCourse> DropCourse(CourseInfo courseInfo, ArrayList<StudentCourse> studentCourseList);

    void RegisteredCourses();

    void ChangeCourseIndexNumber(CourseInfo oldClass, CourseInfo newClass);

    void SwapIndexNumber(int index_ID, int studentAccount);
}
