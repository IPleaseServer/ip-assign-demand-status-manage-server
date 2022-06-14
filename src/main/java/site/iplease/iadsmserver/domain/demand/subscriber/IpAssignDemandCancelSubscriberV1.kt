package site.iplease.iadsmserver.domain.demand.subscriber

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandDto
import site.iplease.iadsmserver.domain.demand.data.dto.DemandStatusDto
import site.iplease.iadsmserver.domain.demand.data.type.DemandStatusPolicyGroup
import site.iplease.iadsmserver.domain.demand.service.DemandStatusService
import site.iplease.iadsmserver.domain.demand.util.DemandConverter
import site.iplease.iadsmserver.domain.demand.util.DemandStatusConverter
import site.iplease.iadsmserver.domain.demand.util.DemandStatusValidator
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCancelErrorOnStatusMessage
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCancelMessage
import site.iplease.iadsmserver.global.demand.subscriber.IpAssignDemandCancelSubscriber
import site.iplease.iadsmserver.infra.alarm.service.PushAlarmService
import site.iplease.iadsmserver.infra.message.service.MessagePublishService
import site.iplease.iadsmserver.infra.message.type.MessageType

@Component
class IpAssignDemandCancelSubscriberV1(
    private val pushAlarmService: PushAlarmService,
    private val demandConverter: DemandConverter,
    private val demandStatusConverter: DemandStatusConverter,
    private val demandStatusService: DemandStatusService,
    private val messagePublishService: MessagePublishService,
    private val demandStatusValidator: DemandStatusValidator
): IpAssignDemandCancelSubscriber {
    override fun subscribe(message: IpAssignDemandCancelMessage) {
        onStart(message)
            .flatMap { logic(it) }
            .flatMap { demand -> onSuccess(demand) }
            .onErrorResume { throwable -> onError(message, throwable) }
            .block()
    }

    private fun onStart(message: IpAssignDemandCancelMessage): Mono<DemandDto> = convert(message)
    private fun logic(demand: DemandDto): Mono<DemandDto> =
        demandStatusConverter.toDto(demand)
            .flatMap { demandStatus -> validate(demandStatus) }
            .flatMap { demandStatus -> removeDemandStatus(demandStatus) }
            .map { demand }
    private fun onSuccess(demand: DemandDto): Mono<Unit> = sendAlarm(receiverId = demand.issuerId)
    private fun onError(demand: IpAssignDemandCancelMessage, error: Throwable): Mono<Unit> = sendError(error, demand)

    private fun convert(message: IpAssignDemandCancelMessage) = message.toMono().flatMap(demandConverter::toDto)
    private fun removeDemandStatus(demandStatus: DemandStatusDto): Mono<Unit> = demandStatusService.remove(demandStatus)
    private fun validate(demandStatus: DemandStatusDto): Mono<DemandStatusDto> = demandStatusValidator.validate(DemandStatusPolicyGroup.CANCEL, demandStatus)
    private fun sendAlarm(receiverId: Long): Mono<Unit> = pushAlarmService.publish(receiverId, "신청이 취소됬어요!", "마음이 바뀌시면 언제든지 다시 신청해주세요 :>")
    private fun sendError(error: Throwable, demand: IpAssignDemandCancelMessage): Mono<Unit> = IpAssignDemandCancelErrorOnStatusMessage(
            id = demand.id,
            issuerId = demand.issuerId,
            title = demand.title,
            description = demand.description,
            usage = demand.usage,
            expireAt = demand.expireAt,
            message = error.localizedMessage
        ).let { messagePublishService.publish(MessageType.DEMAND_ERROR_ON_STATUS, it) }
}