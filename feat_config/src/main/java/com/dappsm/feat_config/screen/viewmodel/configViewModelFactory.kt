package com.dappsm.feat_config.screen.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dappsm.feat_config.isdarkmode.AppDatabase
import com.dappsm.feat_config.isdarkmode.SettingsRepository
import com.dappsm.feat_config.viewmodel.ConfigViewModel

class ConfigViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = AppDatabase.getInstance(context).settingsDao()
        val repo = SettingsRepository(dao)
        return ConfigViewModel(repo) as T
    }
}
