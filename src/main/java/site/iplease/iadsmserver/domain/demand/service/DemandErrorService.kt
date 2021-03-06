package site.iplease.iadsmserver.domain.demand.service

import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandAcceptErrorOnDemandDto
import site.iplease.iadsmserver.domain.demand.data.dto.DemandRejectErrorOnDemandDto

interface DemandErrorService {
    fun handle(demand: DemandRejectErrorOnDemandDto): Mono<Unit>
    fun handle(demand: DemandAcceptErrorOnDemandDto): Mono<Unit>
}
