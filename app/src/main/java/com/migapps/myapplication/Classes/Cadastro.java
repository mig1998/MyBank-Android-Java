package com.migapps.myapplication.Classes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.migapps.myapplication.R;

public class Cadastro extends AppCompatActivity {
    SQLiteDatabase bancoDados;
    private EditText cad_Usuario,cad_Password,cad_Conta,cad_Cpf,cad_Idade,cad_saldo,cad_poupanca;

    private Button Cadastrar,Voltar;
    private Switch mudarSenha;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        //cadastro

        cad_Cpf = findViewById(R.id.cad_CPF);
        cad_Idade = findViewById(R.id.cad_Idade);
        cad_Conta = findViewById(R.id.cad_Conta);
        cad_Usuario = findViewById(R.id.cad_Usuario);
        cad_Password = findViewById(R.id.cad_Password);
        cad_saldo = findViewById(R.id.cad_saldo);
        cad_poupanca = findViewById(R.id.cad_poupanca);


        //buttons

        Cadastrar = findViewById(R.id.btnCadastrar2);
        Voltar = findViewById(R.id.btnVoltar);
        mudarSenha = findViewById(R.id.mudarSenha);


        getSupportActionBar().hide();
        mudarSenha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    cad_Password.setInputType(1);

                }else{
                    cad_Password.setInputType(129);
                }

            }
        });

Voltar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
});


        try {
            //criar o banco
            bancoDados = openOrCreateDatabase("clientes", MODE_PRIVATE, null);

            //executar um codigo sql
            //criar tabela
            bancoDados.execSQL("Create Table if not exists pessoas(ID INTEGER PRIMARY KEY AUTOINCREMENT,Usuario VARCHAR(50) ,Senha VARCHAR(50),Cpf VARCHAR(50) ,Idade VARCHAR(50) ,Conta VARCHAR(50),Saldo Double(9,2),SaldoPoupança Double(9,2)) ");


            Cadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String usuario=cad_Usuario.getText().toString();
                    String senha=cad_Password.getText().toString();
                    String cpf=cad_Cpf.getText().toString();
                    String conta=cad_Conta.getText().toString();
                    String idade=cad_Idade.getText().toString();
                    String saldo=cad_saldo.getText().toString();
                    String poupanca=cad_poupanca.getText().toString();





                    salvarTarefa(usuario,senha,cpf,idade,conta,saldo,poupanca);
                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


















    @Override
    public void onBackPressed() {

    }

    private void salvarTarefa(String c1ad_Usuario,String c1ad_Password,String c1ad_Cpf,String c1ad_Idade,String c1ad_Conta,String c1ad_Saldo,String c1ad_Poupanca){

        try{
            //se o texto recuperado for vazio
            if(c1ad_Usuario.equals("") || c1ad_Password.equals("") || c1ad_Conta.equals("") || c1ad_Cpf.equals("") || c1ad_Idade.equals("") || c1ad_Saldo.equals("") || c1ad_Poupanca.equals("") ){
                Toast.makeText(getApplicationContext(), "Insira os dados Corretamente!", Toast.LENGTH_SHORT).show();


            }else if(c1ad_Usuario.length()>8 || c1ad_Password.length()>8 || c1ad_Conta.length()>8 || c1ad_Cpf.length()>8 || c1ad_Idade.length()>8) {
                Toast.makeText(getApplicationContext(), "Todos os campos aceitam no Maximo 8 Caracteres!", Toast.LENGTH_SHORT).show();

            }else {



                bancoDados.execSQL("INSERT INTO pessoas (Usuario,Senha,Cpf,Idade,Conta,Saldo,SaldoPoupança) VALUES('"+c1ad_Usuario+"','"+c1ad_Password+"','"+c1ad_Cpf+"','"+c1ad_Idade+"','"+c1ad_Conta+"','"+c1ad_Saldo+"','"+c1ad_Poupanca+"') ");


                Toast.makeText(getApplicationContext(), "Cliente salva com sucesso!", Toast.LENGTH_SHORT).show();

            }

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "falha ao cadastrar", Toast.LENGTH_SHORT).show();
            Log.i("erro:",String.valueOf(e));
        }finally {
            bancoDados.close();
        }

    }


    public void clean(View view){

        cad_Cpf.getText().clear();
        cad_Idade.getText().clear();
        cad_Conta.getText().clear();
        cad_Usuario.getText().clear();
        cad_Password.getText().clear();


    }




}












