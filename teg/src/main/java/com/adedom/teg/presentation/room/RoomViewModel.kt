package com.adedom.teg.presentation.room

import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.JoinRoomInfoRequest
import com.adedom.teg.util.TegConstant
import kotlinx.coroutines.launch

class RoomViewModel(
    private val repository: DefaultTegRepository,
) : BaseViewModel<RoomViewState>(RoomViewState()) {

    var listener: JoinRoomInfoListener? = null

    fun incomingRoomPeopleAll() {
        launch {
            repository.incomingRoomPeopleAll { roomPeopleAll ->
                setState { copy(peopleAll = roomPeopleAll.peopleAll) }
            }
            incomingRoomPeopleAll()
        }
    }

    fun incomingPlaygroundRoom() {
        launch {
            repository.incomingPlaygroundRoom { rooms ->
                setState { copy(rooms = rooms.rooms) }
            }
            incomingPlaygroundRoom()
        }
    }

    fun callJoinRoomInfo(roomNo: String?) {
        launch {
            setState { copy(loading = true, isClickable = false) }

            val request = JoinRoomInfoRequest(roomNo)
            when (val resource = repository.callJoinRoomInfo(request)) {
                is Resource.Success -> listener?.onJoinRoomInfoResponse(resource.data)
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false, isClickable = true) }
        }
    }

    fun callChangeCurrentModeMulti() {
        launch {
            when (val resource = repository.callChangeCurrentMode(TegConstant.PLAY_MODE_MULTI)) {
                is Resource.Error -> setError(resource)
            }
        }
    }

}
