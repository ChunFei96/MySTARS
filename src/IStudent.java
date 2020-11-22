

public interface IStudent extends IUser {
    void AddCourse(CourseInfo courseInfo);

    void DropCourse(CourseInfo courseInfo);

    void RegisteredCourses();

    void ChangeCourseIndexNumber(CourseInfo oldClass, CourseInfo newClass);

    void SwapIndexNumber(int index_ID, int studentAccount);
}
