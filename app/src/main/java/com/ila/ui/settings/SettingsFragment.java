package com.ila.ui.settings;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.media.MediaPlayer;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ila.databinding.FragmentSettingsBinding;
import com.ila.R;
public class SettingsFragment extends Fragment {

    MediaPlayer mediaPlayer;
    MediaPlayer mediaPlaceHolder;
    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel SettingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        mediaPlayer = MediaPlayer.create(requireActivity(),R.raw.button_knock);
        mediaPlaceHolder = MediaPlayer.create(requireActivity(),R.raw.placeholder);
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSettings;
        SettingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        Button AndButton = binding.buttonDarkMode;
        AndButton.setOnClickListener(v -> ButtonClicked(0));
        return root;
    }

    public void ButtonClicked(int i) {
        mediaPlayer.start();
        switch(i){
        case 0:
            Context context = this.getContext();
            if(context == null) return;
            if(isNightMode(context))
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            requireActivity().recreate();
            break;
            case 1:
                break;
        default:
            mediaPlaceHolder.start();
            break;
    }
        mediaPlayer.stop();
    }
    public boolean isNightMode(Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }
    @Override
    public void onDestroyView() {
        mediaPlaceHolder.release();
        mediaPlayer.release();
        super.onDestroyView();
        binding = null;
    }
}