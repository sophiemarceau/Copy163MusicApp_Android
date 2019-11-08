package com.sophiemarceauqu.lib_common_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sophiemarceauqu.lib_common_ui.utils.StatusBarUtil;
import com.sophiemarceauqu.lib_image_loader.app.ImageLoaderManager;

import java.util.List;

/**
 * 朋友圈显示1~N张图片View
 */
public class MultiImageViewLayout extends LinearLayout {
    public static int MAX_WIDTH = 0;
    private List<String> imageList;// 照片的url地址列表
    private int pxMoreWandH = 0;// 多张图的宽高
    private int pxImagePadding = StatusBarUtil.dip2px(getContext(), 3);//图片间的间距
    private int MAX_PER_ROW_ROUNT = 3;//每行显示最大数

    private LayoutParams onePicPara, morePara, moreParaColumnFirst, rowPara;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public MultiImageViewLayout(Context context) {
        super(context);
    }

    public MultiImageViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setList(List<String> lists) throws IllegalArgumentException {
        if (lists == null) {
            throw new IllegalArgumentException("imageLists is null...");
        }
        imageList = lists;

        if (MAX_WIDTH > 0) {
            pxMoreWandH = (MAX_WIDTH - pxImagePadding * 2) / 3;//处理右侧图片和内容对不齐问题
            initImageLayoutParams();
        }

        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MAX_WIDTH == 0) {
            int width = measureWidth(widthMeasureSpec);
            if (width > 0) {
                MAX_WIDTH = width;
                if (imageList != null && imageList.size() > 0) {
                    setList(imageList);
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            // result = (int) mTextPaint.measureText(mText) + getPaddingLeft()
            // + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private void initImageLayoutParams() {
        int wrap = LayoutParams.WRAP_CONTENT;
        int match = LayoutParams.MATCH_PARENT;
        onePicPara = new LayoutParams(wrap, wrap);
        moreParaColumnFirst = new LayoutParams(pxMoreWandH, pxMoreWandH);
        morePara = new LayoutParams(pxMoreWandH, pxMoreWandH);
        morePara.setMargins(pxImagePadding, 0, 0, 0);
        rowPara = new LayoutParams(match, wrap);
    }

    //根据imageview的数量 来对view布局进行不同的初始化，还要为每一个view做效果
    private void initView() {
        this.setOrientation(VERTICAL);
        this.removeAllViews();
        if (MAX_WIDTH == 0) {
            //触发onMeasure 测量MultiImageView的最大宽度，MultiImageView的宽度设置match_parent
            addView(new View(getContext()));
            return;
        }

        if (imageList == null || imageList.size() == 0) {
            return;
        }

        if (imageList.size() == 1) {
            addView(createImageView(0, false));
        } else {
            int allCount = imageList.size();
            if (allCount == 4) {
                MAX_PER_ROW_ROUNT = 2;
            } else {
                MAX_PER_ROW_ROUNT = 3;
            }
            int rowCount = allCount / MAX_PER_ROW_ROUNT + (allCount % MAX_PER_ROW_ROUNT > 0 ? 1 : 0);//行数
            for (int rowCursor = 0; rowCursor < rowCount; rowCursor++) {
                LinearLayout rowLayout = new LinearLayout(getContext());
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLayout.setLayoutParams(rowPara);
                if (rowCursor != 0) {
                    rowLayout.setPadding(0, pxImagePadding, 0, 0);
                }

                int columnCount = allCount % MAX_PER_ROW_ROUNT == 0 ? MAX_PER_ROW_ROUNT : allCount % MAX_PER_ROW_ROUNT;//每行的列数
                if (rowCount != rowCount - 1) {
                    columnCount = MAX_PER_ROW_ROUNT;
                }
                addView(rowLayout);
                int rowOffset = rowCursor * MAX_PER_ROW_ROUNT;//行偏移
                for (int columnCursor = 0; columnCursor < columnCount; columnCursor++) {
                    int position = columnCursor + rowOffset;
                    rowLayout.addView(createImageView(position, true));
                }
            }
        }
    }

    private ImageView createImageView(int position, final boolean isMultiImage) {
        String photoInfo = imageList.get(position);
        ImageView imageView = new ImageView(getContext());
        if (isMultiImage) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(position % MAX_PER_ROW_ROUNT == 0 ? moreParaColumnFirst : morePara);
        } else {
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            //图，根据图的大小wrap
            imageView.setLayoutParams(onePicPara);
        }

        imageView.setId(photoInfo.hashCode());
        imageView.setOnClickListener(new ImageOnClickListener(position));
        ImageLoaderManager.getInstance().displayImageForView(imageView, photoInfo);
        return imageView;
    }

    private class ImageOnClickListener implements OnClickListener {
        private int position;

        public ImageOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, position);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
