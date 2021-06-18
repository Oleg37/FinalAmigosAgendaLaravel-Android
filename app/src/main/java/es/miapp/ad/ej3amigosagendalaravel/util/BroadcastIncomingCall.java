package es.miapp.ad.ej3amigosagendalaravel.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import es.miapp.ad.ej3amigosagendalaravel.model.rest.pojo.CallF;
import es.miapp.ad.ej3amigosagendalaravel.model.rest.pojo.Friend;
import es.miapp.ad.ej3amigosagendalaravel.view.activities.MainActivity;
import es.miapp.ad.ej3amigosagendalaravel.viewmodel.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED;
import static android.telephony.TelephonyManager.CALL_STATE_RINGING;
import static android.telephony.TelephonyManager.EXTRA_STATE_RINGING;

public class BroadcastIncomingCall extends BroadcastReceiver {

    private final boolean noRepeat = true;
    public MainActivity main;

    public void setMainActivityHandler(MainActivity main) {
        this.main = main;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ViewModel viewModel = new ViewModelProvider(main).get(ViewModel.class);

        if (!intent.getAction().equals(ACTION_PHONE_STATE_CHANGED)) {
            return;
        }

        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equalsIgnoreCase(EXTRA_STATE_RINGING)) {
            TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            telephony.listen(new PhoneStateListener() {
                @Override
                public void onCallStateChanged(int state, String phoneNumber) {
                    super.onCallStateChanged(state, phoneNumber);

                    if (state == CALL_STATE_RINGING) {
                        Log.v("XYZ", "CALL_STATE_RINGING");
                        return;
                    }

                    /*if (state == CALL_STATE_IDLE) {
                        Log.v("XYZ", "CALL_STATE_IDLE");
                        return;
                    }*/

                    /*if (state == LISTEN_CALL_STATE) {
                        Log.v("XYZ", "LISTEN_CALL_STATE");
                        return;
                    }

                    if (state == LISTEN_CALL_DISCONNECT_CAUSES) {
                        Log.v("XYZ", "LISTEN_CALL_DISCONNECT_CAUSES");
                        return;
                    }

                    if (state == CALL_STATE_OFFHOOK) {
                        Log.v("XYZ", "CALL_STATE_OFFHOOK");
                        return;
                    }


                    if (state == LISTEN_IMS_CALL_DISCONNECT_CAUSES) {
                        Log.v("XYZ", "LISTEN_IMS_CALL_DISCONNECT_CAUSES");
                        return;
                    }

                    if (state == LISTEN_CALL_FORWARDING_INDICATOR) {
                        Log.v("XYZ", "LISTEN_CALL_FORWARDING_INDICATOR");
                        return;
                    }*/

                    if (noRepeat) {
                        saveIncomingCall(viewModel, phoneNumber, new Date().getTime());
                    }
                }
            }, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    public void saveIncomingCall(ViewModel viewModel, String incomingCall, long callDate) {
        ArrayList<Friend> list = viewModel.getListMutableFriends().getValue();

        for (int i = 0; i < Objects.requireNonNull(list).size(); i++) {
            if (PhoneNumberUtils.compare(list.get(i).getPhNumber(), incomingCall)) {
                viewModel.insertCallF(new CallF(0, list.get(i).getId(), DateTransform.dateSQLHHMMSS(callDate)), new Callback<CallF>() {
                    @Override
                    public void onResponse(@NonNull Call<CallF> call, @NonNull Response<CallF> response) {
                        viewModel.getAllFriends();
                    }

                    @Override
                    public void onFailure(@NonNull Call<CallF> call, @NonNull Throwable t) {
                        Log.v("XYZ" + call.getClass(), t.getMessage());
                    }
                });
                break;
            }
        }
    }
}