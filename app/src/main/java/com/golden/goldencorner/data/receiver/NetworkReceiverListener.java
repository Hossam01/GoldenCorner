package com.golden.goldencorner.data.receiver;

public interface NetworkReceiverListener {
    // network available if flag is true
    void onNetworkReceiverChange(boolean flag);
}
