package com.exampl.gs.acaiexpress;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.exampl.gs.acaiexpress.ui.main.MainFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Cadastro extends AppCompatActivity {
    private Button btnRegistrar;
    private EditText editEmail;
    private EditText editSenha;
    private FirebaseAuth mAuth;
    private EditText conSenha;
    public Button voltar;
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializarComponentes();
        eventoClicks();
        mAuth = FirebaseAuth.getInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, MainFragment.newInstance()).commitNow();
        }
    }
    //EVENTOS DE BOTÕES
    private void eventoClicks() {
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                String senha = editSenha.getText().toString().trim();
                criarUser(email,senha);
            }
        });

    }
    //CRIAR USUARIO TRADICIONAL
    private void criarUser(String email, String senha){
        if(email.contains("@") == false){
            alert("ENTRE COM UM EMAIL VALIDO!!!");
        }else{
            if (senha.length() <=7 ){
                alert("SENHA MUITO PEQUENA");

            }else{
                if (editSenha.getText().toString().trim().equals(conSenha.getText().toString().trim()) == false){
                    alert("AS SENHAS NÃO COMBINAM");
                }else{
                    mAuth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete( Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                mAuth.getCurrentUser();
                                alert("Cadastrado com Sucesso!");
                                Intent i =new Intent(Cadastro.this, MainActivity.class);
                                startActivity(i);
                            }else{
                                alert("Email já Cadastrado");
                            }
                        }
                    });
                }
            }
        }
    }

    //INICIALIZA COMPONENTES
    private void inicializarComponentes() {
        btnRegistrar = (Button) findViewById(R.id.btCadastrar);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editSenha = (EditText) findViewById(R.id.editSenha);
        conSenha = ((EditText) findViewById(R.id.ConfSenha));
        voltar = (findViewById(R.id.btnVoltar));
    }
    //MOSTRA MSG
    private  void alert (String msg){
        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        Toast.makeText(Cadastro.this,msg,Toast.LENGTH_SHORT).show();
    }
}
