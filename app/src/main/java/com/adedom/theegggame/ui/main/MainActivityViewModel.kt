package com.adedom.theegggame.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.data.repositories.PlayerRepository

class MainActivityViewModel(private val repository: PlayerRepository) : ViewModel() { // 2/12/19

    fun getPlayers(playerId: String): LiveData<Player> {
        return repository.getPlayers(playerId)
    }
}