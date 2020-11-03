package br.com.adenilson.gistgithub.application.di

import androidx.lifecycle.ViewModelProvider
import br.com.adenilson.base.di.AndroidScheduler
import br.com.adenilson.base.di.IOScheduler
import br.com.adenilson.base.domain.Executor
import br.com.adenilson.base.domain.ExecutorImpl
import br.com.adenilson.base.navigator.Navigator
import br.com.adenilson.gistgithub.di.factory.ViewModelFactory
import br.com.adenilson.gistgithub.navigator.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

@Module
abstract class GistGitHubApplicationModule {

    @Binds
    abstract fun bindNavigator(impl: NavigatorImpl): Navigator

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun bindExecutor(executor: ExecutorImpl): Executor

    companion object {
        @AndroidScheduler
        @Provides
        fun providesAndroidScheduler(): Scheduler = AndroidSchedulers.mainThread()

        @IOScheduler
        @Provides
        fun providesIOScheduler(): Scheduler = Schedulers.io()
    }
}