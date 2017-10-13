package com.example.seokchankwon.verticalsectionrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.seokchankwon.verticalsectionrecyclerview.model.Item;
import com.example.seokchankwon.verticalsectionrecyclerview.model.Section;

/**
 * Created by seokchan.kwon on 2017. 9. 18..
 */

public class MainAdapter extends SectionRecyclerViewAdapter<Section> {

    private OnSectionHeaderClickListener mOnSectionHeaderClickListener;
    private OnSectionItemClickListener mOnSectionItemClickListener;

    public MainAdapter(Context context) {
        super(context);
    }

    @Override
    RecyclerView.ViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.listview_main_section, parent, false);
        return new SectionHeaderHolder(view);
    }

    @Override
    void onBindSectionHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition != RecyclerView.NO_POSITION) {

            int sectionPosition = getSectionPosition(adapterPosition);

            Section section = getItem(sectionPosition);
            SectionHeaderHolder sectionHeaderHolder = (SectionHeaderHolder) holder;

            if (TextUtils.isEmpty(section.title)) {
                sectionHeaderHolder.getTvSectionTitle().setText("");
            } else {
                sectionHeaderHolder.getTvSectionTitle().setText(section.title);
            }

        }
    }

    @Override
    RecyclerView.ViewHolder onCreateSectionItemViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.listview_main_section_item, parent, false);
        return new SectionItemHolder(view);
    }

    @Override
    void onBindSectionItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition != RecyclerView.NO_POSITION) {

            int sectionPosition = getSectionPosition(adapterPosition);
            int sectionItemPosition = getSectionItemPosition(adapterPosition);

            Item item = getItem(sectionPosition).items.get(sectionItemPosition);
            SectionItemHolder sectionItemHolder = (SectionItemHolder) holder;

            if (TextUtils.isEmpty(item.name)) {
                sectionItemHolder.getTvItemName().setText("");
            } else {
                sectionItemHolder.getTvItemName().setText(item.name);
            }

            if (TextUtils.isEmpty(item.desc)) {
                sectionItemHolder.getTvItemDesc().setText(item.desc);
            } else {
                sectionItemHolder.getTvItemDesc().setText(item.desc);
            }

        }
    }

    @Override
    public int getSectionHeaderCount() {
        return getItems().size();
    }

    @Override
    public int getSectionItemCount(int section) {
        return getItems().get(section).items.size();
    }

    public void setOnSectionHeaderClickListener(OnSectionHeaderClickListener listener) {
        mOnSectionHeaderClickListener = listener;
    }

    public void setOnSectionItemClickListener(OnSectionItemClickListener listener) {
        mOnSectionItemClickListener = listener;
    }

    public interface OnSectionHeaderClickListener {
        void onHeaderClick(int sectionPosition, int adapterPosition, Section item);
    }

    public interface OnSectionItemClickListener {
        void onItemClick(int sectionPosition, int sectionItemPosition, int adapterPosition, Item item);
    }

    public class SectionHeaderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvSectionTitle;

        public SectionHeaderHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvSectionTitle = itemView.findViewById(R.id.tv_listview_main_section_title);
        }

        public TextView getTvSectionTitle() {
            return tvSectionTitle;
        }

        @Override
        public void onClick(View v) {
            if (mOnSectionHeaderClickListener == null) {
                return;
            }

            int adapterPosition = getAdapterPosition();
            if (adapterPosition == RecyclerView.NO_POSITION) {
                return;
            }

            int sectionPosition = getSectionPosition(adapterPosition);
            Section section = getItems().get(sectionPosition);

            mOnSectionHeaderClickListener.onHeaderClick(sectionPosition, adapterPosition, section);
        }
    }

    public class SectionItemHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        private TextView tvItemTitle;
        private TextView tvItemDesc;

        public SectionItemHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tvItemTitle = itemView.findViewById(R.id.tv_listview_main_item_name);
            tvItemDesc = itemView.findViewById(R.id.tv_listview_main_item_desc);
        }

        @Override
        public void onClick(View v) {
            if (mOnSectionItemClickListener == null) {
                return;
            }

            int adapterPosition = getAdapterPosition();
            if (adapterPosition == RecyclerView.NO_POSITION) {
                return;
            }

            int sectionPosition = getSectionPosition(adapterPosition);
            int sectionItemPosition = getSectionItemPosition(adapterPosition);

            Item section = getItem(sectionPosition).items.get(sectionItemPosition);

            mOnSectionItemClickListener.onItemClick(sectionPosition, sectionItemPosition, adapterPosition, section);
        }

        public TextView getTvItemName() {
            return tvItemTitle;
        }

        public TextView getTvItemDesc() {
            return tvItemDesc;
        }

    }
}
