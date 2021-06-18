package es.miapp.ad.ej3amigosagendalaravel.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.miapp.ad.ej3amigosagendalaravel.databinding.ItemContactBinding;
import es.miapp.ad.ej3amigosagendalaravel.model.rest.pojo.CallF;
import es.miapp.ad.ej3amigosagendalaravel.util.DateTransform;

public class CallFAdapter extends RecyclerView.Adapter<CallFAdapter.CallFViewHolder> {

    private final List<CallF> callFList;

    public CallFAdapter(List<CallF> list) {
        this.callFList = list;
    }

    @NonNull
    @Override
    public CallFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CallFViewHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull CallFViewHolder holder, int position) {
        holder.init(callFList.get(position));
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (callFList != null) {
            size = callFList.size();
        }
        return size;
    }

    public static class CallFViewHolder extends RecyclerView.ViewHolder {

        public ItemContactBinding b;

        public CallFViewHolder(@NonNull ItemContactBinding b) {
            super(b.getRoot());
            this.b = b;
        }

        public void init(CallF callF) {
            b.tvNameDate.setText(DateTransform.DATEBeautyYYYYMMDD(callF.getCallDate()));
            b.tvHourPhone.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            b.tvHourPhone.setText(DateTransform.dateBeautyHHMMSS(callF.getCallDate()));
        }
    }
}