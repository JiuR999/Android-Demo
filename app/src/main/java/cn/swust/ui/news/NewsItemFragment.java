package cn.swust.ui.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.swust.MainActivity;
import cn.swust.R;
import cn.swust.adapter.NewsAdapter;
import cn.swust.databinding.FragmentNewsItemBinding;
import cn.swust.impl.NewsItemClickListner;
import cn.swust.pojo.News;
import cn.swust.utils.FetchDataUtil;
import cn.swust.utils.OkHttpUtil;
import cn.swust.utils.SharedPreferenceUtil;


public class NewsItemFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    public static final String NEWSLIST = "newslist";
    public static final String RESULT = "result";
    public static final String NEWS = "news";
    public static final String KEY = "c8c00f3d562f504ca8044b34bb1418e6";
    private String category;
    private int position;
    private NewsViewModel viewModel;
    private List<News> newsList;
    private NewsAdapter adapter;

    private String[] newTitle;
    private FragmentNewsItemBinding binding;
    private NavController controller;

    public NewsItemFragment() {
        newsList = new ArrayList<>();
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position 对应新闻.
     * @return A new instance of fragment NewsItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsItemFragment newInstance(int position) {
        NewsItemFragment fragment = new NewsItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newTitle = getContext().getResources().getStringArray(R.array.news);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewsItemBinding.inflate(inflater,container,false);
        //TODO 保存至文件
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavHostFragment fragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        controller = fragment.getNavController();
        String baseUrl = getContext().getString(R.string.baseUrl);
        initViewModel();
        binding.refresh.setOnRefreshListener(refreshLayout -> {
                         getNews(baseUrl);
        });
        String news = (String) SharedPreferenceUtil.readData(getContext(),
                SharedPreferenceUtil.Type.STRING,
                "news", newTitle[position]);
        if(news.isEmpty()){
            getNews(baseUrl);
        } else {
            try {
                viewModel.getJsonObject().postValue(new JSONObject(news));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void getNews(String baseUrl) {
        FetchDataUtil.fetchData(baseUrl +newTitle[position] +
                        "/index?key=" + KEY + "&rand=1",
                viewModel.getJsonObject());
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        viewModel.getJsonObject().observe(getViewLifecycleOwner(), jsonObject -> {
            try {
                if (jsonObject != null) {
                    JSONArray jsonArray = jsonObject.getJSONObject(RESULT)
                            .optJSONArray(NEWSLIST);
                    SharedPreferenceUtil.save(getContext(), SharedPreferenceUtil.Type.STRING
                    , NEWS, newTitle[position], jsonObject.toString());
                    newsList = JSON.parseArray(jsonArray.toString(), News.class);
                    initRecycleView();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void initRecycleView() {
        adapter = new NewsAdapter(getContext(),newsList);
        adapter.setClickListner(new NewsItemClickListner() {
            @Override
            public void lookNews(String url) {
                Bundle bundle = new Bundle();
                bundle.putString(NewsDatailFragment.URL, url);
                controller.navigate(R.id.navigation_news_detail,bundle);
            }
        });
        binding.refresh.finishRefresh();
        binding.recycleNews.setAdapter(adapter);
    }
}