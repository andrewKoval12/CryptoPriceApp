package ua.com.koval.andrey.cryptoapp.domain

class LoadDataUseCase(
    private val repository: CoinRepository
) {
   suspend operator fun invoke() = repository.loadData()
}