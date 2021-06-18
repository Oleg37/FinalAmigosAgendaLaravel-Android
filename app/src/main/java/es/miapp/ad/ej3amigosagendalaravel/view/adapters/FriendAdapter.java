package es.miapp.ad.ej3amigosagendalaravel.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.miapp.ad.ej3amigosagendalaravel.databinding.ItemFriendBinding;
import es.miapp.ad.ej3amigosagendalaravel.model.rest.pojo.Friend;
import es.miapp.ad.ej3amigosagendalaravel.view.listeners.InterfaceListenerFriend;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    private final List<Friend> friendList;
    private final InterfaceListenerFriend listener;

    public FriendAdapter(List<Friend> friendList, InterfaceListenerFriend listener) {
        this.friendList = friendList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendViewHolder(ItemFriendBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        holder.init(friendList.get(position));

        holder.b.btEdit.setOnClickListener(v -> listener.onClickEdit(friendList.get(position)));

        holder.b.btDelete.setOnClickListener(v -> listener.onClickDelete(friendList.get(position)));

        holder.itemView.setOnClickListener(v -> listener.onClickFriend(friendList.get(position)));
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (friendList != null) {
            size = friendList.size();
        }
        return size;
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder {

        public ItemFriendBinding b;

        public FriendViewHolder(@NonNull ItemFriendBinding b) {
            super(b.getRoot());
            this.b = b;
        }

        public void init(Friend numCalls) {
            b.tvName.setText(numCalls.getName());
            b.tvPhone.setText(numCalls.getPhNumber());
            if (numCalls.getDateOfBirth() == null) {
                b.tvDOFBirth.setText("No hay fecha");
            } else {
                b.tvDOFBirth.setText(numCalls.getDateOfBirth());
            }
            b.tvCalls.setText(String.format("%s", numCalls.getTimes()));
        }
    }
}