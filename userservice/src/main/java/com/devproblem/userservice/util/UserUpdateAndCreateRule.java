package com.devproblem.userservice.util;

import com.devproblem.userservice.model.User;
import org.apache.el.util.Validation;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserUpdateAndCreateRule implements UserServiceValidator {
    @Override
    public void validate(User user) {

        if (!getIdentityValid(user.getIdNumber())) {
            throw new IllegalArgumentException("Invalid RSA ID");
        }
        if (!validSaNumber(user)) {
            throw new IllegalArgumentException("Invalid RSA Mobile Number");
        }

    }



    private static Boolean getIdentityValid(String identities) {
        char[] idChars = identities.toCharArray();
        int sum = 0;
        // loop over each digit, except the check-digit
        for (int i = 0; i < idChars.length - 1; i++) {
            int digit = Character.getNumericValue(idChars[i]);
            if ((i % 2) == 0) {
                sum += digit;
            } else {
                sum += digit < 5 ? digit * 2 : digit * 2 - 9;
            }
        }
        int checkDigit = Character.getNumericValue(idChars[idChars.length - 1]);
        int compDigit = (sum * 9) % 10;

        return checkDigit == compDigit;
    }

    private Boolean validSaNumber(User user) {
        String MOBILE_PATTERN = "^(\\+27|0)[6-8][0-9]{8}$";
        Pattern pattern = Pattern.compile(MOBILE_PATTERN);
        return pattern.matcher(user.getPhoneNumber()).matches();

    }
}
