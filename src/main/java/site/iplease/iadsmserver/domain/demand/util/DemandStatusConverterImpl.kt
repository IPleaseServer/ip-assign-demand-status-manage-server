package site.iplease.iadsmserver.domain.demand.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandDto
import site.iplease.iadsmserver.domain.demand.data.dto.DemandStatusDto
import site.iplease.iadsmserver.domain.demand.data.entity.DemandStatus
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCreateMessage

@Component
class DemandStatusConverterImpl: DemandStatusConverter {
    override fun toDto(demand: DemandDto): Mono<DemandStatusDto> =
        demand.toMono()
            .map { DemandStatusDto(demandId = demand.id) }

    override fun toDto(message: IpAssignDemandCreateMessage) =
        message.toMono()
            .map { DemandStatusDto(
                demandId = it.demandId
            ) }

    override fun toDto(entity: DemandStatus) =
        entity.toMono()
            .map { DemandStatusDto(
                id = it.id,
                demandId = it.demandId,
                status = it.status
            ) }

    override fun toEntity(demand: DemandStatusDto): Mono<DemandStatus> =
        demand.toMono()
            .map { DemandStatus(
                id = it.id,
                demandId = it.demandId,
                status = it.status
            ) }
}