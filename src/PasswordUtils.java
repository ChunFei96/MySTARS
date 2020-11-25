
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

/**
 * PasswordUtils - util class that manages password related issue.
 * For example:
 * <p>
 *     hashing the password, verify user enter password, generate random password for new created account
 * </p>
 * @author Tan Wen Jun and Mo Naiming
 * @version 1.0
 * @since 2020-11-10
 */
public class PasswordUtils {
    /**
     * random generator
     */
    private static final Random RANDOM = new SecureRandom();
    /**
     * salt aplhabet
     */
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    /**
     * iterations
     */
    private static final int ITERATIONS = 10000;
    /**
     * key length
     */
    private static final int KEY_LENGTH = 256;

    /**
     * Usage: create the salt
     * @param length indicate the length of the salt that wants to be generated
     * @return salt string
     */
    public static String getSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

    /**
     * Usage: function that use to hash the password
     * @param password password
     * @param salt salt
     * @return the hashed password
     */
    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    /**
     * Usage: create a more secure password that being hashed with salt
     * @param password the password that user has entered
     * @param salt an unique string that use for generating secure password
     * @return the password that has being hashed
     */
    public static String generateSecurePassword(String password, String salt) {
        String returnValue = null;

        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

        returnValue = Base64.getEncoder().encodeToString(securePassword);

        return returnValue;
    }

    /**
     * Usage: use to verify user entered password with the password in the database
     * @param providedPassword the password that user entered during login
     * @param securedPassword the hashed password that stored in UserTable.
     * @param salt some string that needs to be used when generating the password, as well as during password validation
     * @return the status of user password is valid or invalid
     */
    public static boolean verifyUserPassword(String providedPassword, String securedPassword, String salt){
        boolean returnValue = false;

        /* Generate New secure password with the same salt*/
        String newSecurePassword = generateSecurePassword(providedPassword, salt);

        /* Check if two passwords are equal*/
        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);

        return returnValue;
    }

    /**
      * Usage: generate a random password using 4 combination of inputs: UpperCase,LowerCase,special characters and numbers.
      * @param length indicate the size of the auto generated password
     * @return auto-generated password
     */
    public static char[] generatePassword(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for(int i = 4; i< length ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password;
    }

}