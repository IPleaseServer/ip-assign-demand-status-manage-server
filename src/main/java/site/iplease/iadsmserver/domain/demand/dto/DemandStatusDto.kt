package site.iplease.iadsmserver.domain.demand.dto

import site.iplease.iadsmserver.domain.demand.type.DemandStatus

data class DemandStatusDto (
    val id: Long = 0,
    val demandId: Long,
    val issuerId: Long,
    val status: DemandStatus = DemandStatus.CREATE
)
