package com.libary.actionsheet;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouguo on 2018/10/10.
 */

public class ActionSheetDialog {
    /**
     * ActionSheetDialog Type
     */
    public static final int DEFAULT_MODE_TYPE = 1;
    public static final int MULT_SELECT_MODE_TYPE = 2;
    public static final int SINGLE_SELECT_MODE_TYPE = 3;

    private Context context;
    private Dialog dialog;
    private TextView txt_title;
    private TextView txt_cancel;
    private LinearLayout lLayout_content;
    private ScrollView sLayout_content;
    private boolean showTitle = false;
    private List<SheetItem> sheetItemList;
    private Display display;
    private int ModeType;

    public ActionSheetDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ActionSheetDialog builder() {

        View view = LayoutInflater.from(context).inflate(
                R.layout.view_actionsheet, null);

        view.setMinimumWidth(display.getWidth());

        sLayout_content = (ScrollView) view.findViewById(R.id.sLayout_content);
        lLayout_content = (LinearLayout) view
                .findViewById(R.id.lLayout_content);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
        txt_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    public ActionSheetDialog setTitle(String title) {
        showTitle = true;
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText(title);
        return this;
    }

    public ActionSheetDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     *
     * @param strItem
     *
     * @param color
     *
     * @param listener
     * @return
     */
    public ActionSheetDialog addSheetItem(String strItem, SheetItemColor color,
                                          OnSheetItemClickListener listener) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        this.ModeType = DEFAULT_MODE_TYPE;
        sheetItemList.add(new SheetItem(strItem, color, listener));
        return this;
    }

    public ActionSheetDialog addSheetItem(String strItem, SheetItemColor color,
                                          OnSheetFirstIconClickListener firstListener,
                                          OnSheetSecondIconClickListener secondListener, int modeType) {

        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        this.ModeType = modeType;
        sheetItemList.add(new SheetItem(strItem, color, firstListener,
                secondListener));
        return this;
    }

    public ActionSheetDialog addSheetItem(String strItem, SheetItemColor color,
                                          OnSheetItemClickListener listener, int modeType, int drawableId) {

        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        this.ModeType = modeType;
        sheetItemList.add(new SheetItem(strItem, color, listener, drawableId));
        return this;
    }

    private void setSheetItemsSingleMode() {

        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }

        int size = sheetItemList.size();

        if (size >= 7) {
            LayoutParams params = (LayoutParams) sLayout_content
                    .getLayoutParams();
            params.height = display.getHeight() / 2;
            sLayout_content.setLayoutParams(params);
        }

        for (int i = 1; i <= size; i++) {
            final int index = i;
            SheetItem sheetItem = sheetItemList.get(i - 1);
            final String strItem = sheetItem.name;
            SheetItemColor color = sheetItem.color;
            int drawableId = sheetItem.drawableId;
            final OnSheetItemClickListener listener = (OnSheetItemClickListener) sheetItem.itemClickListener;

            LinearLayout itemView = (LinearLayout) LayoutInflater.from(context)
                    .inflate(R.layout.item_cardphone, null);
            ((TextView) itemView.findViewById(R.id.txtPhoneNumber))
                    .setText(strItem);
            itemView.setGravity(Gravity.CENTER);

            itemView.findViewById(R.id.imgCallMessage).setVisibility(View.GONE);
            itemView.findViewById(R.id.imgCallPhone).setBackgroundResource(
                    drawableId);

            if (size == 1) {
                if (showTitle) {
                    itemView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                } else {
                    itemView.setBackgroundResource(R.drawable.actionsheet_single_selector);
                }
            } else {
                if (showTitle) {
                    if (i >= 1 && i < size) {
                        itemView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        itemView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                } else {
                    if (i == 1) {
                        itemView.setBackgroundResource(R.drawable.actionsheet_top_selector);
                    } else if (i < size) {
                        itemView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        itemView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                }
            }

            if (color == null) {
                ((TextView) itemView.findViewById(R.id.txtPhoneNumber))
                        .setTextColor(Color.parseColor(SheetItemColor.Blue
                                .getName()));
            } else {
                ((TextView) itemView.findViewById(R.id.txtPhoneNumber))
                        .setTextColor(Color.parseColor(color.getName()));
            }

            // float scale = context.getResources().getDisplayMetrics().density;
            // int height = (int) (45 * scale + 0.5f);
            // itemView.setLayoutParams(new LinearLayout.LayoutParams(
            // LayoutParams.MATCH_PARENT, height));

            itemView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    listener.onClick(index);
                    dialog.dismiss();
                }
            });

            lLayout_content.addView(itemView);
        }
    }

    private void setSheetItemsMultMode() {

        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }

        int size = sheetItemList.size();

        if (size >= 7) {
            LayoutParams params = (LayoutParams) sLayout_content
                    .getLayoutParams();
            params.height = display.getHeight() / 2;
            sLayout_content.setLayoutParams(params);
        }

        for (int i = 1; i <= size; i++) {
            final int index = i;
            SheetItem sheetItem = sheetItemList.get(i - 1);
            final String strItem = sheetItem.name;
            SheetItemColor color = sheetItem.color;
            final OnSheetFirstIconClickListener listener1 = (OnSheetFirstIconClickListener) sheetItem.itemFirstClickListener;
            final OnSheetSecondIconClickListener listener2 = (OnSheetSecondIconClickListener) sheetItem.itemSecondClickListener;

            LinearLayout itemView = (LinearLayout) LayoutInflater.from(context)
                    .inflate(R.layout.item_cardphone, null);
            ((TextView) itemView.findViewById(R.id.txtPhoneNumber))
                    .setText(strItem);
            itemView.setGravity(Gravity.CENTER);

            if (listener1 != null) {
                itemView.findViewById(R.id.imgCallMessage).setVisibility(
                        View.VISIBLE);
            } else {
                itemView.findViewById(R.id.imgCallMessage).setVisibility(
                        View.GONE);
            }

            if (size == 1) {
                if (showTitle) {
                    itemView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                } else {
                    itemView.setBackgroundResource(R.drawable.actionsheet_single_selector);
                }
            } else {
                if (showTitle) {
                    if (i >= 1 && i < size) {
                        itemView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        itemView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                } else {
                    if (i == 1) {
                        itemView.setBackgroundResource(R.drawable.actionsheet_top_selector);
                    } else if (i < size) {
                        itemView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        itemView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                }
            }

            // float scale = context.getResources().getDisplayMetrics().density;
            // int height = (int) (45 * scale + 0.5f);
            // itemView.setLayoutParams(new LinearLayout.LayoutParams(
            // LayoutParams.MATCH_PARENT, height));

            ((ImageView) itemView.findViewById(R.id.imgCallPhone))
                    .setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            if (listener2 != null) {
                                listener2.onClick(strItem);
                                dialog.dismiss();
                            }
                        }
                    });

            ((ImageView) itemView.findViewById(R.id.imgCallMessage))
                    .setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            if (listener1 != null) {
                                listener1.onClick(strItem);
                                dialog.dismiss();
                            }
                        }
                    });

            lLayout_content.addView(itemView);
        }
    }

    private void setSheetItems() {
        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }

        int size = sheetItemList.size();

        if (size >= 7) {
            LayoutParams params = (LayoutParams) sLayout_content
                    .getLayoutParams();
            params.height = display.getHeight() / 2;
            sLayout_content.setLayoutParams(params);
        }

        for (int i = 1; i <= size; i++) {
            final int index = i;
            SheetItem sheetItem = sheetItemList.get(i - 1);
            String strItem = sheetItem.name;
            SheetItemColor color = sheetItem.color;
            final OnSheetItemClickListener listener = (OnSheetItemClickListener) sheetItem.itemClickListener;

            TextView textView = new TextView(context);
            textView.setText(strItem);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);

            if (size == 1) {
                if (showTitle) {
                    textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                } else {
                    textView.setBackgroundResource(R.drawable.actionsheet_single_selector);
                }
            } else {
                if (showTitle) {
                    if (i >= 1 && i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                } else {
                    if (i == 1) {
                        textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
                    } else if (i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                }
            }

            if (color == null) {
                textView.setTextColor(Color.parseColor(SheetItemColor.Blue
                        .getName()));
            } else {
                textView.setTextColor(Color.parseColor(color.getName()));
            }

            float scale = context.getResources().getDisplayMetrics().density;
            int height = (int) (45 * scale + 0.5f);
            textView.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, height));

            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(index);
                    dialog.dismiss();
                }
            });

            lLayout_content.addView(textView);
        }
    }

    public void show() {
        switch (ModeType) {
            case DEFAULT_MODE_TYPE:
                setSheetItems();
                break;
            case MULT_SELECT_MODE_TYPE:

                setSheetItemsMultMode();
                break;
            case SINGLE_SELECT_MODE_TYPE:

                setSheetItemsSingleMode();
                break;
            default:
                break;
        }

        dialog.show();
    }

    public interface OnSheetItemClickListener {
        void onClick(int which);
    }

    public interface OnSheetFirstIconClickListener {
        void onClick(String value);
    }

    public interface OnSheetSecondIconClickListener {
        void onClick(String value);
    }

    public class SheetItem {
        String name;
        OnSheetItemClickListener itemClickListener;
        OnSheetFirstIconClickListener itemFirstClickListener;
        OnSheetSecondIconClickListener itemSecondClickListener;
        SheetItemColor color;
        int drawableId;

        public SheetItem(String name, SheetItemColor color,
                         OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.color = color;
            this.itemClickListener = itemClickListener;
        }

        public SheetItem(String name, SheetItemColor color,
                         OnSheetItemClickListener itemClickListener, int drawableId) {

            this.name = name;
            this.color = color;
            this.drawableId = drawableId;
            this.itemClickListener = itemClickListener;
        }

        public SheetItem(String name, SheetItemColor color,
                         OnSheetFirstIconClickListener itemFirstClickListener,
                         OnSheetSecondIconClickListener itemSecondClickListener) {

            this.name = name;
            this.color = color;
            this.itemFirstClickListener = itemFirstClickListener;
            this.itemSecondClickListener = itemSecondClickListener;
        }
    }

    public  enum SheetItemColor {
        Blue("#037BFF"), Red("#FD4A2E");

        private String name;

        private SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
