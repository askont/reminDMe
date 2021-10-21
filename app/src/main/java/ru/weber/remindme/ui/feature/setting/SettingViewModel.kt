package ru.weber.remindme.ui.feature.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.weber.remindme.domain.interactor.SettingInteractor
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingInteractor: SettingInteractor
) : ViewModel() {
    val themeState: Flow<Boolean>
        get() = settingInteractor.getThemeSettingData()

    fun changeTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            settingInteractor.changeTheme(isDarkTheme)
        }
    }
}