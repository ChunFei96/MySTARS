
public interface IStudent extends IUser {
    void AddCourse(CourseInfo courseInfo);

    void DropCourse(CourseInfo courseInfo);

    void RegisteredCourses();

    void ChangeCourseIndexNumber(int course_Code);

    void SwapIndexNumber(int index_ID, int student_ID);
}
