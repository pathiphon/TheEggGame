package com.adedom.teg.domain.usecase

import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.MultiItemCollectionRequest
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.presentation.usercase.MultiUseCase

class MultiUseCaseImpl(
    private val repository: DefaultTegRepository,
) : MultiUseCase {

    override suspend fun callMultiItemCollection(multiItemCollectionRequest: MultiItemCollectionRequest): Resource<BaseResponse> {
        val resource = repository.callMultiItemCollection(multiItemCollectionRequest)

        when (resource) {
            is Resource.Success -> {
                if (resource.data.success) {
                    fetchPlayerInfo()
                }
            }
        }

        return resource
    }

    private suspend fun fetchPlayerInfo() {
        when (val resource = repository.callFetchPlayerInfo()) {
            is Resource.Success -> {
                if (resource.data.success) {
                    val playerInfo = resource.data.playerInfo
                    val playerInfoEntity = PlayerInfoEntity(
                        playerId = playerInfo?.playerId.orEmpty(),
                        username = playerInfo?.username,
                        name = playerInfo?.name,
                        image = playerInfo?.image,
                        level = playerInfo?.level,
                        state = playerInfo?.state,
                        gender = playerInfo?.gender,
                        birthDate = playerInfo?.birthDate,
                    )
                    repository.savePlayerInfo(playerInfoEntity)
                }
            }
            is Resource.Error -> fetchPlayerInfo()
        }
    }

    override suspend fun callLeaveRoomInfo(): Resource<BaseResponse> {
        val resource = repository.callLeaveRoomInfo()

        when (resource) {
            is Resource.Success -> {

            }
        }

        return resource
    }

}
