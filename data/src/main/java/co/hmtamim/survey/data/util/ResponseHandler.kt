package co.hmtamim.survey.data.util

import co.hmtamim.survey.data.R
import co.hmtamim.survey.data.resource.ResourcesProvider
import co.hmtamim.survey.data.response.ErrorResponse
import co.hmtamim.survey.domain.base.ApiResponse
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.BufferedSource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class ResponseHandler @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
    val moshi: Moshi
) {
    suspend fun <T> performApiCall(callApi: suspend () -> Response<T>): ApiResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = callApi()
                if (response.isSuccessful) {
                    ApiResponse.Success(response.body()!!)
                } else {
                    val errorResponse =
                        parseErrorMessage(response.errorBody()?.source())
                    ApiResponse.Failure(errorResponse, response.code())
                }
            } catch (e: HttpException) {
                ApiResponse.Failure(
                    e.message ?: resourcesProvider.getString(R.string.error_generic), e.code()
                )
            } catch (e: UnknownHostException) {
                ApiResponse.Failure(resourcesProvider.getString(R.string.error_wifi_data), 0)
            } catch (e: IOException) {
                ApiResponse.Failure(
                    resourcesProvider.getString(R.string.error_internet_connection),
                    0
                )
            } catch (e: Exception) {
                ApiResponse.Failure(resourcesProvider.getString(R.string.error_generic), 0)
            }
        }
    }

    fun parseErrorMessage(source: BufferedSource?): String {
        var errorMessage = "Something went wrong"
        runCatching {
            val jsonAdapter = moshi.adapter(ErrorResponse::class.java).lenient()
            val json = source?.let { jsonAdapter.fromJson(it) }
            json?.errors?.get(0)?.detail?.let { errorMessage = it }
        }.onFailure {
            it.printStackTrace()
        }
        return errorMessage
    }

}