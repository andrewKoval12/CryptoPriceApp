package ua.com.koval.andrey.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import ua.com.koval.andrey.cryptoapp.data.database.AppDatabase
import ua.com.koval.andrey.cryptoapp.data.mapper.CoinMapper
import ua.com.koval.andrey.cryptoapp.data.workers.RefreshDataWorker
import ua.com.koval.andrey.cryptoapp.domain.CoinInfo
import ua.com.koval.andrey.cryptoapp.domain.CoinRepository

class CoinRepositoryImpl(
    private val application: Application
) : CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()

    private val mapper = CoinMapper()

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return coinInfoDao.getPriceList().map { coinListDBModel ->
            coinListDBModel.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return coinInfoDao.getPriceInfoAboutCoin(fromSymbol).map {
            mapper.mapDbModelToEntity(it)
        }
    }

    override fun loadData() {
        val worker = WorkManager.getInstance(application)
        worker.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }

}