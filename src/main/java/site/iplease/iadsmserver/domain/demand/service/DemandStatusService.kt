package site.iplease.iadsmserver.domain.demand.service

import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandDto
import site.iplease.iadsmserver.domain.demand.data.dto.DemandStatusDto

interface DemandStatusService {
    fun add(demandStatus: DemandStatusDto): Mono<DemandStatusDto>
    fun cancelDemand(demand: DemandDto): Mono<Unit>
}
