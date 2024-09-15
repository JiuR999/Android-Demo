package cn.swust.ui.found;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.swust.adapter.FoundMenuRecycleAdapter;
import cn.swust.databinding.FragmentFoundBinding;
import cn.swust.impl.SpacesItemDecoration;

public class FoundFragment extends Fragment {

    private FragmentFoundBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FoundViewModel foundViewModel =
                new ViewModelProvider(this,
                        ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                        .get(FoundViewModel.class);

        binding = FragmentFoundBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initRecycleView(foundViewModel);
        return root;
    }

    private void initRecycleView(FoundViewModel foundViewModel) {
        RecyclerView recyclerView = binding.foundRecycle;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        FoundMenuRecycleAdapter adapter = new FoundMenuRecycleAdapter(getActivity(),foundViewModel.getListDate());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(56));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}