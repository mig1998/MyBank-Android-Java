package com.migapps.myapplication.Classes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.migapps.myapplication.R;

public class MainActivity extends AppCompatActivity {
    private EditText Login,Senha;
    private TextView falha;
    private Button entrar,Cadastrar;
    private Switch mostraSenha;
    SQLiteDatabase bancoDados;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //entrar

        Login=findViewById(R.id.txtLogin);
        Senha=findViewById(R.id.txtPassword);
        entrar=findViewById(R.id.btnEntrar);
        falha=findViewById(R.id.txtFalha);
        mostraSenha=findViewById(R.id.mostraSenha);

        //cad
        Cadastrar=findViewById(R.id.btnCadastrar);

        getSupportActionBar().hide();

        Cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),Cadastro.class);
                startActivity(intent);
            }
        });



        mostraSenha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    Senha.setInputType(1);

                }else{
                    Senha.setInputType(129);
                }

            }
        });


            entrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        if(Login.getText().toString().isEmpty() || Senha.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(),"insira os dados!",Toast.LENGTH_SHORT).show();

                        }else {



                             bancoDados = openOrCreateDatabase("clientes", MODE_PRIVATE, null);



                            bancoDados.execSQL("Create Table if not exists pessoas(ID INTEGER PRIMARY KEY AUTOINCREMENT,Usuario VARCHAR(50) ,Senha VARCHAR(50),Cpf VARCHAR(50) ,Idade VARCHAR(50) ,Conta VARCHAR(50),Saldo Double(9,2),SaldoPoupança Double(9,2)) ");

                            //recuperando dados,  o cursor percorre a tabela com a ajuda do select
                            Cursor cursor = bancoDados.rawQuery("SELECT * FROM pessoas where Usuario='" + Login.getText().toString() + "' and Senha='" + Senha.getText().toString() + "' ", null);


                            //necessario
                            cursor.moveToFirst();

                            if (cursor != null) {
                                Intent intent = new Intent(getApplicationContext(), AreaPrincipal.class);
                                intent.putExtra("User", cursor.getString(cursor.getColumnIndex("ID")));

                                startActivity(intent);
//necessario
                                cursor.moveToNext();


                            } else {
                                falha.setVisibility(1);
                            }



                     }


                } catch (Exception e) {
                    Log.i("erro",String.valueOf(e));
                        falha.setVisibility(1);
                    e.printStackTrace();
                }



            }
            });









    }

    @Override
    public void onBackPressed() {

    }
}



