package com.example.pointtopoint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    public List<orders> ordersList;

    public OrderListAdapter(List<orders> ordersList){
        this.ordersList=ordersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.orderidText.setText(ordersList.get(position).getOrderid());
        holder.ordertypeText.setText(ordersList.get(position).getOrdertype());
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TextView orderidText,ordertypeText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;

            orderidText=(TextView)mView.findViewById(R.id.textView1);
            ordertypeText=(TextView)mView.findViewById(R.id.textView2);

        }
    }
}
