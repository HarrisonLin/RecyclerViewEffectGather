package tufu.recyclervieweffectgather;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * @author tufu
 * @Desctription
 * @date 16/4/29
 */
public class ColorPanel extends LinearLayout implements SeekBar.OnSeekBarChangeListener {


    private LinearLayout mSlideColorBody;

    private SeekBar mRedBar;
    private SeekBar mGreenBar;
    private SeekBar mBlueBar;

    private TextView mRedValue;
    private TextView mGreenValue;
    private TextView mBlueValue;

    private int mColorRed = 0;
    private int mColorBlue = 0;
    private int mColorGreen = 0;

    private ImageView mColorImageView;

    private Button mColorConfirmBtn;

    private int mRgb = Color.BLACK;

    private View mView;

    public ColorPanel(Context context) {
        super(context);
    }

    public ColorPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context){

        LayoutInflater inflater = LayoutInflater.from(context);

        mView = inflater.inflate(R.layout.fragment_item_list, null, false);
        // 颜色快
        mSlideColorBody = getView(R.id.ll_slide_body);

        mRedBar = getView(R.id.red_seekBar);
        mGreenBar = getView(R.id.green_seekBar);
        mBlueBar = getView(R.id.blue_seekBar);

        mRedValue = getView(R.id.r_tv_red_value);
        mGreenValue = getView(R.id.r_tv_green_value);
        mBlueValue = getView(R.id.r_tv_blue_value);

        mRedBar.setOnSeekBarChangeListener(this);
        mGreenBar.setOnSeekBarChangeListener(this);
        mBlueBar.setOnSeekBarChangeListener(this);

        mColorImageView = getView(R.id.image_view);

        mColorConfirmBtn = getView(R.id.btn_confirm);

        mColorConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.red_seekBar:
                mColorRed = progress;
                mRedValue.setText("R("+String.valueOf(mColorRed)+")");
                break;
            case R.id.green_seekBar:
                mColorGreen = progress;
                mGreenValue.setText("G("+String.valueOf(mColorGreen)+")");
                break;
            case R.id.blue_seekBar:
                mColorBlue = progress;
                mBlueValue.setText("G("+String.valueOf(mColorBlue)+")");
                break;
        }

        mRgb = Color.rgb(mColorRed, mColorGreen, mColorBlue);

        mColorImageView.setBackgroundColor(mRgb);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    protected <T extends View> T getView(int id) {
        T result = (T) mView.findViewById(id);
        if (result == null) {
            throw new IllegalArgumentException("view 0x" + Integer.toHexString(id)
                    + " doesn't exist");
        }
        return result;
    }

}
