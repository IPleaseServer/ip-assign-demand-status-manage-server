package site.iplease.iadsmserver.domain.demand.util

import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandDto
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCancelMessage

interface DemandConverter {
    fun toDto(message: IpAssignDemandCancelMessage): Mono<DemandDto>
}
