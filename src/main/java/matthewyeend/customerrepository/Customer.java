/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package matthewyeend.customerrepository;

import java.util.regex.Pattern;
/**
 *
 * @author matthewyeend
 */
public record Customer(Long id, String fullName, String email) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final int MAX_NAME_LENGTH = 100;
    private static final int MAX_EMAIL_LENGTH = 254;

    public Customer {
        if (fullName == null || fullName.isBlank() || fullName.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Full name must be between 1 and 100 characters");
        }
        if (email == null || email.length() > MAX_EMAIL_LENGTH || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email address is not valid");
        }
        fullName = fullName.strip();
        email = email.strip().toLowerCase();
    }
}
