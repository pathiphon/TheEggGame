package com.adedom.theegggame.data.repositories

import com.adedom.library.data.ApiRequest
import com.adedom.theegggame.data.networks.SingleApi

class SingleRepository(private val api: SingleApi) : ApiRequest() {

    fun insertItemCollection(
        playerId: String,
        itemId: Int,
        qty: Int,
        latitude: Double,
        longitude: Double,
        date: String,
        time: String
    ) = apiRequest {
        api.insertItemCollection(
            playerId,
            itemId,
            qty,
            latitude,
            longitude,
            date,
            time
        )
    }

}