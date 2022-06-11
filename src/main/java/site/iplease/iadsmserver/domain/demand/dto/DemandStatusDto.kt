package site.iplease.iadsmserver.domain.demand.dto

import site.iplease.iadsmserver.domain.demand.type.DemandStatusType

data class DemandStatusDto (
    val id: Long = 0,
    val demandId: Long,
    val status: DemandStatusType = DemandStatusType.CREATE
)
