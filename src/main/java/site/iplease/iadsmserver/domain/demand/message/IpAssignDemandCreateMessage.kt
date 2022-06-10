package site.iplease.iadsmserver.domain.demand.message

data class IpAssignDemandCreateMessage (
    val demandId: Long,
    val issuerId: Long
)
