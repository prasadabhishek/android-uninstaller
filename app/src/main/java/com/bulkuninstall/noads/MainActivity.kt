package com.bulkuninstall.noads

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.bulkuninstall.noads.ui.MainScreen
import com.bulkuninstall.noads.ui.MainViewModel
import com.bulkuninstall.noads.ui.theme.BulkUninstallerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BulkUninstallerTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkAccessibilityService()
        viewModel.checkUsageAccess()
    }
}
