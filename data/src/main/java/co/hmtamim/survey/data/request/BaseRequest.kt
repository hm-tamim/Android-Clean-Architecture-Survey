package co.hmtamim.survey.data.request

import co.hmtamim.survey.data.BuildConfig
import com.squareup.moshi.Json

abstract class BaseRequest(
    @property:Json(name = "client_id")
    var clientId: String = BuildConfig.CLIENT_ID,

    @property:Json(name = "client_secret")
    var clientSecret: String = BuildConfig.CLIENT_SECRET
)