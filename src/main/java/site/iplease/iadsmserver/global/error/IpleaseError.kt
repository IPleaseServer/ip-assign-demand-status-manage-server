package site.iplease.iadsmserver.global.error

interface IpleaseError {
    fun getErrorMessage(): String
    fun getErrorDetail(): String
}