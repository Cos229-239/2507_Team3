package com.ila.LoginUi.Identifyer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ila.R;
import com.ila.databinding.FragmentIdentifyerBinding;

    public class IdentifyerFragment extends Fragment {
        private MediaPlayer mediaPlayer;

        private FragmentIdentifyerBinding binding;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {

            binding = FragmentIdentifyerBinding.inflate(inflater, container, false);
            mediaPlayer = MediaPlayer.create(requireActivity(),R.raw.button_knock);
            ImageButton studentButton = binding.studentButton;
            ImageButton teacherButton = binding.TeacherButton;
            studentButton.setOnClickListener(v->ButtonClicked());
            teacherButton.setOnClickListener(v->ButtonClicked());
            return binding.getRoot();
        }
        public void ButtonClicked() {
            mediaPlayer.start();
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.action_to_Home);
            mediaPlayer.stop();
        }
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
            mediaPlayer.release();
        }
    }
