package com.adedom.theegggame.ui.multi.multi

import com.adedom.theegggame.util.BaseViewModel

class MultiActivityViewModel : BaseViewModel() {

    fun setLatlng(roomNo: String, playerId: String, latitude: Double, longitude: Double) =
        multiRepository.setLatlng(roomNo, playerId, latitude, longitude)

    fun getRoomInfo(roomNo: String) = multiRepository.getRoomInfo(roomNo)

    fun getMulti(roomNo: String) = multiRepository.getMulti(roomNo)

    fun insertMulti(roomNo: String, latitude: Double, longitude: Double) =
        multiRepository.insertMulti(roomNo, latitude, longitude)

    fun insertMultiCollection(
        multiId: String,
        roomNo: String,
        playerId: String,
        team: String,
        latitude: Double,
        longitude: Double,
        date: String,
        time: String
    ) = multiRepository.insertMultiCollection(
        multiId,
        roomNo,
        playerId,
        team,
        latitude,
        longitude,
        date,
        time
    )

    fun getMultiScore(roomNo: String) = multiRepository.getMultiScore(roomNo)

}
