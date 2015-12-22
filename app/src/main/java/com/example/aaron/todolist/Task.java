package com.example.aaron.todolist;

import io.realm.RealmObject;

/**
 * Created by aaron on 12/22/15.
 */
public class Task extends RealmObject {

    private String note;

    public void setNote(String myNote){
        note = myNote;
    }

    public String getNote(){
        return note;
    }
}
