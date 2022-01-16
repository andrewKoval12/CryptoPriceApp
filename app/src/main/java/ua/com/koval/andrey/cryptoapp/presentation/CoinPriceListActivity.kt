package ua.com.koval.andrey.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ua.com.koval.andrey.cryptoapp.R
import ua.com.koval.andrey.cryptoapp.databinding.ActivityCoinPriceListBinding
import ua.com.koval.andrey.cryptoapp.domain.CoinInfo
import ua.com.koval.andrey.cryptoapp.presentation.adapters.CoinInfoAdapter


class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var mAdapter: CoinInfoAdapter

    private val binding by lazy { ActivityCoinPriceListBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mAdapter = CoinInfoAdapter(this)
        binding.rvCoinPriceList.adapter = mAdapter
        onClickListener()
        loadData()
    }

    private fun loadData() {
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(this) { mAdapter.submitList(it) }
        binding.rvCoinPriceList.itemAnimator = null
    }

    private fun onClickListener() {
        mAdapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                isOnePaneMode(coinPriceInfo)
            }
        }
    }

    private fun isOnePaneMode(coinPriceInfo: CoinInfo) {
        if (binding.fragmentContainer == null){
            launchDetailActivity(coinPriceInfo.fromSymbol)
        }else{
            launchDetailFragment(coinPriceInfo.fromSymbol)
        }
    }

    private fun launchDetailActivity(fSymbol: String) {
        val intent = CoinDetailActivity.newIntent(
            this@CoinPriceListActivity,
            fSymbol
        )
        startActivity(intent)
    }

    private fun launchDetailFragment(fSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fSymbol))
            .addToBackStack(null)
            .commit()
    }

}