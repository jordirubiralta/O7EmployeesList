package com.example.o7employeeslist.data

sealed class NetworkError(
    open var httpErrorCode: Int? = null,
    open val errorCode: String? = null,
    open val userMessage: String? = null,
    @Transient override val cause: Throwable? = null
) : Throwable(userMessage, cause) {

    data class ConnectionError(
        override var httpErrorCode: Int? = null,
        override val errorCode: String? = null,
        override val userMessage: String = "",
        override val cause: Throwable? = null
    ) : NetworkError(
        errorCode = errorCode,
        userMessage = userMessage,
        cause = cause
    )

    data class HttpError(
        override var httpErrorCode: Int? = null,
        override val errorCode: String? = null,
        override val userMessage: String? = null,
        override val cause: Throwable? = null
    ) : NetworkError(httpErrorCode, errorCode, userMessage, cause)

    data class LockError(
        override var httpErrorCode: Int? = null,
        override val errorCode: String? = null,
        override val userMessage: String? = null,
        override val cause: Throwable? = null,
        val errorWebview: String?
    ) : NetworkError(httpErrorCode, errorCode, userMessage, cause)

    data class CookieExpiredError(
        override var httpErrorCode: Int? = null,
        override val errorCode: String? = null,
        override val userMessage: String? = null,
        override val cause: Throwable? = null
    ) : NetworkError(httpErrorCode, errorCode, userMessage, cause)

    data class TimeoutError(
        override var httpErrorCode: Int? = null,
        override val errorCode: String? = null,
        override val userMessage: String? = null,
        override val cause: Throwable? = null
    ) : NetworkError(httpErrorCode, errorCode, userMessage, cause)

    data class UndefinedError(
        override var httpErrorCode: Int? = null,
        override val errorCode: String? = null,
        override val userMessage: String? = null,
        override val cause: Throwable? = null
    ) : NetworkError(
        httpErrorCode = httpErrorCode,
        errorCode = errorCode,
        userMessage = userMessage ?: "Unknown error",
        cause = cause
    )

    data class CustomNotFoundError(
        override val errorCode: String? = null,
        override val userMessage: String? = null,
        override val cause: Throwable? = null
    ) : NetworkError(
        errorCode = errorCode,
        userMessage = userMessage ?: "Data not found",
        cause = cause
    )

    data class ServerError(
        val code: Int?,
        val description: String?,
        val url: String?,
        val action: String?,
        val key: String?,
        val params: List<String>? = emptyList(),
        val causes: List<Causes>? = emptyList(),
        val details: List<SmartWaitingRoomDetail>? = emptyList()
    ) : NetworkError(
        cause = null
    ) {
        override val errorCode: String? get() = code?.toString()
        override val userMessage: String? get() = causes?.firstOrNull()?.description ?: description

        data class Causes(
            val code: String?,
            val description: String?,
            val key: String?,
            val params: List<String> = emptyList()
        )

        data class SmartWaitingRoomDetail(
            var dataType: String?,
            val operation: String?,
            val queueUserPosition: Int?,
            val maxRetries: Int?,
            val retryAfter: Int?
        )
    }
}
