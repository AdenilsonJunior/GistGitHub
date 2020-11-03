package br.com.adenilson.base.domain

interface Mapper<IN, OUT> {
    fun mapToPresentation(params: IN): OUT
}