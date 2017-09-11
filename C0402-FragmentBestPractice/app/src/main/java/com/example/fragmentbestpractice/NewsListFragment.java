package com.example.fragmentbestpractice;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// 新闻（标题）列表碎片
public class NewsListFragment extends Fragment {

    // 是否显式为双页模式（平板模式）
    private boolean isTwoPane;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list_frag, container, false);

        RecyclerView newsListRView = (RecyclerView) view.findViewById(R.id.news_list_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        newsListRView.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(getNews());
        newsListRView.setAdapter(adapter);
        return view;
    }

    private List<News> getNews() {
        List<News> newsList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            News news = new News();
            news.setTitle(i+". "+ getResources().getString(R.string.news_sample_title));
            news.setContent(
                    getResources().getString(R.string.news_sample_content)+
                    getResources().getString(R.string.news_sample_content)+
                    getResources().getString(R.string.news_sample_content)
            );
            newsList.add(news);
        }
        return newsList;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 判断是否使用双页模式（依据layout-sw600dp\activity_main.xml的news_content_layout1）
        if (getActivity().findViewById(R.id.news_content_layout1) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }
    }

    // 新闻适配器
    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private List<News> mNewsList;

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView newsTitleText;

            public ViewHolder(View itemView) {
                super(itemView);
                newsTitleText = (TextView) itemView.findViewById(R.id.news_list_title);
            }
        }

        public NewsAdapter(List<News> newsList) {
            mNewsList = newsList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_list_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    News news = mNewsList.get(holder.getAdapterPosition());
                    if (isTwoPane) {
                        // 双页模式刷新碎片 NewsContentFragment
                        NewsContentFragment fragment = (NewsContentFragment) getFragmentManager()
                                .findFragmentById(R.id.news_content_fragment);
                        fragment.refresh(news.getTitle(), news.getContent());
                    } else {
                        // 单页模式则启动活动 NewsContentActivity
                        NewsContentActivity.ActionStart(getActivity(), news.getTitle()
                                , news.getContent());
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            News news = mNewsList.get(position);
            holder.newsTitleText.setText(news.getTitle());
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }


    }


}
