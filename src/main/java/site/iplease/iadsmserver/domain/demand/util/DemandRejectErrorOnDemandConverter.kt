package site.iplease.iadsmserver.domain.demand.util

import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandRejectErrorOnDemandDto
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandRejectErrorOnDemandMessage

interface DemandRejectErrorOnDemandConverter {
    fun convert(message: IpAssignDemandRejectErrorOnDemandMessage): Mono<DemandRejectErrorOnDemandDto>
}
