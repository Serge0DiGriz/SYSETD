package com.example.sysetd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final String USER_AGENT = "Mozilla/5.0";
    protected static String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signIn(View view) {
        String input = ((EditText)findViewById(R.id.input)).getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyFTPClientFunctions ftpClient = new MyFTPClientFunctions();
                ftpClient.ftpConnect(
                        getResources().getString(R.string.host),
                        getResources().getString(R.string.login),
                        getResources().getString(R.string.password), 21);

                boolean check = false;
                String[] dirs = ftpClient.ftpPrintFilesList("");
                for (int i = 0; i < dirs.length && !check; i++)
                    check = dirs[i].equals(input);
                if (check) {
                    code = input;
                    ftpClient.ftpDisconnect();
                    Intent intent = new Intent(getApplicationContext(), FileListActivity.class);
                    intent.putExtra("code", code);
                    startActivity(intent);
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(getApplicationContext(),
                                "Incorrect identity!", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        }).start();
    }

    public void inspector(View view) {
        Toast.makeText(getApplicationContext(),
                "Вы вошли как инсектор!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Inspector.class);
        startActivity(intent);
    }


}