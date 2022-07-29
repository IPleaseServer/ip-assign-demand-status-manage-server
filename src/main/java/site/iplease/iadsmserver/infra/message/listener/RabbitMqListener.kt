package site.iplease.iadsmserver.infra.message.listener

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandAcceptErrorOnDemandMessage
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCancelMessage
import site.iplease.iadsmserver.global.demand.subscriber.IpAssignDemandCreateSubscriber
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCreateMessage
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandRejectErrorOnDemandMessage
import site.iplease.iadsmserver.global.demand.subscriber.IpAssignDemandAcceptErrorOnDemandSubscriber
import site.iplease.iadsmserver.global.demand.subscriber.IpAssignDemandCancelSubscriber
import site.iplease.iadsmserver.global.demand.subscriber.IpAssignDemandRejectErrorOnDemandSubscriber
import site.iplease.iadsmserver.infra.message.type.MessageType

@Component
class RabbitMqListener(
    private val ipAssignDemandCreateSubscriber: IpAssignDemandCreateSubscriber,
    private val ipAssignDemandCancelSubscriber: IpAssignDemandCancelSubscriber,
    private val ipAssignDemandAcceptErrorOnDemandSubscriber: IpAssignDemandAcceptErrorOnDemandSubscriber,
    private val ipAssignDemandRejectErrorOnDemandSubscriber: IpAssignDemandRejectErrorOnDemandSubscriber,
    private val objectMapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @RabbitListener(queues = ["iplease.ip.assign.demand.status.manage"])
    fun listen(message: Message) {
        val routingKey = message.messageProperties.receivedRoutingKey
        val payload = String(message.body)
        handleMessage(MessageType.of(routingKey), payload)
            .doOnError{ throwable ->
                logger.error("메세지를 직렬화하는도중 오류가 발생하였습니다!")
                logger.error("exception: ${throwable.localizedMessage}")
                logger.error("payload(string): ${String(message.body)}")
                logger.error("payload(byte): ${message.body}")
            }.onErrorResume { Mono.empty() }
            .block()
    }

    private fun handleMessage(type: MessageType, payload: String): Mono<Unit> =
        when(type) {
            MessageType.IP_ASSIGN_DEMAND_CREATE -> objectMapper.toMono()
                .map { it.readValue(payload, IpAssignDemandCreateMessage::class.java) }
                .map { message -> ipAssignDemandCreateSubscriber.subscribe(message) }
            MessageType.IP_ASSIGN_DEMAND_CANCEL -> objectMapper.toMono()
                .map { it.readValue(payload, IpAssignDemandCancelMessage::class.java) }
                .map { message -> ipAssignDemandCancelSubscriber.subscribe(message) }
            MessageType.IP_ASSIGN_DEMAND_ACCEPT_ERROR_ON_DEMAND -> objectMapper.toMono()
                .map { it.readValue(payload, IpAssignDemandAcceptErrorOnDemandMessage::class.java) }
                .map { message -> ipAssignDemandAcceptErrorOnDemandSubscriber.subscribe(message) }
            MessageType.IP_ASSIGN_DEMAND_REJECT_ERROR_ON_DEMAND -> objectMapper.toMono()
                .map { it.readValue(payload, IpAssignDemandRejectErrorOnDemandMessage::class.java) }
                .map { message -> ipAssignDemandRejectErrorOnDemandSubscriber.subscribe(message) }
            else -> {
                logger.warn("처리대상이 아닌 메세지가 바인딩되어있습니다!")
                logger.warn("routingKey: ${type.routingKey}")
                logger.warn("payload(string): $payload")
                Unit.toMono()
            }
        }
}