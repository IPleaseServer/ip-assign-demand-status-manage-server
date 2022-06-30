package site.iplease.iadsmserver.global.demand.message

import site.iplease.iadsmserver.domain.demand.data.type.DemandStatusType

data class IpAssignDemandAcceptErrorOnDemandMessage (
    val issuerId: Long,
    val demandId: Long,
    val assignIp: String,
    val originStatus: DemandStatusType,
    val message: String
)