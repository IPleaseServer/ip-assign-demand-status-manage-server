package site.iplease.iadsmserver.infra.alarm.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.iadsmserver.infra.alarm.service.data.message.SendAlarmMessage
import site.iplease.iadsmserver.infra.alarm.service.data.type.AlarmType
import site.iplease.iadsmserver.infra.message.service.MessagePublishService
import site.iplease.iadsmserver.infra.message.type.MessageType

@Service
class PushAlarmServiceImpl(
    private val messagePublishService: MessagePublishService
): PushAlarmService {
    override fun publish(receiverId: Long, title: String, description: String): Mono<Unit> =
        Unit.toMono()
            .map { SendAlarmMessage(
                type = AlarmType.FCM,
                receiverId = receiverId,
                title = title,
                description = description
            ) }.flatMap { messagePublishService.publish(type = MessageType.SEND_ALARM, message = it) }
}