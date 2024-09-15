package cn.swust.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alibaba.fastjson.JSON;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.swust.adapter.NewsAdapter;
import cn.swust.databinding.FragmentNewsBinding;
import cn.swust.pojo.News;
import cn.swust.utils.FetchDataUtil;

public class NewsFragment extends Fragment implements View.OnClickListener {

    private FragmentNewsBinding binding;
    private NewsAdapter adapter;
    private List<News> newsList;
    private NewsViewModel viewModel;
    private List<NewsItemFragment> fragments;
    private String[] titles = {"综合", "国内要闻", "社会新闻","IT资讯", "娱乐新闻", "动漫资讯"};


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //initDate();
        initTablayout();
        initFragments();

        registerClick();

        return root;
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        //fragments.add(NewsItemFragment.newInstance());
    }

    private void initTablayout() {
        binding.viewPager2.setAdapter(new FragmentStateAdapter(getActivity()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position >= fragments.size()) {
                    NewsItemFragment itemFragment = NewsItemFragment.newInstance(position);
                    fragments.add(itemFragment);
                    return itemFragment;
                }
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return titles.length;
            }
        });
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TabLayoutMediator mediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
            }
        });
        mediator.attach();
    }


    private void initDate() {
        String url = "https://apis.tianapi.com/generalnews/index?" + "key=c8c00f3d562f504ca8044b34bb1418e6"
                + "&rand=1";
        FetchDataUtil.fetchData(url, viewModel.getJsonObject());
    }

    private void initRecycleView() {
        adapter = new NewsAdapter(getContext(), newsList);
//        binding.recycleNews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        binding.recycleNews.setAdapter(adapter);
    }

    private void registerClick() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //getActivity().stopService(intent);
        binding = null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

    }

    private String format(int item) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        return format.format(item);
    }


}