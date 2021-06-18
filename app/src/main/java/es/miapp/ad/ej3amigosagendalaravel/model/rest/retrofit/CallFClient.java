package es.miapp.ad.ej3amigosagendalaravel.model.rest.retrofit;

import java.util.ArrayList;

import es.miapp.ad.ej3amigosagendalaravel.model.rest.pojo.CallF;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CallFClient {

    @GET("call")
    Call<ArrayList<CallF>> getCallFs();

    @GET("call/{id}")
    Call<CallF> getCallF(@Path("id") long id);

    @POST("call")
    Call<CallF> insertCallF(@Body CallF CallF);

    @PUT("call/{id}")
    Call<Boolean> updateCallF(@Path("id") long id, @Body CallF callF);

    @DELETE("call/{id}")
    Call<CallF> deleteCallF(@Path("id") long id);
}
