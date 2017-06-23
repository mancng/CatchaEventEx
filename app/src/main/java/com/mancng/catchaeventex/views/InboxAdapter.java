package com.mancng.catchaeventex.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mancng.catchaeventex.R;
import com.mancng.catchaeventex.activities.BaseFragmentActivity;

import java.util.ArrayList;

public class InboxAdapter extends RecyclerView.Adapter{

    private BaseFragmentActivity mBaseFragmentActivity;
    private List<ChatRoom> mChatRooms;
    private LayoutInflater mInflater;
    private ChatRoomListener mChatRoomListener;
    private String mCurrentUserEmailString;

    public InboxAdapter(BaseFragmentActivity baseFragmentActivity, ChatRoomListener chatRoomListener, String currentUserEmailString) {
        mBaseFragmentActivity = baseFragmentActivity;
        mChatRoomListener = chatRoomListener;
        mCurrentUserEmailString = currentUserEmailString;
        mInflater = mBaseFragmentActivity.getLayoutInflater();
        mChatRooms = new ArrayList<>();
    }

    public void setChatRooms(List<ChatRoom> chatRooms) {
        mChatRooms.clear();
        mChatRooms.addAll(chatRooms);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_inbox,parent, false);
        final InboxViewHolder inboxViewHolder = new InboxViewHolder(view);
        inboxViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatRoom chatRoom = (ChatRoom) inboxViewHolder.itemView.getTag();
                mChatRoomListener.onChatRoomClicked(chatRoom);
            }
        });

        return inboxViewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((InboxViewHolder) holder).populate(mBaseFragmentActivity,mChatRooms.get(position),mCurrentUserEmailString);

    }

    @Override
    public int getItemCount() {
        return mChatRooms.size();
    }

    public interface ChatRoomListener { //Interface to bring user to the chat when they click the items on the adapter
        void onChatRoomClicked (ChatRoom chatRoom);
    }

}