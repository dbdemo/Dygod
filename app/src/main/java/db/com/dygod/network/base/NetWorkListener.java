package db.com.dygod.network.base;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zdb on 2016/5/18.
 */
public interface NetWorkListener<T> {
    void successful(ArrayList<T> t);
    void failure(IOException e);
}
