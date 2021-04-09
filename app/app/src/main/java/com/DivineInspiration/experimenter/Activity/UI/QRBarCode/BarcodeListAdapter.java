package com.DivineInspiration.experimenter.Activity.UI.QRBarCode;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.Activity.UI.TrialsUI.TrialListAdapter;
import com.DivineInspiration.experimenter.R;
import com.google.zxing.BarcodeFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BarcodeListAdapter extends RecyclerView.Adapter<BarcodeListAdapter.ViewHolder>  {


    List<StringPair> pairs = new ArrayList<>();



    public BarcodeListAdapter(Context context){
        SharedPreferences pref = context.getSharedPreferences("Barcode", Context.MODE_PRIVATE);
        Map<String, ?> map = pref.getAll();
       map.forEach((key, item) -> pairs.add(new StringPair(key, (String)item)));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.barcode_item, null));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.getCode().setText(pairs.get(position).a);
        holder.getCommand().setText(pairs.get(position).b);
    }

    @Override
    public int getItemCount() {
        return pairs.size();
    }


    class StringPair{


        String a;
        String b;

        StringPair(String a ,String b){
            this.a = a;
            this.b = b;
        }


    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView code;

        public TextView getCode() {
            return code;
        }

        public TextView getCommand() {
            return command;
        }

        TextView command;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            code = itemView.findViewById(R.id.barCodeNumber);
            command = itemView.findViewById(R.id.barCodeCommand);
        }



    }
}
