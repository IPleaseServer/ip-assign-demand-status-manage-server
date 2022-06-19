package site.iplease.iadsmserver.global.demand.message

import site.iplease.iadsmserver.domain.demand.data.type.DemandStatusType

data class IpAssignDemandAcceptMessage (
    val issuerId: Long,
    val assignIp: String,
    val demandId: Long,
    val originStatus: DemandStatusType
)
