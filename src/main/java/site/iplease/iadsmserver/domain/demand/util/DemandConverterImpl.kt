package site.iplease.iadsmserver.domain.demand.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandDto
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCancelMessage

@Component
class DemandConverterImpl: DemandConverter {
    override fun toDto(message: IpAssignDemandCancelMessage): Mono<DemandDto> =
        message.toMono().map { DemandDto(
            id = message.id,
            issuerId = message.issuerId,
            title = message.title,
            description = message.description,
            usage = message.usage,
            expireAt = message.expireAt
        ) }
}