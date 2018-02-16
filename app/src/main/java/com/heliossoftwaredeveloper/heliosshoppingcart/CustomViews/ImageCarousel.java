package com.heliossoftwaredeveloper.heliosshoppingcart.CustomViews;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heliossoftwaredeveloper.heliosshoppingcart.R;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.ImageLazyLoaderManager;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.Model.ImageRequestData;

import java.util.ArrayList;

/**
 * Customview Class for carousel view
 *
 * {@link ImageCarousel.ImageCarouselListener} interface
 * to handle interaction events.
 *
 * Created by rngrajo on 06/02/2018.
 */
public class ImageCarousel extends RelativeLayout{

    private Context context;
    private ViewPager viewPager;
    private RadioGroup radioGroup;

    private ArrayList<ImageCarouseDataHolder> dataSet = new ArrayList<>();
    private ImageCarouselListener listener;
    private boolean isAutoPlay;
    private CDTimer cdTimer = new CDTimer(6000, 1000);

    /**
     * Class Constructor
     */
    public ImageCarousel(Context context){
        super(context);
        this.context = context;
        initialize();
        attachCallback();
    }

    /**
     * Class Constructor
     */
    public ImageCarousel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if (!isInEditMode()) {
            initialize();
            attachCallback();
        }
    }

    /**
     * Class Constructor
     */
    public ImageCarousel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        if (!isInEditMode()) {
            initialize();
            attachCallback();
        }
    }

    /**
     * Method initialize objects
     */
    protected void initialize(){

        viewPager = new ViewPager(context);
        radioGroup = new RadioGroup(context);

        RelativeLayout.LayoutParams layoutParams1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        viewPager.setLayoutParams(layoutParams1);
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        int margin = convertDpToPixel(8,context);
        layoutParams.setMargins(0,margin,margin,0);
        radioGroup.setLayoutParams(layoutParams);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(radioGroup.getChildCount() > 0)
                ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        addView(viewPager);
        addView(radioGroup);
    }

    public void setData(ArrayList<ImageCarouseDataHolder> dataSet){
        this.dataSet = dataSet;
        viewPager.setAdapter(new CarouselAdapter());
    }

    public void autoplay(boolean status){
        isAutoPlay = status;
        if(status)
            cdTimer.start();
        else
            cdTimer.cancel();
    }

    public void showRadioButtons(){
        radioGroup.removeAllViews();
        LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        int spaces = convertDpToPixel(4,context);
        layoutParams1.setMargins(spaces,spaces,spaces,spaces);
        for(ImageCarouseDataHolder imageCarouseDataHolder : dataSet){
            RadioButton radioButton = new RadioButton(context);
            radioButton.setId(View.generateViewId());
            radioButton.setLayoutParams(layoutParams1);
            radioButton.setButtonDrawable(R.drawable.radiobutton_selector);
            radioButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(radioGroup.indexOfChild(v));
                }
            });
            radioGroup.addView(radioButton);
        }
    }

    /**
     * Method set button click events/callbacks
     */
    public void attachCallback(){
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
     * Method to set the listener/callback
     *
     * @param listener Parameter 1
     */
    public void setListener(ImageCarouselListener listener){
        this.listener = listener;
    }

    /**
     * Interface to handle next and previous button click event.
     */
    public interface ImageCarouselListener{
        void onCarouselItemClicked(Object object);
    }

    class CarouselAdapter extends PagerAdapter {

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            RelativeLayout.LayoutParams layoutParams1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            RelativeLayout layoutContainer = new RelativeLayout(context);
            layoutContainer.setLayoutParams(layoutParams1);
            layoutContainer.setBackgroundColor(Color.WHITE);
            ImageView imageContent = new ImageView(context);

            RelativeLayout.LayoutParams imageParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            imageParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            imageContent.setLayoutParams(imageParams);
            imageContent.setAdjustViewBounds(true);
            imageContent.setScaleType(ImageView.ScaleType.FIT_CENTER);

            ImageLazyLoaderManager.getInstance().setCallback(new ImageLazyLoaderManager.ImageLazyLoaderManagerCallback() {
                @Override
                public void onImageDownloaded(ImageRequestData imageRequestData) {
                    imageRequestData.getImageView().setImageDrawable(imageRequestData.getDownloadedDrawable());
                }

                @Override
                public void onImageDownloadError(String error) {

                }
            });
            ImageLazyLoaderManager.getInstance().loadImage(new ImageRequestData(imageContent, dataSet.get(position).getImageUrl(), ContextCompat.getDrawable(context, R.mipmap.ic_launcher)));

            imageContent.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    listener.onCarouselItemClicked(dataSet.get(position).getObject());
                }
            });

            RelativeLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

            TextView txtContent = new TextView(context);

            txtContent.setLayoutParams(layoutParams);
            txtContent.setTextColor(Color.WHITE);
            txtContent.setText(dataSet.get(position).getLabel());
            txtContent.setBackgroundColor(Color.argb(150, 0,0,0));
            txtContent.setLines(1);
            txtContent.setEllipsize(TextUtils.TruncateAt.END);
            int paddingSmall = convertDpToPixel(8,context);
            txtContent.setPadding(paddingSmall,paddingSmall,paddingSmall,paddingSmall);

            layoutContainer.addView(imageContent);
            layoutContainer.addView(txtContent);

            container.addView(layoutContainer);
            return layoutContainer;
        }

        @Override
        public int getCount() {
            return dataSet.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class CDTimer extends CountDownTimer{

        public CDTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if(viewPager == null || !isAutoPlay)
                return;

            int next = 0 ;
            if(viewPager.getCurrentItem() < dataSet.size() -1)
                next = viewPager.getCurrentItem()+1;

            viewPager.setCurrentItem(next);
            cdTimer.start();
        }
    }
}
