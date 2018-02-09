package com.heliossoftwaredeveloper.heliosshoppingcart.CustomViews;

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
 * Customview Class that handles dataset selection/picker
 *
 * {@link HorizontalDataSetPicker.HorizontalDataSetPickerListener} interface
 * to handle interaction events.
 *
 * Created by rngrajo on 06/02/2018.
 */
public class HorizontalDataSetPicker extends LinearLayout{

    private Context context;
    private TextView txtValueHolder, txtLabel;
    private ImageButton imgBtnPrevious, imgBtnNext;
    private ArrayList<Integer> dataSetInt = new ArrayList<>();
    private ArrayList<String> dataSetString = new ArrayList<>();

    private DataType dataType;
    private boolean enabled, infinite;
    private String labelText;
    private Object listener;
    private int minimumValue, maximumValue, currentPosition;

    /**
     * Enum for DataType
     */
    public static enum DataType {
        TEXT,
        NUMERIC
    }

    /**
     * Class Constructor
     */
    public HorizontalDataSetPicker(Context context){
        super(context);
        this.context = context;
        initialize();
        attachCallback();
    }

    /**
     * Class Constructor
     */
    public HorizontalDataSetPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if (!isInEditMode()) {
            getAttributes(attrs);
            initialize();
            attachCallback();
        }
    }

    /**
     * Class Constructor
     */
    public HorizontalDataSetPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        if (!isInEditMode()) {
            getAttributes(attrs);
            initialize();
            attachCallback();
        }
    }

    /**
     * Method to get style attributes from attrs.xml
     *
     * @param attrs Parameter 1
     */
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

    /**
     * Method initialize objects
     */
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
        setInfinite(true);
        setLabelText(labelText);
        addView(txtLabel);
        addView(imgBtnPrevious);
        addView(txtValueHolder);
        addView(imgBtnNext);
    }

    /**
     * Method set button click events/callbacks
     */
    public void attachCallback(){
        imgBtnPrevious.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                movePositionToPrevious();
            }
        });

        imgBtnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                movePositionToNext();
            }
        });
    }

    public void movePositionToNext(){
        if(dataType == DataType.NUMERIC){
            if(currentPosition < (dataSetInt.size()-1)){
                currentPosition+=1;
            }
            else
                currentPosition = 0;
            updateValueHolder(Integer.toString(dataSetInt.get(currentPosition)));
        }
        else{
            if(currentPosition < (dataSetString.size()-1)){
                currentPosition+=1;
            }
            else
                currentPosition = 0;
            updateValueHolder(dataSetString.get(currentPosition));
        }
        if(listener!=null){
            if(currentPosition == 0){
                if(listener instanceof HorizontalDataSetPickerListener.HorizontalDataSetPickerListenerInfinite )
                    ((HorizontalDataSetPickerListener.HorizontalDataSetPickerListenerInfinite)listener).onInfiniteTriggeredListener(txtValueHolder.getText().toString(),Integer.valueOf(txtValueHolder.getText().toString()));
            }
            else{
                if(listener instanceof HorizontalDataSetPickerListener.HorizontalDataSetPickerListenerNotInfinite )
                    ((HorizontalDataSetPickerListener.HorizontalDataSetPickerListenerNotInfinite)listener).onNextClickedListener(txtValueHolder.getText().toString(),Integer.valueOf(txtValueHolder.getText().toString()));
                if(listener instanceof HorizontalDataSetPickerListener.HorizontalDataSetPickerListenerInfinite )
                    ((HorizontalDataSetPickerListener.HorizontalDataSetPickerListenerInfinite)listener).onNextClickedListener(txtValueHolder.getText().toString(),Integer.valueOf(txtValueHolder.getText().toString()));
            }
        }
    }

    public void movePositionToPrevious(){
        int maxSize;
        if(dataType == DataType.NUMERIC){
            maxSize = dataSetInt.size()-1;
            if(currentPosition > 0){
                currentPosition-=1;
            }
            else
                currentPosition = maxSize;
            updateValueHolder(Integer.toString(dataSetInt.get(currentPosition)));
        }
        else{
            maxSize = dataSetString.size()-1;
            if(currentPosition > 0){
                currentPosition-=1;
            }
            else
                currentPosition = maxSize;
            updateValueHolder(dataSetString.get(currentPosition));
        }
        if(listener!=null){
            if(currentPosition == maxSize){
                if(listener instanceof HorizontalDataSetPickerListener.HorizontalDataSetPickerListenerInfinite )
                    ((HorizontalDataSetPickerListener.HorizontalDataSetPickerListenerInfinite)listener).onInfiniteTriggeredListener(txtValueHolder.getText().toString(),Integer.valueOf(txtValueHolder.getText().toString()));
            }
            else{
                if(listener instanceof HorizontalDataSetPickerListener.HorizontalDataSetPickerListenerInfinite )
                    ((HorizontalDataSetPickerListener.HorizontalDataSetPickerListenerInfinite)listener).onPreviousClickedListener(txtValueHolder.getText().toString(),Integer.valueOf(txtValueHolder.getText().toString()));
                else
                    ((HorizontalDataSetPickerListener.HorizontalDataSetPickerListenerNotInfinite)listener).onPreviousClickedListener(txtValueHolder.getText().toString(),Integer.valueOf(txtValueHolder.getText().toString()));
            }
        }
    }

    public void updateValueHolder(String value){
        txtValueHolder.setText(value);
    }

    /**
     * Method to convert DP to Pixel
     *
     * @param dp Parameter 1
     * @param context Parameter 2
     *
     * @return the convert value of DP to Pixel
     */
    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    /**
     * Method to set the minimum value of range
     *
     * @param minimumValue Parameter 1
     */
    public void setMinimumValue(int minimumValue){
        this.minimumValue = minimumValue;
    }

    /**
     * Method to set the maximum value of range
     *
     * @param maximumValue Parameter 1
     */
    public void setMaximumValue(int maximumValue){
        this.maximumValue = maximumValue;
    }

    /**
     * Method to set the data with Integer objects
     *
     * @param dataSetInt Parameter 1
     */
    public void setDataSetInt(ArrayList<Integer> dataSetInt){
        this.dataSetInt.clear();
        this.dataSetInt.addAll(dataSetInt);
        txtValueHolder.setText(Integer.toString(dataSetInt.get(0)));
        if(!isInfinite())
        imgBtnPrevious.setVisibility(View.INVISIBLE);
    }

    /**
     * Method to set the data with String objects
     *
     * @param dataSetString Parameter 1
     */
    public void setDataSetString(ArrayList<String> dataSetString){
        this.dataSetString.clear();
        this.dataSetString.addAll(dataSetString);
        txtValueHolder.setText(dataSetString.get(0));
        if(!isInfinite())
        imgBtnPrevious.setVisibility(View.INVISIBLE);
    }

    /**
     * Method to set the data in range
     *
     * @param minimumValue Parameter 1
     * @param maximumValue Parameter 2
     * @param selected  Parameter 3
     */
    public void setDataRange(int minimumValue, int maximumValue, int selected){
        selected = selected - 1;
        setMaximumValue(maximumValue);
        setMinimumValue(minimumValue);

        ArrayList<Integer> rangeSet = new ArrayList<>();
        for(int i =minimumValue; i <= maximumValue; i++){
            rangeSet.add(i);
        }

        this.dataSetInt.clear();
        this.dataSetInt.addAll(rangeSet);
        txtValueHolder.setText(Integer.toString(rangeSet.get(selected)));
        currentPosition = selected;
        if(currentPosition == 0 && !isInfinite())
            imgBtnPrevious.setVisibility(View.INVISIBLE);
    }

    /**
     * Method to set the label text
     *
     * @param labelText Parameter 1
     */
    public void setLabelText(String labelText){
        this.labelText = labelText;
        txtLabel.setText(labelText);
    }

    public boolean isInfinite() {
        return infinite;
    }

    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }

    /**
     * Method to get the current selected value in String data type
     *
     * @return the current selected value
     */
    public String getValueString(){
        if(dataType == DataType.NUMERIC)
            return Integer.toString(dataSetInt.get(currentPosition));
        else
            return dataSetString.get(currentPosition);
    }

    /**
     * Method to get the current selected value in Integer data type
     *
     * @return the current selected value
     */
    public Integer getValueInt(){
        if(dataType == DataType.NUMERIC)
            return dataSetInt.get(currentPosition);
        else
            return Integer.valueOf(dataSetString.get(currentPosition));
    }

    /**
     * Method to set the listener/callback
     *
     * @param listener Parameter 1
     */
    public void setListener(Object listener){
        this.listener = listener;
    }

    /**
     * Interface to handle next and previous button click event.
     */
    public interface HorizontalDataSetPickerListener{
        interface HorizontalDataSetPickerListenerInfinite{
            void onNextClickedListener(String value, int intValue);
            void onPreviousClickedListener(String value, int intValue);
            void onInfiniteTriggeredListener(String value, int intValue);
        }

        interface HorizontalDataSetPickerListenerNotInfinite{
            void onNextClickedListener(String value, int intValue);
            void onPreviousClickedListener(String value, int intValue);
        }
    }
}
