package base.Exceptions;

public class InvalidCommandException extends IemdbException{
    public InvalidCommandException(String errMessage){
        super(errMessage);
    }
}
