package com.example.smartsound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartsound.model.Persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText et1, et2;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String dato1, contra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et1=findViewById(R.id.logUser);
        et2=findViewById(R.id.logPass);
        inicializarFirebase();
    }

    public void registro(View view){
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);


    }

    public void ingresar(View view){
        ingresoAdmin();
    }

    private void ingresoAdmin(){
        databaseReference.child(et1.getText().toString()).child("Datos").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Persona p = snapshot.getValue(Persona.class);
                if (p != null) {
                    dato1 = p.getUsuario();
                    contra = p.getClave();
                    if(et2.getText().toString().equals(contra)){
                        databaseReference.child("Temporal").setValue(p);
                        Intent i = new Intent(Login.this, MenuIngreso.class );
                        startActivity(i);
                    }else
                        Toast.makeText(Login.this, "Contraseña incorrecta",
                                Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(Login.this, "No existe un artículo con dicho Usuario",
                            Toast.LENGTH_SHORT).show();
                System.out.println("Aqui");
                System.out.println(dato1);
                System.out.println(contra);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
    }





}