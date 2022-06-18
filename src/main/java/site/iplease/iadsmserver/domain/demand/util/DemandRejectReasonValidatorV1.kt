package site.iplease.iadsmserver.domain.demand.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class DemandRejectReasonValidatorV1: DemandRejectReasonValidator {
    override fun validate(reason: String): Mono<Unit> = Unit.toMono()//아직 Validation할 내용이 없다.
}