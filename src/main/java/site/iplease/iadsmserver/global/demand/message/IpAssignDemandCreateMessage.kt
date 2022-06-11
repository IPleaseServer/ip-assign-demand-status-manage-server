package site.iplease.iadsmserver.global.demand.message

data class IpAssignDemandCreateMessage (
    val demandId: Long,
    val issuerId: Long
)
