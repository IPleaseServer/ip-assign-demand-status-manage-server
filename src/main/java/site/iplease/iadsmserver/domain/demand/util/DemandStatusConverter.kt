package site.iplease.iadsmserver.domain.demand.util

import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.dto.DemandStatusDto
import site.iplease.iadsmserver.domain.demand.entity.DemandStatus
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCreateMessage

interface DemandStatusConverter {
    fun toDto(message: IpAssignDemandCreateMessage): Mono<DemandStatusDto>
    fun toDto(entity: DemandStatus): Mono<DemandStatusDto>
    fun toEntity(demand: DemandStatusDto): Mono<DemandStatus>
}
