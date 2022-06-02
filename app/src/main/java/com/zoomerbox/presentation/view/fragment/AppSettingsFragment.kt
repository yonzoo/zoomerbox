package com.zoomerbox.presentation.view.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.zoomerbox.R
import com.zoomerbox.ZoomerboxApplication
import com.zoomerbox.di.fragment.FragmentComponent
import com.zoomerbox.presentation.viewmodel.SettingsViewModel
import com.zoomerbox.presentation.viewmodel.SettingsViewModelFactory
import javax.inject.Inject

/**
 * Экран настроек приложения
 */
class AppSettingsFragment : PreferenceFragmentCompat() {

    private lateinit var viewModel: SettingsViewModel

    @Inject
    lateinit var viewModelFactory: SettingsViewModelFactory

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        provideDependencies()
        createViewModel()

        val button: Preference? = findPreference(getString(R.string.clear_cache_key))
        button?.setOnPreferenceClickListener {
            viewModel.clearCache()
            true
        }

        viewModel.getResultLiveData().observe(this) {
            if (it) {
                Toast.makeText(context, "Кэш очищен!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun provideDependencies() {
        val fragmentComponent: FragmentComponent =
            ZoomerboxApplication.getAppComponent(requireContext()).getFragmentComponent()
        fragmentComponent.inject(this)
    }

    private fun createViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)
    }
}
