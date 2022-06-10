package site.iplease.iadsmserver.domain.demand.subscriber

import site.iplease.iadsmserver.domain.demand.message.IpAssignDemandCreateMessage

interface IpAssignDemandCreateSubscriber {
    fun subscribe(message: IpAssignDemandCreateMessage)
}