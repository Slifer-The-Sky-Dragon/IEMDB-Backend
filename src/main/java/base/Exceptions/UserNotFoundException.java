package base.Exceptions;

public class UserNotFoundException extends IemdbException {
    public UserNotFoundException(String errMessage){
        super(errMessage);
    }
}
