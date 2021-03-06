package site.iplease.iadsmserver.domain.demand.service

import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandDto
import site.iplease.iadsmserver.domain.demand.data.dto.DemandStatusDto

interface DemandStatusService {
    fun cancelDemand(demand: DemandDto): Mono<Unit>
    fun createDemand(demandStatus: DemandStatusDto): Mono<DemandStatusDto>
    fun confirmDemand(demandId: Long): Mono<DemandStatusDto>
    fun rejectDemand(demandId: Long, reason: String): Mono<DemandStatusDto>
    fun acceptDemand(demandId: Long, assignIp: String): Mono<DemandStatusDto>
}
