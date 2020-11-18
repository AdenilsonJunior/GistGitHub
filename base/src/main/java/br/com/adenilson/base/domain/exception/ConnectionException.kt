package br.com.adenilson.base.domain.exception

data class ConnectionException(val statusCode: Int) : Exception()