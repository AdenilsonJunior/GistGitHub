package br.com.adenilson.core.domain

interface Interactor<in P, out R> {
    fun execute(params: P): R
}