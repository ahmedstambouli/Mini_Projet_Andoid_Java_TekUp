package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Customer;
import com.example.myapplication.Model.Emplye;

import java.util.ArrayList;

public class EmployeAdapter extends RecyclerView.Adapter<EmployeAdapter.ViewHolder>{
    Context context;
    ArrayList<Emplye> arrayList;

    OnItemClickListener onItemClickListener;


    public EmployeAdapter(Context context, ArrayList<Emplye> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public EmployeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainemployer,parent,false);
        return new EmployeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeAdapter.ViewHolder holder, int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.email.setText(arrayList.get(position).getEmail());
        holder.cin.setText(arrayList.get(position).getCin());
        holder.phoneNumber.setText(arrayList.get(position).getPhoneNumber());
        holder.address.setText(arrayList.get(position).getAddress());

        holder.itemView.setOnClickListener(view -> onItemClickListener.OnClick(arrayList.get(position)));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,email,cin,phoneNumber,address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nameem);
            email=itemView.findViewById(R.id.emailem);
            cin=itemView.findViewById(R.id.cinem);
            phoneNumber=itemView.findViewById(R.id.phoneem);
            address=itemView.findViewById(R.id.addressem);

        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void OnClick(Emplye emplye);
    }
}
