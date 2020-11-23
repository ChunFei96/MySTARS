public interface IAdmin extends IUser{
    public void EditStudentAccessPeriod() throws Exception;

    public void AddStudent();

    public void AddCourse();

    public void PrintStudentListByIndex();

    public void PrintStudentListByCourse();
}
