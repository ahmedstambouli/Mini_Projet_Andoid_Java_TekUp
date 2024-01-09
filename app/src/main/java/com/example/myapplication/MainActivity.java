package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Customer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    FloatingActionButton buttonF;
    EditText name,email,cin,phoneNumber,matricule,address;
    TextView emp;
    FirebaseDatabase database;
    EditText searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView=(EditText)findViewById(R.id.search) ;

        // Configuring the Firebase Database
        FirebaseApp.initializeApp(MainActivity.this);
        database=FirebaseDatabase.getInstance();


        emp = findViewById(R.id.empty);

        buttonF=findViewById(R.id.buttonAdd);


        // Listener for the add customer/employee button
        buttonF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logic of adding a customer/employee via a dialog box
                View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_customer,null);
                name=view1.findViewById(R.id.nameC);
                email=view1.findViewById(R.id.emailC);
                cin=view1.findViewById(R.id.CINC);
                phoneNumber=view1.findViewById(R.id.numberP);
                matricule=view1.findViewById(R.id.matriculeC);
                address=view1.findViewById(R.id.adresseC);

                // Création d'une boîte de dialogue pour ajouter un nouveau client
                AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Add Customer")
                        .setView(view1)

                        // Logic triggered when clicking the "Add" button
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Checking the entered data before adding the customer to the database
                                if (Objects.requireNonNull(name.getText()).toString().isEmpty() )
                                {
                                    Toast.makeText(MainActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
                                }
                                else if (Objects.requireNonNull(email.getText()).toString().isEmpty())
                                {
                                    Toast.makeText(MainActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                                }
                                else if (Objects.requireNonNull(cin.getText()).toString().isEmpty())
                                {
                                    Toast.makeText(MainActivity.this, "CIN is required", Toast.LENGTH_SHORT).show();
                                }
                                else if (Objects.requireNonNull(phoneNumber.getText()).toString().isEmpty())
                                {
                                    Toast.makeText(MainActivity.this, "Phone Number is required", Toast.LENGTH_SHORT).show();
                                }
                                else if (Objects.requireNonNull(address.getText()).toString().isEmpty())
                                {
                                    Toast.makeText(MainActivity.this, "Address Number is required", Toast.LENGTH_SHORT).show();
                                }
                                else if (Objects.requireNonNull(matricule.getText()).toString().isEmpty())
                                {
                                    Toast.makeText(MainActivity.this, "Matricule Number is required", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                                    dialog.setMessage("Storing in Database...");
                                    dialog.show();
                                    Customer customer=new Customer();
                                    customer.setName(name.getText().toString());
                                    customer.setEmail(email.getText().toString());
                                    customer.setCin(cin.getText().toString());
                                    customer.setMatricule(matricule.getText().toString());
                                    customer.setPhoneNumber(phoneNumber.getText().toString());
                                    customer.setAddress(address.getText().toString());


                                    database.getReference().child("customer").push().setValue(customer).
                                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dialog.dismiss();
                                            dialogInterface.dismiss();
                                            Toast.makeText(MainActivity.this, "Save Successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    dialog.dismiss();

                                                    Toast.makeText(MainActivity.this, "There was an error while saving data", Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                }

                            }
                        })
                        // Implementation of a negative button to cancel the addition

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();// Creation of the dialog box with the defined parameters
                        dialog.show();// Display the dialog box

            }
        });

        recyclerView=findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // get data from Firebase Database and display it in the RecyclerView
        database.getReference().child("customer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Processing the data received to display it in the RecyclerView
                ArrayList<Customer> arrayList =new ArrayList<>();
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Customer customer=dataSnapshot.getValue(Customer.class);
                    Objects.requireNonNull(customer).setId(dataSnapshot.getKey());
                    arrayList.add(customer);
                }

                if (arrayList.isEmpty()) {
                    emp.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    emp.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                CustomerAdapter adapter =new CustomerAdapter(MainActivity.this,arrayList);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(new CustomerAdapter.OnItemClickListener() {
                    @Override
                    public void OnClick(Customer customer) {
                        View view=LayoutInflater.from(MainActivity.this).inflate(R.layout.add_customer,null);
                        name=view.findViewById(R.id.nameC);
                        email=view.findViewById(R.id.emailC);
                        cin=view.findViewById(R.id.CINC);
                        phoneNumber=view.findViewById(R.id.numberP);
                        matricule=view.findViewById(R.id.matriculeC);
                        address=view.findViewById(R.id.adresseC);


                        name.setText(customer.getName());
                        email.setText(customer.getEmail());
                        cin.setText(customer.getCin());
                        phoneNumber.setText(customer.getPhoneNumber());
                        matricule.setText(customer.getMatricule());
                        address.setText(customer.getAddress());

                        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

                        AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Edit")
                                .setView(view)
                                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (Objects.requireNonNull(name.getText()).toString().isEmpty() )
                                        {
                                            Toast.makeText(MainActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (Objects.requireNonNull(email.getText()).toString().isEmpty())
                                        {
                                            Toast.makeText(MainActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (Objects.requireNonNull(cin.getText()).toString().isEmpty())
                                        {
                                            Toast.makeText(MainActivity.this, "CIN is required", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (Objects.requireNonNull(phoneNumber.getText()).toString().isEmpty())
                                        {
                                            Toast.makeText(MainActivity.this, "Phone Number is required", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (Objects.requireNonNull(address.getText()).toString().isEmpty())
                                        {
                                            Toast.makeText(MainActivity.this, "Address Number is required", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (Objects.requireNonNull(matricule.getText()).toString().isEmpty())
                                        {
                                            Toast.makeText(MainActivity.this, "Matricule Number is required", Toast.LENGTH_SHORT).show();
                                        }

                                        else {

                                            progressDialog.setMessage("Saving...");
                                            progressDialog.show();
                                            Customer customer=new Customer();
                                            customer.setName(name.getText().toString());
                                            customer.setEmail(email.getText().toString());
                                            customer.setCin(cin.getText().toString());
                                            customer.setMatricule(matricule.getText().toString());
                                            customer.setPhoneNumber(phoneNumber.getText().toString());
                                            customer.setAddress(address.getText().toString());


                                            database.getReference().child("customer").child(customer.getId()).setValue(customer).
                                                    addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            progressDialog.dismiss();
                                                            dialogInterface.dismiss();
                                                            Toast.makeText(MainActivity.this, "Save Successfully!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            progressDialog.dismiss();

                                                            Toast.makeText(MainActivity.this, "There was an error while saving data", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });
                                        }

                                    }
                                }).setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        progressDialog.setTitle("Deleting...");
                                        progressDialog.show();

                                        database.getReference().child("customer").child(customer.getId()).removeValue()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        progressDialog.dismiss();

                                                        Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        progressDialog.dismiss();

                                                    }
                                                });
                                    }
                                }).create();
                        dialog.show();

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Gestion des erreurs lors de la récupération des données depuis Firebase

            }
        });




    }




}