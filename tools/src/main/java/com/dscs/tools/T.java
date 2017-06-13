package com.dscs.tools;

import android.support.v7.app.AppCompatActivity;

import com.dscs.tools.cropwindow.edge.CorpParams;
import com.dscs.tools.view.MultiImageSelector;
import com.dscs.tools.view.datetimeselect.SlideDateTimePicker;

/**
 *
 */

public class T {
    private MultiImageSelector selector;

    public static MultiImageSelector image() {
        return MultiImageSelector.create();
    }

    public static CorpParams corp() {
        return CorpParams.create();
    }

    public static SlideDateTimePicker.Builder datetimepicker(AppCompatActivity context) {
        return new SlideDateTimePicker.Builder(context.getSupportFragmentManager());
    }
}
