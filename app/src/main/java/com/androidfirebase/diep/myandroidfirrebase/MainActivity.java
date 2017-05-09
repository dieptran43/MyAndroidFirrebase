package com.androidfirebase.diep.myandroidfirrebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ChildEventListener{
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AdapterUser adapterUser;
    List<User> usersList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        listView = (ListView) findViewById(R.id.listData);
        usersList = new ArrayList<>();
        adapterUser = new AdapterUser(this, R.layout.custom_listview, usersList);
        listView.setAdapter(adapterUser);

       //databaseReference.addChildEventListener(this);
        //Query order by
        Query query = databaseReference.orderByChild("tuoi").limitToLast(2).startAt(2);
        query.addChildEventListener(this);
    }


    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.d("ADD", "Da add");
        User user = dataSnapshot.getValue(User.class);
        usersList.add(user);
        adapterUser.notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Log.d("Changed", "Da Changed");
        //User user = dataSnapshot.getValue(User.class);
       // String userKey = dataSnapshot.getKey();
        //Log.d("Changed2", userKey);
       // adapterUser.notifyDataSetChanged();

        usersList.clear();
        databaseReference.removeEventListener(this);
        databaseReference.addChildEventListener(this);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        Log.d("Remove", "Da remove");
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        Log.d("Move", "Da move");
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.d("Cancel", "Da Cancel");
    }
}

class User{

    String hoten;
    boolean gioitinh;
    Long tuoi;

    public  User(){

    }
    public  User(String hoten, boolean gioitinh, Long tuoi){
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.tuoi = tuoi;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public boolean isGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public Long getTuoi() {
        return tuoi;
    }

    public void setTuoi(Long tuoi) {
        this.tuoi = tuoi;
    }

}
