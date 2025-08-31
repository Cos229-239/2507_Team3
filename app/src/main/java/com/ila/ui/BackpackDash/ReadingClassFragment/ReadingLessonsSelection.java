package com.ila.ui.BackpackDash.ReadingClassFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ila.R;
import com.ila.databinding.FragmentReadingLessonsSelectionBinding;

public class ReadingLessonsSelection extends Fragment {

    private ReadingLessonsDetailsViewModel mViewModel;
    private FragmentReadingLessonsSelectionBinding binding;

    public static ReadingLessonsSelection newInstance() {
        return new ReadingLessonsSelection();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentReadingLessonsSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        //possibly adding lesson content here
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}