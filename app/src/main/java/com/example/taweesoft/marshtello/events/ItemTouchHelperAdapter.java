package com.example.taweesoft.marshtello.events;

/**
 * Created by TAWEESOFT on 3/7/16 AD.
 */
public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}