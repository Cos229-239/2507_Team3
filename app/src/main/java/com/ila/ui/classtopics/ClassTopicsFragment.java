package com.ila.ui.classtopics;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ila.databinding.FragmentClassTopicsBinding;
import com.ila.ui.lessons.LessonDetailActivity;

public class ClassTopicsFragment extends Fragment {

    private FragmentClassTopicsBinding binding;

    private final String[] mathTopics = {
            "Addition", "Subtraction", "Multiplication", "Division"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentClassTopicsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String subject = requireArguments().getString("subject_name", "Unknown");
        binding.subjectTitle.setText(subject);

        String[] topics;
        switch (subject) {
            case "Math":
                topics = mathTopics;
                break;
            default:
                topics = new String[]{"No topics available"};
                break;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                topics
        );

        binding.topicsList.setAdapter(adapter);

        binding.topicsList.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedTopic = topics[position];
            Bundle bundle = new Bundle();
            bundle.putString("lesson name", selectedTopic);

            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_classTopicsFragment_to_mathLessonDetailFragment, bundle);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}