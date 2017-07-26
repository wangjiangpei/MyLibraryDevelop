package com.dscs.mylibrarydevelop;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.dscs.tools.T;
import com.dscs.tools.view.MultiImageSelector;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE = 1;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.logText)
    TextView logText;
    private ArrayList<String> mSelectPath;
    private Fragment mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.imageView)
    public void onViewClicked() {

        T.image().showCamera(true).
                count(9).                     //设置count变成多选模式
//        cropSize(8, 8, 800, 800, 100).  //设置后变成裁剪模式
        origin(mSelectPath).
                start(mContext, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for (String s : mSelectPath) {
                    sb.append(s).append("\n");
                }
                logText.setText(sb.toString());
                imageView.setImageURI(Uri.parse(mSelectPath.get(0)));
            }
        }
    }
}
