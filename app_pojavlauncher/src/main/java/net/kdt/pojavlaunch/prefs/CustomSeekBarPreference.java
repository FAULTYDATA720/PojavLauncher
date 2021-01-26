package net.kdt.pojavlaunch.prefs;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SeekBarPreference;

import net.kdt.pojavlaunch.R;

public class CustomSeekBarPreference extends SeekBarPreference {

    private String suffix = "";
    private int mMin;
    private TextView textView;

    public CustomSeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.SeekBarPreference, defStyleAttr, defStyleRes);
        mMin = a.getInt(R.styleable.SeekBarPreference_min, 0);
        a.recycle();
    }

    public CustomSeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomSeekBarPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.seekBarPreferenceStyle);

    }

    public CustomSeekBarPreference(Context context) {
        this(context, null);
    }

    @Override
    public void setMin(int min) {
        //Note: since the max (setMax is a final function) is not taken into account properly, setting the min over the max may produce funky results
        super.setMin(min);
        if (min != mMin) {
            mMin = min;
        }
    }


    @Override
    public void onBindViewHolder(PreferenceViewHolder view) {
        super.onBindViewHolder(view);
        textView = (TextView) view.findViewById(R.id.seekbar_value);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekbar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(progress + mMin));
                updateTextViewWithSuffix();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setValue(seekBar.getProgress() + mMin);
                updateTextViewWithSuffix();

            }
        });

        updateTextViewWithSuffix();
    }



    private void updateTextViewWithSuffix(){
        if(!textView.getText().toString().endsWith(suffix)){
            textView.setText(String.format("%s%s", textView.getText(), suffix));
        }

    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
