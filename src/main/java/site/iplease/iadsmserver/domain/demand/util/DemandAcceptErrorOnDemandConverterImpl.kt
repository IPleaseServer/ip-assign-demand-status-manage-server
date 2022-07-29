package site.iplease.iadsmserver.domain.demand.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandAcceptErrorOnDemandDto
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandAcceptErrorOnDemandMessage

@Component
class DemandAcceptErrorOnDemandConverterImpl: DemandAcceptErrorOnDemandConverter {
    override fun convert(message: IpAssignDemandAcceptErrorOnDemandMessage): Mono<DemandAcceptErrorOnDemandDto> =
        message.toMono()
            .map { DemandAcceptErrorOnDemandDto(
                issuerId = message.issuerId,
                demandId = message.demandId,
                assignIp = message.assignIp,
                originStatus = message.originStatus,
                message = message.message
            ) }
}