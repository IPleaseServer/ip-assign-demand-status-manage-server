package site.iplease.iadsmserver.domain.demand.util

import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandStatusDto
import site.iplease.iadsmserver.domain.demand.data.type.DemandStatusPolicyGroup

interface DemandStatusValidator {
    fun validate(policy: DemandStatusPolicyGroup, demand: DemandStatusDto): Mono<DemandStatusDto>
}
