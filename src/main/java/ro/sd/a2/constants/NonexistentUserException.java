package ro.sd.a2.constants;

public class NonexistentUserException extends Exception{

    public NonexistentUserException(String message){
        super(message);
    }

    public NonexistentUserException(){
        super();
    }

}
