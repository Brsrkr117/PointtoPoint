package com.example.pointtopoint;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    public List<orders> ordersList;
    public Context mcontext;

    public OrderListAdapter(List<orders> ordersList, Context mcontext) {
        this.ordersList = ordersList;
        this.mcontext = mcontext;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.orderidText.setText(ordersList.get(position).getOrderid());
        holder.ordertypeText.setText(ordersList.get(position).getOrdertype());

        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, "You have selected this Order to deliver", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mcontext, RiderOtpActivity.class);
                intent.putExtra("orderid", ordersList.get(position).getOrderid());
                intent.putExtra("ordertype", ordersList.get(position).getOrdertype());
                mcontext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TextView orderidText,ordertypeText;
        public RelativeLayout parentlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;

            orderidText=(TextView)mView.findViewById(R.id.textView1);
            ordertypeText=(TextView)mView.findViewById(R.id.textView2);
            parentlayout=(RelativeLayout)mView.findViewById(R.id.parent_layout);

        }
    }
}
