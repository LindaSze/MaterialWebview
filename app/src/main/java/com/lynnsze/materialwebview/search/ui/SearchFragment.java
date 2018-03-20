package com.lynnsze.materialwebview.search.ui;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.test.espresso.*;

import com.lynnsze.materialwebview.base.CustomHandler;
import com.lynnsze.materialwebview.base.MWApplication;
import com.lynnsze.materialwebview.base.MWConfig;
import com.lynnsze.materialwebview.R;
import com.lynnsze.materialwebview.base.CustomAdapter;
import com.lynnsze.materialwebview.base.CustomViewHolder;
import com.lynnsze.materialwebview.base.net.HttpRequestCallback;
import com.lynnsze.materialwebview.main.MainActivity;
import com.lynnsze.materialwebview.main.SpacingDecoration;
import com.lynnsze.materialwebview.base.net.HttpUtil;
import com.lynnsze.materialwebview.search.util.SuggestionBean;
import com.lynnsze.materialwebview.search.util.SuggestionParser;
import com.lynnsze.materialwebview.search.util.TestIdlingResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment {
    private static final int HANDLER_TAG_SUGGESTION = 1;

    private WeakHandler handler = new WeakHandler(this);

    private ArrayList<SuggestionBean> suggestionList = new ArrayList<SuggestionBean>();
    private CustomAdapter suggestionAdapter;

    private HttpRequestCallback<List<SuggestionBean>> suggestionCallback;

    private String keyStr;

    private TestIdlingResource mIdlingResource;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        CustomAdapter.onRecyclerViewItemClick itemClick = new CustomAdapter.onRecyclerViewItemClick() {
            @Override
            public void onItemClick(View v, int position) {
                loadUrl(suggestionList.get(position).getPhrase());
            }
        };

        suggestionAdapter = new CustomAdapter<SuggestionBean>(getActivity(), suggestionList, R.layout.item_search_suggestion, itemClick) {
            @Override
            public void buildItemViewHolder(CustomViewHolder viewHolder, SuggestionBean item, int position) {
                viewHolder.setText(R.id.suggest_item_webtitle, item.getPhrase());
            }
        };

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.search);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(suggestionAdapter);

        int spacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        recyclerView.addItemDecoration(new SpacingDecoration(spacing));
        recyclerView.setPadding(spacing, spacing, spacing, spacing);
        return view;
    }

    public void refreshSuggestion(String key) {
        setTestIdling(false);
        Map<String, String> keyMap = new HashMap<String, String>();
        keyMap.put("q", keyStr);

        if(suggestionCallback==null)
            suggestionCallback=new HttpRequestCallback<List<SuggestionBean>>() {

                @Override
                public void before(int requestCode) {
                    super.before(requestCode);
                }

                @Override
                public void success(int requestCode, List<SuggestionBean> data) {
                    setTestIdling(true);
                    if (data != null && data.size() > 0) {
                        Message msg = handler.obtainMessage(HANDLER_TAG_SUGGESTION);
                        msg.obj = data;
                        handler.sendMessage(msg);
                    }
                }

                @Override
                public void error(int requestCode, int errorCode, String msg) {
                    setTestIdling(true);
                }

                @Override
                public void after(int requestCode) {
                    super.after(requestCode);
                }
            };

        HttpUtil.getInstance().requestWithoutHeader(MWConfig.URL_SEARCH_SUGGESTION_PREFIX, keyMap, new SuggestionParser(),suggestionCallback);
    }


    public void loadUrl(String keyStr) {
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).loadWebView(MWConfig.URL_DEFAULT + keyStr, true);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (suggestionList != null && suggestionList.size() > 0) {
                suggestionList.clear();
                suggestionAdapter.setData(suggestionList);
                suggestionAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(HANDLER_TAG_SUGGESTION);
        if(MWConfig.isDebug)
        MWApplication.getRefWatcher().watch(this);
    }

    private class WeakHandler extends CustomHandler<SearchFragment> {
        WeakHandler(SearchFragment fragment) {
            super(fragment);
        }

        @Override
        public void message(Message msg) {
            SearchFragment outer = super.weakReference.get();
            if (outer == null) {
                return;
            }
            if (msg.what == HANDLER_TAG_SUGGESTION) {
                if (msg.obj != null) {
                    if (suggestionList == null)
                        suggestionList = new ArrayList<SuggestionBean>();
                    else
                        suggestionList.clear();
                    suggestionList.addAll((ArrayList<SuggestionBean>) msg.obj);
                    suggestionAdapter.setData(suggestionList);
                    suggestionAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void setTestIdling(boolean isIdle){
        if(mIdlingResource!=null)
            mIdlingResource.setIdleState(isIdle);
    }


    @VisibleForTesting
    public IdlingResource getIdlingResource() {
        if(mIdlingResource == null)
            mIdlingResource = new TestIdlingResource();
        return mIdlingResource;
    }

}
