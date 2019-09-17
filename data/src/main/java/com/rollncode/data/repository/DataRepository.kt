package com.rollncode.data.repository

import android.annotation.SuppressLint
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@SuppressLint("CheckResult")
object DataRepository {

    private val profileProvider by lazy { ProfileRealmProvider() }
    private val personProvider by lazy { PersonRealmProvider() }
    /** If result is empty string, that means user not authorized */
    private val userId get() = profileProvider.getAllItems().firstOrNull()?.id ?: ""
    /** flowable for storing network data into database with debounce */
    private val storeFlowable: Flowable<IMomentsAndPersons> = Flowable.create({ emitter ->
        storeDataListener = {
            currentData += it
            emitter.onNext(currentData)
        }
    }, BUFFER)
    private var currentData = CurrentMomentsPersonsResponse(listOf(), listOf(), 0, 0)
    private var storeDataListener: (IMomentsAndPersons) -> Unit = {}

    val personsFlowable: Flowable<List<IPerson>>
        get() = personProvider.getFlowable()
            .map { list -> list.map { PersonRealmModel.newInstance(it) } }

    init {
        /** debounce for avoiding writing to database frequently */
        storeFlowable
            .debounce(3, TimeUnit.SECONDS)
            .subscribe({ result ->
                personProvider.saveList(result.people)
            }, { error ->
                error.printStackTrace()
                toLog("error: ${error.message}")
            })
    }

    fun downloadUsers(startPosition: Int = 0,
                      size: Int = PeopleRequestUseCase.PAGE_SIZE): Single<out IMomentsAndPersons> =
        Api.getAllPeopleRx(userId, startPosition, size)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { it.dropNullPersons() }
            .doOnSuccess { storeDataListener(it) }

    private operator fun CurrentMomentsPersonsResponse.plus(new: IMomentsAndPersons) =
        this.copy(
            moments = moments + new.moments,
            people = people + new.people,
            totalMoments = totalMoments,
            totalPeople = totalPeople
        )

    private data class CurrentMomentsPersonsResponse(override val moments: List<IMoment>,
                                                     override val people: List<IPerson>,
                                                     override val totalMoments: Int,
                                                     override val totalPeople: Int) : IMomentsAndPersons

    @Suppress("SENSELESS_COMPARISON")
    private fun IMomentsAndPersons.dropNullPersons(): IMomentsAndPersons =
        CurrentMomentsPersonsResponse(moments,
            /** they can be null in server responses */
            people.filter { it.firstName != null && it.lastName != null },
            totalMoments,
            totalPeople)
}