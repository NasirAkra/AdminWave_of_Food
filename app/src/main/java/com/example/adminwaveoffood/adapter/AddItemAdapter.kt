package com.example.adminwaveoffood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwaveoffood.databinding.ItemItemBinding

@Suppress("DEPRECATION")
class AddItemAdapter(private val menuItemName:ArrayList<String>, private val menuItemPrice:ArrayList<String>, private val menuItemImage:ArrayList<Int>): RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder>() {

  private val itemQuantities=IntArray(menuItemName.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding=ItemItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }



    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int =menuItemName.size

    inner class AddItemViewHolder (private val binding: ItemItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.foodName.text=menuItemName[position]
            binding.FoodPrices.text=menuItemPrice[position]
            binding.foodImageView.setImageResource(menuItemImage[position])
            binding.Plusbutton.setOnClickListener {
                increaseQuantities()
            }
            binding.MinusButton.setOnClickListener {
                decreaseQuantities()
            }
            binding.deletButton.setOnClickListener {
                deletItem()
            }


        }
        private fun increaseQuantities() {
            if (itemQuantities[position]<10)
            {
                itemQuantities[position]++
                binding.quantities.text=itemQuantities[position].toString()
            }




        }
        private fun decreaseQuantities() {
            if (itemQuantities[position]>1)
            {
                itemQuantities[position]--
                binding.quantities.text=itemQuantities[position].toString()

            }

        }
        private fun deletItem() {
            menuItemName.removeAt(position)
            menuItemPrice.removeAt(position)
            menuItemImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuItemName.size)

        }


    }




}