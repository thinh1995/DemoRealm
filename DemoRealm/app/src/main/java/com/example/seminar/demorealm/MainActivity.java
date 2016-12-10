package com.example.seminar.demorealm;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.seminar.demorealm.Model.Person;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {

    private EditText txtName, txtAge;
    private Button btnAdd, btnView, btnDelete;
    private TextView txtView;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();

        txtName = (EditText) findViewById(R.id.txtName);
        txtAge = (EditText) findViewById(R.id.txtAge);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        txtView = (TextView) findViewById(R.id.txtView);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_to_database(txtName.getText().toString().trim(), Integer.parseInt(txtAge.getText().toString().trim()));
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh_database();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Name = txtName.getText().toString();
                delete_from_database(Name);
            }
        });


    }

    private void delete_from_database(String Name) {
        final RealmResults<Person> persons = realm.where(Person.class).equalTo("Name", Name).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                persons.deleteFromRealm(0); // Delete and remove object directly
            }
        });
    }

    private void refresh_database() {
        RealmResults<Person> result = realm.where(Person.class).findAllAsync();
        result.load();
        String output="";

        for(Person person:result){
            output+=person.toString();
        }
        txtView.setText(output);
    }

    private void save_to_database(final String Name, final int Age) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Person person = bgRealm.createObject(Person.class);
                person.setName(Name);
                person.setAge(Age);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.v("Success", "---------->OK<--------------");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.e("Failed", error.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
