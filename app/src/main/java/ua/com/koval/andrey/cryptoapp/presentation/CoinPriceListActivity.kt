package ua.com.koval.andrey.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ua.com.koval.andrey.cryptoapp.presentation.adapters.CoinInfoAdapter
import ua.com.koval.andrey.cryptoapp.databinding.ActivityCoinPriceListBinding
import ua.com.koval.andrey.cryptoapp.data.model.CoinPriceInfo


class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var mAdapter: CoinInfoAdapter

    private lateinit var binding: ActivityCoinPriceListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        onClickListener()
        loadData()
    }

    private fun loadData() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[CoinViewModel::class.java]
        viewModel.priceList.observe(this, {
            mAdapter.coinInfoList = it as ArrayList<CoinPriceInfo>
        })
    }

    private fun onClickListener() {
        mAdapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                val intent = CoinDetailActivity.newIntent(
                    this@CoinPriceListActivity,
                    coinPriceInfo.fromSymbol
                )
                startActivity(intent)
            }
        }
    }

    private fun init() {
        binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAdapter = CoinInfoAdapter(this)
        binding.rvCoinPriceList.adapter = mAdapter
    }
}