/**
 * Utitlies class to create and de hash passwords when in use, will also compare the passwords already in the database with the written passwords to log into the service
 * @author gIlias
 */
package george.javawebemail.Utilities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PlainTextToHashUtil {
    private static String convertPlainTextToHas(String plainTextString) throws NoSuchAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashedBytes = md.digest(plainTextString.getBytes(StandardCharsets.UTF_8));

            StringBuilder sbForString = new StringBuilder();
            for (byte currentHashBytes : hashedBytes) {
                sbForString.append(String.format("%02x", currentHashBytes));
            }
            return sbForString.toString();
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
            throw new NoSuchAlgorithmException("error with the instance of the algorithm used");
        }
    }

    /**
     * Method to add salt and use the conversion method above to convert the given
     * salt to a full hash
     * 
     * @param plainTextString
     * @return
     * @throws NoSuchAlgorithmException
     * @author gIlias
     */
    public static String addSaltAndConvert(String plainTextString) throws NoSuchAlgorithmException {
        String completePlainTextString = ("prefSuchUp" + plainTextString + "suffSuchUp");
        try {
            return PlainTextToHashUtil.convertPlainTextToHas(completePlainTextString);
        } catch (NoSuchAlgorithmException nsaeTwo) {
            nsaeTwo.printStackTrace();
            throw new NoSuchAlgorithmException();
        }

    }

    /**
     * method to check the plain text password as well as the hashed and salted
     * password
     * 
     * @param saltedHashToCompareTo
     * @param givenPlainTexString
     * @return
     * @author gIlias
     */
    public static boolean compareHashToPlain(String saltedHashToCompareTo, String givenPlainTexString) {
        try {
            String salted = addSaltAndConvert(givenPlainTexString);

            if (salted.equals(saltedHashToCompareTo)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * a method that will check to see if the given string is hashed, will be used
     * in the mapping methods in the entity classes to see if the string needs to be
     * hashed
     * 
     * if the method returns false the string is not hashed, if the return is true
     * then the string is hashed
     * 
     * @param possibleHashedPassword
     * @author gIlias
     * @return boolean
     */
    public boolean checkIfStringIsHashsed(String potentialHash) {
        try {
            String testSuffix = convertPlainTextToHas("suffSuchUp");
            if (testSuffix.equals(potentialHash.substring(testSuffix.length(), potentialHash.length()))) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        } catch (IndexOutOfBoundsException ioobe) {
            ioobe.printStackTrace();
            return false;
        }
        return false;
    }

}