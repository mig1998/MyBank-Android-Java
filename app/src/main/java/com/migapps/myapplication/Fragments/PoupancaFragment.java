package com.migapps.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.migapps.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PoupancaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PoupancaFragment extends Fragment {
    private static final String Arquivo_Preferencia="ArquivoPreferencia";
    private TextView txtQtdPoupanca,SaldoPoupancaAtual;
    private EditText editMoneyPoupanca;
    private Button btnDepositarPoupanca,btnRetirarPoupanca;
    SQLiteDatabase bancoDados;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PoupancaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PoupancaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PoupancaFragment newInstance(String param1, String param2) {
        PoupancaFragment fragment = new PoupancaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_poupanca, container, false);


        txtQtdPoupanca=view.findViewById(R.id.txtQtdSaldoPoupança);
        SaldoPoupancaAtual=view.findViewById(R.id.SaldoPoupancaAtual);
        editMoneyPoupanca=view.findViewById(R.id.editMoneyPoupança);

        btnDepositarPoupanca=view.findViewById(R.id.btnDepositPoupanca);
        btnRetirarPoupanca=view.findViewById(R.id.btnRetirarPoupanca);



        SharedPreferences sharedPreferences= this.getActivity().getSharedPreferences(Arquivo_Preferencia,0);
      final String user=sharedPreferences.getString("IDuser","Nada");

        //criar o banco
        bancoDados = getActivity().openOrCreateDatabase("clientes", Context.MODE_PRIVATE, null);


        bancoDados.execSQL("Create Table if not exists pessoas(ID INTEGER PRIMARY KEY AUTOINCREMENT,Usuario VARCHAR(50) ,Senha VARCHAR(50),Cpf VARCHAR(50) ,Idade VARCHAR(50) ,Conta VARCHAR(50),Saldo Double(9,2),SaldoPoupança Double(9,2)) ");


        Cursor cursor=bancoDados.rawQuery("SELECT * FROM pessoas WHERE ID='"+user+"'",null);
        cursor.moveToFirst();

        SaldoPoupancaAtual.setText("Saldo: R$: "+cursor.getString(cursor.getColumnIndex("SaldoPoupança")));

        btnDepositarPoupanca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{


                    String qtdValor=editMoneyPoupanca.getText().toString();




                    Cursor cursor=bancoDados.rawQuery("SELECT * FROM pessoas WHERE ID='"+user+"'",null);


                    cursor.moveToFirst();

                    String qtdSaldo=cursor.getString(cursor.getColumnIndex("Saldo"));
                    String qtdSaldoPoupanca=cursor.getString(cursor.getColumnIndex("SaldoPoupança"));



                    Deposito(qtdSaldo,qtdSaldoPoupanca,qtdValor,user);






                }catch (Exception e){

                    e.printStackTrace();


                }


            }
        });



        btnRetirarPoupanca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                try{


                    String qtdValor=editMoneyPoupanca.getText().toString();



                    Cursor cursor=bancoDados.rawQuery("SELECT * FROM pessoas WHERE ID='"+user+"'",null);


                    cursor.moveToFirst();

                    String qtdSaldo=cursor.getString(cursor.getColumnIndex("Saldo"));
                    String qtdSaldoPoupanca=cursor.getString(cursor.getColumnIndex("SaldoPoupança"));



                    Retirar(qtdSaldo,qtdSaldoPoupanca,qtdValor,user);




                }catch (Exception e){

                    e.printStackTrace();


                }

            }
        });


    return view;
    }







    public void Deposito(String saldo,String SaldoPoupanca,String valor,String userID){



        Double valor1=Double.parseDouble(saldo);
        Double valor2=Double.parseDouble(SaldoPoupanca);
        Double valor3=Double.parseDouble(valor);


        double resultado=valor1-valor3;

        double resultado2=valor2+valor3;



        bancoDados.execSQL("Update pessoas set Saldo='"+resultado+"' where ID='"+userID+"'");
        bancoDados.execSQL("Update pessoas set SaldoPoupança='"+resultado2+"' where ID='"+userID+"'");

        Cursor cursor=bancoDados.rawQuery("SELECT * FROM pessoas WHERE ID='"+userID+"'",null);


        cursor.moveToFirst();




        txtQtdPoupanca.setText("+ R$: "+cursor.getString(cursor.getColumnIndex("SaldoPoupança")));


    }

    public void Retirar(String saldo,String SaldoPoupanca,String valor,String userID){



        Double valor1=Double.parseDouble(saldo);
        Double valor2=Double.parseDouble(SaldoPoupanca);
        Double valor3=Double.parseDouble(valor);


        double resultado=valor1+valor3;

        double resultado2=valor2-valor3;



        bancoDados.execSQL("Update pessoas set Saldo='"+resultado+"' where ID='"+userID+"'");
        bancoDados.execSQL("Update pessoas set SaldoPoupança='"+resultado2+"' where ID='"+userID+"'");

        Cursor cursor=bancoDados.rawQuery("SELECT * FROM pessoas WHERE ID='"+userID+"'",null);


        cursor.moveToFirst();




        txtQtdPoupanca.setText("- R$: "+cursor.getString(cursor.getColumnIndex("SaldoPoupança")));


    }


}