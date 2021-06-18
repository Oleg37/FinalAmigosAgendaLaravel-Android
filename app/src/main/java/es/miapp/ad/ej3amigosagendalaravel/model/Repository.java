package es.miapp.ad.ej3amigosagendalaravel.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import es.miapp.ad.ej3amigosagendalaravel.model.rest.pojo.CallF;
import es.miapp.ad.ej3amigosagendalaravel.model.rest.pojo.Friend;
import es.miapp.ad.ej3amigosagendalaravel.model.rest.retrofit.CallFClient;
import es.miapp.ad.ej3amigosagendalaravel.model.rest.retrofit.FriendClient;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Getter
@Setter
public class Repository {

    private final String RETROFIT_URL = "https://informatica.ieszaidinvergeles.org:10026/laraveles/ej3AmigosAgendaLaravel/public/api/";
    private Context context;

    private Friend friend;

    private FriendClient friendClient;
    private CallFClient callFClient;

    private Retrofit retrofit;

    private MutableLiveData<ArrayList<Friend>> listMutableFriends = new MutableLiveData<>();
    private MutableLiveData<ArrayList<CallF>> listMutableCallsF = new MutableLiveData<>();

    public Repository(Context context) {
        this.context = context;
        retrofit = new Retrofit.Builder()
                .baseUrl(RETROFIT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        friendClient = retrofit.create(FriendClient.class);
        callFClient = retrofit.create(CallFClient.class);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Friend -> Insert - Update - Delete
    ///////////////////////////////////////////////////////////////////////////

    public void insertFriend(Friend friend) {
        Call<Friend> request = friendClient.insertFriend(friend);

        request.enqueue(new Callback<Friend>() {
            @Override
            public void onResponse(@NonNull Call<Friend> call, @NonNull Response<Friend> response) {
                if (!response.isSuccessful()) {
                    Log.v("XYZ - ERROR", "Ha ocurrido un error");
                    Toast.makeText(context, "¡Este usuario ya existe!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.v("XYZ4", "Agregado con éxito");
                getAllFriends();
            }

            @Override
            public void onFailure(@NonNull Call<Friend> call, @NonNull Throwable t) {
                Log.v("XYZ3", t.getMessage() + request);

                noInternet(t);
            }
        });
    }

    public void updateFriend(long id, Friend friend) {
        Call<Friend> request = friendClient.updateFriend(id, friend);

        request.enqueue(new Callback<Friend>() {
            @Override
            public void onResponse(@NonNull Call<Friend> call, @NonNull Response<Friend> response) {
                Log.v("XYZ", "Modificado con éxito");
            }

            @Override
            public void onFailure(@NonNull Call<Friend> call, @NonNull Throwable t) {
                Log.v("XYZ" + call.getClass(), t.getMessage());
                noInternet(t);
            }
        });
    }

    public void deleteFriend(long id) {
        Call<Friend> request = friendClient.deleteFriend(id);

        request.enqueue(new Callback<Friend>() {
            @Override
            public void onResponse(@NonNull Call<Friend> call, @NonNull Response<Friend> response) {
                Log.v("XYZ", "Eliminado con éxito");
                getAllFriends();
            }

            @Override
            public void onFailure(@NonNull Call<Friend> call, @NonNull Throwable t) {
                Log.v("XYZ" + call.getClass(), t.getMessage());
                noInternet(t);
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    // Call -> Insert - Update - Delete
    ///////////////////////////////////////////////////////////////////////////

    public void insertCallF(CallF callF, Callback<CallF> callFCallback) {
        Call<CallF> request = callFClient.insertCallF(callF);

        request.enqueue(callFCallback);
    }

    public void updateCallF(long id, CallF callF) {
        Call<Boolean> request = callFClient.updateCallF(id, callF);

        request.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                Log.v("XYZ", "Modificado con éxito");
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                Log.v("XYZ" + call.getClass(), t.getMessage());
                noInternet(t);
            }
        });
    }

    public void deleteCallF(long id) {
        Call<CallF> request = callFClient.deleteCallF(id);

        request.enqueue(new Callback<CallF>() {
            @Override
            public void onResponse(@NonNull Call<CallF> call, @NonNull Response<CallF> response) {
                Log.v("XYZ", "Eliminado con éxito");
            }

            @Override
            public void onFailure(@NonNull Call<CallF> call, @NonNull Throwable t) {
                Log.v("XYZ" + call.getClass(), t.getMessage());
                noInternet(t);
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    // List Operations
    ///////////////////////////////////////////////////////////////////////////

    public void getAllFriends() {
        Call<ArrayList<Friend>> call = friendClient.getFriends();

        call.enqueue(new Callback<ArrayList<Friend>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Friend>> call, @NonNull Response<ArrayList<Friend>> response) {
                listMutableFriends.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Friend>> call, @NonNull Throwable t) {
                noInternet(t);
            }
        });
    }

    public void getAllCalls() {
        Call<ArrayList<CallF>> call = callFClient.getCallFs();

        call.enqueue(new Callback<ArrayList<CallF>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<CallF>> call, @NonNull Response<ArrayList<CallF>> response) {
                listMutableCallsF.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<CallF>> call, @NonNull Throwable t) {
                noInternet(t);
            }
        });
    }

    public void deleteAll() {
        Call<ArrayList<Friend>> request = friendClient.deleteAll();

        request.enqueue(new Callback<ArrayList<Friend>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Friend>> call, @NonNull Response<ArrayList<Friend>> response) {
                Log.v("XYZ", "¡Todos los amigos eliminados!");
                getAllFriends();
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Friend>> call, @NonNull Throwable t) {
                Log.v("XYZ" + call.getClass(), t.getMessage());
                noInternet(t);
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    // Extra Operations
    ///////////////////////////////////////////////////////////////////////////

    private void noInternet(@NotNull Throwable t) {
        if (Objects.requireNonNull(t.getMessage()).contains("Unable to resolve host")) {
            Toast.makeText(context, "No hay conexión a internet!", Toast.LENGTH_SHORT).show();
        }
    }
}
