package com.sheet;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.libary.actionsheet.ActionSheetDialog;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private String[] data = new String[]{"1","2","3"};
    private Button button1,button2,button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDefaultActionSheet();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMultActionSheet();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSingleActionSheet();
            }
        });
    }

    private void showDefaultActionSheet(){
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(
                mContext).builder().setCancelable(false)
                .setCanceledOnTouchOutside(true);
        for (int i = 0; i < data.length; i++) {
            actionSheetDialog.addSheetItem(data[i],
                    ActionSheetDialog.SheetItemColor.Blue,
                    new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {
                            Toast.makeText(mContext,"点击的是："+data[which - 1],Toast.LENGTH_LONG).show();
                        }
                    });
        }
        actionSheetDialog.show();
    }

    private void showMultActionSheet(){
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(
                mContext).builder().setCancelable(false)
                .setCanceledOnTouchOutside(true);
        for (int i = 0; i < data.length; i++) {
            actionSheetDialog.addSheetItem(data[i],
                    ActionSheetDialog.SheetItemColor.Blue,
                    /*new ActionSheetDialog.OnSheetFirstIconClickListener() {
                        @Override
                        public void onClick(String value) {
                            Toast.makeText(mContext,"frist icon"+value,Toast.LENGTH_LONG).show();
                        }
                    }*/null, new ActionSheetDialog.OnSheetSecondIconClickListener() {
                        @Override
                        public void onClick(String value) {
                            Toast.makeText(mContext,"second icon"+value,Toast.LENGTH_LONG).show();
                        }
                    },ActionSheetDialog.MULT_SELECT_MODE_TYPE);
        }
        actionSheetDialog.show();
    }

    private void showSingleActionSheet(){
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(
                mContext).builder().setCancelable(false)
                .setCanceledOnTouchOutside(true);
        for (int i = 0; i < data.length; i++) {
            actionSheetDialog.addSheetItem(data[i],
                    ActionSheetDialog.SheetItemColor.Blue,
                    new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {
                            Toast.makeText(mContext,"点击的是："+data[which - 1],Toast.LENGTH_LONG).show();
                        }
                    },ActionSheetDialog.SINGLE_SELECT_MODE_TYPE,R.drawable.action_message);
        }
        actionSheetDialog.show();
    }
}
