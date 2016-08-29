package com.maximus.voicemessenger;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Muhammad on 28/08/2016.
 */
public class ChatMessageViewHolder extends RecyclerView.ViewHolder {

    TextView sender, key;
    public ChatMessageViewHolder(View itemView) {
        super(itemView);
        sender = (TextView) itemView.findViewById(R.id.sender);
        key = (TextView) itemView.findViewById(R.id.msg);
    }
}
