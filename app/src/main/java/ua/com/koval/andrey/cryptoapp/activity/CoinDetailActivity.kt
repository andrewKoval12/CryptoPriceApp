package ua.com.koval.andrey.cryptoapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ua.com.koval.andrey.cryptoapp.databinding.ActivityCoinDetailBinding
import ua.com.koval.andrey.cryptoapp.viewmodel.CoinViewModel

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    private lateinit var binding: ActivityCoinDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
    }

    private fun setData() {
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[CoinViewModel::class.java]
        if (fromSymbol != null) {
            viewModel.getDetailInfo(fromSymbol).observe(this, {
                with(binding) {
                    with(it) {
                        tvPrice.text = price
                        tvMinPrice.text = lowDay
                        tvMaxPrice.text = highDay
                        tvLastMarket.text = lastMarket
                        tvLastUpdate.text = getFormattedTime()
                        tvFromSymbol.text = fromSymbol
                        tvToSymbol.text = toSymbol
                        Picasso.get().load(getFullImgUrl()).into(ivLogoCoin)
                    }
                }
            })
        }
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}