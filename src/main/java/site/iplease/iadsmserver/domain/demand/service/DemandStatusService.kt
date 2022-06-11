package site.iplease.iadsmserver.domain.demand.service

import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandStatusDto

interface DemandStatusService {
    fun add(demand: DemandStatusDto): Mono<DemandStatusDto>

}
