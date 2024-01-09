package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Emplye;
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

public class HomeEmploye extends AppCompatActivity {
    RecyclerView recyclerView;

    FloatingActionButton buttonFE;
    EditText name, email, cin, phoneNumber, address;
    TextView emp;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_employe);
        FirebaseApp.initializeApp(HomeEmploye.this);

        database = FirebaseDatabase.getInstance();

        emp = findViewById(R.id.emptyE);

        buttonFE = findViewById(R.id.buttonAddE);


        buttonFE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(HomeEmploye.this).inflate(R.layout.addemployer, null);
                name = view1.findViewById(R.id.nameE);
                email = view1.findViewById(R.id.emailE);
                cin = view1.findViewById(R.id.CINE);
                phoneNumber = view1.findViewById(R.id.numberE);
                address = view1.findViewById(R.id.adresseE);


                AlertDialog dialog = new AlertDialog.Builder(HomeEmploye.this)
                        .setTitle("Add Employe")
                        .setView(view1)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (Objects.requireNonNull(name.getText()).toString().isEmpty()) {
                                    Toast.makeText(HomeEmploye.this, "Name is required", Toast.LENGTH_SHORT).show();
                                } else if (Objects.requireNonNull(email.getText()).toString().isEmpty()) {
                                    Toast.makeText(HomeEmploye.this, "Email is required", Toast.LENGTH_SHORT).show();
                                } else if (Objects.requireNonNull(cin.getText()).toString().isEmpty()) {
                                    Toast.makeText(HomeEmploye.this, "CIN is required", Toast.LENGTH_SHORT).show();
                                } else if (Objects.requireNonNull(phoneNumber.getText()).toString().isEmpty()) {
                                    Toast.makeText(HomeEmploye.this, "Phone Number is required", Toast.LENGTH_SHORT).show();
                                } else if (Objects.requireNonNull(address.getText()).toString().isEmpty()) {
                                    Toast.makeText(HomeEmploye.this, "Address Number is required", Toast.LENGTH_SHORT).show();
                                } else {
                                    ProgressDialog dialog = new ProgressDialog(HomeEmploye.this);
                                    dialog.setMessage("Storing in Database...");
                                    dialog.show();
                                    Emplye emplye = new Emplye();
                                    emplye.setName(name.getText().toString());
                                    emplye.setEmail(email.getText().toString());
                                    emplye.setCin(cin.getText().toString());
                                    emplye.setPhoneNumber(phoneNumber.getText().toString());
                                    emplye.setAddress(address.getText().toString());

                                    database.getReference().child("employe").push().setValue(emplye).
                                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    dialog.dismiss();
                                                    dialogInterface.dismiss();
                                                    Toast.makeText(HomeEmploye.this, "Save Successfully!", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    dialog.dismiss();

                                                    Toast.makeText(HomeEmploye.this, "There was an error while saving data", Toast.LENGTH_SHORT).show();

                                                }
                                            });


                                }

                            }

                        })


                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                dialog.show();

            }

        });

        recyclerView=findViewById(R.id.recyclerViewEm);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database.getReference().child("employe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Emplye> arrayList =new ArrayList<>();
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Emplye emplye=dataSnapshot.getValue(Emplye.class);
                    Objects.requireNonNull(emplye).setId(dataSnapshot.getKey());
                    arrayList.add(emplye);
                }

                if (arrayList.isEmpty()) {
                    emp.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                else {
                    emp.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                EmployeAdapter adapter =new EmployeAdapter(HomeEmploye.this,arrayList);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new EmployeAdapter.OnItemClickListener() {
                    @Override
                    public void OnClick(Emplye emplye) {
                        View view=LayoutInflater.from(HomeEmploye.this).inflate(R.layout.addemployer,null);
                        name = view.findViewById(R.id.nameE);
                        email = view.findViewById(R.id.emailE);
                        cin = view.findViewById(R.id.CINE);
                        phoneNumber = view.findViewById(R.id.numberE);
                        address = view.findViewById(R.id.adresseE);



                        name.setText(emplye.getName());
                        email.setText(emplye.getEmail());
                        cin.setText(emplye.getCin());
                        phoneNumber.setText(emplye.getPhoneNumber());
                        address.setText(emplye.getAddress());

                        ProgressDialog progressDialog = new ProgressDialog(HomeEmploye.this);

                        AlertDialog dialog = new AlertDialog.Builder(HomeEmploye.this)
                                .setTitle("Add Employe")
                                .setView(view)
                                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (Objects.requireNonNull(name.getText()).toString().isEmpty() )
                                        {
                                            Toast.makeText(HomeEmploye.this, "Name is required", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (Objects.requireNonNull(email.getText()).toString().isEmpty())
                                        {
                                            Toast.makeText(HomeEmploye.this, "Email is required", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (Objects.requireNonNull(cin.getText()).toString().isEmpty())
                                        {
                                            Toast.makeText(HomeEmploye.this, "CIN is required", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (Objects.requireNonNull(phoneNumber.getText()).toString().isEmpty())
                                        {
                                            Toast.makeText(HomeEmploye.this, "Phone Number is required", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (Objects.requireNonNull(address.getText()).toString().isEmpty())
                                        {
                                            Toast.makeText(HomeEmploye.this, "Address Number is required", Toast.LENGTH_SHORT).show();
                                        }


                                        else {

                                            progressDialog.setMessage("Saving...");
                                            progressDialog.show();
                                            Emplye emplye1=new Emplye();
                                            emplye1.setName(name.getText().toString());
                                            emplye1.setEmail(email.getText().toString());
                                            emplye1.setCin(cin.getText().toString());
                                            emplye1.setPhoneNumber(phoneNumber.getText().toString());
                                            emplye1.setAddress(address.getText().toString());


                                            database.getReference().child("employe").child(emplye1.getId()).setValue(emplye1).
                                                    addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            progressDialog.dismiss();
                                                            dialogInterface.dismiss();
                                                            Toast.makeText(HomeEmploye.this, "Save Successfully!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            progressDialog.dismiss();

                                                            Toast.makeText(HomeEmploye.this, "There was an error while saving data", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });
                                        }
                                    }
                                })
                                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        progressDialog.setTitle("Deleting...");
                                        progressDialog.show();

                                        database.getReference().child("employe").child(emplye.getId()).removeValue()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        progressDialog.dismiss();

                                                        Toast.makeText(HomeEmploye.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();

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

            }
        });

    }



}