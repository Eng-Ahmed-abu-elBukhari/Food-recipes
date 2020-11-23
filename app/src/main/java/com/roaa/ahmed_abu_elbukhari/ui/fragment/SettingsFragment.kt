package com.roaa.ahmed_abu_elbukhari.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.navGraphViewModels
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.roaa.ahmed_abu_elbukhari.R
import com.roaa.ahmed_abu_elbukhari.data.utils.Constant
import com.roaa.ahmed_abu_elbukhari.ui.viewmodel.MainViewModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val settingViewModel:MainViewModel by navGraphViewModels(R.id.nav_graph)
    private lateinit var mSharedPreferencesArea: SharedPreferences
    private lateinit var sharedAreaEditor:SharedPreferences.Editor


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey) }


    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSharedPreferencesArea = requireContext().getSharedPreferences(Constant.AREA_SHARED_PREFERENCE_KEY,0)
        sharedAreaEditor = mSharedPreferencesArea.edit()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listPreferencesSetting()
    }


    private fun listPreferencesSetting(){
        val mListPreferences = preferenceManager.
        findPreference<Preference>("area") as ListPreference

        mListPreferences.onPreferenceChangeListener = Preference.
        OnPreferenceChangeListener { _, newValue ->
            applyNewDataToSharedPref(newValue = newValue.toString())
            updateMealArea(newArea = newValue.toString())
            true
        }


    }

    private fun updateMealArea(newArea:String){
        settingViewModel.updateMealSearchArea(newArea = newArea)
    }


    private fun applyNewDataToSharedPref(newValue:String){
        sharedAreaEditor.putString(Constant.AREA_VALUE,newValue).apply()
    }
}






