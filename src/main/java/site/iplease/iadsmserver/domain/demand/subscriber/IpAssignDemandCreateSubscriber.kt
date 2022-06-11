package site.iplease.iadsmserver.domain.demand.subscriber

import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCreateMessage

interface IpAssignDemandCreateSubscriber {
    fun subscribe(message: IpAssignDemandCreateMessage)
}