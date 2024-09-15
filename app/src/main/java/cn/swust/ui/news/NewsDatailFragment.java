package cn.swust.ui.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.Toast;

import cn.swust.R;
import cn.swust.databinding.FragmentNewsDatailBinding;

public class NewsDatailFragment extends Fragment {
    public static final String URL = "url";
    private String url;
    private FragmentNewsDatailBinding binding;
    public NewsDatailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment NewsDatailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsDatailFragment newInstance(String param1) {
        NewsDatailFragment fragment = new NewsDatailFragment();
        Bundle args = new Bundle();
        args.putString(URL, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsDatailBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.webNewsDetail.setWebViewClient(new WebViewClient());
        binding.webNewsDetail.getSettings().setJavaScriptEnabled(true);
        binding.webNewsDetail.loadUrl(url);
    }
}