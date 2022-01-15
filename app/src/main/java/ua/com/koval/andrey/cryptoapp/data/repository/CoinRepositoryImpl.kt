package ua.com.koval.andrey.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.delay
import ua.com.koval.andrey.cryptoapp.data.database.AppDatabase
import ua.com.koval.andrey.cryptoapp.data.mapper.CoinMapper
import ua.com.koval.andrey.cryptoapp.data.network.ApiFactory
import ua.com.koval.andrey.cryptoapp.data.network.ApiService
import ua.com.koval.andrey.cryptoapp.domain.CoinInfo
import ua.com.koval.andrey.cryptoapp.domain.CoinRepository

class CoinRepositoryImpl(
    private val application: Application
): CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val apiService = ApiFactory.apiService

    private val mapper = CoinMapper()

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDao.getPriceList()){ it ->
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)){
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true) {
            val topCoins = apiService.getTopCoinsInfo(limit = 50)
            val fSymbols = mapper.mapNamesListToString(topCoins)
            val jsonContainer = apiService.getFullPriceList(fSyms = fSymbols)
            val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
            val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
            coinInfoDao.insertPriceList(dbModelList)
            delay(10000)
        }
    }

}