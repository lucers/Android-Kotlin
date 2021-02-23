package com.lucers.widget.wheelview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lucers.widget.R;

import java.util.List;

/**
 * WheelView
 *
 * @author ycuwq
 * @date 2017/12/12
 */
public class WheelView<T> extends View {

    private List<T> list;

    @ColorInt
    private int itemTextColor;

    private int itemTextSize;

    private TextPaint itemTextPaint;

    private boolean textGradual;

    @ColorInt
    private int selectedItemTextColor;

    private int selectedItemTextSize;

    private TextPaint selectedItemPaint;

    private Paint paint;

    private int textMaxWidth, textMaxHeight;

    private String itemMaxWidthText;

    private int itemCount;

    private int itemHeightSpace, itemWidthSpace;

    private int itemHeight;

    private int currentPosition;

    private boolean zoom;

    private boolean showCurtain;

    @ColorInt
    private int curtainColor;

    private boolean showCurtainBorder;

    @ColorInt
    private int curtainBorderColor;

    private Rect drawRect;

    private Rect selectedItemRect;

    private int firstItemDrawWidth, firstItemDrawHeight;

    private int centerItemDrawnHeight;

    private Scroller scroller;

    private int touchSlop;

    private boolean touchSlopFlag;

    private VelocityTracker tracker;

    private int touchDownHeight;

    private int scrollOffsetHeight;

    private int lastDownHeight;

    private boolean cyclic;

    private int maxFlingHeight, minFlingHeight;

    private int minVelocity = 50, maxVelocity = 12000;

    private boolean abortScroller;

    private LinearGradient linearGradient;

    private Handler handler = new Handler(Looper.getMainLooper());

    private OnWheelChangeListener<T> onWheelChangeListener;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (scroller.computeScrollOffset()) {
                scrollOffsetHeight = scroller.getCurrY();
                postInvalidate();
                handler.postDelayed(this, 15);
            }
            if (scroller.isFinished() || (scroller.getFinalY() == scroller.getCurrY() && scroller.getFinalX() == scroller.getCurrX())) {
                if (itemHeight == 0) {
                    return;
                }
                int position = -scrollOffsetHeight / itemHeight;
                position = fixItemPosition(position);
                if (currentPosition != position && currentPosition < list.size()) {
                    currentPosition = position;
                    if (onWheelChangeListener == null) {
                        return;
                    }
                    onWheelChangeListener.onWheelSelected(list.get(position), position);
                }
            }
        }
    };

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPaint();
        linearGradient = new LinearGradient(itemTextColor, selectedItemTextColor);
        drawRect = new Rect();
        selectedItemRect = new Rect();
        scroller = new Scroller(context);

        ViewConfiguration configuration = ViewConfiguration.get(context);
        touchSlop = configuration.getScaledTouchSlop();
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WheelView);
        itemTextSize = typedArray.getDimensionPixelSize(R.styleable.WheelView_itemTextSize, getResources().getDimensionPixelSize(R.dimen.default_text_size));
        itemTextColor = typedArray.getColor(R.styleable.WheelView_itemTextColor, Color.BLACK);
        textGradual = typedArray.getBoolean(R.styleable.WheelView_textGradual, true);
        cyclic = typedArray.getBoolean(R.styleable.WheelView_wheelCyclic, false);
        itemCount = typedArray.getInteger(R.styleable.WheelView_halfVisibleItemCount, 2);
        itemMaxWidthText = typedArray.getString(R.styleable.WheelView_itemMaximumWidthText);
        selectedItemTextColor = typedArray.getColor(R.styleable.WheelView_selectedTextColor, Color.parseColor("#33AAFF"));
        selectedItemTextSize = typedArray.getDimensionPixelSize(R.styleable.WheelView_selectedTextSize, getResources().getDimensionPixelSize(R.dimen.default_text_size));
        currentPosition = typedArray.getInteger(R.styleable.WheelView_currentItemPosition, 0);
        itemWidthSpace = typedArray.getDimensionPixelSize(R.styleable.WheelView_itemWidthSpace, 0);
        itemHeightSpace = typedArray.getDimensionPixelSize(R.styleable.WheelView_itemHeightSpace, getResources().getDimensionPixelOffset(R.dimen.item_height_space));
        zoom = typedArray.getBoolean(R.styleable.WheelView_zoomInSelectedItem, true);
        showCurtain = typedArray.getBoolean(R.styleable.WheelView_wheelCurtain, false);
        curtainColor = typedArray.getColor(R.styleable.WheelView_wheelCurtainColor, Color.parseColor("#303D3D3D"));
        showCurtainBorder = typedArray.getBoolean(R.styleable.WheelView_wheelCurtainBorder, false);
        curtainBorderColor = typedArray.getColor(R.styleable.WheelView_wheelCurtainBorderColor, Color.BLACK);
        typedArray.recycle();
    }

    public void computeTextSize() {
        textMaxWidth = textMaxHeight = 0;
        if (list == null || list.size() == 0) {
            return;
        }

        paint.setTextSize(Math.max(selectedItemTextSize, itemTextSize));

        if (!TextUtils.isEmpty(itemMaxWidthText)) {
            textMaxWidth = (int) paint.measureText(itemMaxWidthText);
        } else {
            textMaxWidth = (int) paint.measureText(list.get(0).toString());
        }
        Paint.FontMetrics metrics = paint.getFontMetrics();
        textMaxHeight = (int) (metrics.bottom - metrics.top);
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        itemTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        itemTextPaint.setStyle(Paint.Style.FILL);
        itemTextPaint.setTextAlign(Paint.Align.CENTER);
        itemTextPaint.setColor(itemTextColor);
        itemTextPaint.setTextSize(itemTextSize);
        selectedItemPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        selectedItemPaint.setStyle(Paint.Style.FILL);
        selectedItemPaint.setTextAlign(Paint.Align.CENTER);
        selectedItemPaint.setColor(selectedItemTextColor);
        selectedItemPaint.setTextSize(selectedItemTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = textMaxWidth + itemWidthSpace;
        int height = (textMaxHeight + itemHeightSpace) * getVisibleItemCount();

        width += getPaddingLeft() + getPaddingRight();
        height += getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(measureSize(specWidthMode, specWidthSize, width),
                measureSize(specHeightMode, specHeightSize, height));
    }

    private int measureSize(int specMode, int specSize, int size) {
        if (specMode == MeasureSpec.EXACTLY) {
            return specSize;
        } else {
            return Math.min(specSize, size);
        }
    }

    private void computeFlingLimitHeight() {
        if (list == null || list.size() == 0) {
            return;
        }
        minFlingHeight = cyclic ? Integer.MIN_VALUE : -itemHeight * (list.size() - 1);
        maxFlingHeight = cyclic ? Integer.MAX_VALUE : 0;
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        drawRect.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        itemHeight = drawRect.height() / getVisibleItemCount();
        firstItemDrawWidth = drawRect.centerX();
        firstItemDrawHeight = (int) ((itemHeight - (selectedItemPaint.ascent() + selectedItemPaint.descent())) / 2);

        selectedItemRect.set(getPaddingLeft(), itemHeight * itemCount,
                getWidth() - getPaddingRight(), itemHeight + itemHeight * itemCount);
        computeFlingLimitHeight();
        centerItemDrawnHeight = firstItemDrawHeight + itemHeight * itemCount;
        scrollOffsetHeight = -itemHeight * currentPosition;
    }

    private int fixItemPosition(int position) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        if (position < 0) {
            position = list.size() + (position % list.size());

        }
        if (position >= list.size()) {
            position = position % list.size();
        }
        return position;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (list == null || list.size() == 0) {
            return;
        }
        paint.setTextAlign(Paint.Align.CENTER);
        if (showCurtain) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(curtainColor);
            canvas.drawRect(selectedItemRect, paint);
        }
        if (showCurtainBorder) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(curtainBorderColor);
            canvas.drawRect(selectedItemRect, paint);
            canvas.drawRect(drawRect, paint);
        }
        int drawSelectedPosition = -scrollOffsetHeight / itemHeight;
        paint.setStyle(Paint.Style.FILL);
        for (int drawDataPosition = drawSelectedPosition - itemCount - 1; drawDataPosition <= drawSelectedPosition + itemCount + 1; drawDataPosition++) {
            int position = drawDataPosition;
            if (cyclic) {
                position = fixItemPosition(position);
            } else {
                if (position < 0 || position > list.size() - 1) {
                    continue;
                }
            }
            T data = list.get(position);

            int itemDrawHeight = firstItemDrawHeight + (drawDataPosition + itemCount) * itemHeight + scrollOffsetHeight;
            int distanceHeight = Math.abs(centerItemDrawnHeight - itemDrawHeight);

            if (textGradual) {
                if (distanceHeight < itemHeight) {
                    float colorRatio = 1 - (distanceHeight / (float) itemHeight);
                    selectedItemPaint.setColor(linearGradient.getColor(colorRatio));
                    itemTextPaint.setColor(linearGradient.getColor(colorRatio));
                } else {
                    selectedItemPaint.setColor(selectedItemTextColor);
                    itemTextPaint.setColor(itemTextColor);
                }
                float alphaRatio;
                if (itemDrawHeight > centerItemDrawnHeight) {
                    alphaRatio = (drawRect.height() - itemDrawHeight) /
                            (float) (drawRect.height() - (centerItemDrawnHeight));
                } else {
                    alphaRatio = itemDrawHeight / (float) centerItemDrawnHeight;
                }
                alphaRatio = alphaRatio < 0 ? 0 : alphaRatio;
                selectedItemPaint.setAlpha((int) (alphaRatio * 255));
                itemTextPaint.setAlpha((int) (alphaRatio * 255));
            }
            if (zoom) {
                if (distanceHeight < itemHeight) {
                    float addedSize = (itemHeight - distanceHeight) / (float) itemHeight * (selectedItemTextSize - itemTextSize);
                    selectedItemPaint.setTextSize(itemTextSize + addedSize);
                    itemTextPaint.setTextSize(itemTextSize + addedSize);
                } else {
                    selectedItemPaint.setTextSize(itemTextSize);
                    itemTextPaint.setTextSize(itemTextSize);
                }
            } else {
                selectedItemPaint.setTextSize(itemTextSize);
                itemTextPaint.setTextSize(itemTextSize);
            }
            String drawText = TextUtils.ellipsize(data.toString(), itemTextPaint, getWidth() * 0.8f, TextUtils.TruncateAt.END).toString();
            if (distanceHeight < itemHeight / 2) {
                canvas.drawText(drawText, firstItemDrawWidth, itemDrawHeight, selectedItemPaint);
            } else {
                canvas.drawText(drawText, firstItemDrawWidth, itemDrawHeight, itemTextPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (tracker == null) {
            tracker = VelocityTracker.obtain();
        }
        tracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                    abortScroller = true;
                } else {
                    abortScroller = false;
                }
                tracker.clear();
                touchDownHeight = lastDownHeight = (int) event.getY();
                touchSlopFlag = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (touchSlopFlag && Math.abs(touchDownHeight - event.getY()) < touchSlop) {
                    break;
                }
                touchSlopFlag = false;
                float move = event.getY() - lastDownHeight;
                scrollOffsetHeight += move;
                lastDownHeight = (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (!abortScroller && touchDownHeight == lastDownHeight) {
                    performClick();
                    if (event.getY() > selectedItemRect.bottom) {
                        int scrollItem = (int) (event.getY() - selectedItemRect.bottom) / itemHeight + 1;
                        scroller.startScroll(0, scrollOffsetHeight, 0,
                                -scrollItem * itemHeight);

                    } else if (event.getY() < selectedItemRect.top) {
                        int scrollItem = (int) (selectedItemRect.top - event.getY()) / itemHeight + 1;
                        scroller.startScroll(0, scrollOffsetHeight, 0,
                                scrollItem * itemHeight);
                    }
                } else {
                    tracker.computeCurrentVelocity(1000, maxVelocity);
                    int velocity = (int) tracker.getYVelocity();
                    if (Math.abs(velocity) > minVelocity) {
                        scroller.fling(0, scrollOffsetHeight, 0, velocity,
                                0, 0, minFlingHeight, maxFlingHeight);
                        scroller.setFinalY(scroller.getFinalY() +
                                computeDistanceToEndPoint(scroller.getFinalY() % itemHeight));
                    } else {
                        scroller.startScroll(0, scrollOffsetHeight, 0,
                                computeDistanceToEndPoint(scrollOffsetHeight % itemHeight));
                    }
                }
                if (!cyclic) {
                    if (scroller.getFinalY() > maxFlingHeight) {
                        scroller.setFinalY(maxFlingHeight);
                    } else if (scroller.getFinalY() < minFlingHeight) {
                        scroller.setFinalY(minFlingHeight);
                    }
                }
                handler.post(runnable);
                tracker.recycle();
                tracker = null;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private int computeDistanceToEndPoint(int remainder) {
        if (Math.abs(remainder) > itemHeight / 2) {
            if (scrollOffsetHeight < 0) {
                return -itemHeight - remainder;
            } else {
                return itemHeight - remainder;
            }
        } else {
            return -remainder;
        }
    }


    public void setOnWheelChangeListener(OnWheelChangeListener<T> onWheelChangeListener) {
        this.onWheelChangeListener = onWheelChangeListener;
    }

    public OnWheelChangeListener<T> getOnWheelChangeListener() {
        return onWheelChangeListener;
    }

    public void setDataList(@NonNull List<T> dataList) {
        list = dataList;
        if (dataList.size() == 0) {
            return;
        }
        computeTextSize();
        computeFlingLimitHeight();
        requestLayout();
        postInvalidate();
    }

    public int getItemTextColor() {
        return itemTextColor;
    }

    public void setItemTextColor(@ColorInt int itemTextColor) {
        if (this.itemTextColor == itemTextColor) {
            return;
        }
        itemTextPaint.setColor(itemTextColor);
        this.itemTextColor = itemTextColor;
        linearGradient.setStartColor(itemTextColor);
        postInvalidate();
    }

    public int getItemTextSize() {
        return itemTextSize;
    }

    public void setItemTextSize(int itemTextSize) {
        if (this.itemTextSize == itemTextSize) {
            return;
        }
        this.itemTextSize = itemTextSize;
        itemTextPaint.setTextSize(itemTextSize);
        computeTextSize();
        postInvalidate();
    }

    public void setSelectedItemTextColor(@ColorInt int selectedItemTextColor) {
        if (this.selectedItemTextColor == selectedItemTextColor) {
            return;
        }
        selectedItemPaint.setColor(selectedItemTextColor);
        this.selectedItemTextColor = selectedItemTextColor;
        linearGradient.setEndColor(selectedItemTextColor);
        postInvalidate();
    }

    public void setSelectedItemTextSize(int selectedItemTextSize) {
        if (this.selectedItemTextSize == selectedItemTextSize) {
            return;
        }
        selectedItemPaint.setTextSize(selectedItemTextSize);
        this.selectedItemTextSize = selectedItemTextSize;
        computeTextSize();
        postInvalidate();
    }

    public void setItemMaximumWidthText(String itemMaximumWidthText) {
        itemMaxWidthText = itemMaximumWidthText;
        requestLayout();
        postInvalidate();
    }

    public int getVisibleItemCount() {
        return itemCount * 2 + 1;
    }

    public void setHalfVisibleItemCount(int halfVisibleItemCount) {
        if (itemCount == halfVisibleItemCount) {
            return;
        }
        itemCount = halfVisibleItemCount;
        requestLayout();
    }

    public void setItemWidthSpace(int itemWidthSpace) {
        if (this.itemWidthSpace == itemWidthSpace) {
            return;
        }
        this.itemWidthSpace = itemWidthSpace;
        requestLayout();
    }

    public void setItemHeightSpace(int itemHeightSpace) {
        if (this.itemHeightSpace == itemHeightSpace) {
            return;
        }
        this.itemHeightSpace = itemHeightSpace;
        requestLayout();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        setCurrentPosition(currentPosition, true);
    }

    public synchronized void setCurrentPosition(int currentPosition, boolean smoothScroll) {
        if (list == null || list.size() == 0) {
            return;
        }
        if (currentPosition > list.size() - 1) {
            currentPosition = list.size() - 1;
        }
        if (currentPosition < 0) {
            currentPosition = 0;
        }
        if (this.currentPosition == currentPosition) {
            return;
        }
        if (!scroller.isFinished()) {
            scroller.abortAnimation();
        }

        if (smoothScroll && itemHeight > 0) {
            scroller.startScroll(0, scrollOffsetHeight, 0, (this.currentPosition - currentPosition) * itemHeight);
            int finalY = -currentPosition * itemHeight;
            scroller.setFinalY(finalY);
            handler.post(runnable);
        } else {
            this.currentPosition = currentPosition;
            scrollOffsetHeight = -itemHeight * this.currentPosition;
            postInvalidate();
            if (onWheelChangeListener != null) {
                onWheelChangeListener.onWheelSelected(list.get(currentPosition), currentPosition);
            }
        }
    }

    public void setZoomInSelectedItem(boolean zoomInSelectedItem) {
        if (zoom == zoomInSelectedItem) {
            return;
        }
        zoom = zoomInSelectedItem;
        postInvalidate();
    }

    public void setCyclic(boolean cyclic) {
        if (this.cyclic == cyclic) {
            return;
        }
        this.cyclic = cyclic;
        computeFlingLimitHeight();
        requestLayout();
    }

    public void setMinimumVelocity(int minimumVelocity) {
        minVelocity = minimumVelocity;
    }

    public void setMaximumVelocity(int maximumVelocity) {
        maxVelocity = maximumVelocity;
    }

    public void setTextGradual(boolean textGradual) {
        if (this.textGradual == textGradual) {
            return;
        }
        this.textGradual = textGradual;
        postInvalidate();
    }

    public void setShowCurtain(boolean showCurtain) {
        if (this.showCurtain == showCurtain) {
            return;
        }
        this.showCurtain = showCurtain;
        postInvalidate();
    }

    public void setCurtainColor(@ColorInt int curtainColor) {
        if (this.curtainColor == curtainColor) {
            return;
        }
        this.curtainColor = curtainColor;
        postInvalidate();
    }

    public void setShowCurtainBorder(boolean showCurtainBorder) {
        if (this.showCurtainBorder == showCurtainBorder) {
            return;
        }
        this.showCurtainBorder = showCurtainBorder;
        postInvalidate();
    }

    public void setCurtainBorderColor(@ColorInt int curtainBorderColor) {
        if (this.curtainBorderColor == curtainBorderColor) {
            return;
        }
        this.curtainBorderColor = curtainBorderColor;
        postInvalidate();
    }

    public interface OnWheelChangeListener<T> {

        /**
         * onWheelSelected
         *
         * @param item     item
         * @param position position
         */
        void onWheelSelected(T item, int position);
    }

    public static class LinearGradient {

        private int mStartColor;
        private int mEndColor;
        private int mRedStart;
        private int mBlueStart;
        private int mGreenStart;
        private int mRedEnd;
        private int mBlueEnd;
        private int mGreenEnd;

        public LinearGradient(@ColorInt int startColor, @ColorInt int endColor) {
            mStartColor = startColor;
            mEndColor = endColor;
            updateColor();
        }

        public void setStartColor(@ColorInt int startColor) {
            mStartColor = startColor;
            updateColor();
        }

        public void setEndColor(@ColorInt int endColor) {
            mEndColor = endColor;
            updateColor();
        }

        private void updateColor() {
            mRedStart = Color.red(mStartColor);
            mBlueStart = Color.blue(mStartColor);
            mGreenStart = Color.green(mStartColor);
            mRedEnd = Color.red(mEndColor);
            mBlueEnd = Color.blue(mEndColor);
            mGreenEnd = Color.green(mEndColor);
        }

        public int getColor(float ratio) {
            int red = (int) (mRedStart + ((mRedEnd - mRedStart) * ratio + 0.5));
            int greed = (int) (mGreenStart + ((mGreenEnd - mGreenStart) * ratio + 0.5));
            int blue = (int) (mBlueStart + ((mBlueEnd - mBlueStart) * ratio + 0.5));
            return Color.rgb(red, greed, blue);
        }
    }
}
