package site.iplease.iadsmserver.global.demand.subscriber

import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCreateMessage

interface IpAssignDemandCreateSubscriber {
    fun subscribe(message: IpAssignDemandCreateMessage)
}