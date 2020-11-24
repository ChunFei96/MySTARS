/**
 * Student interface extends user interface for common function and to be implemented by Student concrete class
 * @author Lee Chun Fei & Mindy Hwang Mei Hui
 * @version 1.0
 * @since 2020-11-10
 */
public interface IStudent extends IUser {
    /**
     * Allow student to register course with vacancy available
     * @param courseInfo The course selected by student
     * @param isSwapIndex Flag to check if student performs the swap index with other student, bypass reduce vacancy or queue process
     */
    void AddCourse(CourseInfo courseInfo, Boolean isSwapIndex);

    /**
     * Allow student to drop their registered course
     * @param courseInfo The course dropped by student
     * @param isSwapIndex Flag to check if student performs the swap index with other student, bypass reduce vacancy or queue process
     */
    void DropCourse(CourseInfo courseInfo, Boolean isSwapIndex);

    /**
     * Allow student to print their registered courses
     */
    void RegisteredCourses();

    /**
     * Allow student to change course's class index
     * @param oldClass The course's class index dropped by student
     * @param newClass The course's class index added by student
     * @param isSwapIndex Flag to check if student performs the swap index with other student, bypass reduce vacancy or queue process
     */
    void ChangeCourseIndexNumber(CourseInfo oldClass, CourseInfo newClass, Boolean isSwapIndex);

    /**
     * Allow student to swap course's class index with another student
     * @param secondStudent Student 2 that agreed to swap course's class index with student 1
     * @param firstStudentCourse Student 1 course class's index that swap to student 2
     * @param secondStudentCourse Student 2 course class's index that swap to student 1
     * @param isSwapIndex Flag to check if student performs the swap index with other student, bypass reduce vacancy or queue process
     */
    void SwapIndexNumber(Student secondStudent, CourseInfo firstStudentCourse, CourseInfo secondStudentCourse, Boolean isSwapIndex);
}