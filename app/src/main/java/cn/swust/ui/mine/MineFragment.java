package cn.swust.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import cn.swust.databinding.FragmentHomeBinding;
import cn.swust.databinding.FragmentMineBinding;
import cn.swust.ui.home.HomeViewModel;


public class MineFragment extends Fragment {
    private FragmentMineBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentMineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMine;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}