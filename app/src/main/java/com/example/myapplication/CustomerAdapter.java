package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Customer;

import java.util.ArrayList;


public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder>  {

    Context context;
    ArrayList<Customer>arrayList;

    OnItemClickListener onItemClickListener;


    public CustomerAdapter(Context context, ArrayList<Customer> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.name.setText(arrayList.get(position).getName());
        holder.email.setText(arrayList.get(position).getEmail());
        holder.cin.setText(arrayList.get(position).getCin());
        holder.phoneNumber.setText(arrayList.get(position).getPhoneNumber());
        holder.matricule.setText(arrayList.get(position).getMatricule());
        holder.address.setText(arrayList.get(position).getAddress());

        holder.itemView.setOnClickListener(view -> onItemClickListener.OnClick(arrayList.get(position)));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
    TextView name,email,cin,phoneNumber,matricule,address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            email=itemView.findViewById(R.id.email);
            cin=itemView.findViewById(R.id.cin);
            phoneNumber=itemView.findViewById(R.id.phone);
            matricule=itemView.findViewById(R.id.matricule);
            address=itemView.findViewById(R.id.address);

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void OnClick(Customer customer);
    }
}
