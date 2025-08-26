package com.ila.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.speech.tts.TextToSpeech;

import com.ila.R;
import com.ila.databinding.FragmentSettingsBinding;
import com.ila.playSounds.PlaySounds;
import com.ila.settings.SettingsHandler;

import java.util.Locale;

public class SettingsFragment extends Fragment{

    private TextToSpeech tts;
    private FragmentSettingsBinding binding;
    private SettingsHandler settingsHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel SettingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        settingsHandler = new SettingsHandler(this.getContext());

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
                    PlaySounds.getInstance(requireContext()).playSound(R.raw.placeholder);
                }
            }
        });
        return root;
    }
    
    public void ButtonClicked(int i) {
        PlaySounds.getInstance(this.getContext()).playSound(R.raw.button_knock);
        switch(i){
        case 0:
            settingsHandler.setNightMode();
            break;
        default:
            break;
    }
    }

    @Override
    public void onDestroyView() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroyView();
        binding = null;
    }
}