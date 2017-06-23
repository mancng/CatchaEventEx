package com.mancng.catchaeventex.views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class InboxViewHolder extends RecyclerView.ViewHolder{

    @BindView(R2.id.list_inbox_eventName)
    TextView mEventName;

    @BindView(R2.id.list_inbox_lastMessage)
    TextView mLastMessage;

    @BindView(R2.id.list_inbox_timeStamp)
    TextView mTimeStamp;

    @BindView(R2.id.list_inbox_newMessageIndicator)
    ImageView mNewMessageIndicator;


    public InboxViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void populate (Context context, ChatRoom chatRoom, String currentUserEmail) {
        itemView.setTag(chatRoom);

        String mEventNameString = chatRoom.getEventNameString();

        mEventName.setText(mEventNameString); //Populate user name

        String lastMessageSent = chatRoom.getLastMessageSent(); //Populate last message

        if (!chatRoom.isSentLastMessage() && chatRoom.getLastMessageSenderEmail().equals(currentUserEmail)) { //Check and see if this last message is a draft
            lastMessageSent = "Me: " + lastMessageSent + " (Draft)";
        }

        if (chatRoom.isSentLastMessage() && chatRoom.getLastMessageSenderEmail().equals(currentUserEmail)) {
            lastMessageSent = "Me: " + lastMessageSent;
        }

        if (!chatRoom.getLastMessageSenderEmail().equals(currentUserEmail)) {
            lastMessageSent = chatRoom.getLastMessageSenderName() + ": " + lastMessageSent;
        }

        if (!chatRoom.isLastMessageRead()) { //Populate new message indicator
            mNewMessageIndicator.setVisibility(View.VISIBLE);
        } else {
            mNewMessageIndicator.setVisibility(View.GONE);
        }

        mLastMessage.setText(lastMessageSent); //Populate last message

        Long unixTimeStamp = Long.valueOf(chatRoom.getMessageTimeStamp()); //Convert TimeStamp
        DateTime getTimeStamp = new DateTime(unixTimeStamp);
        DateTimeFormatter timeFormat = DateTimeFormat.forPattern("h:mm a");

        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("MMM dd, yyyy");
        String date = dateFormat.print(unixTimeStamp);

        String timeStamp = timeFormat.print(getTimeStamp);
        mTimeStamp.setText(timeStamp); //Populate time stamp

        //Get midnight reference
        DateTime midnightToday = DateTime.now().withTimeAtStartOfDay();
        DateTime myDateTime = getTimeStamp;

        //Change time stamp display when current time passes midnight
        if (myDateTime.isAfter(midnightToday)) {
            mTimeStamp.setText(timeStamp);
        } else {
            mTimeStamp.setText(date);
        }
    }
}