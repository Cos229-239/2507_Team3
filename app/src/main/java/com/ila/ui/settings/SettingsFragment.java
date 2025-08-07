package com.ila.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.media.MediaPlayer;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.speech.tts.TextToSpeech;
import com.ila.databinding.FragmentSettingsBinding;
import com.ila.R;
import com.ila.settings.SettingsHandler;

import java.util.Locale;

public class SettingsFragment extends Fragment{

    MediaPlayer mediaPlayer;
    MediaPlayer mediaPlaceHolder;
    private TextToSpeech tts;
    private FragmentSettingsBinding binding;
    private SettingsHandler settingsHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel SettingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        settingsHandler = new SettingsHandler(this.getContext());
        mediaPlayer = MediaPlayer.create(requireActivity(),R.raw.button_knock);
        mediaPlaceHolder = MediaPlayer.create(requireActivity(),R.raw.placeholder);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textSettings;
        SettingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        Button AndButton = binding.buttonDarkMode;
        AndButton.setOnClickListener(v -> ButtonClicked(0));
        tts = new TextToSpeech(requireContext().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS)
                {
                    tts.setLanguage(Locale.US);
                }
                else {
                    mediaPlaceHolder.start();
                }
            }
        });
        return root;
    }
    public void ButtonClicked(int i) {
        mediaPlayer.start();
        switch(i){
        case 0:
            settingsHandler.setNightMode();
            break;
            case 1:
                break;
        default:
            mediaPlaceHolder.start();
            break;
    }
        mediaPlayer.stop();
    }

    @Override
    public void onDestroyView() {
        mediaPlaceHolder.release();
        mediaPlayer.release();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroyView();
        binding = null;
    }
}