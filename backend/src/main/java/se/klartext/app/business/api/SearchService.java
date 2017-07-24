package se.klartext.app.business.api;


import io.reactivex.Observable;

/**
 * Created by chuan on 2017-06-24.
 */
public interface SearchService<T> {

    Observable<T> findMatch(String type,String query);
}
