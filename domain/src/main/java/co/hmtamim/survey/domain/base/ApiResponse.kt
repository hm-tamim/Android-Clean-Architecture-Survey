package co.hmtamim.survey.domain.base

sealed class ApiResponse<T> {
    data class Success<T>(var data: T) : ApiResponse<T>()
    data class Failure<T>(val message: String, val code: Int) : ApiResponse<T>()
    companion object {
        fun <T> success(data: T): ApiResponse<T> = Success(data)
        fun <T> failure(message: String = "", code: Int = 0): ApiResponse<T> =
            Failure(message, code)
    }
}