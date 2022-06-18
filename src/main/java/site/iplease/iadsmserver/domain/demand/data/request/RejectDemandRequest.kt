package site.iplease.iadsmserver.domain.demand.data.request

import org.hibernate.validator.constraints.Length

data class RejectDemandRequest (
    @field:Length(max = 255)
    val reason: String
)