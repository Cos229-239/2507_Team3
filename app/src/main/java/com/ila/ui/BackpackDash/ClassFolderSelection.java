package com.ila.ui.BackpackDash;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ila.R;
import com.ila.databinding.FragmentClassfolderselectionBinding;
import com.ila.ui.BackpackDash.MathClassFragment.MathLessonsSelection;
import com.ila.ui.BackpackDash.ReadingClassFragment.ReadingLessonsSelection;
import com.ila.ui.BackpackDash.ScienceClassFragment.ScienceLessonsSelection;

public class ClassFolderSelection extends Fragment {

    private FragmentClassfolderselectionBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentClassfolderselectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        binding.imageView6.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                float x = event.getX();
                float y = event.getY();

                int imageWidth = v.getWidth();
                int imageHeight = v.getHeight();

                if (x > 100 && x < 300 && y > 300 & y < 450) {
                    startActivity(new Intent(requireContext(), MathLessonsSelection.class));
                } else if (x > 310 && x < 510 && y > 300 && y < 450) {
                    startActivity(new Intent(requireContext(), ReadingLessonsSelection.class));
                } else if (x > 520 && x < 720 && y > 300 && y < 450){
                    startActivity(new Intent(requireContext(), ScienceLessonsSelection.class));
                }
            }
            return true;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            View blurOverlay = binding.blurOverlay;
            blurOverlay.setRenderEffect(
                    RenderEffect.createBlurEffect(25f, 25f, Shader.TileMode.CLAMP)
            );
            blurOverlay.setBackgroundColor(Color.parseColor("#AA000000"));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}