package com.bulkuninstall.noads.domain.usecase

import com.bulkuninstall.noads.data.AppRepository
import com.bulkuninstall.noads.data.model.AppInfo
import javax.inject.Inject

class GetInstalledAppsUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): List<AppInfo> {
        return repository.getInstalledApps()
    }
}
