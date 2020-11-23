

public interface IStudent extends IUser {
    void AddCourse(CourseInfo courseInfo, Boolean isSwapIndex);

    void DropCourse(CourseInfo courseInfo, Boolean isSwapIndex);

    void RegisteredCourses();

    void ChangeCourseIndexNumber(CourseInfo oldClass, CourseInfo newClass);

    void SwapIndexNumber(int index_ID, int studentAccount);
}
