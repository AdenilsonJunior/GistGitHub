package br.com.adenilson.core.domain

import br.com.adenilson.core.di.AndroidScheduler
import br.com.adenilson.core.di.IOScheduler
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface Executor {
    fun <PARAMS, RESPONSE> execute(
        useCase: UseCase<PARAMS, Flowable<RESPONSE>>,
        params: PARAMS
    ): Flowable<RESPONSE>

    fun <PARAMS, RESPONSE> execute(
        useCase: UseCase<PARAMS, Observable<RESPONSE>>,
        params: PARAMS
    ): Observable<RESPONSE>

    fun <PARAMS, RESPONSE> execute(
        useCase: UseCase<PARAMS, Single<RESPONSE>>,
        params: PARAMS
    ): Single<RESPONSE>

    fun <PARAMS> execute(useCase: UseCase<PARAMS, Completable>, params: PARAMS): Completable
}

class ExecutorImpl @Inject constructor(
    @AndroidScheduler val observeOn: Scheduler,
    @IOScheduler val subscribeOn: Scheduler
) : Executor {

    override fun <PARAMS, RESPONSE> execute(
        useCase: UseCase<PARAMS, Flowable<RESPONSE>>,
        params: PARAMS
    ): Flowable<RESPONSE> {
        return useCase
            .execute(params)
            .observeOn(observeOn)
            .subscribeOn(subscribeOn)
    }

    override fun <PARAMS, RESPONSE> execute(
        useCase: UseCase<PARAMS, Observable<RESPONSE>>,
        params: PARAMS
    ): Observable<RESPONSE> {
        return useCase
            .execute(params)
            .observeOn(observeOn)
            .subscribeOn(subscribeOn)
    }

    override fun <PARAMS, RESPONSE> execute(
        useCase: UseCase<PARAMS, Single<RESPONSE>>,
        params: PARAMS
    ): Single<RESPONSE> {
        return useCase
            .execute(params)
            .observeOn(observeOn)
            .subscribeOn(subscribeOn)
    }

    override fun <PARAMS> execute(
        useCase: UseCase<PARAMS, Completable>,
        params: PARAMS
    ): Completable {
        return useCase
            .execute(params)
            .observeOn(observeOn)
            .subscribeOn(subscribeOn)
    }
}