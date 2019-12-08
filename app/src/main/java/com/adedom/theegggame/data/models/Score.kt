package com.adedom.theegggame.data.models

import com.adedom.utility.VALUES1
import com.adedom.utility.VALUES2
import com.google.gson.annotations.SerializedName

data class Score(
    @SerializedName(VALUES1) val teamA: Int,
    @SerializedName(VALUES2) val teamB: Int
)