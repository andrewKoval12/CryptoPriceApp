package ua.com.koval.andrey.cryptoapp.domain

class GetCoinInfoListUseCase(
    private val repository: CoinRepository
    ){
    operator fun invoke() = repository.getCoinInfoList()
}