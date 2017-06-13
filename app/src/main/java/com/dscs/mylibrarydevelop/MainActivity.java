package com.dscs.mylibrarydevelop;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.dscs.tools.T;
import com.dscs.tools.utils.LogUtils;
import com.dscs.tools.view.MultiImageSelector;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE = 2;
    private ArrayList<String> mSelectPath;
    private Activity mContext;
    private String imagePath;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pickImage(REQUEST_IMAGE);
            }
        });
    }
    private void pickImage(int index) {
        T.image().showCamera(true).
//                count(9).                     //设置count变成多选模式
                cropSize(8, 8, 800, 800,100).  //设置后变成裁剪模式
                origin(mSelectPath).
                start(mContext, index);
//        MultiImageSelector selector = MultiImageSelector.create();
//        selector.showCamera(true).
//                count(9).
//                size(8, 8, 800, 800).
//                compress(100).
//                single().
//                origin(mSelectPath).
//                start(mContext, index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                imagePath = mSelectPath.get(0);
                for (String s : mSelectPath) {
                    LogUtils.i(s);
                }
                imageView.setImageURI(Uri.parse(mSelectPath.get(0)));
            }
        }
    }
}
