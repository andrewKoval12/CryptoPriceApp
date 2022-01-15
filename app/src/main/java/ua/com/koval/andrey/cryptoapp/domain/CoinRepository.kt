package ua.com.koval.andrey.cryptoapp.domain

import androidx.lifecycle.LiveData
import ua.com.koval.andrey.cryptoapp.data.model.CoinInfo

interface CoinRepository {
    fun getCoinInfoList(): LiveData<List<CoinInfo>>

    fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo>
}