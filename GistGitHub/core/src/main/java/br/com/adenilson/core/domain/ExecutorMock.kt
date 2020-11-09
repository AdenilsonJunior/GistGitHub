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
        interactor: Interactor<PARAMS, Flowable1<RESPONSE>>,
        params: PARAMS
    ): Flowable1<RESPONSE> {
        return interactor.execute(params).observeOn(testScheduler)
    }

    override fun <PARAMS, RESPONSE> execute(
        interactor: Interactor<PARAMS, Observable<RESPONSE>>,
        params: PARAMS
    ): Observable<RESPONSE> {
        return interactor.execute(params).observeOn(testScheduler)
    }

    override fun <PARAMS, RESPONSE> execute(
        interactor: Interactor<PARAMS, Single<RESPONSE>>,
        params: PARAMS
    ): Single<RESPONSE> {
        return interactor.execute(params).observeOn(testScheduler)
    }

    override fun <PARAMS> execute(
        interactor: Interactor<PARAMS, Completable>,
        params: PARAMS
    ): Completable {
        return interactor.execute(params).observeOn(testScheduler)
    }
}