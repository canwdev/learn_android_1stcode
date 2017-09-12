package com.example.fragmentbestpractice;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

// 用于双页模式显示的新闻内容碎片
public class NewsContentFragment extends Fragment {

    private View view;
    TextView uselessText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_content_frag, container, false);
        uselessText = (TextView) view.findViewById(R.id.useless_text);

        return view;

    }

    // 刷新View新闻内容
    public void refresh(String newsTitle, String newsContent) {
        uselessText.setVisibility(View.GONE);

        View newsLayout = (View) view.findViewById(R.id.news_layout);
        newsLayout.setVisibility(View.VISIBLE);

        // 滚动回顶部
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.news_scroll_view);
        scrollView.scrollTo(0,0);

        TextView newsTitleText = (TextView) view.findViewById(R.id.news_title);
        TextView newsContentText = (TextView) view.findViewById(R.id.news_content);

        newsTitleText.setText(newsTitle);
        newsContentText.setText(newsContent);
    }
}
