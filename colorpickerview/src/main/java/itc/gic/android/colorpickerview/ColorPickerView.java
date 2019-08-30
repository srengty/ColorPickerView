package itc.gic.android.colorpickerview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.preference.ColorPickerPreferenceManager;

public class ColorPickerView extends FrameLayout {
    private OnChooseColorListener listener;
    private Button button;
    private int selectedColor = Color.BLACK;
    private ColorPickerDialog.Builder colorPicker;
    private androidx.appcompat.app.AlertDialog alertDialog;

    public ColorPickerView(@NonNull Context context) {
        super(context);
        init(context, null,0);
    }

    public ColorPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs,0);
    }

    public ColorPickerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }
    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        initAttributes(context, attrs);
        button = new AppCompatButton(context);
        button.setPadding(0,0,0,0);
        button.setMinHeight(dp2px(30));
        button.setMinWidth(dp2px(30));
        button.setMinimumWidth(dp2px(30));
        button.setMinimumHeight(dp2px(30));
        button.setBackgroundColor(selectedColor);
        FrameLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        button.setLayoutParams(layoutParams);
        addView(button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                performCustomClick(0,0);
            }
        });
        colorPicker = new ColorPickerDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                .setTitle("ColorPicker Dialog")
                .setPreferenceName("MyColorPickerDialog")
                .setPositiveButton(context.getString(android.R.string.ok),
                        new ColorEnvelopeListener() {
                            @Override
                            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                setButtonColor(envelope);
                            }
                        })
                .setNegativeButton(context.getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                .attachAlphaSlideBar(true) // default is true. If false, do not show the AlphaSlideBar.
                .attachBrightnessSlideBar(true);  // default is true. If false, do not show the BrightnessSlideBar.
        alertDialog = colorPicker.create();
        ColorPickerPreferenceManager manager = ColorPickerPreferenceManager.getInstance(context);
        manager.setColor("MyColorPickerDialog", selectedColor);
    }

    private void initAttributes(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorPickerView);
        int count = typedArray.getIndexCount();
        int attrIndex;
        for(int i=0;i<count;i++){
            attrIndex = typedArray.getIndex(i);
            if(attrIndex == R.styleable.ColorPickerView_color){
                selectedColor = typedArray.getColor(attrIndex, Color.BLACK);
            }
        }
    }

    private void setButtonColor(ColorEnvelope envelope) {
        selectedColor = envelope.getColor();
        button.setBackgroundColor(selectedColor);
        if(listener != null) listener.onChooseColor(selectedColor);

    }

    private void performCustomClick(float x, float y) {
        if(alertDialog != null) {
            alertDialog.show();
        }
    }

    private int dp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    public void setOnChooseColorListener(OnChooseColorListener listener) {
        this.listener = listener;
    }

    public interface OnChooseColorListener{
        void onChooseColor(int color);
    }
}
