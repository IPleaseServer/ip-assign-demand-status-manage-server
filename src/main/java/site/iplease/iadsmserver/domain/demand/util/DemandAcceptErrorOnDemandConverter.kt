package site.iplease.iadsmserver.domain.demand.util

import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandAcceptErrorOnDemandDto
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandAcceptErrorOnDemandMessage

interface DemandAcceptErrorOnDemandConverter {
    fun convert(message: IpAssignDemandAcceptErrorOnDemandMessage): Mono<DemandAcceptErrorOnDemandDto>
}
