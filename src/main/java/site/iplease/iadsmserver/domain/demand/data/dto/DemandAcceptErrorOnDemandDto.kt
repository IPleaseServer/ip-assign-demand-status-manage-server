package site.iplease.iadsmserver.domain.demand.data.dto

import site.iplease.iadsmserver.domain.demand.data.type.DemandStatusType

data class DemandAcceptErrorOnDemandDto (
    val issuerId: Long,
    val demandId: Long,
    val assignIp: String,
    val originStatus: DemandStatusType,
    val message: String
)