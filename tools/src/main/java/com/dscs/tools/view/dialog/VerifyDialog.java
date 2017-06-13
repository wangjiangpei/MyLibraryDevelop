package com.dscs.tools.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.dscs.tools.R;


/**
 *
 */

public class VerifyDialog extends Dialog implements View.OnClickListener {
    private VerifyListener listener;
    private Context context;
    private TextView info;
    private TextView confirm;
    private TextView countermand;
    private String message;
    private String confirmText;
    private String countermandText;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirm) {
            listener.confirm();
        } else {
            listener.countermand();
        }
        VerifyDialog.this.cancel();
    }


    public interface VerifyListener {
        public void confirm();

        public void countermand();
    }

    public VerifyDialog(Context context) {
        this(context, R.style.quick_option_dialog);

    }

    public VerifyDialog(Context context, int themeResId) {
        super(context, themeResId);
        View view = getLayoutInflater().inflate(R.layout.dialog_verify, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.setContentView(view);
    }
    public VerifyDialog setVerifyListener(VerifyListener listener) {
        this.listener = listener;
        return this;
    }
    public VerifyDialog setMessage(String message) {
        this.message=message;
        return this;
    }
    public VerifyDialog setText(String confirmText,String countermandText){
       this.confirmText = confirmText;
        this.countermandText=countermandText;
        return this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();


    }

    private void init() {
        confirm = (TextView) findViewById(R.id.confirm);
        countermand = (TextView) findViewById(R.id.countermand);
        info = (TextView) findViewById(R.id.info);
        if (confirmText!=null) {
            confirm.setText(confirmText);
        }
        if (countermandText!=null) {
            countermand.setText(countermandText);
        }
        info.setText(message);
        confirm.setOnClickListener(this);
        countermand.setOnClickListener(this);
    }
}
