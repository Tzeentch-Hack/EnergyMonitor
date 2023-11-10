package com.tzeentch.energy_saver.repositories

import com.tzeentch.energy_saver.local.Constants
import com.tzeentch.energy_saver.remote.NetworkResultState
import com.tzeentch.energy_saver.remote.dto.AuthResultDto
import com.tzeentch.energy_saver.remote.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Parameters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AuthRepository constructor(private val httpClient: HttpClient) {

    suspend fun loginUser(
        user: String,
        password: String,
        ip:String
    ): Flow<NetworkResultState<AuthResultDto>> {
        return flowOf(
            safeApiCall {
                httpClient.post(urlString = Constants.BASE_URL + ip + Constants.LOGIN_USER) {
                    setBody(
                        FormDataContent(Parameters.build {
                            append("username", user)
                            append("password", password)
                        })
                    )
                }.body()
            }
        )
    }

    suspend fun registerUser(
        user: String,
        password: String,
        ip:String
    ): Flow<NetworkResultState<AuthResultDto>> {
        return flowOf(
            safeApiCall {
                httpClient.post(urlString = Constants.BASE_URL + ip + Constants.REGISTER_USER) {
                    setBody(
                        FormDataContent(Parameters.build {
                            append("username", user)
                            append("password", password)
                        })
                    )
                }.body()
            }
        )

    }
}