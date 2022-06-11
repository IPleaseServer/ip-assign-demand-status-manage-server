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

@Component
class DemandStatusValidatorV1(
    private val demandStatusRepository: DemandStatusRepository
): DemandStatusValidator {
    override fun validate(policy: DemandStatusPolicyGroup, demand: DemandStatusDto): Mono<DemandStatusDto> =
        policy.toMono().flatMap {
            when(it) {
                DemandStatusPolicyGroup.CREATE -> validateNotExists(demand).flatMap { validateStatusCreate(demand) } }
        }.map { demand }

    //해당 예약이 존재하지 않아야한다.
    private fun validateNotExists(demand: DemandStatusDto) =
        demandStatusRepository.existsByDemandId(demand.demandId)
            .flatMap {
                if(it) Mono.error(DemandStatusAlreadyExistsException("해당 예약ID를 가지는 예약상태정보가 이미 존재합니다!", demand.demandId))
                else Unit.toMono()
            }

    private fun validateStatusCreate(demand: DemandStatusDto) =
        demand.toMono().flatMap {
                if(demand.status != DemandStatusType.CREATE) Mono.error(WrongDemandStatusTypeException("예약 생성시 예약의 상태는 CREATE이어야합니다.", demand.status))
                else Unit.toMono()
            }
}