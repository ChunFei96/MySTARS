
/**
 * Admin interface extends user interface for common function and to be implemented by Admin concrete class
 * @author Tan Wen Jun & Mo Naiming
 * @version 1.0
 * @since 2020-11-10
 */

public interface IAdmin extends IUser{
    /**
     * Allow admin to change student's access period, and update to the database.
     * @throws Exception
     */
    public void EditStudentAccessPeriod() throws Exception;

    /**
     * Allow admin to add student into database.
     */
    public void AddStudent();

    /**
     * Allow admin to add course into database.
     */
    public void AddCourse();

    /**
     * Report display student list, filter by class index
     */
    public void PrintStudentListByIndex();

    /**
     * Report display student list, filter by course index
     */
    public void PrintStudentListByCourse();
}
