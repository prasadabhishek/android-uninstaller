package com.bulkuninstall.noads.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bulkuninstall.noads.data.model.SortType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val SHOW_SYSTEM_APPS = booleanPreferencesKey("show_system_apps")
    private val DEFAULT_SORT = stringPreferencesKey("default_sort")

    val showSystemApps: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[SHOW_SYSTEM_APPS] ?: false
    }

    val defaultSort: Flow<SortType> = context.dataStore.data.map { preferences ->
        val sortName = preferences[DEFAULT_SORT] ?: SortType.NAME.name
        SortType.valueOf(sortName)
    }

    suspend fun updateShowSystemApps(show: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SHOW_SYSTEM_APPS] = show
        }
    }

    suspend fun updateDefaultSort(sortType: SortType) {
        context.dataStore.edit { preferences ->
            preferences[DEFAULT_SORT] = sortType.name
        }
    }
}
