package com.yeeyuntech.framework.ui.widget.databinding;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.BindingAdapter;
import android.databinding.BindingMethods;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethods;
import android.databinding.adapters.ImageViewBindingAdapter;
import android.databinding.adapters.ListenerUtil;
import android.databinding.adapters.TextViewBindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yeeyuntech.framework.R;
import com.yeeyuntech.framework.ui.widget.CommonItem;
import com.yeeyuntech.framework.ui.widget.MyScrollView;

/**
 * Created by JuiceShui on 2017/5/5.
 * To strive,to seek,to find,and not to give up
 */

@InverseBindingMethods({
})
@BindingMethods({
})
public class ViewBindingAdapter {
    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    //    这个他们自定义的有
    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, RecyclerView.LayoutManager manager) {
        recyclerView.setLayoutManager(manager);
    }

    @BindingAdapter("imageUrl")
    public static void setImage(View view, String url) {
//        Glide.with(mContext).load(viewModel.getThumb()).crossFade().into(messageBinding.thumb);
        if (view instanceof ImageView) {
            Glide.with(view.getContext())
                    .load(url)
                    .into((ImageView) view);
        } else {
            throw new RuntimeException("view must be a child of ImageView");
        }
    }

    @BindingAdapter("onScroller")
    public static void setScoller(MyScrollView view, MyScrollView.OnScrollerListener listener) {
        view.setOnScrollerListener(listener);
    }

    /**
     * 设置事件
     *
     * @param view
     * @param textAttrChanged
     */
    @BindingAdapter(value = {"textAttrChanged"})
    public static void setTextWatcher(final View view,
                                      final InverseBindingListener textAttrChanged) {
        BindingText baseDataBidingView;
        if (view instanceof BindingText) {
            baseDataBidingView = (BindingText) view;
        } else {
            return;
        }
        final TextWatcher newValue;
        if (textAttrChanged == null) {
            newValue = null;
        } else {
            newValue = new TextWatcher() {
                String oldStr;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    oldStr = s.toString();
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String newStr = s.toString();
                    if (newStr.equals(oldStr)) {
                        return;
                    }
                    textAttrChanged.onChange();
                }
            };
        }
        final TextWatcher oldValue = ListenerUtil.trackListener(baseDataBidingView.getBindingView(), newValue, R.id.textWatcher);
        if (oldValue != null) {
            baseDataBidingView.delTextWatch(oldValue);
        }
        if (newValue != null) {
            baseDataBidingView.addTextWatch(newValue);
        }
    }

    /**
     * 设置值
     *
     * @param view
     * @param text
     */
    @BindingAdapter("bindingText")
    public static void setText(View view, CharSequence text) {
        BindingText baseDataBidingView;
        if (view instanceof BindingText) {
            baseDataBidingView = (BindingText) view;
        } else {
            throw new RuntimeException("error");
        }
        final CharSequence oldText = baseDataBidingView.getBindingText();
        if (text == oldText || (text == null && oldText.length() == 0) || text == null) {
            return;
        }
        if (baseDataBidingView instanceof Spanned) {
            if (text.equals(oldText)) {
                return; // No change in the spans, so don't set anything.
            }
        } else if (!haveContentsChanged(text, oldText)) {
            return; // No content changes, so don't set anything.
        }
        baseDataBidingView.setBindingText(text.toString());
    }

    /**
     * 双向绑定Ω
     *
     * @param view
     * @return
     */
    @InverseBindingAdapter(attribute = "bindingText", event = "textAttrChanged")
    public static String getTextString(View view) {
        BindingText baseDataBidingView;
        if (view instanceof BindingText) {
            baseDataBidingView = (BindingText) view;
        } else {
            throw new RuntimeException("error");
        }
        return baseDataBidingView.getBindingText();
    }

    /**
     * 这个  方法暂时没用
     *
     * @param str1
     * @param str2
     * @return
     */
    private static boolean haveContentsChanged(CharSequence str1, CharSequence str2) {
        if ((str1 == null) != (str2 == null)) {
            return true;
        } else if (str1 == null) {
            return false;
        }
        final int length = str1.length();
        if (length != str2.length()) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    @BindingAdapter("rightText")
    public static void setItemRightText(CommonItem view, CharSequence text) {
        final CharSequence oldText = view.getmRightText();
        if (text == oldText || (text == null && oldText.length() == 0) || text == null) {
            return;
        }
        if (view instanceof Spanned) {
            if (text.equals(oldText)) {
                return; // No change in the spans, so don't set anything.
            }
        } else if (!haveContentsChanged(text, oldText)) {
            return; // No content changes, so don't set anything.
        }
        view.setmRightText(text.toString());
    }

    @BindingAdapter("leftText")
    public static void setItemLeftText(CommonItem view, CharSequence text) {
        final CharSequence oldText = view.getmLeftText();
        if (text == oldText || (text == null && oldText.length() == 0) || text == null) {
            return;
        }
        if (view instanceof Spanned) {
            if (text.equals(oldText)) {
                return; // No change in the spans, so don't set anything.
            }
        } else if (!haveContentsChanged(text, oldText)) {
            return; // No content changes, so don't set anything.
        }
        view.setmLeftText(text.toString());
    }

    /**
     * 设置加载本地图片
     *
     * @param view
     * @param res
     */
    @BindingAdapter("setPic")
    public static void setPicRes(ImageView view, int res) {
        if (res != 0) {
            view.setImageResource(res);
        }
    }

    @BindingAdapter("fadeVisible")
    public static void setFadeVisible(final View view, boolean visible) {
        if (view.getTag() == null) {
            view.setTag(true);
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        } else {
            view.animate().cancel();
            if (visible && view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
                view.setAlpha(0);
                view.animate().alpha(1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setAlpha(1);
                    }
                });
            } else if (!visible && view.getVisibility() != View.GONE) {
                view.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setAlpha(1);
                        view.setVisibility(View.GONE);
                    }
                });
            }
        }
    }
}
