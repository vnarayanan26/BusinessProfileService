package com.example.intuit.businessprofileservice.validator;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class Validator {
    private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private static final String COMPANY_NAME_REGEX = "^.{2,500}$";

    public static final Pattern COMPANY_NAME_PATTERN = Pattern.compile(COMPANY_NAME_REGEX);

    private final ArrayList<String> errorList = new ArrayList<>();

    private boolean hasValidationFailedBefore = false;

    private Validator() {
    }


    public static Validator validator() {
        return new Validator();
    }

    public Validator validate(Consumer<String[]> onError) {
        if (!errorList.isEmpty()) {
            onError.accept(errorList.toArray(new String[0]));
            hasValidationFailedBefore = true;
        }
        return this;
    }

    public Validator isNotNull(Object object, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (object == null) {
            addErrorMessage(message);
        }
        return this;
    }

    public Validator isNotNullAndBlank(String val, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val == null || val.trim().isEmpty()) {
            addErrorMessage(message);
        }
        return this;
    }

    public Validator isNumeric(String val, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (!StringUtils.isNumeric(val)) {
            addErrorMessage(message);
        }
        return this;
    }

    public Validator isNotNullOrEmpty(String val, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val == null || val.isEmpty()) {
            addErrorMessage(message);
        }
        return this;
    }

    public <T> Validator isNotNullOrEmpty(Collection<T> val, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val == null || val.isEmpty()) {
            addErrorMessage(message);
        }
        return this;
    }

    public <T> Validator isNotNullOrEmpty(T[] val, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val == null || val.length == 0) {
            addErrorMessage(message);
        }
        return this;
    }


    public Validator isEmail(String val, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val == null || !EMAIL_PATTERN.matcher(val).matches()) {
            addErrorMessage(message);
        }
        return this;
    }

    public Validator isCompanyName(String val, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val == null || !COMPANY_NAME_PATTERN.matcher(val).matches()) {
            addErrorMessage(message);
        }
        return this;
    }

    public <T> Validator sizeInRange(Collection<T> val, int lowerLimit, int upperLimit, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val != null && val.size() < lowerLimit && val.size() >= upperLimit) {
            addErrorMessage(message);
        }
        return this;
    }

    public <T> Validator sizeGreaterThan(Collection<T> val, int lowerLimit, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val != null && val.size() < lowerLimit) {
            addErrorMessage(message);
        }
        return this;
    }

    public <T> Validator sizeLesserThan(Collection<T> val, int upperLimit, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val != null && val.size() >= upperLimit) {
            addErrorMessage(message);
        }
        return this;
    }

    public <T> Validator sizeInRange(T[] val, int lowerLimit, int upperLimit, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val != null && val.length < lowerLimit && val.length >= upperLimit) {
            addErrorMessage(message);
        }
        return this;
    }

    public <T> Validator sizeGreaterThan(T[] val, int lowerLimit, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val != null && val.length < lowerLimit) {
            addErrorMessage(message);
        }
        return this;
    }

    public <T> Validator sizeLesserThan(T[] val, int upperLimit, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val != null && val.length >= upperLimit) {
            addErrorMessage(message);
        }
        return this;
    }

    public <T extends Comparable<T>> Validator inRange(T val, T lowerLimit, T upperLimit, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val != null && (val.compareTo(lowerLimit) < 0 || val.compareTo(upperLimit) > 0)) {
            addErrorMessage(message);
        }
        return this;
    }

    public <T extends Comparable<T>> Validator greaterThan(T val, T lowerLimit, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val != null && val.compareTo(lowerLimit) <= 0) {
            addErrorMessage(message);
        }
        return this;
    }

    public <T extends Comparable<T>> Validator greaterThanOrEqual(Supplier<T> valSupplier, T lowerLimit, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        T val = valSupplier.get();
        if (val != null && val.compareTo(lowerLimit) < 0) {
            addErrorMessage(message);
        }
        return this;
    }

    public <T extends Comparable<T>> Validator lessThanOrEqual(T val, T upperLimit, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val != null && val.compareTo(upperLimit) > 0) {
            addErrorMessage(message);
        }
        return this;
    }

    public <T extends Comparable<T>> Validator lessThan(T val, T upperLimit, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val != null && val.compareTo(upperLimit) >= 0) {
            addErrorMessage(message);
        }
        return this;
    }

    public Validator isTrue(Supplier<Boolean> predicate, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }
        if (!predicate.get()) {
            addErrorMessage(message);
        }
        return this;
    }

    public Validator isFalse(Supplier<Boolean> predicate, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }
        if (predicate.get()) {
            addErrorMessage(message);
        }
        return this;
    }

    private void addErrorMessage(String message) {
        errorList.add(message);

            hasValidationFailedBefore = true;

    }
}
