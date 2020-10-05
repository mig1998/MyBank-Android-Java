package com.migapps.myapplication.Classes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.migapps.myapplication.Fragments.DepositarFragment;
import com.migapps.myapplication.Fragments.EmprestimoFragment;
import com.migapps.myapplication.Fragments.PoupancaFragment;
import com.migapps.myapplication.Fragments.SacarFragment;
import com.migapps.myapplication.Fragments.SaldoFragment;
import com.migapps.myapplication.R;

public class AreaPrincipal extends AppCompatActivity {
    private static final String Arquivo_Preferencia="ArquivoPreferencia";

    private PoupancaFragment Poupanca;
    private SaldoFragment Saldo;
    private DepositarFragment Depositar;
    private SacarFragment Sacar;
    private EmprestimoFragment Emprestimo;
    private Button btnSaldo,btnSaldoPoupanca,btnExtrato,btnDeposito,btnSacar,btnEmprestimo;
    private TextView txtUsuario,txtConta,txtCpf,txtIdade;
    SQLiteDatabase bancoDados;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_principal);



        //txt

        txtCpf=findViewById(R.id.txtCPF);
        txtIdade=findViewById(R.id.txtIdade);
        txtConta=findViewById(R.id.txtConta);
        txtUsuario=findViewById(R.id.txtUsuario);


        //buttons
        btnSaldo=findViewById(R.id.btnSaldo);
        btnSaldoPoupanca=findViewById(R.id.btnSaldoPoupanca);

        btnDeposito=findViewById(R.id.btnDeposito);
        btnSacar=findViewById(R.id.btnSacar);
        btnEmprestimo=findViewById(R.id.btnEmprestimo);



        Bundle dados=getIntent().getExtras();
        String user=dados.getString("User");


        SharedPreferences sharedPreferences=getSharedPreferences(Arquivo_Preferencia,0);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString("IDuser",user);
        editor.commit();




        try{
            //criar o banco
            bancoDados = openOrCreateDatabase("clientes", MODE_PRIVATE, null);

            //executar um codigo sql
            //criar tabela
            bancoDados.execSQL("Create Table if not exists pessoas(ID INTEGER PRIMARY KEY AUTOINCREMENT,Usuario VARCHAR(50) ,Senha VARCHAR(50),Cpf VARCHAR(50) ,Idade VARCHAR(50) ,Conta VARCHAR(50),Saldo Double(9,2),SaldoPoupança Double(9,2)) ");


            Cursor cursor=bancoDados.rawQuery("SELECT * FROM pessoas WHERE ID='"+user+"'",null);

            cursor.moveToFirst();

            while(cursor!=null){
                txtUsuario.setText("Usuario: "+cursor.getString(cursor.getColumnIndex("Usuario")));

                txtConta.setText("Conta: "+cursor.getString(cursor.getColumnIndex("Conta")));
                txtIdade.setText("Idade: "+cursor.getString(cursor.getColumnIndex("Idade")));
                txtCpf.setText("Cpf: "+cursor.getString(cursor.getColumnIndex("Cpf")));






                Log.i("saldo",cursor.getString(cursor.getColumnIndex("SaldoPoupança")));
            cursor.moveToNext();




            }





        }catch (Exception e){
            e.printStackTrace();
            Log.i("erro",e.getMessage());
        }



        //fragment
        Saldo=new SaldoFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameConteudo,Saldo);
        transaction.commit();

    btnSaldo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //fragment
            Saldo=new SaldoFragment();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameConteudo,Saldo);
            transaction.commit();
        }
    });

btnSaldoPoupanca.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //fragment
        Poupanca=new PoupancaFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameConteudo,Poupanca);
        transaction.commit();
    }
});




btnDeposito.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Depositar=new DepositarFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameConteudo,Depositar);
        transaction.commit();

    }
});



btnSacar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Sacar=new SacarFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameConteudo,Sacar);
        transaction.commit();

    }
});


btnEmprestimo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Emprestimo=new EmprestimoFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameConteudo,Emprestimo);
        transaction.commit();
    }
});





    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu){


        getMenuInflater().inflate(R.menu.area_principal, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onBackPressed() {

    }


    @Override
    protected void onPause() {

        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.Sair:
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;


            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}





