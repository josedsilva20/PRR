package prr.core.exception;


public class InvalidIdException extends Exception {
    public InvalidIdException(String msg){
        super(msg);
    }
    public InvalidIdException(){
        super();
    }
}
