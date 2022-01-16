package ua.com.koval.andrey.cryptoapp.domain

import androidx.lifecycle.LiveData
import ua.com.koval.andrey.cryptoapp.data.network.model.CoinNameDto

interface CoinRepository {
    fun getCoinInfoList(): LiveData<List<CoinInfo>>

    fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo>

    suspend fun loadData()
}