package ru.mail.sergey_balotnikov.homework2_2.task1;

import java.util.ArrayList;

public class ObserverContactList {

    private static ObserverContactList instance;
    private ArrayList<ListListener> listListeners = new ArrayList<ListListener>();

    public static ObserverContactList getInstance(){
        if(instance==null){
            instance = new ObserverContactList();
        }
        return instance;
    }

    private ObserverContactList() {
    }

    public void notyfiListChanged(final ArrayList<Contact> contacts){
        if(!listListeners.isEmpty()){
            for(ListListener listener:listListeners){
                listener.onContactListChanged(contacts);
            }

        }
    }

    public void subscribe(ListListener listener){
        if(listener!=null){
            listListeners.add(listener);
        }
    }

    public void unSubscribe(ListListener listener){
        if(listener!=null){
            listListeners.remove(listener);
        }
    }
}
