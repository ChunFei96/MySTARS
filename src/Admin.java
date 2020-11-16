





public class Admin extends User{

    public Admin(String name, String nationality, String email, String username, String password, int contactNo) {
        super(name, nationality, email, username, password, contactNo);
    }

    private void menu(){
        System.out.println("====== Welcome Admin ======");
        System.out.println("1. Add Student");
        System.out.println("2. Edit Student");
        System.out.println("3. Add Course");
        System.out.println("4. Update Course");
        System.out.println("5. Edit Student Access");
        System.out.println("6. Print StudentList By Index Number");
        System.out.println("7. Print StudentList By Course");
        System.out.println("8. Retrieve Class Vacancy");
        System.out.println("9. Logout");
        System.out.println("=====================");
    }


    private void AddStudent(){

    }

    private void EditStudent(){

    }

    private void AddCourse(){

    }

    private void UpdateCourse(){

    }

    private void EditStudentAccess(){

    }

    private void PrintStudentListByIndexNumber(){

    }

    private void PrintStudentListByCourse(){

    }

    private int getClassVacancy(int indexNumber){
        return 0;
    }
}
