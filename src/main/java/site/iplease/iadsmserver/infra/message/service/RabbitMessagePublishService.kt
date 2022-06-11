package site.iplease.iadsmserver.infra.message.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.iadsmserver.infra.message.type.MessageType

@Service
class RabbitMessagePublishService(
    private val objectMapper: ObjectMapper,
    private val rabbitTemplate: RabbitTemplate
): MessagePublishService {
    companion object { const val EXCHANGE_NAME = "iplease.message" }
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun publish(type: MessageType, message: Any): Mono<Unit> =
        Unit.toMono()
            .map { objectMapper.writeValueAsString(message) }
            .map { sendMessage(type.routingKey, it) }

    private fun sendMessage(routingKey: String, data: String) {
        logger.info("메세지를 송신합니다.")
        logger.info("routingKey: $routingKey")
        logger.info("data: $data")
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, data)
    }
}