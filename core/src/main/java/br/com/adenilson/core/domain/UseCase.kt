package br.com.adenilson.core.domain

interface UseCase<in P, out R> {
    fun execute(params: P): R
}