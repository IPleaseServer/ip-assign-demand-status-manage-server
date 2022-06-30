package site.iplease.iadsmserver.domain.demand.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandAcceptErrorOnDemandDto
import site.iplease.iadsmserver.domain.demand.data.dto.DemandRejectErrorOnDemandDto
import site.iplease.iadsmserver.domain.demand.data.entity.DemandStatus
import site.iplease.iadsmserver.domain.demand.data.type.DemandStatusType
import site.iplease.iadsmserver.domain.demand.repository.DemandStatusRepository
import site.iplease.iadsmserver.global.common.util.DateUtil
import site.iplease.iadsmserver.infra.alarm.service.PushAlarmService
import kotlin.math.absoluteValue
import kotlin.random.Random

@Service
class DemandErrorServiceImpl(
    private val demandStatusRepository: DemandStatusRepository,
    private val pushAlarmService: PushAlarmService,
    private val dateUtil: DateUtil
): DemandErrorService {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun handle(demand: DemandRejectErrorOnDemandDto): Mono<Unit> =
        rollback(demand.demandId, demand.originStatus)
        .map { createRandomId() }
        .flatMap { id -> logErrorOnStatus(id, demand) }
        .flatMap { id -> pushAlarmService.publish(demand.issuerId, "할당IP신청거절중, 오류가 발생했습니다!", "다음 아이디로 관리자에게 문의해주세요!  - $id") }

    override fun handle(demand: DemandAcceptErrorOnDemandDto): Mono<Unit> =
        rollback(demand.demandId, demand.originStatus)
        .map { createRandomId() }
        .flatMap { id -> logErrorOnStatus(id, demand) }
        .flatMap { id -> pushAlarmService.publish(demand.issuerId, "할당IP신청수락중, 오류가 발생했습니다!", "다음 아이디로 관리자에게 문의해주세요!  - $id") }

    private fun rollback(demandId: Long, originStatus: DemandStatusType) =
        demandStatusRepository.existsByDemandId(demandId).flatMap { isExists ->
            if(isExists) demandStatusRepository.findByDemandId(demandId)
            else DemandStatus(id = 0, demandId = demandId, status = originStatus).toMono()
        }.map{ demandStatus -> demandStatus.copy(status = originStatus) }
        .flatMap { demandStatus -> demandStatusRepository.save(demandStatus) }

    private fun logErrorOnStatus(id: String, demand: DemandRejectErrorOnDemandDto): Mono<String> =
        logErrorOnStatus(id, "할당IP신청거절중 오류가 발생했습니다!",
            issuerId = demand.issuerId, demandId = demand.demandId, errorMessage = demand.message)

    private fun logErrorOnStatus(id: String, demand: DemandAcceptErrorOnDemandDto): Mono<String> =
        logErrorOnStatus(id, "할당IP신청수락중 오류가 발생했습니다!",
            issuerId = demand.issuerId, demandId = demand.demandId, errorMessage = demand.message)

    private fun logErrorOnStatus(id: String, description: String, issuerId: Long, demandId: Long, errorMessage: String): Mono<String> =
        id.toMono().map {
            logger.warn("[$id] $description")
            logger.warn("[$id] issuerId: $issuerId")
            logger.warn("[$id] demandId: $demandId")
            logger.warn("[$id] message: $errorMessage")
        }.map { id }

    //가끔 한두번은 겹쳐도 상관없다.
    private fun createRandomId() =  dateUtil.dateNow().dayOfMonth.toString() + Random.nextInt().absoluteValue.toString()
}