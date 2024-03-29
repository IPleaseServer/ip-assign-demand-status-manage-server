package site.iplease.iadsmserver.domain.demand.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandDto
import site.iplease.iadsmserver.domain.demand.data.dto.DemandStatusDto
import site.iplease.iadsmserver.domain.demand.data.entity.DemandStatus
import site.iplease.iadsmserver.domain.demand.data.response.DemandStatusResponse
import site.iplease.iadsmserver.global.demand.message.*

@Component
class DemandStatusConverterImpl: DemandStatusConverter {
    override fun toDto(demandId: Long): Mono<DemandStatusDto> =
        demandId.toMono()
            .map { DemandStatusDto(demandId = demandId) }

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

    override fun toErrorMessage(message: IpAssignDemandCancelMessage, error: Throwable): Mono<IpAssignDemandCancelErrorOnStatusMessage> =
        message.toMono()
            .map {  IpAssignDemandCancelErrorOnStatusMessage(
                id = it.id,
                issuerId = it.issuerId,
                title = it.title,
                description = it.description,
                usage = it.usage,
                expireAt = it.expireAt,
                message = error.localizedMessage
            ) }

    override fun toRejectMessage(demandStatus: DemandStatusDto, reason: String, issuerId: Long): Mono<IpAssignDemandRejectMessage> =
        demandStatus.toMono().map { IpAssignDemandRejectMessage(
            demandId = it.demandId,
            issuerId = issuerId,
            originStatus = it.status,
            reason = reason
        ) }

    override fun toAcceptMessage(demandStatus: DemandStatusDto, issuerId: Long, assignIp: String): Mono<IpAssignDemandAcceptMessage> =
        demandStatus.toMono().map { IpAssignDemandAcceptMessage(
            demandId = it.demandId,
            issuerId = issuerId,
            originStatus = it.status,
            assignIp = assignIp
        ) }

    override fun toResponse(dto: DemandStatusDto): Mono<DemandStatusResponse> =
        dto.toMono().map { DemandStatusResponse(
            id = dto.id,
            demandId = dto.demandId,
            status = dto.status
        ) }
}