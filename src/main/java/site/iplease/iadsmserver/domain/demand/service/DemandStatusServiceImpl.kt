package site.iplease.iadsmserver.domain.demand.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandStatusDto
import site.iplease.iadsmserver.domain.demand.repository.DemandStatusRepository
import site.iplease.iadsmserver.domain.demand.util.DemandStatusConverter

@Service
class DemandStatusServiceImpl(
    private val demandStatusConverter: DemandStatusConverter,
    private val demandStatusRepository: DemandStatusRepository
): DemandStatusService {
    override fun add(demandStatus: DemandStatusDto): Mono<DemandStatusDto> =
        demandStatusConverter.toEntity(demandStatus)//Dto 를 Entity로 전환한다.
            .flatMap { entity -> demandStatusRepository.save(entity) }//전환한 Entity를 영속성 컨텍스트에 저장한다.
            .flatMap { entity -> demandStatusConverter.toDto(entity) }//저장한 Entity를 Dto로 전환하여 반환한다.

    override fun remove(demandStatus: DemandStatusDto): Mono<Unit> =
            demandStatusRepository.deleteById(demandStatus.id).then(Unit.toMono())
}