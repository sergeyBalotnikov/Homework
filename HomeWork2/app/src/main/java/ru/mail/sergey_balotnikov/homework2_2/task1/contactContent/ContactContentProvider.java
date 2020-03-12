package ru.mail.sergey_balotnikov.homework2_2.task1.contactContent;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.mail.sergey_balotnikov.homework2_2.task1.ContactDao;
import ru.mail.sergey_balotnikov.homework2_2.task1.ContactRoomDatabase;

public class ContactContentProvider extends ContentProvider {

    public static final String LOG_TAG="SVB";
    public static final String AUTHORITY = "ru.mail.sergey_balotnikov.ContactContentProvider";
    public static final int KEY_ALL_DATA = 121;
    public static final int KEY_DELETE_CONTACT = 144;
    private static final UriMatcher URI_MATCHER;
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY,"DATA/ALL", KEY_ALL_DATA);
        URI_MATCHER.addURI(AUTHORITY,"DATA/#", KEY_DELETE_CONTACT);
    }
    private ContactDao contactDao;

    @Override
    public boolean onCreate() {
        if(getContext()!=null){
            contactDao = ContactRoomDatabase.getDatabase(getContext()).contactDao();
        } else {
            Log.d(LOG_TAG, "provider on create getContext is null");
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor = null;
        if(URI_MATCHER.match(uri)==KEY_ALL_DATA){
            if(contactDao!=null){
                cursor=contactDao.getAllContacts();
            } else {
                Log.d(LOG_TAG, "Query() contactDao is null");
            }
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int result = -1;
        if(URI_MATCHER.match(uri)==KEY_DELETE_CONTACT){
            if(contactDao!=null){
                result = contactDao.deleteById(ContentUris.parseId(uri));
            } else {
                Log.d(LOG_TAG, "delete() contactDao is null");
            }
        }
        return result;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
