package com.linhlee.vidientu.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.linhlee.vidientu.R;

/**
 * Created by Linh Lee on 6/19/2017.
 */
public class NoticeDialog extends Dialog {
    private String content;
    private TextView contentText;
    private TextView okButton;

    public NoticeDialog(Context context, String content) {
        super(context);
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_notice);

        contentText = (TextView) findViewById(R.id.content_text);
        okButton = (TextView) findViewById(R.id.ok_button);

        contentText.setText(content);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
