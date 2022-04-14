package ro.sd.a2.constants;

public class ExistingUserException extends Exception{

    public ExistingUserException(String message){
        super(message);
    }

    public ExistingUserException(){
        super();
    }

}
