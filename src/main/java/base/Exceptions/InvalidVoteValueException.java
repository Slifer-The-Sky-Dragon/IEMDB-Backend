package base.Exceptions;

public class InvalidVoteValueException extends IemdbException{
    public InvalidVoteValueException(String errMessage) {
        super(errMessage);
    }
}
