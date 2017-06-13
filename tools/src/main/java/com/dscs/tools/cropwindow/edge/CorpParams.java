package com.dscs.tools.cropwindow.edge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dscs.tools.imageselecttools.CropImageActivity;
import com.dscs.tools.view.MultiImageSelector;

import java.io.Serializable;

/**
 *
 */

public class CorpParams implements Serializable {
    public String imagePath;
    public int guidelines;
    public boolean fixedAspectRatio = true;
    public MultiImageSelector selector;

    public static CorpParams create() {
        return new CorpParams();
    }
    public  CorpParams params(String imagePath,
                              int guidelines,
                              MultiImageSelector selector,
                              boolean fixedAspectRatio){
        this.imagePath = imagePath;
        this.guidelines = guidelines;
        this.selector = selector;
        this.fixedAspectRatio =fixedAspectRatio;
        return this;
    }
    public void  start(Activity context){
        Intent innerIntent = new Intent(context, CropImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Serializable", selector);
        innerIntent.putExtra("bundle", bundle);
        innerIntent.putExtra("data", imagePath);
        innerIntent.putExtra("fixedAspectRatio", true);    // 是否可以自定义比例
        innerIntent.putExtra("guidelines", 1);              //按压效果
        context.startActivityForResult(innerIntent, CropImageActivity.REQUEST_CROP);
    }
    public void  start(Fragment context){
        Intent innerIntent = new Intent(context.getActivity(), CropImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Serializable", selector);
        innerIntent.putExtra("bundle", bundle);
        innerIntent.putExtra("data", imagePath);
        innerIntent.putExtra("fixedAspectRatio", true);    // 是否可以自定义比例
        innerIntent.putExtra("guidelines", 1);              //按压效果
        context.startActivityForResult(innerIntent, CropImageActivity.REQUEST_CROP);
    }
}
