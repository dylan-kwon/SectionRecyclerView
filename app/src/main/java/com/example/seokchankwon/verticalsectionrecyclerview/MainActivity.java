package com.example.seokchankwon.verticalsectionrecyclerview;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.seokchankwon.verticalsectionrecyclerview.model.Item;
import com.example.seokchankwon.verticalsectionrecyclerview.model.Section;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout mRefreshLayout;

    private RecyclerView mRecyclerView;

    private MainAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

//        LinearLayoutManager layoutManager =
//                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        GridLayoutManager layoutManager =
                new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mAdapter.getItemViewType(position) == MainAdapter.SECTION_HEADER_VIEW_TYPE) {
                    return 3;
                }
                return 1;
            }
        });

        mAdapter = new MainAdapter(this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setItems(makeItems());
        mAdapter.setOnSectionHeaderClickListener(new MainAdapter.OnSectionHeaderClickListener() {
            @Override
            public void onHeaderClick(int sectionPosition, int adapterPosition, Section item) {
                Toast.makeText(
                        MainActivity.this,
                        "adapterPosition = " + adapterPosition + "\n"
                                + "sectionPosition = " + sectionPosition + "\n"
                                + "title = " + item.title + "\n"
                                + "size = " + item.items.size() + "\n",
                        Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnSectionItemClickListener(new MainAdapter.OnSectionItemClickListener() {
            @Override
            public void onItemClick(int sectionPosition, int sectionItemPosition, int adapterPosition, Item item) {
                Toast.makeText(
                        MainActivity.this,
                        "adapterPosition = " + adapterPosition + "\n"
                                + "sectionPosition = " + sectionPosition + "\n"
                                + "sectionItemPosition = " + sectionItemPosition + "\n"
                                + "title = " + item.name + "\n"
                                + "desc = " + item.desc + "\n",
                        Toast.LENGTH_SHORT).show();
            }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.notifySectionChanged(1);
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initView() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sr_activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_activity_main);
    }

    private ArrayList<Section> makeItems() {
        ArrayList<Section> sections = new ArrayList<>();

        int itemTotalCount = 0;
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            Section section = new Section();

            section.title = "Section" + (i + 1);
            section.items = new ArrayList<>();

            int itemCount = random.nextInt(9) + 1;

            for (int j = 0; j < itemCount; j++) {
                Item item = new Item("Title: " + itemTotalCount, "Desc: " + itemTotalCount);
                section.items.add(item);
                itemTotalCount++;
            }

            sections.add(section);
        }
        return sections;
    }
}
