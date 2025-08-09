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
import com.ila.Sounds.PlaySounds;
import com.ila.R;
import com.ila.databinding.FragmentIdentifyerBinding;

    public class IdentifyerFragment extends Fragment {

        private FragmentIdentifyerBinding binding;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {

            binding = FragmentIdentifyerBinding.inflate(inflater, container, false);
            ImageButton StudentButton = binding.studentButton;
            ImageButton TeacherButton = binding.teacherButton;
            StudentButton.setOnClickListener(v->ButtonClicked(0));
            TeacherButton.setOnClickListener(v->ButtonClicked(1));
            return binding.getRoot();
        }
        public void ButtonClicked(int i) {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            switch (i)
            {
                case 0 :
                    PlaySounds.playSound(this.getContext(),R.raw.button_knock);
                    navController.navigate(R.id.action_to_Home);
                    break;
                case 1:
                    PlaySounds.playSound(this.getContext(),R.raw.placeholder);
                    navController.navigate(R.id.action_Home_to_Profile);
                    break;
            }

        }
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
    }
