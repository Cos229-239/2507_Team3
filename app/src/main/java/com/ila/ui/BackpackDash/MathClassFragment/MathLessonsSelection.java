package com.ila.ui.BackpackDash.MathClassFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ila.R;
import com.ila.databinding.FragmentMathLessonsSelectionBinding;

public class MathLessonsSelection extends Fragment {

    private MathLessonsDetailsViewModel mViewModel;
    private FragmentMathLessonsSelectionBinding binding;

    public static MathLessonsSelection newInstance() {
        return new MathLessonsSelection();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMathLessonsSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}