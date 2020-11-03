package br.com.adenilson.base.domain

interface Interactor<in P, out R> {
    fun execute(params: P): R
}