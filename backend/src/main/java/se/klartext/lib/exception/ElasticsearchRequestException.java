package se.klartext.lib.exception;

public class ElasticsearchRequestException extends RuntimeException {

    public ElasticsearchRequestException (String message){
        super(message);
    }
}
