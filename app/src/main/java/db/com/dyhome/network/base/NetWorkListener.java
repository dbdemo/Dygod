package db.com.dyhome.network.base;


/**
 * Created by zdb on 2016/5/18.
 */
public interface NetWorkListener<T> {
    void successful(T t);

    void failure(Object e);
}
