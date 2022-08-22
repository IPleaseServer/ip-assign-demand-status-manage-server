package site.iplease.iadsmserver.domain.demand.subscriber

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.data.type.DemandStatusType
import site.iplease.iadsmserver.domain.demand.service.DemandErrorService
import site.iplease.iadsmserver.domain.demand.util.DemandAcceptErrorOnDemandConverter
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandAcceptErrorOnDemandMessage
import site.iplease.iadsmserver.global.demand.subscriber.IpAssignDemandAcceptErrorOnDemandSubscriber
import site.iplease.iadsmserver.infra.alarm.service.PushAlarmService

@Component
class IpAssignDemandAcceptErrorOnDemandSubscriberV1(
    private val demandAcceptErrorOnDemandConverter: DemandAcceptErrorOnDemandConverter,
    private val demandErrorService: DemandErrorService,
    private val pushAlarmService: PushAlarmService
): IpAssignDemandAcceptErrorOnDemandSubscriber {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun subscribe(message: IpAssignDemandAcceptErrorOnDemandMessage) {
        demandAcceptErrorOnDemandConverter.convert(message)
            .flatMap { demand -> demandErrorService.handle(demand) }
            .flatMap { pushAlarmService.publish(message.issuerId, "신청 수락에 실패했어요!", "자세한 내용은 관리자에게 문의해주세요!") }
            .doOnSuccess { logRollback(message.originStatus) }
            .doOnError { logError(it) }
            .onErrorResume { Mono.empty() }
            .block()
    }

    private fun logRollback(originStatus: DemandStatusType) {
        logger.info("신청 수락에 대한 보상트랜젝션을 성공적으로 수행하였습니다! 기존 상태: $originStatus")
    }

    private fun logError(throwable: Throwable) {
        logger.info("신청 수락에 대한 보상트랜젝션을 수행하던중 오류가 발생하였습니다!")
        logger.info("exception: ${throwable::class.simpleName}")
        logger.info("message: ${throwable.localizedMessage}")
        logger.trace("stacktrace")
        logger.trace(throwable.stackTraceToString())
    }
}