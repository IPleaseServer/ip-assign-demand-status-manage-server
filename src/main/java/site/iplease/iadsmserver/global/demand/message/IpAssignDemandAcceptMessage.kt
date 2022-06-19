package site.iplease.iadsmserver.global.demand.message

import site.iplease.iadsmserver.domain.demand.data.type.DemandStatusType

data class IpAssignDemandAcceptMessage (
    val demandId: Long,
    val issuerId: Long,
    val originStatus: DemandStatusType,
    val assignIp: String,
)
