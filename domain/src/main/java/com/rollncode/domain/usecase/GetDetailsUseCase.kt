package com.rollncode.domain.usecase

import androidx.lifecycle.LiveData
import com.rollncode.domain.entity.Detail
import com.rollncode.domain.repository.DetailsRepository
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(private val detailsRepository: DetailsRepository) : BaseUseCase<Any?, LiveData<Detail>, Any?>() {
    override fun execute(params: Any?, onError: (Any?) -> Unit) = detailsRepository.getAll()
}