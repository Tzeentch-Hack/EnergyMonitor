package com.tzeentch.energy_saver.remote


import com.tzeentch.energy_saver.remote.dto.ErrorResponseDto
import com.tzeentch.energy_saver.remote.dto.toDomain
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import org.json.JSONObject

suspend fun <T : Any?> safeApiCall(apiCall: suspend () -> T): NetworkResultState<T> {
    return try {
        NetworkResultState.Loading
        val result = apiCall.invoke()
        NetworkResultState.Success(result)
    } catch (e: Exception) {
        val error = try {
            JSONObject(e.message.toString()).getString("detail")
        } catch (e: Exception) {
            "UnExpected"
        }
        NetworkResultState.Failure(error)
    }
}

/**Generate [Exception] from network or system error when making network calls
 *
 * @throws [Exception]
 * */
internal suspend fun parseNetworkError(
    errorResponse: HttpResponse? = null,
    exception: Exception? = null
): Exception {
    throw errorResponse?.body<ErrorResponseDto>()?.toDomain() ?: ErrorResponse(
        statusMessage = exception?.message ?: "Error"
    )
}
