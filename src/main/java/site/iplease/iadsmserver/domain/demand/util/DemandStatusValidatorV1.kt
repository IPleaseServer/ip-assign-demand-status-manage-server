package site.iplease.iadsmserver.domain.demand.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandStatusDto
import site.iplease.iadsmserver.domain.demand.exception.DemandStatusAlreadyExistsException
import site.iplease.iadsmserver.domain.demand.exception.WrongDemandStatusTypeException
import site.iplease.iadsmserver.domain.demand.repository.DemandStatusRepository
import site.iplease.iadsmserver.domain.demand.data.type.DemandStatusPolicyGroup
import site.iplease.iadsmserver.domain.demand.data.type.DemandStatusType
import site.iplease.iadsmserver.domain.demand.exception.UnCancellableDemandStatusException
import site.iplease.iadsmserver.domain.demand.exception.UnknownDemandStatusException

@Component
class DemandStatusValidatorV1(
    private val demandStatusRepository: DemandStatusRepository
): DemandStatusValidator {
    override fun validate(policy: DemandStatusPolicyGroup, demand: DemandStatusDto): Mono<DemandStatusDto> =
        policy.toMono().flatMap {
            when(it!!) {
                DemandStatusPolicyGroup.CREATE -> validateExists(demand, false).flatMap { validateStatus(demand, DemandStatusType.CREATE) }
                DemandStatusPolicyGroup.CANCEL -> validateExists(demand).flatMap { validateStatusCancellable(demand) }
            }
        }.map { demand }

    private fun validateExists(demand: DemandStatusDto, beExists: Boolean = true) =
        demandStatusRepository.existsByDemandId(demand.demandId)
            .flatMap {
                if(it == beExists) Unit.toMono()
                else if(beExists) Mono.error(UnknownDemandStatusException("해당 예약ID를 가지는 예약상태정보를 찾을 수 없습니다! - ${demand.demandId}"))
                else Mono.error(DemandStatusAlreadyExistsException("해당 예약ID를 가지는 예약상태정보가 이미 존재합니다!", demand.demandId))
            }

    private fun validateStatus(demand: DemandStatusDto, beStatus: DemandStatusType) =
        demand.toMono().flatMap {
                if(demand.status == beStatus) Unit.toMono()
                else Mono.error(WrongDemandStatusTypeException("예약 생성시 예약의 상태는 CREATE이어야합니다.", demand.status))
            }

    private fun validateStatusCancellable(demand: DemandStatusDto) =
        demand.toMono().flatMap {
            if(DemandStatusType.isCancellable(demand.status)) Unit.toMono()
            else Mono.error(UnCancellableDemandStatusException("취소할 수 없는 예약상태정보입니다. - ${demand.status}"))
        }
}