package site.iplease.iadsmserver.domain.demand.util

import reactor.core.publisher.Mono

interface DemandRejectReasonValidator {
    fun validate(reason: String): Mono<Unit>
}
