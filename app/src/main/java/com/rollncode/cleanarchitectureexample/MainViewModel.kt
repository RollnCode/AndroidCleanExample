package com.rollncode.cleanarchitectureexample

import android.app.Activity
import androidx.lifecycle.LiveData
import com.rollncode.domain.entity.Detail
import com.rollncode.domain.usecase.GetDetailsUseCase
import javax.inject.Inject

class MainViewModel(activity: Activity) : BaseViewModel(activity.application) {

    @Inject
    protected lateinit var getDetailsUseCase: GetDetailsUseCase

    init {
        App.applicationComponent.detailsComponent().inject(this)
    }

    val details: LiveData<List<Detail>> by lazy { getDetailsUseCase.execute(null) {} }
}