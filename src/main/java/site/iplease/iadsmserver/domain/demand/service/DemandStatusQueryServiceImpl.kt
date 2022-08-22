package site.iplease.iadsmserver.domain.demand.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.data.dto.DemandStatusDto
import site.iplease.iadsmserver.domain.demand.repository.DemandStatusRepository
import site.iplease.iadsmserver.domain.demand.util.DemandStatusConverter

@Service
class DemandStatusQueryServiceImpl(
    private val demandStatusRepository: DemandStatusRepository,
    private val demandStatusConverter: DemandStatusConverter
): DemandStatusQueryService {
    override fun getStatusByDemandId(demandId: Long): Mono<DemandStatusDto> =
        demandStatusRepository.findByDemandId(demandId)
            .flatMap { demandStatusConverter.toDto(it) }
}