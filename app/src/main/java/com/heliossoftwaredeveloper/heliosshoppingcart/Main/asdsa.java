/* (c) Disney. All rights reserved. *//*

package com.disney.wdpro.support.input;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.disney.wdpro.commons.annotations.AccessibilityValues;
import com.disney.wdpro.support.R;
import com.disney.wdpro.support.accessibility.ContentDescriptionBuilder;
import com.disney.wdpro.support.input.validation.AbstractValidator;
import com.disney.wdpro.support.input.validation.Validator;
import com.disney.wdpro.support.util.ViewUtil;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.support.v4.widget.TextViewCompat.setTextAppearance;

*/
/**
 * This class provides a skeletal implementation of a <tt>FloatLabelTextField</tt> view to minimize
 * the effort required to implement a concrete class. This Abstract class also provides common
 * functionality to all <tt>FloatLabelTextField</tt>.
 * <p>
 * To implement a <tt>FloatLabelTextField</tt>, the programmer needs only to extend this class and
 * he will get an EditText with the FloatingLabel pattern, a clear text button and the ability to
 * add Filters and Validators.
 * <p>
 * The documentation for each non-abstract method in this class describes its implementation in
 * detail. Each of these methods may be overridden if the EditText being implemented admits a more
 * efficient or different implementation.
 * <p>
 * This class is a member of the Disney "Forms" Input framework
 * <p>
 * NOTE: <a href="https://github.disney.com/pages/snowball/ref-app/style/textfields/">Style and behavior Document</a>
 *
 * @author frjaimes
 *//*

public abstract class AbstractFloatLabelTextField extends LinearLayout {

    public interface ValidationFieldListener {
        void onValidationErrorFired();
    }

    public static enum DataType {
        TEXT,
        ALPHANUMERIC,
        NUMERIC,
        NAME,
        PASSWORD
    }

    // Returns drawables index for the left, top, right, and bottom borders of a textview.
    public static enum TextViewDrawableSides {
        TEXTVIEW_DRAWABLE_LEFT(0),
        TEXTVIEW_DRAWABLE_TOP(1),
        TEXTVIEW_DRAWABLE_RIGHT(2),
        TEXTVIEW_DRAWABLE_BOTTOM(3);

        private int side = 0;

        TextViewDrawableSides(int side) {
            this.side = side;
        }

        public int getSideIndex() {
            return side;
        }
    }

    private ValidationFieldListener validationFieldListener;
    private String label;
    private String errorMessage;
    private CharSequence announceForAccessibilityErrorPrefix;
    private String helperText;
    private int minLength;
    private int maxLenght;
    private int defaultLeftPadding;
    private int validationColor;
    private int labelFocusColor;
    private int editTextImportantForAccessibility;
    private boolean enableAnnounceForAccessibilityOnError;
    private boolean replaceAsteriskEnabled;
    private boolean preventAddingEditTextOnAccessibility;
    private boolean preventAddingLabelOnAccessibility;
    private int editTextId;

    private boolean isFloatingLabelEnable;
    protected boolean isClearButtonEnable;
    private boolean isEmptyAllowed;
    private boolean enabled;
    private boolean valid;
    private boolean isNotValidShown;
    private boolean isNotValidShownOnFocus;
    private boolean isLabelShown;
    private boolean addRequiredToAccessibility;
    private boolean innerElementsAccessibilityEnabled;
    private String textForAccessibility;
    private DataType dataType;
    private List<Validator> validators;
    private Validator activatedValidator;
    private List<OnFocusChangeListener> onFocusChangeListeners;
    private int hint_color = getResources().getColor(R.color.text_hint);
    private String contentDescriptionPrefix;

    private Context context;
    private TextView textView;
    private TextView helperTextView;
    protected EditText editText;
    protected RelativeLayout editTextLayout;
    protected ImageView closeButtonView;
    private int clearButtonId = getResources().getIdentifier("ic_clear_holo_light", "drawable", "android");
    protected Drawable imgCloseButton = getResources().getDrawable(clearButtonId);
    protected int defaultBackgroundId = getResources().getIdentifier("textfield_multiline_default_holo_light",
            "drawable", "android");
    private int activeBackgroundId = getResources().getIdentifier("textfield_multiline_activated_holo_light",
            "drawable", "android");
    private int errorBackgroundId = R.drawable.inputlib_default_edit_text_error;

    private static final String REGEX_FOR_END_ASTERISK = "\\*$";
    private static final String EMPTY_STRING = "";
    private static final String ASTERISK = "*";
    private static final InputFilter[] EMPTY_ARRAY_FILTERS = new InputFilter[0];

    public AbstractFloatLabelTextField(Context context) {
        super(context);
        this.context = context;
        initialize();
        attachListeners();
    }

    public AbstractFloatLabelTextField(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getAttributes(attrs);
        if (!isInEditMode()) {
            initialize();
            attachListeners();
        }
    }

    public AbstractFloatLabelTextField(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        getAttributes(attrs);
        if (!isInEditMode()) {
            initialize();
            attachListeners();
        }
    }

    */
/**
     * Retrieve styled attribute information in the Context's theme. See
     * {@link Resources.Theme#obtainStyledAttributes(int, int[])} for more information. The custom
     * attributes are defined in attrs.xml file. If no attribute is defined, default ones are
     * provided.
     *
     * @param attrs attributes defined in the context
     *//*

    private void getAttributes(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FloatingLabelTextField);
            try {

                isFloatingLabelEnable = ta.getBoolean(R.styleable.FloatingLabelTextField_isFloatingLabel, true);
                isClearButtonEnable = ta.getBoolean(R.styleable.FloatingLabelTextField_isClearButton, true);
                isEmptyAllowed = ta.getBoolean(R.styleable.FloatingLabelTextField_isEmptyAllowed, false);
                enabled = ta.getBoolean(R.styleable.FloatingLabelTextField_isEnabled, true);
                validationColor = ta.getColor(R.styleable.FloatingLabelTextField_validationColor, Color.RED);
                labelFocusColor = ta.getColor(R.styleable.FloatingLabelTextField_labelFocusColor, hint_color);
                dataType = DataType.values()[ta.getInt(R.styleable.FloatingLabelTextField_dataType,
                        DataType.TEXT.ordinal())];
                isNotValidShownOnFocus = ta.getBoolean(R.styleable.FloatingLabelTextField_isNotValidShownOnFocus, false);

                label = ta.getString(R.styleable.FloatingLabelTextField_floatingLabel);
                if (label == null) {
                    label = context.getString(R.string.default_label);
                }
                errorMessage = ta.getString(R.styleable.FloatingLabelTextField_validationMessage);
                if (errorMessage == null) {
                    errorMessage = context.getString(R.string.default_validation_error_message, label.toLowerCase(Locale.US));
                }
                enableAnnounceForAccessibilityOnError = ta.getBoolean(R.styleable.FloatingLabelTextField_isAccessibilityOnErrorEnabled, false);
                replaceAsteriskEnabled = ta.getBoolean(
                        R.styleable.FloatingLabelTextField_enableReplacingFinalLabelAsteriskWithRequired,
                        false);
                preventAddingEditTextOnAccessibility = ta.getBoolean(
                        R.styleable.FloatingLabelTextField_preventAddingEditTextOnAccessibility,
                        false);
                preventAddingLabelOnAccessibility = ta.getBoolean(
                        R.styleable.FloatingLabelTextField_preventAddingLabelOnAccessibility,
                        false);
                announceForAccessibilityErrorPrefix = ta.getString(R.styleable.FloatingLabelTextField_announceForAccessibilityErrorPrefix);
                helperText = ta.getString(R.styleable.FloatingLabelTextField_helperText);
                addRequiredToAccessibility = ta.getBoolean(R.styleable.FloatingLabelTextField_addRequiredToAccessibility, false);
                textForAccessibility = ta.getString(R.styleable.FloatingLabelTextField_textForAccessibility);
                editTextId = ta.getResourceId(R.styleable.FloatingLabelTextField_editTextId, View.NO_ID);

            } finally {
                ta.recycle();
            }
        }
    }

    */
/**
     * Method that initialize the Custom <tt>FloatLabelTextField</tt>, setting the clear button,
     * creating the custom component to provide the floating label functionality and setting the
     * onTouchListener and TextChangedListener to provide functionality depending on the state of
     * the EditText.
     *//*

    protected void initialize() {
        valid = true;
        isNotValidShown = false;
        isLabelShown = false;
        validators = new ArrayList<>();
        onFocusChangeListeners = new ArrayList<>();

        textView = new TextView(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0, ViewUtil.convertDpToPixel(6, context), 0, 0);
        textView.setLayoutParams(params);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_size_12));
        textView.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);

        editText = initializeEditText(context);
        //We first init as not important because the edittext is empty, after that depends on editTextImportantForAccessibility that should be YES
        // by default
        setEditTextImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);

        defaultLeftPadding = editText.getPaddingLeft();
        imgCloseButton.setBounds(0, 0, imgCloseButton.getIntrinsicWidth(), imgCloseButton.getIntrinsicHeight());

        helperTextView = new TextView(context);
        LayoutParams helperTextParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        Resources resources = context.getResources();
        helperTextParams.setMargins(resources.getDimensionPixelSize(R.dimen.margin_large), resources.getDimensionPixelSize(R.dimen.margin_normal),
                0, 0);
        helperTextView.setLayoutParams(helperTextParams);
        helperTextView.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);
        helperTextView.setText(helperText);
        helperTextView.setVisibility(Strings.isNullOrEmpty(helperText) ? GONE : VISIBLE);

        textView.setText(label);
        editText.setHint(label);
        createDefaultLayout();
        setOrientation(LinearLayout.VERTICAL);
        addView(textView);

        createClearButtonWithAccessibility();
        editText.setId(editTextId);
        addView(editTextLayout);
        addView(helperTextView);

        textView.setVisibility(View.INVISIBLE);
        setEnabled(enabled);
        editText.setBackgroundResource(android.R.color.transparent);
        editTextLayout.setBackgroundResource(defaultBackgroundId);
        showClearButton(!editText.getText().toString().isEmpty());
        validate();

        initializeAccessibility();
    }

    */
/**
     * We separate this here because methods like {@link android.widget.EditText#setTransformationMethod}
     * or {@link android.widget.EditText#setInputType} call listeners for text change and having
     * the {@link #isNotValidShownOnFocus} set to true  and {@link #isEmptyAllowed } set to false
     * was causing the error appear as soon as the page is rendered
     *//*

    protected void attachListeners() {
        // if text changes, take care of the button
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearButton(!editText.getText().toString().isEmpty());
                displayValidationStatus();
            }

            @Override
            public void afterTextChanged(Editable textEditable) {
                setEditTextAppearance();
                if (!isNotValidShown) {
                    setContentDescriptionWithEditTextMessage();
                }
                showClearButton(editText.hasFocus());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if (!isNotValidShown) {
                        editTextLayout.setBackgroundResource(activeBackgroundId);
                    }
                    String input = editText.getText().toString();
                    if (!input.isEmpty()) {
                        showClearButton(true);
                    }
                }
                if (!hasFocus) {
                    editText.setImportantForAccessibility(editTextImportantForAccessibility);
                    if (validate()) {
                        editTextLayout.setBackgroundResource(defaultBackgroundId);
                    } else {
                        showNotValid(!isNotValidShown);
                    }
                    showClearButton(false);
                }
                enableInnerElementsAccessibility(hasFocus);
                for (OnFocusChangeListener listener : onFocusChangeListeners) {
                    listener.onFocusChange(view, hasFocus);
                }
            }
        });
    }

    protected void initializeAccessibility() {

        editText.setAccessibilityDelegate(new AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);

                if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED) {
                    enableInnerElementsAccessibility(true);
                }

                ContentDescriptionBuilder builder = new ContentDescriptionBuilder(context)
                        .append(setContentDescriptionWithEditTextMessage());
                host.setContentDescription(builder.toString());
            }
        });
    }

    private void enableInnerElementsAccessibility(boolean enable) {
        innerElementsAccessibilityEnabled = enable;
        setEditTextImportantForAccessibility(enable ? IMPORTANT_FOR_ACCESSIBILITY_YES : IMPORTANT_FOR_ACCESSIBILITY_NO);
        closeButtonView.setImportantForAccessibility(enable ? IMPORTANT_FOR_ACCESSIBILITY_YES : IMPORTANT_FOR_ACCESSIBILITY_NO);
        setImportantForAccessibility(enable ? IMPORTANT_FOR_ACCESSIBILITY_NO : IMPORTANT_FOR_ACCESSIBILITY_YES);
        if (enable) {
            editText.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
        } else {
            sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
            editText.clearFocus();
        }
    }

    private void createClearButtonWithAccessibility() {
        editTextLayout = new RelativeLayout(this.context);
        editTextLayout.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 0);
        editText.setLayoutParams(layoutParams);
        editTextLayout.addView(editText);

        closeButtonView = new ImageView(this.context);
        closeButtonView.setContentDescription(context.getString(R.string.accessibility_clear_button));
        closeButtonView.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
        closeButtonView.setImageDrawable(imgCloseButton);
        closeButtonView.setBackgroundResource(android.R.color.transparent);
        layoutParams = new RelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.img_close_button_size),
                getResources().getDimensionPixelSize(R.dimen.img_close_button_size));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.addRule(RelativeLayout.RIGHT_OF, editText.getId());
        closeButtonView.setLayoutParams(layoutParams);
        closeButtonView.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.padding_bottom_close_button_size));
        closeButtonView.setClickable(true);
        closeButtonView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClearButtonEnable) {
                    clear();
                    showClearButton(false);
                    hideLabel();
                    enableInnerElementsAccessibility(true);
                }
            }
        });

        editTextLayout.addView(closeButtonView);
    }

    */
/**
     * Display the corresponding validation label
     *//*

    public void displayValidationStatus() {
        if (!validate()) {
            if (editText.hasFocus()) {
                if (isNotValidShownOnFocus) {
                    showNotValid(!isNotValidShown);
                } else {
                    if (!editText.getText().toString().isEmpty()) {
                        showLabel();
                    } else {
                        hideLabel();
                    }
                }
            } else if (!editText.getText().toString().isEmpty()) {
                showNotValid(!isNotValidShown);
            }
            editText.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);
        } else {
            String text = editText.getText().toString();
            if (!text.isEmpty()) {
                showLabel();
            } else {
                hideLabel();
            }
            //When the edit text is active we should be able to focus on it if it is not a secure input.
            editText.setImportantForAccessibility(
                    editText.hasFocus() && !preventAddingEditTextOnAccessibility ? getEditTextImportantForAccessibilityOnFocus() :
                            Strings.isNullOrEmpty(text) ? IMPORTANT_FOR_ACCESSIBILITY_NO : editTextImportantForAccessibility);
        }
    }

    */
/**
     * @return value for {@link android.widget.EditText} accessibility when it has focus
     *//*

    @AccessibilityValues()
    protected int getEditTextImportantForAccessibilityOnFocus() {
        return IMPORTANT_FOR_ACCESSIBILITY_YES;
    }

    */
/**
     * Appends the specified element to the end of the Validators list.
     *
     * @param newValidator element to be appended to the list
     *//*

    public void addValidator(Validator newValidator) {
        validators.add(newValidator);
    }

    */
/**
     * Method that receives an array of characters to filter. For every character it creates an
     * InputFilter and set it to the EditText using {@link #addFilter(InputFilter) addFilter}
     *
     * @param filters array of characters to filter
     *//*

    public void addFilter(final Character... filters) {
        for (final Character a : filters) {
            InputFilter filter = new InputFilter() {

                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
                                           int dend) {

                    if (source instanceof SpannableStringBuilder) {
                        SpannableStringBuilder sourceAsSpannableBuilder = (SpannableStringBuilder) source;

                        for (int i = end - 1; i >= start; i--) {
                            if (source.charAt(i) == a) {
                                sourceAsSpannableBuilder.delete(i, i + 1);
                            }
                        }
                        return source;
                    } else {
                        StringBuilder filteredStringBuilder = new StringBuilder();
                        for (int i = start; i < end; i++) {
                            char currentChar = source.charAt(i);
                            if (source.charAt(i) != a) {
                                filteredStringBuilder.append(currentChar);
                            }
                        }
                        return filteredStringBuilder.toString();
                    }
                }
            };
            addFilter(filter);
        }
    }

    */
/**
     * Appends the specified InputFilter to the Filters lists and set the InputFilter array to the
     * EditText
     *
     * @param filter InputFilter to add to
     *//*

    public void addFilter(InputFilter filter) {
        int size = editText.getFilters().length + 1;
        InputFilter[] inputFilters = new InputFilter[size];
        int n = 0;
        if (editText.getFilters().length > 0) {
            for (InputFilter inputFilter : editText.getFilters()) {
                inputFilters[n++] = inputFilter;
            }
        }
        inputFilters[n] = filter;
        editText.setFilters(inputFilters);
    }

    */
/**
     * Clears the array of filters for the {@link #editText}
     *//*

    public void clearFilters() {
        editText.setFilters(EMPTY_ARRAY_FILTERS);
    }

    */
/**
     * Clears the list of validators
     *//*

    public void clearValidators() {
        validators.clear();
    }

    */
/**
     * Method that shows and set the properties for the validation label
     *//*

    public void showNotValid(boolean animationEnabled) {

        boolean validatorActive = activatedValidator != null && activatedValidator instanceof AbstractValidator
                && !Strings.isNullOrEmpty(((AbstractValidator) activatedValidator).getErrorMessage());

        String errorMessage = validatorActive ? ((AbstractValidator) activatedValidator).getErrorMessage() : this.errorMessage;

        textView.setText(errorMessage);
        textView.setTextColor(validationColor);
        textView.setVisibility(View.VISIBLE);

        if (animationEnabled && !isNotValidShown) {
            textView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.txt_go_up));
        }
        editTextLayout.setBackgroundResource(errorBackgroundId);
        boolean wasNotValidShown = isNotValidShown;
        isNotValidShown = true;
        isLabelShown = false;
        valid = false;

        String textToAnnounce = setContentDescriptionWithEditTextMessage();

        if (!wasNotValidShown && enableAnnounceForAccessibilityOnError) {
            this.announceForAccessibility(textToAnnounce);
        }

        if (validationFieldListener != null) {
            validationFieldListener.onValidationErrorFired();
        }
    }

    */
/**
     * Clears the {@link #editText} without announcing the error even if {@link #enableAnnounceForAccessibilityOnError} is true
     *//*

    public void clearWithoutAnnouncingAccessibilityError() {
        boolean previousValue = enableAnnounceForAccessibilityOnError;
        setEnableAnnounceForAccessibilityOnError(false);
        clear();
        setEnableAnnounceForAccessibilityOnError(previousValue);
    }

    */
/**
     * Clears the {@link #editText}
     * It will announce the error for accessibility mode if {@link #enableAnnounceForAccessibilityOnError} is true
     *//*

    public void clear() {
        editText.setText(EMPTY_STRING);
    }

    */
/**
     * Method that shows and set the properties for the floating label
     *//*

    private void showLabel() {
        if (!isLabelShown) {
            textView.setText(label);
            textView.setTextColor(hint_color);
            textView.setVisibility(View.VISIBLE);
            textView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.txt_go_up));
            updateBackground();
            isLabelShown = true;
            isNotValidShown = false;
        }
    }

    */
/**
     * Display label with out animation
     *//*

    public void showLabelWithAnimationOff() {
        if (!isLabelShown) {
            textView.setText(label);
            textView.setTextColor(hint_color);
            textView.setVisibility(View.VISIBLE);
            textView.clearAnimation();
            updateBackground();
            isLabelShown = true;
            isNotValidShown = false;
        }
    }

    */
/**
     * Display label when clear button is pressed
     *//*

    public void showLabelOnClearButton() {
        closeButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClearButtonEnable) {
                    clear();
                    showClearButton(false);
                    showLabelWithAnimationOff();
                }
            }
        });
    }

    */
/**
     * Method that hides the floating label
     *//*

    private void hideLabel() {
        if (textView.getVisibility() != View.INVISIBLE) {
            textView.setText(label);
            textView.setVisibility(View.INVISIBLE);
            textView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.txt_go_down));
            updateBackground();
            isLabelShown = false;
            isNotValidShown = false;
            setContentDescriptionWithEditTextMessage();
        }
    }

    */
/**
     * Method that set the appearance of the floating label pattern, configuring and setting in the
     * correct place the textView and editText
     *//*

    private void createDefaultLayout() {
        editText.setGravity(Gravity.LEFT | Gravity.TOP);
        textView.setGravity(Gravity.LEFT);
        textView.setPadding(5, 0, 5, 0);
    }

    */
/**
     * Method that hides and show the clear button according if there is text on the editText
     *//*

    protected void showClearButton(boolean show) {
        show = isClearButtonEnable && show;
        closeButtonView.setVisibility(show ? VISIBLE : INVISIBLE);

        //Update padding to avoid X overlap large text
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) editText.getLayoutParams();
        if (show) {
            layoutParams.setMargins(0, 0, getResources().getDimensionPixelSize(R.dimen.img_close_button_size), 0);
            editText.setLayoutParams(layoutParams);
        } else {
            layoutParams.setMargins(0, 0, 0, 0);
            editText.setLayoutParams(layoutParams);
        }
    }

    */
/**
     * Method that validates if the user input is correct or not, depending in the validators
     * defined in {@link #addValidator(Validator) addValidator}
     *
     * @return <tt>true</tt> if all validate methods from all validators return true <tt>false</tt>
     * otherwise
     *//*

    public boolean validate() {
        this.activatedValidator = null;
        String input = editText.getText().toString();
        if (input.isEmpty()) {
            this.valid = isEmptyAllowed;
        } else {
            this.valid = validators == null || validators.isEmpty();
            if (!this.valid) {
                for (Validator validator : validators) {
                    this.valid = validator.validate(input);
                    if (!this.valid) {
                        this.activatedValidator = validator;
                        return false;
                    }
                }
            }
        }
        return this.valid;
    }

    public int getMinLength() {
        return minLength;
    }

    public boolean isPreventAddingLabelOnAccessibility() {
        return preventAddingLabelOnAccessibility;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLenght() {
        return maxLenght;
    }

    public void setMaxLenght(int maxLenght) {
        this.maxLenght = maxLenght;
    }

    public boolean isFloatingLabelEnable() {
        return isFloatingLabelEnable;
    }

    public void setFloatingLabelEnable(boolean isFloatingLabelEnable) {
        this.isFloatingLabelEnable = isFloatingLabelEnable;
    }

    public boolean isClearButtonEnable() {
        return isClearButtonEnable;
    }

    public void setClearButtonEnable(boolean isClearButtonEnable) {
        this.isClearButtonEnable = isClearButtonEnable;
        showClearButton(!editText.getText().toString().isEmpty());
    }

    public DataType getDataType() {
        return dataType;
    }

    public List<Validator> getValidators() {
        return validators;
    }

    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        editText.setHint(label);
        refreshFloatingLabel();
        setContentDescriptionWithEditTextMessage();
    }

    public void refreshFloatingLabel() {
        if (isLabelShown) {
            textView.setText(label);
            setContentDescriptionWithEditTextMessage();
        } else if (isNotValidShown) {
            textView.setText(errorMessage);
            showNotValid(false);
        }
    }

    public boolean isEmptyAllowed() {
        return isEmptyAllowed;
    }

    public void setEmptyAllowed(boolean isEmptyAllowed) {
        this.isEmptyAllowed = isEmptyAllowed;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    */
/**
     * {@link #getErrorMessage()} is the default message, but using multiple error messages this method
     * provides the last fired error message. Normally this is useful for compose accessibility in screens.
     *
     * @return the last fired error message or default message.
     *//*

    public String getLastFiredErrorMessage() {
        return (this.activatedValidator != null && this.activatedValidator instanceof AbstractValidator)
                ? ((AbstractValidator) activatedValidator).getErrorMessage() : errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getValidationColor() {
        return validationColor;
    }

    public void setValidationColor(int validationColor) {
        this.validationColor = validationColor;
    }

    public int getLabelFocusColor() {
        return labelFocusColor;
    }

    public void setLabelFocusColor(int labelFocusColor) {
        this.labelFocusColor = labelFocusColor;
    }

    public int getErrorBackgroundId() {
        return errorBackgroundId;
    }

    public void setErrorBackgroundId(int errorBackgroundId) {
        this.errorBackgroundId = errorBackgroundId;
    }

    public String getText() {
        if (getEditText() != null && getEditText().getText() != null) {
            return getEditText().getText().toString();
        }
        return EMPTY_STRING;
    }

    public boolean isValid() {
        return this.valid;
    }

    */
/**
     * Sets the content description prefix to be announced by the Accessibility Service after this element's name.
     *
     * @param contentDescriptionPrefix prefix to be announced
     *//*

    public void setContentDescriptionPrefix(String contentDescriptionPrefix) {
        this.contentDescriptionPrefix = contentDescriptionPrefix;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        helperTextView.setEnabled(enabled);
        setTextAppearance(helperTextView, enabled ? R.style.Avenir_Roman_C2_D : R.style.Avenir_Roman_C2_I);
        if (editText != null) {
            editText.setEnabled(enabled);
            setEditTextAppearance();
            showClearButton(enabled);
            if (enabled) {
                editText.setPadding(defaultLeftPadding, 0, 0, 0);
                updateBackground();
            } else {
                editTextLayout.setBackgroundColor(Color.TRANSPARENT);
                editText.setPadding(0, 0, 0, 0);
            }
        }
        if (!isNotValidShown) {
            setContentDescriptionWithEditTextMessage();
        }
    }

    protected void updateBackground() {
        if (this.editText.hasFocus()) {
            editTextLayout.setBackgroundResource(activeBackgroundId);
        } else {
            editTextLayout.setBackgroundResource(defaultBackgroundId);
        }
    }

    private void setEditTextAppearance() {
        setTextAppearance(editText,
                enabled ? Strings.isNullOrEmpty(editText.getText().toString()) ? R.style.Avenir_Roman_B2_L : R.style.Avenir_Roman_B2_D
                        : R.style.Avenir_Roman_B2_I
        );

        editText.setHintTextColor(getResources().getColor(enabled ? R.color.text_light_gray : R.color.text_disabled));
    }

    @Override
    public boolean isEnabled() {
        return getEditText() != null && getEditText().isEnabled();
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    protected EditText initializeEditText(Context context) {
        return new DisneyEditText(context);
    }

    public void setText(String text) {
        editText.setText(text);
        editText.setVisibility(VISIBLE);
        //Added for accessibility
        setContentDescriptionWithEditTextMessage();
    }

    */
/**
     * Change helper text update content description
     * If {@link #helperTextView} was not visible it will become visible
     *
     * @param helperText to be set if not empty
     *//*

    public void setHelperText(String helperText) {
        if (!Strings.isNullOrEmpty(helperText)) {
            this.helperText = helperText;
            helperTextView.setText(helperText);
            helperTextView.setEnabled(enabled);
            helperTextView.setVisibility(VISIBLE);
            setTextAppearance(helperTextView, enabled ? R.style.Avenir_Roman_C2_D : R.style.Avenir_Roman_C2_I);
            setContentDescriptionWithEditTextMessage();
        }
    }

    */
/**
     * Sets the content description for the parent {@link LinearLayout} with the contents
     * of it's {@link AbstractFloatLabelTextField#label} and inner {@link EditText}.
     * If the {@link AbstractFloatLabelTextField#contentDescriptionPrefix} is not null, it will be added after the input name.
     * <p>
     * If {@link #preventAddingEditTextOnAccessibility} the content of this will be not appended to the parent {@link LinearLayout}
     * This case is to avoid reading content like credit card numbers, or CC security codes.
     *//*

    public String setContentDescriptionWithEditTextMessage() {
        boolean replaceAsteriskEnabled = label.contains(ASTERISK) && this.replaceAsteriskEnabled;

        ContentDescriptionBuilder mainBuilder = new ContentDescriptionBuilder(getContext());
        ContentDescriptionBuilder editTextBuilder = new ContentDescriptionBuilder(getContext());

        appendAccessibilityTextAtTheBeginning(mainBuilder);

        if (contentDescriptionPrefix != null) {
            mainBuilder.appendWithSeparator(contentDescriptionPrefix);
        }

        if (addRequiredToAccessibility) {
            mainBuilder.appendWithSeparator((!preventAddingLabelOnAccessibility) ? label : EMPTY_STRING);
            appendAccessibilityTextBeforeRequired(mainBuilder);
            mainBuilder.append(R.string.accessibility_required_for_asterisk);
        } else if (replaceAsteriskEnabled) {
            mainBuilder.appendWithSeparator((!preventAddingLabelOnAccessibility) ? label.replaceAll(REGEX_FOR_END_ASTERISK, EMPTY_STRING)
                    : EMPTY_STRING);
            appendAccessibilityTextBeforeRequired(mainBuilder);
            mainBuilder.append(R.string.accessibility_required_for_asterisk);
        } else {
            mainBuilder.appendWithSeparator((!preventAddingLabelOnAccessibility) ? label : EMPTY_STRING);
            appendAccessibilityTextBeforeRequired(mainBuilder);
        }

        if (!Strings.isNullOrEmpty(helperText)) {
            mainBuilder.appendWithSeparator(helperText);
        }

        appendAccessibilityTextBeforeValue(mainBuilder);

        if (enableAnnounceForAccessibilityOnError && isNotValidShown) {

            boolean validatorActive = activatedValidator != null && activatedValidator instanceof AbstractValidator
                    && !Strings.isNullOrEmpty(((AbstractValidator) activatedValidator).getErrorMessage());

            String errorMessage = validatorActive ? ((AbstractValidator) activatedValidator).getErrorMessage() : this.errorMessage;

            boolean validatorHasAccessibilityMessage = validatorActive &&
                    !Strings.isNullOrEmpty(((AbstractValidator) activatedValidator).getAccessibilityErrorMessage());

            if (validatorHasAccessibilityMessage) {
                mainBuilder.appendWithSeparator(((AbstractValidator) activatedValidator).getAccessibilityErrorMessage());
            } else if (!TextUtils.isEmpty(announceForAccessibilityErrorPrefix)) {
                mainBuilder.appendWithSeparator(announceForAccessibilityErrorPrefix.toString());
            } else {
                mainBuilder.appendWithSeparator(errorMessage);
            }

        }

        if (!preventAddingEditTextOnAccessibility && getDataType() != DataType.PASSWORD) {
            String contentDescriptionForEditText = getContentDescriptionForEditText();
            mainBuilder.appendWithSeparator(contentDescriptionForEditText);
            */
/**
             *This is because they ask to Read Individual Letter on
             * <a href="https://github.disney.com/pages/snowball/ref-app/style/textfields/">document</a>
             * when the user focus on the {@link #editText} only
             *//*

            editTextBuilder.splitAndAppend(contentDescriptionForEditText);
        } else if (preventAddingEditTextOnAccessibility
                && !Strings.isNullOrEmpty(textForAccessibility)
                && !Strings.isNullOrEmpty(getContentDescriptionForEditText())) {
            mainBuilder.append(textForAccessibility);
        }

        editText.setContentDescription(editTextBuilder.toString());

        if (!enabled) {
            mainBuilder.appendWithSeparator(R.string.accessibility_disabled_suffix);
        } else if (!innerElementsAccessibilityEnabled) {
            mainBuilder.appendWithSeparator(R.string.accessibility_double_tap_to_activate);
        }

        appendAccessibilityTextAtTheEnd(mainBuilder);

        String contentDescription = mainBuilder.toString();
        setContentDescription(contentDescription);

        return contentDescription;

    }


    protected String getContentDescriptionForEditText() {
        return editText.getText().toString();
    }

    protected void appendAccessibilityTextAtTheBeginning(ContentDescriptionBuilder builder) {
        //no-op
    }

    protected void appendAccessibilityTextBeforeRequired(ContentDescriptionBuilder builder) {
        //no-op
    }

    protected void appendAccessibilityTextBeforeValue(ContentDescriptionBuilder builder) {
        //no-op
    }

    protected void appendAccessibilityTextAtTheEnd(ContentDescriptionBuilder builder) {
        //no-op
    }

    public void setIsNotValidShownOnFocus(boolean isNotValidShownOnFocus) {
        this.isNotValidShownOnFocus = isNotValidShownOnFocus;
    }

    */
/**
     * Appends the specified element to the end of the onFocusChangeListeners list.
     *
     * @param onFocusChangeListener element to be appended to the list
     *//*

    public void addOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        onFocusChangeListeners.add(onFocusChangeListener);
    }

    */
/**
     * Removes all elements from OnFocusChangeListener list
     *//*

    public void clearOnFocusChangeListeners() {
        onFocusChangeListeners.clear();
    }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        showClearButton(clickable);
        editText.setClickable(clickable);
    }

    */
/**
     * Shows an error message only one time. That error will be replaced with the old error message as soon as the validation status is displayed
     * again.
     *
     * @param temporalErrorMsg The error message that will be shown only one time
     *//*

    public void showOneTimeError(String temporalErrorMsg) {
        */
/**
         * We have to call displayValidationStatus() before calling showNotValid(true) so the isNotValidShown field is reset to false. If we don't
         * do that the error background of the label won't be shown again after the first time we call showNotValid(true).
         *//*

        this.displayValidationStatus();
        String oldError = this.getErrorMessage();
        this.setErrorMessage(temporalErrorMsg);
        this.showNotValid(true);
        this.setErrorMessage(oldError);
    }

    */
/**
     * Sets the text that will be called when the error label is displayed if <br>
     * enableAnnounceForAccessibilityOnError is set to true
     *
     * @param resource string resource that represents the text
     *//*

    public void setLabelAnnounceForAccessibilityOnErrorPrefix(int resource) {
        setLabelAnnounceForAccessibilityOnErrorPrefix(context.getString(resource));
    }

    */
/**
     * Sets the text that will be used when the error label is displayed if <br>
     * enableAnnounceForAccessibilityOnError is set to true
     *
     * @param charSequence CharSequence that represents the text
     *//*

    public void setLabelAnnounceForAccessibilityOnErrorPrefix(CharSequence charSequence) {
        announceForAccessibilityErrorPrefix = charSequence;
    }

    */
/**
     * If set to true announceForAccessibility method will be called with the <br>
     * <code>labelAnnounceForAccessibilityOnErrorText</code> if it is not null or empty <br>
     * or the inline text by default whenever <code>showNotValid()</code> method is called.
     *
     * @param enabled
     *//*

    public void setEnableAnnounceForAccessibilityOnError(boolean enabled) {
        enableAnnounceForAccessibilityOnError = enabled;
    }

    */
/**
     * Adds the ability to replace the asterisk at the end of the inner {@link TextView} label
     * for a "required" so the asterisk can be announced by accessibility correctly.
     *
     * @param enabled if true replaces an ending asterisk with a required in the <code>contentDescription</code>
     *                if false just places the <code>contentDescription</code> of the view to what the label text says
     *//*

    public void setReplaceAsteriskEnabled(boolean enabled) {
        replaceAsteriskEnabled = enabled;
    }

    */
/**
     * Sets the label announceForAccessibility mode for this views inner {@link TextView}
     *
     * @param mode How to determine whether this view is important for accessibility.
     *
     * @see #IMPORTANT_FOR_ACCESSIBILITY_YES
     * @see #IMPORTANT_FOR_ACCESSIBILITY_NO
     * @see #IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
     * @see #IMPORTANT_FOR_ACCESSIBILITY_AUTO
     *//*

    public void setLabelImportantForAccessibility(int mode) {
        textView.setImportantForAccessibility(mode);
    }

    */
/**
     * Sets the label announceForAccessibility mode for this views inner {@link EditText}
     *
     * @param mode How to determine whether this view is important for accessibility.
     *
     * @see #IMPORTANT_FOR_ACCESSIBILITY_YES
     * @see #IMPORTANT_FOR_ACCESSIBILITY_NO
     * @see #IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
     * @see #IMPORTANT_FOR_ACCESSIBILITY_AUTO
     *//*

    public void setEditTextImportantForAccessibility(int mode) {
        editTextImportantForAccessibility = mode;
        editText.setImportantForAccessibility(mode);
    }

    */
/**
     * It sets the style of the FloatLabelTextField EditText
     *
     * @param style
     *//*

    public void setEditTextStyle(int style) {
        setTextAppearance(editText, style);
    }

    */
/**
     * It sets the style of the FloatLabelTextField TextView
     *
     * @param style
     *//*

    public void setLabelStyle(int style) {
        setTextAppearance(textView, style);
    }

    */
/**
     * It sets if adding label text in {@link #setContentDescriptionWithEditTextMessage()}
     *
     * @param preventAddingLabelOnAccessibility
     *//*

    public void setPreventAddingLabelOnAccessibility(boolean preventAddingLabelOnAccessibility) {
        this.preventAddingLabelOnAccessibility = preventAddingLabelOnAccessibility;
    }

    */
/**
     * It sets a validation error listener.
     *
     * @param validationFieldListener
     *//*

    public void setValidationFieldListener(ValidationFieldListener validationFieldListener) {
        this.validationFieldListener = validationFieldListener;
    }

    */
/**
     * It sets id in the editText for automation purpose
     *
     * @param id
     *//*

    public void setEditTextIdForAutomation(int id) {
        this.editText.setId(id);
    }
}
*/
