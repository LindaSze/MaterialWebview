package com.lynnsze.materialwebview.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lynnsze.materialwebview.R;
import com.lynnsze.materialwebview.base.CustomAdapter;
import com.lynnsze.materialwebview.base.CustomViewHolder;

import java.util.ArrayList;

public class TopSitesFragment extends Fragment implements HomePanel {
    private ArrayList<TopSite> topSites;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topsites, container, false);

        initData();
        CustomAdapter<TopSite> adapter = new CustomAdapter<TopSite>(getActivity(), topSites, R.layout.topsite, null) {
            @Override
            public void buildItemViewHolder(CustomViewHolder viewHolder, TopSite item, int position) {
                viewHolder.setText(R.id.title, item.title);
                viewHolder.setItemBackgroundColor(item.color);
            }
        };

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.topSites);
        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 2));
        recyclerView.setAdapter(adapter);

        int spacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        recyclerView.addItemDecoration(new SpacingDecoration(spacing));
        recyclerView.setPadding(spacing, spacing, spacing, spacing);


        return view;
    }

    private void initData() {
        topSites = new ArrayList<TopSite>();
        topSites.add(new TopSite("Demo 1", 0xffcc4f45));
        topSites.add(new TopSite("Demo 2", 0xff1897da));
        topSites.add(new TopSite("Demo 3", 0xff65bc24));
        topSites.add(new TopSite("Demo 4", 0xfff17c22));
    }

    @Override
    public String getTitle() {
        return "Top Sites";
    }


    public static class TopSite {
        public final String title;
        public final int color;

        public TopSite(String title, int color) {
            this.title = title;
            this.color = color;
        }
    }
}
