package com.tzeentch.energy_saver.repositories

import com.tzeentch.energy_saver.local.Constants
import com.tzeentch.energy_saver.remote.NetworkResultState
import com.tzeentch.energy_saver.remote.dto.DeviceDto
import com.tzeentch.energy_saver.remote.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Parameters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MainRepository constructor(
    private val httpClient: HttpClient
) {
    suspend fun getDevices(
        token: String,
        ip: String
    ): Flow<NetworkResultState<List<DeviceDto>>> {
        return flowOf(
            safeApiCall {
                httpClient.get(urlString = Constants.BASE_URL + ip + Constants.GET_DEVICE_STATISTIC) {
                    header("Authorization", "Bearer $token")
                }.body()
            }
        )

    }

    suspend fun disableDevices(
        token: String,
        ip: String,
        id: String
    ): Flow<NetworkResultState<Any>> {
        return flowOf(
            safeApiCall {
                httpClient.post(urlString = Constants.BASE_URL + ip + Constants.DISABLE_DEVICE) {
                    parameter("search_params", id)
                    header("Authorization", "Bearer $token")
                }.body()
            }
        )

    }
}