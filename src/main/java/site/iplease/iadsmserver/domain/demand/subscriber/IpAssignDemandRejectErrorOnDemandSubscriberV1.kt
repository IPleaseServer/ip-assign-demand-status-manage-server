package site.iplease.iadsmserver.domain.demand.subscriber

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.service.DemandErrorService
import site.iplease.iadsmserver.domain.demand.util.DemandRejectErrorOnDemandConverter
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandRejectErrorOnDemandMessage
import site.iplease.iadsmserver.global.demand.subscriber.IpAssignDemandRejectErrorOnDemandSubscriber
import site.iplease.iadsmserver.infra.alarm.service.PushAlarmService

@Component
class IpAssignDemandRejectErrorOnDemandSubscriberV1(
    private val demandRejectErrorOnDemandConverter: DemandRejectErrorOnDemandConverter,
    private val demandErrorService: DemandErrorService,
    private val pushAlarmService: PushAlarmService
): IpAssignDemandRejectErrorOnDemandSubscriber {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun subscribe(message: IpAssignDemandRejectErrorOnDemandMessage) {
        demandRejectErrorOnDemandConverter.convert(message)
            .flatMap { demand ->  demandErrorService.handle(demand) }
            .flatMap { pushAlarmService.publish(message.issuerId, "신청 거절에 실패했어요!", "자세한 내용은 관리자에게 문의해주세요!") }
            .doOnSuccess { logRollback() }
            .doOnError { logError(it) }
            .onErrorResume { Mono.empty() }
            .block()
    }

    private fun logRollback() {
        logger.info("신청 거절에 대한 보상트랜젝션을 성공적으로 수행하였습니다!")
    }

    private fun logError(throwable: Throwable) {
        logger.info("신청 거절에 대한 보상트랜젝션을 수행하던중 오류가 발생하였습니다!")
        logger.info("exception: ${throwable::class.simpleName}")
        logger.info("message: ${throwable.localizedMessage}")
        logger.trace("stacktrace")
        logger.trace(throwable.stackTraceToString())
    }
}