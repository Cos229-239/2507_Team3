package com.ila.ui.classselection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ila.databinding.FragmentClassSelectionBinding;

public class ClassSelectionFragment extends Fragment {

    private FragmentClassSelectionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ClassSelectionViewModel classSelectionViewModel =
                new ViewModelProvider(this).get(ClassSelectionViewModel.class);

        binding = FragmentClassSelectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textClassSelection;
        classSelectionViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}