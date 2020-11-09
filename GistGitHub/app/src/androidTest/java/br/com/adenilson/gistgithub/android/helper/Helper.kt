package br.com.adenilson.gistgithub.android.helper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import br.com.adenilson.gistgithub.R
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory
import org.junit.Assert
import org.mockito.Mockito
import javax.inject.Provider

inline fun <reified T : Any> createFakeActivityInjector(crossinline block: T.() -> Unit): DispatchingAndroidInjector<Any> =
    mutableMapOf(Pair<Class<*>, Provider<AndroidInjector.Factory<*>>>(T::class.java, Provider {
        AndroidInjector.Factory<Activity> {
            AndroidInjector { instance ->
                if (instance is T) {
                    instance.block()
                }
            }
        }
    })).let { map ->
        DispatchingAndroidInjector_Factory.newInstance(map, emptyMap())
    }

inline fun <reified T : Activity> getCurrentActivityInStage(stage: Stage): T? =
    ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(stage).iterator()
        .let { activities ->
            if (activities.hasNext()) {
                activities.next().let {
                    if (it is T) {
                        it
                    } else null
                }
            } else {
                null
            }
        }

inline fun <reified T : Activity> useActivity(
    intent: Intent? = null,
    block: (ActivityScenario<T>) -> Unit
) {
    if (intent == null) {
        ActivityScenario.launch(T::class.java).use { block.invoke(it) }
    } else {
        ActivityScenario.launch<T>(intent).use { block.invoke(it) }
    }
}

inline fun <reified T : Fragment> useFragmentInContainer(
    args: Bundle? = null,
    factory: FragmentFactory? = null,
    block: (FragmentScenario<T>) -> Unit
) {
    FragmentScenario.launchInContainer(T::class.java, args, R.style.AppTheme, factory)
        .also { scenario ->
            block.invoke(scenario)
        }
}

fun Any.clearMocks(vararg mocks: Any) {
    mocks.forEach { Mockito.clearInvocations(it) }
}

inline fun <reified T : Activity> ActivityScenario<T>.assertActivityIsFinished() {
    try {
        onActivity {
            Assert.assertTrue(it.isFinishing)
        }
    } catch (e: Exception) {
        Assert.assertEquals(Lifecycle.State.DESTROYED, state)
    }
}
