package com.bulkuninstall.noads.domain.usecase

import com.bulkuninstall.noads.data.AppRepository
import javax.inject.Inject

class UninstallAppsUseCase @Inject constructor(
    private val repository: AppRepository
) {
    // In Android, uninstalls are usually handled by the UI layer via Intents.
    // This usecase could provide helper logic or validation if needed.
    // For now, we'll just keep it as a placeholder or use it to get package names.
}
