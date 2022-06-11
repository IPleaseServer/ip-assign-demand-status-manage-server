package site.iplease.iadsmserver.domain.demand.util

import site.iplease.iadsmserver.domain.demand.dto.DemandStatusDto
import site.iplease.iadsmserver.domain.demand.message.IpAssignDemandCreateMessage

interface DemandStatusConverter {
    fun toDto(message: IpAssignDemandCreateMessage): DemandStatusDto

}
