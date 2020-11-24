

/**
 * Enum helper class to define constant variable
 * @author Krystal Hwang Yan Lin 
 * @version 1.0
 * @since 2020-11-05
 */
public abstract class EnumHelper {

    /**
     * List of User Role
     */
    public static enum UserRole { STUDENT, ADMIN}

    /**
     * List of Gender
     */
    public static enum Gender{ MALE, FEMALE}

    /**
     * List of Status
     */
    public static enum Status{ ACTIVE, DEACTIVED }

    /**
     * List of Course Type
     */
    public static enum CourseType{ ONLINE, LEC,LEC_TUT,LEC_TUT_LAB }
}
