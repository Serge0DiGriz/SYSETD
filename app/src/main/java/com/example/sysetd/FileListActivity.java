package com.example.sysetd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FileListActivity extends AppCompatActivity {
    private ArrayAdapter<String> filesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        Bundle arguments = getIntent().getExtras();
        String code = arguments.getString("code");
        filesAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                MyFTPClientFunctions ftpClient = new MyFTPClientFunctions();
                ftpClient.ftpConnect(
                        getResources().getString(R.string.host),
                        getResources().getString(R.string.login),
                        getResources().getString(R.string.password), 21);
                String[] files = ftpClient.ftpPrintFilesList(code);
                runOnUiThread(()->{
                    filesAdapter.addAll(files);
                });
                ftpClient.ftpDisconnect();
            }}).start();

        ((ListView)findViewById(R.id.fileList)).setAdapter(filesAdapter);

    }
}