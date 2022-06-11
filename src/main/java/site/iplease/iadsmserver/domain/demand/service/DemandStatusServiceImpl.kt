package site.iplease.iadsmserver.domain.demand.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.dto.DemandStatusDto
import site.iplease.iadsmserver.domain.demand.repository.DemandStatusRepository
import site.iplease.iadsmserver.domain.demand.util.DemandStatusConverter

@Service
class DemandStatusServiceImpl(
    private val demandStatusConverter: DemandStatusConverter,
    private val demandStatusRepository: DemandStatusRepository
): DemandStatusService {
    override fun add(demand: DemandStatusDto): Mono<DemandStatusDto> =
        demandStatusConverter.toEntity(demand)//Dto 를 Entity로 전환한다.
            .flatMap { entity -> demandStatusRepository.save(entity) }//전환한 Entity를 영속성 컨텍스트에 저장한다.
            .flatMap { entity -> demandStatusConverter.toDto(entity) }//저장한 Entity를 Dto로 전환하여 반환한다.
}