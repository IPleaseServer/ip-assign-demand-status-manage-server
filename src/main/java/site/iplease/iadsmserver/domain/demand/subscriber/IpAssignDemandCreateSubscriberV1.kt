package site.iplease.iadsmserver.domain.demand.subscriber

import org.springframework.stereotype.Component
import site.iplease.iadsmserver.domain.demand.service.DemandStatusService
import site.iplease.iadsmserver.domain.demand.util.DemandStatusConverter
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCreateMessage
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandErrorOnStatusMessage
import site.iplease.iadsmserver.global.demand.subscriber.IpAssignDemandCreateSubscriber
import site.iplease.iadsmserver.infra.alarm.service.PushAlarmService
import site.iplease.iadsmserver.infra.message.service.MessagePublishService
import site.iplease.iadsmserver.infra.message.type.MessageType

@Component
class IpAssignDemandCreateSubscriberV1(
    private val demandStatusConverter: DemandStatusConverter,
    private val demandStatusService: DemandStatusService,
    private val pushAlarmService: PushAlarmService,
    private val messagePublishService: MessagePublishService,
): IpAssignDemandCreateSubscriber {
    override fun subscribe(message: IpAssignDemandCreateMessage) {
        demandStatusConverter.toDto(message)//메세지를 DemandStatusDto로 변환한다.
            .flatMap { demandStatusService.createDemand(it) }//Service에게로 예약추가로직을 위임한다. //it.let(this::validate).flatMap(this::add)
            .flatMap { pushAlarmService.publish(message.issuerId, "신청이 등록됬어요!", "담당 선생님이 신청을 확인하시면 다시 알려드릴게요 :)") }//추가 성공시, 유저에게 푸시알람을 전송한다.
            .onErrorResume { throwable -> //추가 실패시 예약추가 실패 메세지를 전파한다.
                IpAssignDemandErrorOnStatusMessage(demandId = message.demandId, issuerId = message.issuerId, message = throwable.localizedMessage)
                    .let { messagePublishService.publish(MessageType.IP_ASSIGN_DEMAND_CREATE_ERROR_ON_STATUS, it) }
            }.block()
    }
}