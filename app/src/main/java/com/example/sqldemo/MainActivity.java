package com.example.sqldemo;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MyDatabase database;
    private ListView lvDanhSach;
    private Button btnAdd;
    private ArrayList<Person> personList;
    private PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new MyDatabase(this);
        lvDanhSach = findViewById(R.id.lvDanhSach);
        btnAdd = findViewById(R.id.btnAdd);

        personList = new ArrayList<>();
        adapter = new PersonAdapter(this, personList);
        lvDanhSach.setAdapter(adapter);

        loadData();

        btnAdd.setOnClickListener(v -> showDialogAdd());
    }

    private void loadData() {
        personList.clear();
        Cursor cursor = database.getAllPersons();
        while (cursor.moveToNext()) {
            personList.add(new Person(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getString(3)
            ));
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private void showDialogAdd() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_person, null);
        EditText etName = dialogView.findViewById(R.id.etName);
        EditText etAge = dialogView.findViewById(R.id.etAge);
        EditText etAddress = dialogView.findViewById(R.id.etAddress);

        new AlertDialog.Builder(this)
            .setTitle("Them moi person")
            .setView(dialogView)
            .setPositiveButton("Them", (dialog, which) -> {
                String name = etName.getText().toString();
                String ageStr = etAge.getText().toString();
                String address = etAddress.getText().toString();

                if (name.isEmpty() || ageStr.isEmpty() || address.isEmpty()) {
                    Toast.makeText(this, "Vui long nhap day du thong tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                database.insertPerson(name, Integer.parseInt(ageStr), address);
                loadData();
                Toast.makeText(this, "Them thanh cong", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Huy", null)
            .show();
    }

    public void showDialogUpdate(Person person) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_person, null);
        EditText etName = dialogView.findViewById(R.id.etName);
        EditText etAge = dialogView.findViewById(R.id.etAge);
        EditText etAddress = dialogView.findViewById(R.id.etAddress);

        etName.setText(person.getName());
        etAge.setText(String.valueOf(person.getAge()));
        etAddress.setText(person.getAddress());

        new AlertDialog.Builder(this)
            .setTitle("Cap nhat person")
            .setView(dialogView)
            .setPositiveButton("Luu", (dialog, which) -> {
                String name = etName.getText().toString();
                String ageStr = etAge.getText().toString();
                String address = etAddress.getText().toString();

                if (name.isEmpty() || ageStr.isEmpty() || address.isEmpty()) {
                    Toast.makeText(this, "Vui long nhap day du thong tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                database.updatePerson(person.getId(), name, Integer.parseInt(ageStr), address);
                loadData();
                Toast.makeText(this, "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Huy", null)
            .show();
    }

    public void showDialogDelete(Person person) {
        new AlertDialog.Builder(this)
            .setTitle("Xoa person")
            .setMessage("Ban co muon xoa " + person.getName() + "?")
            .setPositiveButton("Xoa", (dialog, which) -> {
                database.deletePerson(person.getId());
                loadData();
                Toast.makeText(this, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Huy", null)
            .show();
    }
}
