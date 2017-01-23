package db.com.dyhome.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;


/**
 * Created by zdb on 2017/1/20.
 */

public class OkHttpUtls {

    private OkHttpClient client = new OkHttpClient();
    private okHttpLinner okHttpLinner;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0://成功
                    if (okHttpLinner != null) {
                        okHttpLinner.Success((String) msg.obj);
                    }
                    break;
                case 1://失败
                    if (okHttpLinner != null) {
                        okHttpLinner.Failure((Response) msg.obj);
                    }
                    break;
            }
        }
    };

    public void setOkHttpLinner(OkHttpUtls.okHttpLinner okHttpLinner) {
        this.okHttpLinner = okHttpLinner;
    }

    public void post(final String url, final Map<String, String> param, okHttpLinner okHttpLinner) {
        this.okHttpLinner = okHttpLinner;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FormEncodingBuilder fromBody = new FormEncodingBuilder();
                    for (Map.Entry<String, String> entry : param.entrySet()) {
                        fromBody.add(entry.getKey(), entry.getValue());
                    }
                    RequestBody requestBody = fromBody.build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        try {
                            InputStream ips = response.body().byteStream();
                            String head = response.header("Content-Disposition");
                            String oName = head.substring(head.indexOf("=") + 2, head.length() - 1);
                            File file = new File(SdCardUtils.getSdCardDirectory() + oName);
                            if (file.exists()) {

                                Message msg = Message.obtain();
                                msg.what = 0;
                                msg.obj = file.getPath();
                                handler.sendMessage(msg);

                                return;
                            }
                            OutputStream ops = new FileOutputStream(file);
                            byte[] buf = new byte[1024];
                            int by = 0;
                            int bys = 0;
                            while ((by = ips.read(buf)) != -1) {
                                ops.write(buf, 0, by);
                                bys += by;
                            }
                            ips.close();
                            ops.close();

                            Message msg = Message.obtain();
                            msg.what = 0;
                            msg.obj = file.getPath();
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();

                            Message msg = Message.obtain();
                            msg.what = 1;
                            msg.obj = response;
                            handler.sendMessage(msg);
                        }
                    } else {
                        Message msg = Message.obtain();
                        msg.what = 1;
                        msg.obj = response;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public interface okHttpLinner {
        void Success(String response);

        void Failure(Response response);
    }
}
