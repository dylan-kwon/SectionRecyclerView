package com.example.seokchankwon.verticalsectionrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by seokchan.kwon on 2017. 9. 18..
 */

public abstract class SectionRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int SECTION_HEADER_VIEW_TYPE = 0;
    public static final int SECTION_ITEM_VIEW_TYPE = 1;

    public static final int NO_ITEM_COUNT = -1;

    public int mTotalItemCount = NO_ITEM_COUNT;

    private Context mContext;

    private ArrayList<T> mSections;
    private ArrayList<Integer> mSectionIndicator;
    private ArrayList<Integer> mSectionHeaderPositions;

    private LayoutInflater mLayoutInflater;


    public SectionRecyclerViewAdapter(Context context) {
        mContext = context;
        mSections = new ArrayList<>();
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SECTION_HEADER_VIEW_TYPE:
                return onCreateSectionHeaderViewHolder(parent, viewType);

            case SECTION_ITEM_VIEW_TYPE:
                return onCreateSectionItemViewHolder(parent, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case SECTION_HEADER_VIEW_TYPE:
                onBindSectionHeaderViewHolder(holder, position);
                break;

            case SECTION_ITEM_VIEW_TYPE:
                onBindSectionItemViewHolder(holder, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mTotalItemCount == NO_ITEM_COUNT) {
            return initItemCount();
        }
        return mTotalItemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return SECTION_HEADER_VIEW_TYPE;
        }

        int section = mSectionIndicator.get(position);
        int beforeSection = mSectionIndicator.get(position - 1);

        if (section != beforeSection) {
            return SECTION_HEADER_VIEW_TYPE;
        } else {
            return SECTION_ITEM_VIEW_TYPE;
        }
    }

    public Context getContext() {
        return mContext;
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    public void setItems(ArrayList<T> items) {
        mSections.clear();
        mTotalItemCount = NO_ITEM_COUNT;

        if (items != null) {
            mSections.addAll(items);
        }

        notifyDataSetChanged();
    }

    public ArrayList<T> getItems() {
        return mSections;
    }

    public T getItem(int position) {
        return mSections.get(position);
    }

    private int initItemCount() {
        int itemCount = 0;
        int sectionCount = getSectionHeaderCount();

        if (mSectionIndicator == null) {
            mSectionIndicator = new ArrayList<>();
        } else {
            mSectionIndicator.clear();
        }

        mSectionHeaderPositions = new ArrayList<>();

        for (int i = 0; i < sectionCount; i++) {
            int sectionItemCount = getSectionItemCount(i) + 1;

            for (int j = 0; j < sectionItemCount; j++) {
                if (j == 0) {
                    mSectionHeaderPositions.add(itemCount + 1);
                }
                mSectionIndicator.add(i);
            }
            itemCount += sectionItemCount;
        }

        mTotalItemCount = itemCount;
        return mTotalItemCount;
    }

    public int getSectionPosition(int adapterPosition) {
        return mSectionIndicator.get(adapterPosition);
    }

    public int getSectionHeaderPosition(int sectionPosition) {
        return mSectionHeaderPositions.get(sectionPosition);
    }

    public int getSectionItemPosition(int adapterPosition) {
        int sectionPosition = getSectionPosition(adapterPosition);
        int targetPosition = getSectionHeaderPosition(sectionPosition);

        int sectionItemPosition = 0;

        while (targetPosition != adapterPosition) {
            if (targetPosition > mTotalItemCount) {
                return NO_ITEM_COUNT;
            }
            targetPosition++;
            sectionItemPosition++;
        }

        return sectionItemPosition;
    }

    public void notifySectionChanged(int section) {
        int changedStartPosition = NO_ITEM_COUNT;
        int changedItemCount = 0;

        int itemCount = mSectionIndicator.size();

        for (int i = 0; i < itemCount; i++) {
            int targetSection = mSectionIndicator.get(i);

            if (section != targetSection) {
                continue;
            }

            if (changedStartPosition == NO_ITEM_COUNT) {
                changedStartPosition = i;
            }

            changedItemCount++;
        }

        if (changedStartPosition != NO_ITEM_COUNT) {
            notifyItemRangeChanged(changedStartPosition, changedItemCount);
        }
    }

    abstract public int getSectionHeaderCount();

    abstract public int getSectionItemCount(int section);

    abstract RecyclerView.ViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType);

    abstract void onBindSectionHeaderViewHolder(RecyclerView.ViewHolder holder, int position);

    abstract RecyclerView.ViewHolder onCreateSectionItemViewHolder(ViewGroup parent, int viewType);

    abstract void onBindSectionItemViewHolder(RecyclerView.ViewHolder holder, int position);

}