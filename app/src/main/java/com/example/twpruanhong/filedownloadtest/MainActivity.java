package com.example.twpruanhong.filedownloadtest;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button mBtn;
    private Context context = this;
    private final OkHttpClient client  = new OkHttpClient();
    private final static String Tag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn = (Button) findViewById(R.id.btn_download);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startThread();
            }
        });
    }

    public void startThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    download();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        Toast.makeText(MainActivity.this,"下载完成",Toast.LENGTH_SHORT).show();
    }
    public void download() throws IOException {
//        String urlPath = "http://172.16.0.47:8035/servlet/com.zotn.screens.msv.FileDownloadServlet?func=downloadAttach&id=72164829";
//        String urlPath = "http://172.16.0.135:8038/company/2/msv/file/2017/72164770";
//        String urlPath = "http://172.16.0.135:8038/company/2/msv/file/2017/72164814";
        String urlPath = "http://172.16.0.135:8033/servlet/com.zotn.mobile.servlet.GetZhengwenServlet?gwId=71461944&cmpyId=2";
//        try {
//            URL url = new URL(urlPath);
//            InputStream is = url.openStream();
//            String fileEndName = ".txt";
//            OutputStream os = context.openFileOutput("File" + System.currentTimeMillis() + fileEndName,Context.MODE_PRIVATE);
//            byte[] buffer = new byte[1024];
//            int len = 0;
//            while ((len = is.read(buffer)) >0 ) {
//                os.write(buffer,0,len);
//            }
//
//            is.close();
//            os.close();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Request request = new Request.Builder().url(urlPath).build();
        Response response = client.newCall(request).execute();
//        String txtPath = context.getExternalCacheDir().getPath();
//        File storefile = context.getExternalCacheDir();
        File storefile = Environment.getExternalStorageDirectory();
//        File textFile = new File(storefile,"正文72164814.doc");
        File textFile = new File(storefile,"通过正文URl.doc");
        if(!textFile.exists()) {
            textFile.createNewFile();
        }
        if (response.isSuccessful()) {
            FileOutputStream fileOs = new FileOutputStream(textFile);
            fileOs.write(response.body().bytes());
            fileOs.flush();
            fileOs.close();
        }
    }
}
