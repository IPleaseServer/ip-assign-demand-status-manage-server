package site.iplease.iadsmserver.infra.message.listener

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import site.iplease.iadsmserver.domain.demand.subscriber.IpAssignDemandCreateSubscriber
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCreateMessage
import site.iplease.iadsmserver.infra.message.type.MessageType

@Component
class RabbitMqListener(
    private val ipAssignDemandCreateSubscriber: IpAssignDemandCreateSubscriber,
    private val objectMapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @RabbitListener(queues = ["iplease.ip.assign.demand"])
    fun listen(message: Message) {
        val routingKey = message.messageProperties.receivedRoutingKey
        when(MessageType.of(routingKey)) {
            MessageType.IP_ASSIGN_DEMAND_CREATE ->
                objectMapper.readValue(String(message.body), IpAssignDemandCreateMessage::class.java)
                    .let { ipAssignDemandCreateSubscriber.subscribe(message = it) }
            else -> {
                logger.warn("처리대상이 아닌 메세지가 바인딩되어있습니다!")
                logger.warn("routingKey: ${message.messageProperties.receivedRoutingKey}")
                logger.warn("payload(string): ${String(message.body)}")
                logger.warn("payload(byte): ${message.body}")
            }
        }
    }
}