package com.longthai.audiomixer.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.longthai.audiomixer.R
import com.longthai.audiomixer.`interface`.MusicCounterListener
import com.longthai.audiomixer.data.GlobalData
import com.longthai.audiomixer.ui.PlayingActivity
import com.longthai.audiomixer.ui.RecordingActivity

class ModelListAdapter(val list: List<GlobalData>?,val context:Context) : RecyclerView.Adapter<ModelListAdapter.MyViewHolder>()
{
    val Selectionlist = ArrayList<Int>()
    var Totalclicks:Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list!!.size
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {

        holder.songname.text = list!!.get(position).name
        holder.username.text = list!!.get(position).username
        holder.item_id.text = "ID:${list!!.get(position).id}"
        holder.licensed.text = list!!.get(position).license


        holder.playsong.setOnClickListener {

            val intent = Intent(context, PlayingActivity::class.java).apply {
                this.putExtra("id",list!!.get(position).id)
            }


            context.startActivity(intent)


        }





        holder.itemview.setOnClickListener {

            if(Selectionlist.contains(list!!.get(position).id)){


                Selectionlist.remove(list!!.get(position).id)
                holder.itemview.setBackgroundColor(Color.parseColor("#ffffff"))
                Log.d("Array Adapter","$Selectionlist")

                if (context is MusicCounterListener) {
                    (context as MusicCounterListener).callback(false,list!!.get(position).id)
                }



            }else{

                Selectionlist.add(list!!.get(position).id)
                holder.itemview.setBackgroundColor(Color.parseColor("#008577"))
                Log.d("Array Adapter","$Selectionlist")

                if (context is MusicCounterListener) {
                    (context as MusicCounterListener).callback(true,list!!.get(position).id)
                }


//                if(Selectionlist.size.equals(2)){
//                    Log.d("Navigate" ,"Called totalclicks in Two")
//
//                    val intent = Intent(context, RecordingActivity::class.java).apply {
//                        this.putExtra("list",Selectionlist)
//                    }
//
//
//                    context.startActivity(intent)
//
//                }

            }









        }


    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item)
    {
        val songname: TextView = item.findViewById(R.id.songname_)
        val licensed: TextView = item.findViewById(R.id.licensed_)
        val username: TextView = item.findViewById(R.id.singername_)
        val itemview:RelativeLayout = item.findViewById(R.id.itemview)
        val item_id:TextView = item.findViewById(R.id.Item_ID)
        val playsong:ImageView = item.findViewById(R.id.playsong_)

    }
}