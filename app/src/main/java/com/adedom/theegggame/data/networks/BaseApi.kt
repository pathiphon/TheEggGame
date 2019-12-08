package com.adedom.theegggame.data.networks

import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.utility.VALUES1
import com.adedom.utility.VALUES2
import com.adedom.utility.VALUES3
import com.adedom.utility.VALUES4
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BaseApi {

    @FormUrlEncoded
    @POST("insert-logs.php")
    fun insertLogs(
        @Field(VALUES1) randomKey: String,
        @Field(VALUES2) dateIn: String,
        @Field(VALUES3) timeIn: String,
        @Field(VALUES4) playerId: String
    ): Call<JsonResponse>

    companion object {
        operator fun invoke(): BaseApi {
            return RetrofitClient.instance()
                .create(BaseApi::class.java)
        }
    }
}