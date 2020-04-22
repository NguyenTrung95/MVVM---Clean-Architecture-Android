package com.nencer.thesieure.customview
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.nencer.thesieure.R
import com.paginate.recycler.LoadingListItemCreator


class CustomLoadingListItemCreator : LoadingListItemCreator {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val viewRoot = View.inflate(parent!!.context, R.layout.item_loading, null)
        viewRoot.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        return VH(viewRoot)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    }

    internal class VH(itemView: View) : RecyclerView.ViewHolder(itemView)
}
