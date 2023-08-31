package vendas.services.exceptions;

public class DatabaseExceptions extends RuntimeException{
    private static final long serialverionUID=1L;

    public DatabaseExceptions(String msg){
        super(msg);
    }

}
