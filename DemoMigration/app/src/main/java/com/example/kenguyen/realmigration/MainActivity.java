package com.example.kenguyen.realmigration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.kenguyen.realmigration.model.MyMigration;
import com.example.kenguyen.realmigration.model.Person;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private RealmConfiguration config;
    private RealmResults<Person> personList;

    private ListView listView;
    private List<String> data = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configRealm();

      //  initData();

        query();

        addData();

        showInList();
    }

    void configRealm() {
        config = new RealmConfiguration.Builder(this)
                .name("DemoMigration.realm")
                .schemaVersion(1)
                .migration(new MyMigration())
                .build();

        realm = Realm.getInstance(config);
    }

    void initData() {
        realm.beginTransaction();

        realm.copyToRealm(new Person("Hứa", "Quý", 21));
        realm.copyToRealm(new Person("Nguyễn", "Thịnh", 22));
        realm.copyToRealm(new Person("Nguyễn", "Quốc", 23));
        realm.copyToRealm(new Person("Nguyễn", "Tài", 24));

        realm.commitTransaction();
    }

    void query() {
        personList = realm.where(Person.class)
                .findAll();
    }

    void addData() {
        for (Person p : personList) {
            data.add(p.toString());
        }
    }

    void showInList() {
        listView = (ListView) findViewById(R.id.listview);

        ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
    }
}