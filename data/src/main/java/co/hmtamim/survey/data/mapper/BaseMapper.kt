package co.hmtamim.survey.data.mapper

import co.hmtamim.survey.domain.base.ApiResponse

interface Mapper<R, E> {
    fun mapFromApiResponse(source: R): E
}

fun <R, E> mapFromResponseToEntity(
    apiResponse: ApiResponse<R>,
    mapper: Mapper<R, E>
): ApiResponse<E> {
    return when (apiResponse) {
        is ApiResponse.Success -> ApiResponse.success(mapper.mapFromApiResponse(apiResponse.data))
        is ApiResponse.Failure -> ApiResponse.failure(apiResponse.message, apiResponse.code)
    }
}
