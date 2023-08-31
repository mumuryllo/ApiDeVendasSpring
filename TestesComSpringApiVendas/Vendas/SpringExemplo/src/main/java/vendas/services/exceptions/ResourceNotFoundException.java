package vendas.services.exceptions;

public class ResourceNotFoundException extends  RuntimeException{
    private static final long serialverionUID=1L;

    public ResourceNotFoundException(Object id){
        super("Erro na Execução! ID " + id );
    }

}
