package site.iplease.iadsmserver.domain.demand.exception

import site.iplease.iadsmserver.global.error.IpleaseError

data class UnCancellableDemandStatusException(private val errorDetail: String): RuntimeException("$ERROR_MESSAGE - $errorDetail"), IpleaseError {
    companion object { private const val ERROR_MESSAGE = "예약을 취소할 수 없습니다!" }

    override fun getErrorMessage() = ERROR_MESSAGE
    override fun getErrorDetail() = errorDetail
}
