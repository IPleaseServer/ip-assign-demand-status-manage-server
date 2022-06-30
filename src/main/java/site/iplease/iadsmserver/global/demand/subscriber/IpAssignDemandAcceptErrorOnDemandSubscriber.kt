package site.iplease.iadsmserver.global.demand.subscriber

import site.iplease.iadsmserver.global.demand.message.IpAssignDemandAcceptErrorOnDemandMessage

interface IpAssignDemandAcceptErrorOnDemandSubscriber {
    fun subscribe(message: IpAssignDemandAcceptErrorOnDemandMessage)
}