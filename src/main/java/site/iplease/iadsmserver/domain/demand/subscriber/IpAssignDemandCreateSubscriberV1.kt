package site.iplease.iadsmserver.domain.demand.subscriber

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandStatusDto
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCreateMessage
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandErrorOnStatusMessage
import site.iplease.iadsmserver.domain.demand.service.DemandStatusService
import site.iplease.iadsmserver.domain.demand.data.type.DemandStatusPolicyGroup
import site.iplease.iadsmserver.domain.demand.util.DemandStatusConverter
import site.iplease.iadsmserver.domain.demand.util.DemandStatusValidator
import site.iplease.iadsmserver.global.demand.subscriber.IpAssignDemandCreateSubscriber
import site.iplease.iadsmserver.infra.message.service.MessagePublishService
import site.iplease.iadsmserver.infra.message.type.MessageType
import site.iplease.iadsmserver.infra.alarm.service.PushAlarmService

@Component
class IpAssignDemandCreateSubscriberV1(
    private val demandStatusConverter: DemandStatusConverter,
    private val demandStatusValidator: DemandStatusValidator,
    private val demandStatusService: DemandStatusService,
    private val pushAlarmService: PushAlarmService,
    private val messagePublishService: MessagePublishService,
): IpAssignDemandCreateSubscriber {
    override fun subscribe(message: IpAssignDemandCreateMessage) {
        onStart(message)
            .flatMap { logic(it) }
            .flatMap { demand -> onSuccess(message.issuerId, demand) }
            .onErrorResume { throwable -> onError(message, throwable) }
            .block()
    }

    private fun onStart(message: IpAssignDemandCreateMessage) = convert(message)
    private fun logic(demand: DemandStatusDto) = demand.let(this::validate).flatMap(this::add)
    private fun onSuccess(issuerId: Long, demand: DemandStatusDto) = sendAlarm(receiverId = issuerId)
    private fun onError(demand: IpAssignDemandCreateMessage, error: Throwable) = sendError(error, demandId = demand.demandId, issuerId = demand.issuerId)

    private fun convert(message: IpAssignDemandCreateMessage) = message.toMono().flatMap(demandStatusConverter::toDto)
    private fun validate(demand: DemandStatusDto): Mono<DemandStatusDto> = demandStatusValidator.validate(
        DemandStatusPolicyGroup.CREATE, demand)
    private fun add(demand: DemandStatusDto): Mono<DemandStatusDto> = demandStatusService.add(demand)
    private fun sendAlarm(receiverId: Long): Mono<Unit> = pushAlarmService.publish(receiverId, "예약이 등록됬어요!", "담당 선생님이 예약을 확인하시면 다시 알려드릴게요 :)")
    private fun sendError(error: Throwable, demandId: Long, issuerId: Long): Mono<Unit> =
        IpAssignDemandErrorOnStatusMessage(demandId = demandId, issuerId = issuerId, message = error.localizedMessage)
            .let { messagePublishService.publish(MessageType.DEMAND_ERROR_ON_STATUS, it) }
}