package com.heliossoftwaredeveloper.heliosshoppingcart.Utilities;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;
import java.util.ArrayList;

/**
 * Created by rngrajo on 06/02/2018.
 */

public class HorizontalDataSetPicker extends LinearLayout{

    private Context context;
    private TextView txtValueHolder, txtLabel;
    private ImageButton imgBtnPrevious, imgBtnNext;
    private ArrayList<Integer> dataSetInt = new ArrayList<>();
    private ArrayList<String> dataSetString = new ArrayList<>();

    private int currentPosition = 0;
    private DataType dataType;
    private boolean enabled;
    private String labelText;
    private HorizontalDataSetPickerListener listener;
    private int minimumValue = 1, maximumValue = 99;

    public static enum DataType {
        TEXT,
        NUMERIC
    }

    public HorizontalDataSetPicker(Context context){
        super(context);
        this.context = context;
        initialize();
        attachCallback();
    }

    public HorizontalDataSetPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if (!isInEditMode()) {
            getAttributes(attrs);
            initialize();
            attachCallback();
        }
    }

    public HorizontalDataSetPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        if (!isInEditMode()) {
            getAttributes(attrs);
            initialize();
            attachCallback();
        }
    }

    private void getAttributes(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HorizontalDataSetPickerField);
            try {
                enabled = ta.getBoolean(R.styleable.HorizontalDataSetPickerField_isEnabled, true);
                labelText = ta.getString(R.styleable.HorizontalDataSetPickerField_labelText);
                dataType = DataType.values()[ta.getInt(R.styleable.HorizontalDataSetPickerField_dataType,
                        DataType.NUMERIC.ordinal())];
            } finally {
                ta.recycle();
            }
        }
    }

    protected void initialize(){
        setOrientation(HORIZONTAL);

        txtValueHolder = new TextView(context);
        txtLabel = new TextView(context);
        imgBtnPrevious = new ImageButton(context);
        imgBtnNext = new ImageButton(context);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 0);
        params.gravity = Gravity.CENTER;

        txtValueHolder.setTypeface(txtValueHolder.getTypeface(), Typeface.BOLD);
        txtValueHolder.setLayoutParams(params);
        txtValueHolder.setTextColor(Color.BLACK);
        txtValueHolder.setGravity(Gravity.CENTER);

        txtLabel.setTypeface(txtLabel.getTypeface(), Typeface.BOLD);
        txtLabel.setLayoutParams(params);
        txtLabel.setTextColor(Color.BLACK);
        txtLabel.setGravity(Gravity.CENTER);

        LayoutParams helperTextParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        helperTextParams.setMargins(0, 0, 0, 0);
        imgBtnNext.setLayoutParams(helperTextParams);
        imgBtnPrevious.setLayoutParams(helperTextParams);

        imgBtnNext.setPadding(convertDpToPixel(8, context),0,convertDpToPixel(8, context),0);
        imgBtnPrevious.setPadding(convertDpToPixel(8, context),0,convertDpToPixel(8, context),0);

        imgBtnNext.setImageResource(R.drawable.ic_navigate_next);
        imgBtnPrevious.setImageResource(R.drawable.ic_navigate_before);

        imgBtnNext.setBackgroundResource(0);
        imgBtnPrevious.setBackgroundResource(0);
        txtValueHolder.setText("0");

        txtLabel.setText(labelText);


        addView(txtLabel);
        addView(imgBtnPrevious);
        addView(txtValueHolder);
        addView(imgBtnNext);
    }

    public void attachCallback(){
        imgBtnPrevious.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(currentPosition > 0){
                    int newPosition = currentPosition - 1;

                    if(dataType == DataType.NUMERIC)
                        txtValueHolder.setText(Integer.toString(dataSetInt.get(newPosition)));
                    else
                        txtValueHolder.setText(dataSetString.get(newPosition));

                    currentPosition = newPosition;
                    if(currentPosition == 0){
                        imgBtnPrevious.setVisibility(View.INVISIBLE);
                    }
                    imgBtnNext.setVisibility(View.VISIBLE);


                    if(listener!=null)
                        listener.onPreviousClickedListener(txtValueHolder.getText().toString(),dataSetInt.get(newPosition));
                }
            }
        });

        imgBtnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(currentPosition < dataSetInt.size()){
                    int newPosition = currentPosition + 1;

                    if(dataType == DataType.NUMERIC)
                        txtValueHolder.setText(Integer.toString(dataSetInt.get(newPosition)));
                    else
                        txtValueHolder.setText(dataSetString.get(newPosition));

                    currentPosition = newPosition;
                    if(currentPosition == (dataSetInt.size()-1)){
                        imgBtnNext.setVisibility(View.INVISIBLE);
                    }
                    imgBtnPrevious.setVisibility(View.VISIBLE);

                    if(listener!=null)
                        listener.onNextClickedListener(txtValueHolder.getText().toString(), dataSetInt.get(newPosition));
                }
            }
        });
    }

    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    public void setDataSetInt(ArrayList<Integer> dataSetInt){
        this.dataSetInt.clear();
        this.dataSetInt.addAll(dataSetInt);
        txtValueHolder.setText(Integer.toString(dataSetInt.get(0)));
        imgBtnPrevious.setVisibility(View.INVISIBLE);
    }

    public void setMinimumValue(int minimumValue){
        this.minimumValue = minimumValue;
    }

    public void setMaximumValue(int maximumValue){
        this.maximumValue = maximumValue;
    }

    public void setDataSetString(ArrayList<String> dataSetString){
        this.dataSetString.clear();
        this.dataSetString.addAll(dataSetString);
        txtValueHolder.setText(dataSetString.get(0));
        imgBtnPrevious.setVisibility(View.INVISIBLE);
    }

    public void setDataRange(int minimumValue, int maximumValue, int selected){
        selected = selected - 1;
        this.maximumValue = maximumValue;
        this.minimumValue = minimumValue;

        ArrayList<Integer> rangeSet = new ArrayList<>();
        for(int i =minimumValue; i <= maximumValue; i++){
            rangeSet.add(i);
        }

        this.dataSetInt.clear();
        this.dataSetInt.addAll(rangeSet);
        txtValueHolder.setText(Integer.toString(rangeSet.get(selected)));
        currentPosition = selected;
        if(currentPosition == 0)
            imgBtnPrevious.setVisibility(View.INVISIBLE);
    }

    public void setLabelText(String labelText){
        this.labelText = labelText;
        txtLabel.setText(labelText);
    }

    public String getValueString(){
        if(dataType == DataType.NUMERIC)
            return Integer.toString(dataSetInt.get(currentPosition));
        else
            return dataSetString.get(currentPosition);
    }

    public Integer getValueInt(){
        if(dataType == DataType.NUMERIC)
            return dataSetInt.get(currentPosition);
        else
            return Integer.valueOf(dataSetString.get(currentPosition));
    }


    public void setListener(HorizontalDataSetPickerListener listener){
        this.listener = listener;
    }

    public interface HorizontalDataSetPickerListener{
        void onNextClickedListener(String value, int intValue);
        void onPreviousClickedListener(String value, int intValue);
    }



}
