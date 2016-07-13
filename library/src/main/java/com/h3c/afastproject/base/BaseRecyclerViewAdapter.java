package com.h3c.afastproject.base;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.h3c.afastproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;


/**
 * Created by H3c on 16/5/22.
 */

public abstract class BaseRecycleViewAdapter<E, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> implements View.OnClickListener {
    public static final int ANIM_NONE = 0;
    public static final int ANIM_SWING_IN_BOTTOM = 1;
    public static final int ANIM_SWING_IN_RIGHT = 2;
    private static final int ANIM_DURATION = 200;
    private int currentAnimType = ANIM_NONE;
    private int animatingPosition = -1;
    private HashSet<Integer> animedItem = new HashSet<>();
    private OvershootInterpolator overshootInterpolator = new OvershootInterpolator();

    public SelectedListener mListener;
    public Context mContext;
    public List<E> mData;

    public BaseRecycleViewAdapter() {}
    public BaseRecycleViewAdapter(Context context) {
        this.mContext = context;
    }
    public BaseRecycleViewAdapter(List<E> data) {
        setData(data);
    }
    public BaseRecycleViewAdapter(Context context, List<E> data) {
        this(context);
        setData(data);
    }

    public void setAnimType(int animType) {
        this.currentAnimType = animType;
    }

    public E getDataWithPosition(int position) {
        if(mData != null) {
            return mData.get(position);
        }

        return null;
    }

    public void setData(List<E> data) {
        this.mData = data;
    }

    public void addData(E data) {
        if(mData == null) {
            mData = new ArrayList<>();
        }

        mData.add(data);
    }

    public void removeData(E data) {
        if(mData != null) {
            mData.remove(data);
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(setLayoutContent(), parent, false);
        itemView.setOnClickListener(this);
        return createNewViewHolder(itemView);
    }

    abstract public int setLayoutContent();
    abstract public VH createNewViewHolder(View rootView);

    @Override
    public int getItemCount() {
        if(mData != null) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.itemView.setTag(R.id.view_tag_id, position);
        onBaseBindViewHolder(holder, position);

        if(currentAnimType != ANIM_NONE && !animedItem.contains(position)) {
            animedItem.add(position);
            if(position > animatingPosition) {
                holder.itemView.animate().cancel();
                holder.itemView.setAlpha(0f);
                switch (currentAnimType) {
                    case ANIM_SWING_IN_BOTTOM:
                        swingInBottom(holder.itemView, position);
                        break;
                    case ANIM_SWING_IN_RIGHT:
                        swingInRight(holder.itemView, position);
                        break;
                    default:
                        break;

                }
                animatingPosition = position;
            }
//            } else if (position == 0) animatingPosition = 0;
        }
    }

    abstract public void onBaseBindViewHolder(VH holder, int position);

    @Override
    public void onClick(View view) {
        if(mListener == null) return;

        int index = (int) view.getTag(R.id.view_tag_id);

        if(mData != null && index < mData.size()) {
            mListener.onItemClick(view, index, mData.get(index));
        } else {
            mListener.onItemClick(view, index, null);
        }
    }

    public void setSelectedListener(SelectedListener<E> listener) {
        mListener = listener;
    }


    public interface SelectedListener<E> {
        void onItemClick(View clickedView, int position, E i);
    }

    private void swingInBottom(View animatableView, int animatablePosition) {
        Animator fadeAnimator = ObjectAnimator.ofFloat(animatableView, "alpha",
                0.5f, 1);
        Animator translateAnimator = ObjectAnimator.ofFloat(animatableView, "translationY",
                250, 0);
        Animator[] combinedAnimator = new Animator[2];
        combinedAnimator[0] = fadeAnimator;
        combinedAnimator[1] = translateAnimator;

        AnimatorSet set = new AnimatorSet();
        set.playTogether(combinedAnimator);
        set.setStartDelay(calculateAnimationDelay(animatablePosition));
        set.setInterpolator(overshootInterpolator);
        set.setDuration(ANIM_DURATION);
        set.start();
    }

    private void swingInRight(View animatableView, int animatablePosition) {
        Animator fadeAnimator = ObjectAnimator.ofFloat(animatableView, "alpha",
                0.5f, 1);
        Animator translateAnimator = ObjectAnimator.ofFloat(animatableView, "translationX",
                250, 0);
        Animator[] combinedAnimator = new Animator[2];
        combinedAnimator[0] = fadeAnimator;
        combinedAnimator[1] = translateAnimator;

        AnimatorSet set = new AnimatorSet();
        set.playTogether(combinedAnimator);
        set.setStartDelay(calculateAnimationDelay(animatablePosition));
        set.setInterpolator(overshootInterpolator);
        set.setDuration(ANIM_DURATION);
        set.start();
    }

    long lastAnimDelayTime = 0;
    int delayKey = 0;
    @SuppressLint("NewApi")
    private int calculateAnimationDelay(final int position) {
        long currentAnimDelayTime = Calendar.getInstance().getTimeInMillis();
        long timeDelay = currentAnimDelayTime - lastAnimDelayTime;
        lastAnimDelayTime = currentAnimDelayTime;

        if(position == 0) {
            return position;
        }

        int delay;
        int mAnimationDelayMillis = 60;
        if(timeDelay > 1000) {
            delayKey = position;
        }

        delay = mAnimationDelayMillis * ((delayKey > 1) ? (position % delayKey) : position);
//        LogUtils.e("DDD:" + delay + "====" + timeDelay + "===" + (position % delayKey) + "===" + delayKey + "===" + position);
        return delay;
    }
}
