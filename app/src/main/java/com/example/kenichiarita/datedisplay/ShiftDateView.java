package com.example.kenichiarita.datedisplay;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.text.TextPaint;
import android.graphics.Paint;

public final class ShiftDateView extends LinearLayout{

    private final SimpleDateFormat mDateFormat;
    private final SimpleDateFormat mTimeFormat;

    private final TextView mStartDate;
    private final TextView mEndDate;

    private final TextView mStartTime;
    private final TextView mEndTime;
    private final TextView mHyphen;

    private final RelativeLayout mAffterTime;
    private final TextView mAfterStartTime;
    private final TextView mAfterStartAllow;
    private final TextView mAfterEndTime;
    private final TextView mAfterEndAllow;


    public ShiftDateView(Context context) {
        this(context, null);
    }

    public ShiftDateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShiftDateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.custom_date_view, this);

        mDateFormat = new SimpleDateFormat(getContext().getString(R.string.shift_date_format));
        mTimeFormat = new SimpleDateFormat(getContext().getString(R.string.shift_time_format));

        mStartDate = (TextView) findViewById(R.id.start_date_text);
        mEndDate = (TextView) findViewById(R.id.end_date_text);
        mStartTime = (TextView) findViewById(R.id.start_time_text);
        mEndTime = (TextView) findViewById(R.id.end_time_text);
        mHyphen = (TextView) findViewById(R.id.center_hyphen);
        mAffterTime = (RelativeLayout) findViewById(R.id.after_time_layout);
        mAfterStartTime = (TextView) findViewById(R.id.after_start_time_text);
        mAfterStartAllow = (TextView) findViewById(R.id.after_start_allow);
        mAfterEndTime = (TextView) findViewById(R.id.after_end_time_text);
        mAfterEndAllow = (TextView) findViewById(R.id.after_end_allow);

        setVisibility(View.GONE);
    }

    /**
     * シフトの日付を表示します。
     *
     * @param startTime
     * @param endTime
     * @param afterStartTime
     * @param afterEndTime
     */
    public void show(final Date startTime, final Date endTime, final Date afterStartTime, final Date afterEndTime) {
        showDate(startTime, endTime);
        showTime(startTime, endTime, afterStartTime, afterEndTime);
        showAfterTime(afterStartTime, afterEndTime);
        setVisibility(View.VISIBLE);
    }

    /**
     * 開始日と終了日を表示します。
     *
     * @param startTime
     * @param endTime
     */
    private void showDate(final Date startTime, final Date endTime) {
        final String startDate = mDateFormat.format(startTime);
        final String endDate = mDateFormat.format(endTime);

        // 開始日付を表示します
        mStartDate.setText(startDate);

        final long dateTimeTo = startTime.getTime();
        final long dateTimeFrom = endTime.getTime();
        // 差分の日数を算出します
        final long dayDiff = (dateTimeFrom - dateTimeTo) / (1000 * 60 * 60 * 24);

        // 終了日付を表示します。
        if (dayDiff >= 1) {
            mEndDate.setText(endDate);
            mEndDate.setVisibility(View.VISIBLE);
        } else {
            mEndDate.setVisibility(View.GONE);
        }
    }

    /**
     * 開始時間と終了時間を表示します。
     *
     * @param startTime
     * @param endTime
     * @param afterStartTime
     * @param afterEndTime
     */
    private void showTime(final Date startTime, final Date endTime, final Date afterStartTime, final Date afterEndTime) {
        final String shiftStartTime = mTimeFormat.format(startTime);
        final String shiftEndTime = mTimeFormat.format(endTime);

        // 開始時間
        if (afterStartTime != null && startTime.compareTo(afterStartTime) != 0) {
            // 変更開始時間との差分がある場合は、文字色をグレーに変更し取り消し線を表示します
            mStartTime.setTextColor(Color.GRAY);

            final TextPaint paint =  mStartTime.getPaint();
            paint.setFlags(mStartTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            paint.setAntiAlias(true);
        }
        mStartTime.setText(shiftStartTime);

        // 終了時間
        if (afterEndTime != null && startTime.compareTo(afterEndTime) != 0) {
            // 変更終了時間との差分がある場合は、文字色をグレーに変更し取り消し線を表示します
            mEndTime.setTextColor(Color.GRAY);
            mHyphen.setTextColor(Color.GRAY);

            final TextPaint paint =  mEndTime.getPaint();
            paint.setFlags(mEndTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            paint.setAntiAlias(true);
        }
        mEndTime.setText(shiftEndTime);

    }

    /**
     * 変更開始時間と変更終了時間を表示します。
     *
     * @param afterStartTime
     * @param afterEndTime
     */
    private void showAfterTime(final Date afterStartTime, final Date afterEndTime) {
        boolean showFlag = false;
        if (afterStartTime != null) {
            // 変更開始時間と矢印を表示します
            final String shiftAfterStartTime = mTimeFormat.format(afterStartTime);
            mAfterStartTime.setText(shiftAfterStartTime);
            mAfterStartAllow.setVisibility(View.VISIBLE);
            showFlag = true;
        }
        if (afterEndTime != null) {
            // 変更終了時間と矢印を表示します
            final String shiftAfterEndTime = mTimeFormat.format(afterEndTime);
            mAfterEndTime.setText(shiftAfterEndTime);
            mAfterEndAllow.setVisibility(View.VISIBLE);
            showFlag = true;
        }
        // 変更時間の表示がありの場合に変更レイアウトを表示します
        mAffterTime.setVisibility(showFlag ? View.VISIBLE : View.GONE);
    }

}
