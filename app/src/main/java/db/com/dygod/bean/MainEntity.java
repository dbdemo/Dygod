package db.com.dygod.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zdb on 2016/5/19.
 * 主页实体
 */
public class MainEntity implements Serializable {

private ArrayList<MainNesEntity> mainNesEntities;

    public ArrayList<MainNesEntity> getMainNesEntities() {
        return mainNesEntities;
    }

    public void setMainNesEntities(ArrayList<MainNesEntity> mainNesEntities) {
        this.mainNesEntities = mainNesEntities;
    }
}
