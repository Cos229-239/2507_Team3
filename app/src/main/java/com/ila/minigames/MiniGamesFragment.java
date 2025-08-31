package com.ila.minigames;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ila.R;
import com.ila.databinding.FragmentMinigamesBinding;

public class MiniGamesFragment extends Fragment {

    private FragmentMinigamesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMinigamesBinding.inflate(inflater, container, false);

        ImageButton mergeFishButton = binding.mergeFishButton;
        mergeFishButton.setOnClickListener(v -> ButtonClicked(0));
        return binding.getRoot();
    }
    public void ButtonClicked(int game)
    {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        switch (game)
        {
            case 0 :
                navController.navigate(R.id.to_merge_fish);
                break;
            case 1:
                //TODO: make more games
                break;
            default:
                Toast.makeText(requireContext(),"No such game",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
