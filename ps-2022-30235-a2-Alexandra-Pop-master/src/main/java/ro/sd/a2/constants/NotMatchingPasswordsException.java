package ro.sd.a2.constants;

public class NotMatchingPasswordsException extends Exception{

    public NotMatchingPasswordsException(String message){
        super(message);
    }

    public NotMatchingPasswordsException(){
        super();
    }

}
