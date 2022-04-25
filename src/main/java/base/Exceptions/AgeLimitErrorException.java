package base.Exceptions;

public class AgeLimitErrorException extends IemdbException{
    public AgeLimitErrorException(String errMessage){
        super(errMessage);
    }
}