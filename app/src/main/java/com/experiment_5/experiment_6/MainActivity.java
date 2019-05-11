package com.experiment_5.experiment_6;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.experiment_5.experiment_6.BaiduTranslate.TransApi;
import com.experiment_5.experiment_6.BaiduTranslate.TranslateResult;
import com.google.gson.Gson;

import java.util.List;


public class MainActivity extends AppCompatActivity {


    private Button btn;
    private TextView txt;
    private Handler handler = new Handler();
    private EditText edTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        edTxt = (EditText) findViewById(R.id.edTxt);
        btn = (Button) findViewById(R.id.btn);
        txt = (TextView) findViewById(R.id.txt);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String query =edTxt.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String resultJson = new TransApi().getTransResult(query, "auto", "en");
                        //拿到结果，对结果进行解析。
                        Gson gson = new Gson();
                        TranslateResult translateResult = gson.fromJson(resultJson, TranslateResult.class);
                        final List<TranslateResult.TransResultBean> trans_result = translateResult.getTrans_result();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                String dst = "";
                                for (TranslateResult.TransResultBean s : trans_result
                                ) {
                                    dst = dst + "\n" + s.getDst();
                                }

                                txt.setText(dst);
                            }
                        });

                    }
                }).start();
            }
        });

    }
}
