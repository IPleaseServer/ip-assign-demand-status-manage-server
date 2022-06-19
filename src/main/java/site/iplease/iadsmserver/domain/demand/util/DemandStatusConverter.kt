package site.iplease.iadsmserver.domain.demand.util

import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandDto
import site.iplease.iadsmserver.domain.demand.data.dto.DemandStatusDto
import site.iplease.iadsmserver.domain.demand.data.entity.DemandStatus
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCancelErrorOnStatusMessage
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCancelMessage
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCreateMessage
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandRejectMessage

interface DemandStatusConverter {
    fun toDto(demandId: Long): Mono<DemandStatusDto>
    fun toDto(demand: DemandDto): Mono<DemandStatusDto>
    fun toDto(entity: DemandStatus): Mono<DemandStatusDto>
    fun toDto(message: IpAssignDemandCreateMessage): Mono<DemandStatusDto>

    fun toEntity(demand: DemandStatusDto): Mono<DemandStatus>

    fun toErrorMessage(message: IpAssignDemandCancelMessage, error: Throwable):  Mono<IpAssignDemandCancelErrorOnStatusMessage>
    fun toRejectMessage(demandStatus: DemandStatusDto, reason: String, issuerId: Long): Mono<IpAssignDemandRejectMessage>
}
