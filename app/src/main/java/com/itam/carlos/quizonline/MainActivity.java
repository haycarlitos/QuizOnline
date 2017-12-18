package com.itam.carlos.quizonline;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itam.carlos.quizonline.Common.Common;
import com.itam.carlos.quizonline.Model.Usuario;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {
    MaterialEditText etNuevoUsuario, etNuevoPassword, etNuevoMail; //Para registrarse
    MaterialEditText etUsuario, etPassword; //Para iniciar sesion

    Button btnSignUp, btnSignIn;

    FirebaseDatabase db;
    DatabaseReference usuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase
        db = FirebaseDatabase.getInstance();
        usuarios = db.getReference("Usuarios");

        etUsuario = (MaterialEditText)findViewById(R.id.etUsuario);
        etPassword = (MaterialEditText)findViewById(R.id.etPassword);

        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignUpDialog();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(etUsuario.getText().toString(),etPassword.getText().toString());
            }
        });
    }

    private void signIn(final String usuario, final String pwd){
        usuarios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(usuario).exists()){
                    if(!usuario.isEmpty()){
                        Usuario login = dataSnapshot.child(usuario).getValue(Usuario.class);
                        if(login.getPassword().equals(pwd)){
                            Intent homeActivity = new Intent (MainActivity.this,Home.class);
                            Common.usuarioActual = login ;
                            startActivity(homeActivity);
                            finish();

                        }
                        else
                            Toast.makeText(MainActivity.this,"Password incorrecto",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this,"Por favor completa el campo de usuario", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"El usuario no existe.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showSignUpDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Sign Up");
        alertDialog.setMessage("Completa todos los campos");

        LayoutInflater inflater = this.getLayoutInflater();
        View sign_up_layout = inflater.inflate(R.layout.sign_up_layout,null);

        etNuevoUsuario = (MaterialEditText)sign_up_layout.findViewById(R.id.etNuevoUsuario);
        etNuevoPassword = (MaterialEditText)sign_up_layout.findViewById(R.id.etNuevoPassword);
        etNuevoMail = (MaterialEditText)sign_up_layout.findViewById(R.id.etNuevoMail);

        alertDialog.setView(sign_up_layout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final Usuario usuario = new Usuario(etNuevoUsuario.getText().toString(),
                                              etNuevoPassword.getText().toString(),
                                              etNuevoMail.getText().toString());

                usuarios.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(usuario.getNombre()).exists())
                            Toast.makeText(MainActivity.this,"El usuario ya existe.",Toast.LENGTH_SHORT).show();
                        else{
                            usuarios.child(usuario.getNombre()).setValue(usuario);
                            Toast.makeText(MainActivity.this,"Alta exitosa.",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialogInterface.dismiss();

            }
        });

        alertDialog.show();

    }
}
