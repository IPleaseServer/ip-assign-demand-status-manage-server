package site.iplease.iadsmserver.domain.demand.util

import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.dto.DemandStatusDto
import site.iplease.iadsmserver.domain.demand.type.DemandStatusPolicyGroup

interface DemandStatusValidator {
    fun validate(policy: DemandStatusPolicyGroup, demand: DemandStatusDto): Mono<DemandStatusDto>
}
