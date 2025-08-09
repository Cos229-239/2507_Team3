package com.ila.LoginUi.Identifyer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.ila.ButtonSound.buttonSound;
import com.ila.R;
import com.ila.databinding.FragmentIdentifyerBinding;

    public class IdentifyerFragment extends Fragment {

        private FragmentIdentifyerBinding binding;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {

            binding = FragmentIdentifyerBinding.inflate(inflater, container, false);
            ImageButton studentButton = binding.studentButton;
            ImageButton teacherButton = binding.TeacherButton;
            studentButton.setOnClickListener(v->ButtonClicked());
            teacherButton.setOnClickListener(v->ButtonClicked());
            return binding.getRoot();
        }
        public void ButtonClicked() {
            buttonSound.playButtonSound(this.getContext());
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.action_to_Home);
        }
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
    }
