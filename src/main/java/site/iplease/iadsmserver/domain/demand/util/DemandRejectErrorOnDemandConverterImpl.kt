package site.iplease.iadsmserver.domain.demand.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandRejectErrorOnDemandDto
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandRejectErrorOnDemandMessage

@Component
class DemandRejectErrorOnDemandConverterImpl: DemandRejectErrorOnDemandConverter {
    override fun convert(message: IpAssignDemandRejectErrorOnDemandMessage): Mono<DemandRejectErrorOnDemandDto> =
        message.toMono()
            .map { DemandRejectErrorOnDemandDto(
                demandId = message.demandId,
                issuerId = message.issuerId,
                originStatus = message.originStatus,
                message = message.message
            ) }
}