package ua.com.koval.andrey.cryptoapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ua.com.koval.andrey.cryptoapp.R
import ua.com.koval.andrey.cryptoapp.databinding.ItemCoinInfoBinding
import ua.com.koval.andrey.cryptoapp.domain.CoinInfo


class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var onCoinClickListener: OnCoinClickListener? = null
    var coinInfoList = arrayListOf<CoinInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding =
            LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        holder.bind(coinInfoList[position])
    }

    override fun getItemCount() = coinInfoList.size


    inner class CoinInfoViewHolder(item: View) :
        RecyclerView.ViewHolder(item) {
        private val binding = ItemCoinInfoBinding.bind(item)

        fun bind(coinPriceInfo: CoinInfo) {
            with(coinPriceInfo) {
                with(binding) {
                    val symbolsTemplate = context.resources.getString(R.string.symbols_template)
                    val lastUpdateTemplate =
                        context.resources.getString(R.string.last_update_template)
                    tvSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                    tvPrice.text = price
                    tvLastUpdate.text =
                        String.format(lastUpdateTemplate, lastUpdate)
                    Glide.with(context).load(imageUrl).into(ivLogoCoin)
                    itemView.setOnClickListener {
                        onCoinClickListener?.onCoinClick(coinPriceInfo)
                    }
                }
            }
        }
    }

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinInfo)
    }
}