package base.Utilities;

public class Response {
    private Status status = null;
    private Object data = null;

    public void setStatus(Status status){
        this.status = status;
    }
    public Status getStatus(){
        return this.status;
    }
    public void setData(Object data){
        this.data = data;
    }
    public Object getData(){
        return this.data;
    }

    public Response() {
    }

    public Response(Status status, Object data){
        this.status = status;
        this.data = data;
    }
}
