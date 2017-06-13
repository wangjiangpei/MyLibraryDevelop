package com.dscs.tools.cropwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dscs.tools.R;
import com.dscs.tools.view.CropImageView;
import com.dscs.tools.view.MultiImageSelector;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * intent 参数
 * 比例
 * int x = 1;
 * int y = 1;
 * int px = 800;
 * int py = 800;
 * 比例锁定  true时锁定
 * Boolean fixedAspectRatio = true;
 * int guidelines = 0;//   方格  0无 2有 1 按下有
 * compress = 100; 最大100
 */
public class CropImageActivity extends AppCompatActivity {
    public static String ROOT_PATH;
    public static final int ROTATE_NINETY_DEGREES = 90;
    public static final int REQUEST_CROP = 120;
    private TextView rotate;
    private TextView save;
    private CropImageView cropImageView;
    //比例锁定  true时锁定
    private Boolean fixedAspectRatio = true;
    private int guidelines = 0;//   方格  0无 2有 1 按下有
    private String path;
    private MultiImageSelector selector;
    private Bitmap croppedImage;
    private Context mContext;
    private Bitmap thisImage;
    private Button mSubmitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MIS_NO_ACTIONBAR);
        setContentView(R.layout.activity_crop_image);
        ROOT_PATH = getCacheDir() + "/Neiquan/";
        mContext = this;
        initView();
        initParams();
    }

    private void initParams() {
        Intent intent = getIntent();
        if (intent != null) {
            selector = (MultiImageSelector) intent.getBundleExtra("bundle").getSerializable("Serializable");
            fixedAspectRatio = intent.getBooleanExtra("fixedAspectRatio", true);
            guidelines = intent.getIntExtra("guidelines", 1);
            path = intent.getStringExtra("data");
        }
        cropImageView.setAspectRatio(selector.aspectX, selector.aspectY);
        cropImageView.setFixedAspectRatio(fixedAspectRatio);
        cropImageView.setGuidelines(guidelines);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap mImage = BitmapFactory.decodeFile(path, options);
        // 压缩图片
        options.inSampleSize = Math.max(1, (int) Math.ceil(Math.max(
                (double) options.outWidth / 1024f,
                (double) options.outHeight / 1024f)));
        options.inJustDecodeBounds = false;
        thisImage = BitmapFactory.decodeFile(path, options);
        cropImageView.setImageBitmap(thisImage);
    }

    private void initView() {
        cropImageView = (CropImageView) findViewById(R.id.CropImageView);
        save = (TextView) findViewById(R.id.save);
        rotate = (TextView) findViewById(R.id.rotate);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                croppedImage = cropImageView.getCroppedImage();
                String fileSrc = saveBitmapToFile(mContext, croppedImage);
                // 获取图片保存路径
                // 获取图片的宽和高
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Bitmap mImage = BitmapFactory.decodeFile(fileSrc, options);
                // 压缩图片
                options.inSampleSize = Math.max(1, (int) Math.ceil(Math.max(
                        (double) options.outWidth / 1024f,
                        (double) options.outHeight / 1024f)));
                options.inJustDecodeBounds = false;
                mImage = BitmapFactory.decodeFile(fileSrc, options);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //可根据流量及网络状况对图片进行压缩
                mImage.compress(Bitmap.CompressFormat.JPEG, selector.compress, baos);
                Intent intent = new Intent();
                intent.putExtra("path", fileSrc);
                makeText(mContext, fileSrc, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, intent);
                finish();
                mImage.recycle();
                thisImage.recycle();
                croppedImage.recycle();
            }
        });
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropImageView.rotateImage(ROTATE_NINETY_DEGREES);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mSubmitButton = (Button) findViewById(R.id.commit);
//        mSubmitButton.setVisibility(View.VISIBLE);
//        mSubmitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }

    /**
     * 保存Bitmap至本地
     *
     * @param bmp
     */
    public String saveBitmapToFile(Context context, Bitmap bmp) {
        List<File> images = listFileSortByModifyTime(ROOT_PATH);
        for (File image : images) {
            Log.i("666", "saveBitmapToFile: " + images.size() + "---" + image.getPath());
        }
        if (images.size() > 20) {
            for (int i = 0; i < images.size(); i++) {
                if (i > images.size() / 2) {
                    Log.i("666", "saveBitmapToFile: 执行");
                    images.get(i).delete();
                }
            }
        }
        File root = new File(ROOT_PATH);
        if (!root.isFile()) {
            root.mkdirs();
        }
        String file_path = root.getPath() + getPathName();
        File file = new File(file_path);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file_path;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getPathName() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
        return "/IMG-" + df.format(new Date(System.currentTimeMillis())) + ".jpg";
    }

    /**
     * 获取目录下所有文件(按时间排序)
     *
     * @param path
     * @return
     */
    public static List<File> listFileSortByModifyTime(String path) {
        List<File> list = getFiles(path, new ArrayList<File>());
        if (list != null && list.size() > 0) {
            Collections.sort(list, new Comparator<File>() {
                public int compare(File file, File newFile) {
                    if (file.lastModified() < newFile.lastModified()) {
                        return -1;
                    } else if (file.lastModified() == newFile.lastModified()) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        }
        return list;
    }

    /**
     * 获取目录下所有文件
     *
     * @param realpath
     * @param files
     * @return
     */
    public static List<File> getFiles(String realpath, List<File> files) {
        File realFile = new File(realpath);
        if (realFile.isDirectory()) {
            File[] subfiles = realFile.listFiles();
            for (File file : subfiles) {
                if (file.isDirectory()) {
                    getFiles(file.getAbsolutePath(), files);
                } else {
                    files.add(file);
                }
            }
        }
        return files;
    }
}
