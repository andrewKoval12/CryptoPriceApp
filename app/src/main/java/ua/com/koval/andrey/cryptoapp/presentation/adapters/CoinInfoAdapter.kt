package ua.com.koval.andrey.cryptoapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ua.com.koval.andrey.cryptoapp.R
import ua.com.koval.andrey.cryptoapp.databinding.ItemCoinInfoBinding
import ua.com.koval.andrey.cryptoapp.domain.CoinInfo
import java.lang.String.format


class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoViewHolder>() {

    var onCoinClickListener: OnCoinClickListener? = null
    var coinInfoList = arrayListOf<CoinInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding =
            ItemCoinInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder.binding) {
            with(coin) {
                val symbolsTemplate =
                    context.resources.getString(R.string.symbols_template)
                val lastUpdateTemplate =
                    context.resources.getString(R.string.last_update_template)
                tvSymbols.text = format(symbolsTemplate, fromSymbol, toSymbol)
                tvPrice.text = price
                tvLastUpdate.text = format(lastUpdateTemplate, lastUpdate)
                Glide.with(context).load(imageUrl).into(ivLogoCoin)
                root.setOnClickListener {
                    onCoinClickListener?.onCoinClick(this)
                }
            }
        }
    }

    override fun getItemCount() = coinInfoList.size


    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinInfo)
    }

}
