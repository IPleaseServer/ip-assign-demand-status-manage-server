package site.iplease.iadsmserver.domain.demand.exception

import site.iplease.iadsmserver.global.error.IpleaseError

data class DemandStatusAlreadyExistsException(private val errorDetail: String): RuntimeException("$ERROR_MESSAGE - $errorDetail"), IpleaseError {
    companion object { private const val ERROR_MESSAGE = "이미 존재하는 예약상태정보입니다!" }

    override fun getErrorMessage() = ERROR_MESSAGE
    override fun getErrorDetail() = errorDetail
}