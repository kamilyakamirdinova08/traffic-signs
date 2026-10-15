package com.example.traffic_signs.settings;

import android.app.Activity;
import android.content.Intent; //Колдонмонун компоненттеринин ортосунда маалыматтарды өткөрүү үчүн Intent  классты импорттоо
import android.content.res.Configuration; //Түзмөктүн конфигурация параметрлерине кирүү үчүн Configuration классты импорттоо
import android.content.res.Resources; //Колдонмонун ресурстарына кирүү үчүн  Resources классты импорттоо
import android.os.Bundle; //Компоненттердин ортосунда маалыматтарды өткөрүү жана абалды сактоо үчүн Bundle классты импорттоо
import android.preference.ListPreference; //Колдонмонун жөндөөлөрүнөн артыкчылык тизмесин түзүү үчүн ListPreference классты импорттоо
import android.preference.Preference; //
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;

import com.example.traffic_signs.MainActivity; // Замените MainActivity на ваш класс активности
import com.example.traffic_signs.R;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_screen);

        // Find the language ListPreference
        ListPreference languagePreference = (ListPreference) findPreference("language");
        if (languagePreference != null) {
            languagePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String selectedLang = newValue.toString();
                    setLocal(SettingsFragment.this.getActivity(), selectedLang);
                    restartApp(); // Перезапустить приложение после изменения языка
                    return true;
                }
            });
        }
    }

    public void setLocal(Activity activity, String langCode) {
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    private void restartApp() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}
