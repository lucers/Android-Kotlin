package com.lucers.common.model

import com.airbnb.mvrx.BaseMvRxViewModel
import com.lucers.common.state.CountDownState
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * CountDownModel
 */
class CountDownModel(initialState: CountDownState) :
    BaseMvRxViewModel<CountDownState>(initialState) {

    var disposable: Disposable? = null

    fun startCount() {
        disposable = Observable.interval(0L, 1L, TimeUnit.SECONDS)
            .subscribe {
                setState {
                    copy(
                        countTime = countDown(countTime),
                        countOver = countDown(countTime) == 0L
                    )
                }
            }
    }

    private fun countDown(countTime: Long): Long {
        return if (countTime == 0L) {
            stopCount()
            0L
        } else {
            countTime - 1L
        }
    }

    fun stopCount() {
        disposable?.dispose()
    }

    fun finishCount() {
        disposable?.dispose()
        setState {
            copy(
                countTime = 0, countOver = true
            )
        }
    }
}