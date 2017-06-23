package com.mancng.catchaeventex.services;

import android.database.Observable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mancng.catchaeventex.R;
import com.mancng.catchaeventex.activities.BaseFragmentActivity;
import com.mancng.catchaeventex.views.InboxAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LiveMessageServices {

    public static LiveMessageServices mLiveMessageServices;

    private final int SERVER_SUCCESS = 4;
    private final int SERVER_FAILURE = 5;

    public static LiveMessageServices getInstance() {
        if (mLiveMessageServices == null) {
            return new LiveMessageServices();
        } else {
            return mLiveMessageServices;
        }
    }


    //ValueEventListener to get chat to InboxFragment
    public ValueEventListener getAllChats (final RecyclerView recyclerView, final TextView textView,
                                           final InboxAdapter adapter) {

        final List<ChatRoom> chatRooms = new ArrayList<>();

        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatRooms.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    ChatRoom chatRoom = snapshot.getValue(ChatRoom.class);
                    chatRooms.add(chatRoom);
                }

                if (chatRooms.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);

                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    adapter.setChatRooms(chatRooms);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }
}

    //Subscription to sendMessages
    public DisposableObserver sendMessages(final Socket socket, String eventId, String chatId, String messageId, String eventNameString,
                                           final BaseFragmentActivity activity) {

        final List<String> details = new ArrayList<>();
        details.add(eventId);
        details.add(chatId);
        details.add(messageId);
        details.add(eventNameString);

        Observable<List<String>> listObservable = Observable.just(details);

        return listObservable
                .subscribeOn(Schedulers.io())
                .map(new Function<List<String>, Integer>() {
                    @Override
                    public Integer apply(List<String> strings) throws Exception {
                        JSONObject sendData = new JSONObject(); //Make a jsondata to send to the server

                        try {
                            sendData.put("eventId", strings.get(0));
                            sendData.put("chatId", strings.get(1));
                            sendData.put("messageId", strings.get(2));
                            sendData.put("eventNameString", strings.get(3));

                            socket.emit("details", sendData);
                            return SERVER_SUCCESS;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            FirebaseCrash.report(e);
                            return SERVER_FAILURE;
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer value) {
                        if (value == 5) {
                            Toast.makeText(activity, R.string.server_error, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
