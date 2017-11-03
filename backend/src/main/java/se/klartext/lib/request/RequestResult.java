package se.klartext.lib.request;

import java.util.Optional;

public class RequestResult<T> {

    private  T result;
    private  Throwable error;

    public RequestResult(T result){
        this.result = result;
    }

    public RequestResult(Throwable e){
        this.error = e;
    }

    public Optional<T> getResult(){
        return Optional.ofNullable(result);
    }

    public Optional<Throwable> getError(){
        return Optional.ofNullable(error);
    }
}