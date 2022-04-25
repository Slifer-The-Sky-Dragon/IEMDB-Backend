package base.Exceptions;

public class MovieAlreadyExistsException extends IemdbException{
    public MovieAlreadyExistsException(String errMessage){
        super(errMessage);
    }
}
