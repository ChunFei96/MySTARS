
/**
 * Admin concrete class that implements admin interface for mandatory function and extends User class for common attributes
 * @author Tan Wen Jun and Mo Naiming
 * @version 1.0
 * @since 2020-11-10
 */

public class Admin extends User implements IAdmin{

    /**
     * Create a admin object with admin information
     * @param name Admin's name
     * @param nationality Admin's nationality
     * @param email Admin's email
     * @param username Admin's username
     * @param password Admin's password
     * @param contactNo Admin's contact nu,ber
     * @param role Admin's role
     * @param sex Admin's gender
     */
    public Admin(String name, String nationality, String email, String username, String password, String contactNo,
                 EnumHelper.UserRole role, EnumHelper.Gender sex) {
        super(name, nationality, email, username, password, contactNo, EnumHelper.UserRole.ADMIN, sex);
    }

    /**
     * Edit student's access period to restrict student course registration period
     * @throws Exception
     */
    public void EditStudentAccessPeriod() throws Exception {
        new CourseManager().EditStudentAccessPeriod();
    }

    /**
     * Add new student into the school system
     */
    public void AddStudent(){
        new CourseManager().AddStudent();
    }

    /**
     *  Add new course into the school system
     */
    public void AddCourse(){
        new CourseManager().AddCourse();
    }

    /**
     * Update course's details
     */
    public void UpdateCourse(){
        new CourseManager().UpdateCourse();
    }

    /**
     * Export student list to .txt file by index filtering
     */
    public void PrintStudentListByIndex() {
        new CourseManager().PrintStudentListByIndex();
    }

    /**
     * Export student list to .txt file by course filtering
     */
    public void PrintStudentListByCourse() {
        new CourseManager().PrintStudentListByCourse();
    }

    /**
     * Check course vacancy
     * @return the course's vacancy
     */
    public int CheckCourseVacancy(){
        int result = new CourseManager().CheckCourseVacancy();
        return result;
    }
}
