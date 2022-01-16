package ua.com.koval.andrey.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ua.com.koval.andrey.cryptoapp.databinding.ActivityCoinPriceListBinding
import ua.com.koval.andrey.cryptoapp.domain.CoinInfo
import ua.com.koval.andrey.cryptoapp.presentation.adapters.CoinInfoAdapter


class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var mAdapter: CoinInfoAdapter

    private val binding by lazy { ActivityCoinPriceListBinding.inflate(layoutInflater) }


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
        viewModel.coinInfoList.observe(this) {
            mAdapter.coinInfoList = it as ArrayList<CoinInfo>
        }
    }

    private fun onClickListener() {
        mAdapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                val intent = CoinDetailActivity.newIntent(
                    this@CoinPriceListActivity,
                    coinPriceInfo.fromSymbol
                )
                startActivity(intent)
            }

        }
    }

    private fun init() {
        setContentView(binding.root)
        mAdapter = CoinInfoAdapter(this)
        binding.rvCoinPriceList.adapter = mAdapter
    }
}