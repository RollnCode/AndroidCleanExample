package com.rollncode.domain.usecase

import io.reactivex.Flowable

class PeopleRequestUseCase(override val errorHandler: (Throwable) -> Unit = {}) : IBaseUseCase {

    companion object {
        const val PAGE_SIZE = 500
    }

    val peopleFlowable: Flowable<List<IPerson>>
        get() =
            DataRepository.personsFlowable.mergeWith(getDataFlowable(0).toList().map { it.flatten() })

    /** get full list of people */
    private fun getDataFlowable(startPosition: Int, loadSize: Int = PAGE_SIZE): Flowable<List<IPerson>> =
        DataRepository.downloadUsers(startPosition, loadSize)
            .toFlowable()
            .map { it.people }
            .flatMap { list ->
                if (list.size == loadSize) Flowable.just(list).concatWith(getDataFlowable(startPosition + PAGE_SIZE))
                else Flowable.just(list)
            }
}