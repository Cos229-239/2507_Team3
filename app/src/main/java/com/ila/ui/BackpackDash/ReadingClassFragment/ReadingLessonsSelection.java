package com.ila.ui.BackpackDash.ReadingClassFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ila.ui.BackpackDash.MathClassFragment.MathLessonsDetailsViewModel;

public class ReadingLessonsSelection extends Fragment {

    private MathLessonsDetailsViewModel mViewModel;
    private FragmentMathlessonDetailBinding binding;

    public static ReadingLessonsSelection newInstance() {
        return new ReadingLessonsSelection();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMathLessonDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        String lessonName = requireArguments().getString("lesson_name", "Unknown Lesson");
        binding.lessonTitle.setText(lessonName);

        //possibly adding lesson content here
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}