package site.iplease.iadsmserver.domain.demand.exception

import site.iplease.iadsmserver.global.error.IpleaseError

data class WrongDemandStatusTypeException(private val errorDetail: String): RuntimeException("$ERROR_MESSAGE - $errorDetail"), IpleaseError {
    companion object { private const val ERROR_MESSAGE = "잘못된 예약상태입니다!" }

    override fun getErrorMessage() = ERROR_MESSAGE
    override fun getErrorDetail() = errorDetail
}
