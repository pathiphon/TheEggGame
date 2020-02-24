package com.adedom.theegggame.ui.single

import android.location.Location
import com.adedom.library.extension.readPrefFile
import com.adedom.library.extension.writePrefFile
import com.adedom.library.util.GoogleMapActivity
import com.adedom.library.util.GoogleMapActivity.Companion.sLatLng
import com.adedom.library.util.KEY_EMPTY
import com.adedom.theegggame.data.models.Single
import com.adedom.theegggame.util.*
import com.adedom.theegggame.util.extension.playSoundKeep
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.Marker

class SingleActivityViewModel : BaseViewModel() {

    private val single by lazy { arrayListOf<Single>() }
    private val markerItems by lazy { arrayListOf<Marker>() }
    var switchItem = GameSwitch.ON

    var itemBonus: Int = 0

    fun keepItemSingle(
        playerId: String?,
        itemId: Int,
        qty: Int,
        latitude: Double,
        longitude: Double
    ) = singleRepository.insertItemCollection(playerId, itemId, qty, latitude, longitude)

    fun fetchBackpack(playerId: String?) = singleRepository.fetchBackpack(playerId)

    fun checkRadius(insertItem: (Int) -> Unit) {
        single.forEachIndexed { index, item ->
            val distance = FloatArray(1)
            Location.distanceBetween(
                sLatLng.latitude,
                sLatLng.longitude,
                item.latitude,
                item.longitude,
                distance
            )

            if (distance[0] < RADIUS_ONE_HUNDRED_METER) {
                insertItem.invoke(index)
                if (item.itemId == ITEM_EGG_NORMAL) itemBonus++ // Bonus
                markerItems[index].remove()
                single.removeAt(index)
                switchItem = GameSwitch.ON
                GoogleMapActivity.sContext.playSoundKeep() // sound
                return
            }

            if (distance[0] > RADIUS_TWO_KILOMETER) {
                switchItem = GameSwitch.ON
                single.removeAt(index)
                return
            }
        }
    }

    fun rndMultiItem() {
        if (single.size < MIN_ITEM) {
            val numItem = (MIN_ITEM..MAX_ITEM).random()
            for (i in 0 until numItem) {
                val (lat, lng) = rndLatLng(sLatLng)
                val item = Single((1..3).random(), lat, lng)
                single.add(item)
            }
        }
    }

    fun itemMessages(itemId: Int, values: Int): String {
        return when (itemId) {
            1 -> "Experience point : $values"
            2 -> "Egg I : $values" // egg false
            3 -> "Egg II : $values" // radius
            4 -> "Egg III : $values" // stun
            else -> KEY_EMPTY
        }
    }

    fun getItemValues(i: Int): Pair<Int, Int> {
        var myItem = single[i].itemId // item Id
        var values = (Math.random() * 100).toInt() + 20 // number values && minimum 20

        val timeStart = timeStamp
        val timeNow = System.currentTimeMillis() / 1000
        if (timeNow > timeStart + TIME_FIVE_MINUTE.toLong()) values *= 2 // Multiply 2

        when (myItem) {
            2 -> { // mystery box
                myItem = (1..2).random() // random exp and item*/
                values += (1..20).random() // mystery box + 20 point.
                if (myItem == 1) {
                    itemBonus++
                } else {
                    myItem = (2..4).random() // random item
                    values = 1
                }
            }
            3 -> { // item
                myItem = (2..4).random()
                values = 1
            }
            4 -> {// Bonus
                myItem = 1
                values = (300..399).random()
            }
        }
        return Pair(myItem, values)
    }

    fun rndItemBonus() {
        if (itemBonus % 3 == 0 && itemBonus != 0) {
            itemBonus = 0
            for (i in 1..MAX_ITEM) {
                val (lat, lng) = rndLatLng(sLatLng)
                val item = Single(4, lat, lng)
                single.add(item)
            }
            switchItem = GameSwitch.ON

            if (GoogleMapActivity.sContext.readPrefFile(KEY_MISSION_SINGLE_GAME) == KEY_MISSION_UNSUCCESSFUL) {
                GoogleMapActivity.sContext.writePrefFile(
                    KEY_MISSION_SINGLE_GAME,
                    KEY_MISSION_SUCCESSFUL
                )
            }
        }
    }

    fun checkItem(item: (ArrayList<Single>, ArrayList<Marker>) -> Unit) {
        if (switchItem == GameSwitch.ON) {
            switchItem = GameSwitch.OFF
            item.invoke(single, markerItems)
        }
    }

    fun createBot(bot: () -> Unit) {
        val bonus = single.filter { it.itemId == 4 }
        if (bonus.count() > 1) bot.invoke()
    }

    companion object {
        var markerPlayer: Marker? = null
        var markerBot: Marker? = null
        var circlePlayer: Circle? = null
    }
}

