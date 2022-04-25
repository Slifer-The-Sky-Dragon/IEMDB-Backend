package base.Exceptions;

public class MovieNotFoundException extends IemdbException {
    public MovieNotFoundException(String errMessage){
        super(errMessage);
    }
}
