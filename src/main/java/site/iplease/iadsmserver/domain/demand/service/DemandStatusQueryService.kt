package site.iplease.iadsmserver.domain.demand.service

import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandStatusDto

interface DemandStatusQueryService {
    fun getStatusByDemandId(demandId: Long): Mono<DemandStatusDto>

}
