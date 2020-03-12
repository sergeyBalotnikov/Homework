package ru.mail.sergey_balotnikov.contactresolver.model;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ContactResolver {
    public static final String LOG_TAG = "SVB";
    private static Uri URI_ALL_DATA =Uri.parse("content://ru.mail.sergey_balotnikov.ContactContentProvider/DATA/ALL");
    private static Uri URI_DELETE =Uri.parse("content://ru.mail.sergey_balotnikov.ContactContentProvider/DATA/#");

    public static ContactResolver newInstance(@NonNull Context context){
        return new ContactResolver(context.getContentResolver());
    }

    private ContentResolver contentResolver;

    private ContactResolver(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public List<Contact> getAllContact(){
        Cursor resultCursor = contentResolver.query(URI_ALL_DATA, null, null, null, null);
        ArrayList<Contact> resultList = new ArrayList<>();
        if (resultCursor!=null){
            if(resultCursor.moveToFirst()){
                int indexId = resultCursor.getColumnIndex("_id");
                int indexName = resultCursor.getColumnIndex("name");
                int indexNumber = resultCursor.getColumnIndex("number");
                int indexIsNumber = resultCursor.getColumnIndex("is_number");
                do{
                    Contact contact = new Contact(
                            resultCursor.getInt(indexId),
                            resultCursor.getString(indexName),
                            resultCursor.getString(indexNumber),
                            resultCursor.getInt(indexIsNumber) == 1
                    );
                    resultList.add(contact);
                }
                while (resultCursor.moveToNext());
            }
        } else {
            Log.d(LOG_TAG, "getAllContacts resultCursor==null");
        }
        return resultList;
    }

    public void deleteContactById(int id){
        Uri uri = ContentUris.withAppendedId(URI_DELETE, id);
        contentResolver.delete(uri, null, null);
    }

}
