package com.adedom.teg.presentation.room

import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.websocket.RoomListSocket
import kotlinx.coroutines.launch

class RoomViewModel(
    private val repository: DefaultTegRepository,
) : BaseViewModel<RoomViewState>(RoomViewState()) {

    fun fetchRooms() {
        launch {
            setState { copy(loading = true) }

            when (val resource = repository.callFetchRooms()) {
                is Resource.Success -> setState { copy(rooms = resource.data.rooms) }
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

    fun incomingRoom() {
        launch {
            repository.incomingRoom { roomListSocket ->
                setState { copy(peopleAll = roomListSocket.peopleAll ?: 0) }
            }
        }
    }

    fun outgoingRoom() {
        launch {
            val request = RoomListSocket()
            repository.outgoingRoom(request)
        }
    }

}