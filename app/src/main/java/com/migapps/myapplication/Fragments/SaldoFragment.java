package com.migapps.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.migapps.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaldoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaldoFragment extends Fragment {
    private static final String Arquivo_Preferencia="ArquivoPreferencia";
    private TextView SaldoAtual;
    SQLiteDatabase bancoDados;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SaldoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaldoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SaldoFragment newInstance(String param1, String param2) {
        SaldoFragment fragment = new SaldoFragment();
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
        View view= inflater.inflate(R.layout.fragment_saldo, container, false);

        SaldoAtual=view.findViewById(R.id.txtQtdSaldo);

        SharedPreferences sharedPreferences= this.getActivity().getSharedPreferences(Arquivo_Preferencia,0);
        String user=sharedPreferences.getString("IDuser","Nada");






        try{
            //criar o banco
            bancoDados = getActivity().openOrCreateDatabase("clientes", Context.MODE_PRIVATE, null);

            //executar um codigo sql
            //criar tabela
            bancoDados.execSQL("Create Table if not exists pessoas(ID INTEGER PRIMARY KEY AUTOINCREMENT,Usuario VARCHAR(50) ,Senha VARCHAR(50),Cpf VARCHAR(50) ,Idade VARCHAR(50) ,Conta VARCHAR(50),Saldo Double(9,2),SaldoPoupança Double(9,2)) ");


            Cursor cursor=bancoDados.rawQuery("SELECT * FROM pessoas WHERE ID='"+user+"'",null);


            cursor.moveToFirst();

            while(cursor!=null){

                SaldoAtual.setText("R$: "+cursor.getString(cursor.getColumnIndex("Saldo")));

                cursor.moveToNext();
            }








        }catch (Exception e){
            e.printStackTrace();
            Log.i("erro",e.getMessage());
        }





        return view;
    }









}