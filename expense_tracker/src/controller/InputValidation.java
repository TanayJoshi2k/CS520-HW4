package controller;

import java.util.Arrays;

/**
 * The InputValidation class provides methods for validating input values.
 */
public class InputValidation {

  /**
   * Checks if the provided amount is a valid value.
   *
   * @param amount The amount to be validated.
   * @return {@code true} if the amount is valid; {@code false} otherwise.
   */
  public static boolean isValidAmount(double amount) {

    // Check range
    if (amount > 1000) {
      return false;
    }
    if (amount < 0) {
      return false;
    }
    if (amount == 0) {
      return false;
    }
    return true;
  }

  /**
   * Checks if the provided category is a valid value.
   *
   * @param category The category to be validated.
   * @return {@code true} if the category is valid; {@code false} otherwise.
   */
  public static boolean isValidCategory(String category) {

    if (category == null) {
      return false;
    }

    if (category.trim().isEmpty()) {
      return false;
    }

    if (!category.matches("[a-zA-Z]+")) {
      return false;
    }

    String[] validWords = { "food", "travel", "bills", "entertainment", "other" };

    if (!Arrays.asList(validWords).contains(category.toLowerCase())) {
      // invalid word
      return false;
    }

    return true;

  }

}