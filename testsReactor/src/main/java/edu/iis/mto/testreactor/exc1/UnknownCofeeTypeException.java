package edu.iis.mto.testreactor.exc1;

public class UnknownCofeeTypeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnknownCofeeTypeException(String message) {
        super(message);
    }

}
