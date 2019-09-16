package com.rollncode.domain.usecase

import androidx.lifecycle.LiveData
import com.rollncode.domain.entity.Details
import com.rollncode.domain.repository.DetailsRepository
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(private val detailsRepository: DetailsRepository) : BaseUseCase<Any?, LiveData<Details>, Any?>() {
    override fun execute(params: Any?, onError: (Any?) -> Unit) = detailsRepository.getAll()
}