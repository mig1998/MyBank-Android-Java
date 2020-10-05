package com.migapps.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.migapps.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SacarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SacarFragment extends Fragment {
    private static final String Arquivo_Preferencia="ArquivoPreferencia";
    private TextView txtSaldoSaque,txtQtdSaque,txtSaldoAtualSaque;
    private EditText MoneySaque;
    private Button Saque;

    SQLiteDatabase bancoDados;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SacarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SacarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SacarFragment newInstance(String param1, String param2) {
        SacarFragment fragment = new SacarFragment();
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
      View view= inflater.inflate(R.layout.fragment_sacar, container, false);
        txtSaldoSaque=view.findViewById(R.id.txtSaldoSaque);
        txtQtdSaque=view.findViewById(R.id.txtQtdSaque);
        txtSaldoAtualSaque=view.findViewById(R.id.saldoAtualSaque);
        MoneySaque=view.findViewById(R.id.MoneySaque);
        Saque=view.findViewById(R.id.btnSaque);

        SharedPreferences sharedPreferences= this.getActivity().getSharedPreferences(Arquivo_Preferencia,0);
      final String user=sharedPreferences.getString("IDuser","Nada");




        MoneySaque.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
txtQtdSaque.setText("R$: "+s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //criar o banco
        bancoDados = getActivity().openOrCreateDatabase("clientes", Context.MODE_PRIVATE, null);


        //criar tabela
        bancoDados.execSQL("Create Table if not exists pessoas(ID INTEGER PRIMARY KEY AUTOINCREMENT,Usuario VARCHAR(50) ,Senha VARCHAR(50),Cpf VARCHAR(50) ,Idade VARCHAR(50) ,Conta VARCHAR(50),Saldo Double(9,2),SaldoPoupança Double(9,2)) ");


        Cursor cursor=bancoDados.rawQuery("SELECT * FROM pessoas WHERE ID='"+user+"'",null);


        cursor.moveToFirst();

        txtSaldoAtualSaque.setText("Saldo: R$: "+cursor.getString(cursor.getColumnIndex("Saldo")));




        Saque.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        try{


            String qtdValor=MoneySaque.getText().toString();



            Cursor cursor=bancoDados.rawQuery("SELECT * FROM pessoas WHERE ID='"+user+"'",null);


            cursor.moveToFirst();

            String qtdSaldo=cursor.getString(cursor.getColumnIndex("Saldo"));



            Sacando(qtdSaldo,qtdValor,user);






        }catch (Exception e){

            e.printStackTrace();


        }



    }
});






        return view;

    }










    public void Sacando(String saldo,String valor,String userID){



        Double valor1=Double.parseDouble(saldo);
        Double valor2=Double.parseDouble(valor);

        double resultado=valor1-valor2;

        bancoDados.execSQL("Update pessoas set Saldo='"+resultado+"' where ID='"+userID+"'");

        Cursor cursor=bancoDados.rawQuery("SELECT * FROM pessoas WHERE ID='"+userID+"'",null);


        cursor.moveToFirst();




        txtSaldoSaque.setText(cursor.getString(cursor.getColumnIndex("Saldo")));


    }




}