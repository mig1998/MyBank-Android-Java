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
 * Use the {@link EmprestimoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmprestimoFragment extends Fragment {
    private static final String Arquivo_Preferencia="ArquivoPreferencia";
    private TextView txtSaldoEmprestimo,txtTaxa,txtQtdEmprestimo;
    private EditText MoneyEmprestimo;
    private Button btnEmprestimo;
    SQLiteDatabase bancoDados;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmprestimoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmprestimoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmprestimoFragment newInstance(String param1, String param2) {
        EmprestimoFragment fragment = new EmprestimoFragment();
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
        View view= inflater.inflate(R.layout.fragment_emprestimo, container, false);


        txtSaldoEmprestimo=view.findViewById(R.id.txtSaldoEmprestimo);
        txtQtdEmprestimo=view.findViewById(R.id.txtQtdEmprestimo);
        txtTaxa=view.findViewById(R.id.txtTaxaEmprestimo);
        MoneyEmprestimo=view.findViewById(R.id.MoneyEmprestimo);
        btnEmprestimo=view.findViewById(R.id.btnEmpresta);


        SharedPreferences sharedPreferences= this.getActivity().getSharedPreferences(Arquivo_Preferencia,0);
       final String user=sharedPreferences.getString("IDuser","Nada");



        MoneyEmprestimo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtQtdEmprestimo.setText("R$: "+s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnEmprestimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{


                    String qtdValor=MoneyEmprestimo.getText().toString();


                    //criar o banco
                    bancoDados = getActivity().openOrCreateDatabase("clientes", Context.MODE_PRIVATE, null);

                    //executar um codigo sql
                    //criar tabela
                    bancoDados.execSQL("Create Table if not exists pessoas(ID INTEGER PRIMARY KEY AUTOINCREMENT,Usuario VARCHAR(50) ,Senha VARCHAR(50),Cpf VARCHAR(50) ,Idade VARCHAR(50) ,Conta VARCHAR(50),Saldo Double(9,2),SaldoPoupança Double(9,2)) ");


                    Cursor cursor=bancoDados.rawQuery("SELECT * FROM pessoas WHERE ID='"+user+"'",null);


                    cursor.moveToFirst();

                    String qtdSaldo=cursor.getString(cursor.getColumnIndex("Saldo"));



                    Emprestando(qtdSaldo,qtdValor,user);






                }catch (Exception e){

                    e.printStackTrace();


                }



            }
        });

        return view;
    }






    public void Emprestando(String saldo,String valor,String userID){



        Double valor1=Double.parseDouble(saldo);
        Double valor2=Double.parseDouble(valor);

        double resultado=valor2*15/100;
        double resultado1=valor2-resultado;
        double resultado2=valor1+resultado1;

        txtTaxa.setText("Taxa : R$:"+String.valueOf(resultado));

        bancoDados.execSQL("Update pessoas set Saldo='"+resultado2+"' where ID='"+userID+"'");

        Cursor cursor=bancoDados.rawQuery("SELECT * FROM pessoas WHERE ID='"+userID+"'",null);


        cursor.moveToFirst();




        txtSaldoEmprestimo.setText(cursor.getString(cursor.getColumnIndex("Saldo")));


    }






}