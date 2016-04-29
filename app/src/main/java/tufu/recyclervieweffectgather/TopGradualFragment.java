package tufu.recyclervieweffectgather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import tufu.recyclervieweffectgather.dummy.DummyContent;
import tufu.recyclervieweffectgather.dummy.DummyContent.DummyItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TopGradualFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private View mView;
    private RecyclerView recyclerView;

    private Paint mPaint;
    private int layerId;

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
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TopGradualFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TopGradualFragment newInstance(int columnCount) {
        TopGradualFragment fragment = new TopGradualFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_item_list, container, false);

        recyclerView = getView(R.id.list);
        // Set the adapter
        Context context = mView.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(new RecyclerViewAdapter(DummyContent.ITEMS, mListener));

        doTopGradualEffect();

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

        return mView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

        int rgb = Color.rgb(mColorRed, mColorGreen, mColorBlue);

        mColorImageView.setBackgroundColor(rgb);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }


    public void doTopGradualEffect(){
        if(recyclerView == null){
            return ;
        }

        // 渐变效果.
        mPaint = new Paint();
        final Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        mPaint.setXfermode(xfermode);

        final LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, 100.0f, new int[]{0, Color.BLACK}, null, Shader.TileMode.CLAMP);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(canvas, parent, state);

                mPaint.setXfermode(xfermode);
                mPaint.setShader(linearGradient);
                canvas.drawRect(0.0f, 0.0f, parent.getRight(), 100.0f, mPaint);
                mPaint.setXfermode(null);
                canvas.restoreToCount(layerId);
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                layerId = c.saveLayer(0.0f, 0.0f, (float) parent.getWidth(), (float) parent.getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
            }
        });
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
