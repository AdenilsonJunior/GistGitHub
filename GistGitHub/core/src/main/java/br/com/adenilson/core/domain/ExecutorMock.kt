package br.com.adenilson.core.domain

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.core.Flowable as Flowable1

class ExecutorMock : Executor {

    private val testScheduler: Scheduler = Schedulers.trampoline()

    init {
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
    }

    override fun <PARAMS, RESPONSE> execute(
        useCase: UseCase<PARAMS, Flowable1<RESPONSE>>,
        params: PARAMS
    ): Flowable1<RESPONSE> {
        return useCase.execute(params).observeOn(testScheduler)
    }

    override fun <PARAMS, RESPONSE> execute(
        useCase: UseCase<PARAMS, Observable<RESPONSE>>,
        params: PARAMS
    ): Observable<RESPONSE> {
        return useCase.execute(params).observeOn(testScheduler)
    }

    override fun <PARAMS, RESPONSE> execute(
        useCase: UseCase<PARAMS, Single<RESPONSE>>,
        params: PARAMS
    ): Single<RESPONSE> {
        return useCase.execute(params).observeOn(testScheduler)
    }

    override fun <PARAMS> execute(
        useCase: UseCase<PARAMS, Completable>,
        params: PARAMS
    ): Completable {
        return useCase.execute(params).observeOn(testScheduler)
    }
}