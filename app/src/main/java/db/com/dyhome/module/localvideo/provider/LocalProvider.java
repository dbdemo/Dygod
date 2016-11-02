package db.com.dyhome.module.localvideo.provider;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import db.com.dyhome.bean.LocalVideoEntity;
import db.com.dyhome.utils.FileUtils;

/**
 * Created by zdb on 2016/11/2.
 */

public class LocalProvider implements AbstructProvider {
    private LocalVideoLinner linner;
    private List<LocalVideoEntity> list;

    public LocalProvider(LocalVideoLinner linner) {
        this.linner = linner;
    }


    @Override
    public void getAllList() {
        list = new ArrayList<>();

        AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {

            @Override
            protected String doInBackground(String... params) {
                String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                getAllFile(rootDir);
                return "";
            }

            @Override
            protected void onPostExecute(String s) {
                if (linner != null) {
                    linner.success(list);
                }
            }
        };
        task.execute();
    }

    private void getAllFile(String directory) {
        File f = new File(directory);
        File[] listFile = f.listFiles();
        for (int i = 0; i < listFile.length; i++) {
            if (listFile[i].isDirectory()) {
                getAllFile(listFile[i].getAbsolutePath());
            } else {
                File fList = listFile[i];
                String fileName = fList.getName();
                int start = fileName.lastIndexOf(".");
                if (start != -1) {
                    String endName = fileName.substring(start + 1, fileName.length());
                    String[] nameStyle = new String[]{"mkv", "AVI", "wma", "rmvb", "rm", "flash", "mp4", "mid", "3GP"};
                    for (int j = 0; j < nameStyle.length; j++) {
                        if (nameStyle[j].equals(endName)) {
                            LocalVideoEntity entity = new LocalVideoEntity(fList.getName(), fList.getAbsolutePath(), fList.length(), FileUtils.getVideoThumbnail(fList.getPath()));
                            list.add(entity);
                        }
                    }
                }
            }
        }
    }

    public interface LocalVideoLinner {
        public void success(List<LocalVideoEntity> list);

        public void fail();
    }
}
