package ru.mrroot.my_test_8.presenter

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ru.mrroot.my_test_8.presenter.SchedulerProvider

class ScheduleProviderStub : SchedulerProvider {
    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }
}