package br.com.adenilson.core.domain

interface Mapper<IN, OUT> {
    fun mapTo(params: IN): OUT
}