package es.miapp.ad.ej3amigosagendalaravel.model.rest.retrofit;

import java.util.ArrayList;

import es.miapp.ad.ej3amigosagendalaravel.model.rest.pojo.Friend;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FriendClient {

    @GET("friend")
    Call<ArrayList<Friend>> getFriends();

    @GET("friend/{id}")
    Call<Friend> getFriend(@Path("id") long id);

    @POST("friend")
    Call<Friend> insertFriend(@Body Friend friend);

    @PUT("friend/{id}")
    Call<Friend> updateFriend(@Path("id") long id, @Body Friend friend);

    @DELETE("friend/{id}")
    Call<Friend> deleteFriend(@Path("id") long id);

    @DELETE("destroyAll")
    Call<ArrayList<Friend>> deleteAll();
}
