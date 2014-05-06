package pl.mdziedzic.unittests.validators;

import pl.mdziedzic.unittests.exception.ValidationException;

public class PhoneValidator {
	
	/**
   * Poprawny number telefonu to +48.696696696, +12.2322222
	 * @throws ValidationException 
   */
	public static void validate(String phone) throws ValidationException {
    String phoneNumberRegex = "^\\+[0-9]{1,3}\\.[0-9]{6,9}$";
    if (phone == null || !phone.matches(phoneNumberRegex)) {
        throw new ValidationException("Blad walidacji");
    }
}

}
