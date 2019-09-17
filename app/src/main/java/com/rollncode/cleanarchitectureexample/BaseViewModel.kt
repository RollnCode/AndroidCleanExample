package com.rollncode.cleanarchitectureexample

import android.app.Application
import androidx.annotation.CallSuper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.net.SocketTimeoutException
import java.util.concurrent.CancellationException

abstract class BaseViewModel(holder: IContextHolder) : AndroidViewModel(holder.ctx.applicationContext as Application) {

    private val executeErrorHandler: (Throwable, Boolean) -> Unit by lazy {
        { e: Throwable, withProgress: Boolean ->
            if (e !is CancellationException)
                e.toLogTag()

            if (withProgress) when (e) {
                is CancellationException  -> Unit

                is SocketTimeoutException -> sendErrors(R.id.error_api_timeout any R.string.error_api_timeout)
                is ApiException           -> sendErrors(e.toAnyError())
                is HttpException          -> sendErrors(*e.toAnyErrors().toTypedArray())

                else                      -> sendErrors(R.id.error_unknown any (e.message
                    ?: e::class.java.name))
            }
        }
    }
    private val compositeDisposable = CompositeDisposable()
    private val showProgressImpl = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> get() = showProgressImpl

    private fun addDisposable(d: Disposable) = compositeDisposable.add(d)

    private fun <T> createRequestTransformer() = SingleTransformer<T, T> { upstream ->
        upstream
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showProgressImpl %= true }
            .doFinally { showProgressImpl %= false }
    }

    protected fun <T> Observable<T>.subscribeDefault(onNext: (T) -> Unit) =
        addDisposable(observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNext, { executeErrorHandler(it, true) }))

    protected fun <T> Flowable<T>.subscribeDefault(onNext: (T) -> Unit) =
        addDisposable(observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNext, { executeErrorHandler(it, true) }))

    protected fun <T> Single<T>.subscribeDefault(onNext: (T) -> Unit) =
        addDisposable(compose(createRequestTransformer<T>())
            .subscribe(onNext, { executeErrorHandler(it, true) }))

    @CallSuper
    override fun onCleared() {
        compositeDisposable.clear()
    }

    abstract fun sendErrors(vararg errors: AnyError)
}