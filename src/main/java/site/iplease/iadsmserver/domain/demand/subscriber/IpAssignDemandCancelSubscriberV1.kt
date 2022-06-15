package site.iplease.iadsmserver.domain.demand.subscriber

import org.springframework.stereotype.Component
import site.iplease.iadsmserver.domain.demand.service.DemandStatusService
import site.iplease.iadsmserver.domain.demand.util.DemandConverter
import site.iplease.iadsmserver.domain.demand.util.DemandStatusConverter
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
): IpAssignDemandCancelSubscriber {
    override fun subscribe(message: IpAssignDemandCancelMessage) {
        demandConverter.toDto(message)//메세지를 DemandDto로 변환한다.
            .flatMap { demand -> demandStatusService.cancelDemand(demand) }//Service에게로 예약취소로직을 위임한다.
            .flatMap { _ -> pushAlarmService.publish(message.issuerId, "신청이 취소됬어요!", "마음이 바뀌시면 언제든지 다시 신청해주세요 :>") }//취소 성공시, 유저에게 푸시알람을 전송한다.
            .onErrorResume { throwable -> //취소 실패시, 예약취소 실패 메세지를 전파한다.
                demandStatusConverter.toErrorMessage(message, throwable)//예약취소 실패메세지를 구성한다.
                    .flatMap { messagePublishService.publish(MessageType.DEMAND_ERROR_ON_STATUS, it) } //구성한 실패메세지를 발행한다.
            }.block()
    }
}