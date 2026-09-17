package br.edu.unisenai.rangonaregua;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.GoogleAuthProvider;

import org.jspecify.annotations.NonNull;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail;
    EditText edtSenha;
    Button btnEntrar;
    Button btnCriarConta;

    Button btnRecuperar;

    SignInButton btnGoogle;


    private FirebaseAuth auth;

    private ActivityResultLauncher<Intent> signInLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        task.addOnSuccessListener(googleAccount -> {
                            AuthCredential credential = GoogleAuthProvider.getCredential(googleAccount.getIdToken(), null);
                            autenticar.signInWithCredential(credential);

                            Intent rota = new Intent(this, MainActivity.class);
                            startActivity(rota);
                            finish();
                        });
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.etEmail);
        edtSenha = findViewById(R.id.etSenha);
        btnEntrar = findViewById(R.id.btEntrar);
        btnCriarConta = findViewById(R.id.btCriarConta);
        btnRecuperar = findViewById(R.id.btnRecuperar);
        btnGoogle = findViewById(R.id.btnGoogle);

        auth = FirebaseAuth.getInstance();

        // Cada botão registra o clique uma única vez. Registrar de
        // novo no mesmo botão substitui o listener anterior, não soma.
        btnCriarConta.setOnClickListener(v -> criarConta());
        btnEntrar.setOnClickListener(v -> entrar());
        btnRecuperar.setOnClickListener(v -> recuperar());
        btnGoogle.setOnClickListener(view -> google());
    }

    private void criarConta() {
        if (edtEmail.getText().toString().isEmpty()) {
            edtEmail.setError("Informe seu e-mail.");
            return;
        }
        if (edtSenha.getText().toString().isEmpty()) {
            edtSenha.setError("Informe sua senha.");
            return;
        }

        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        abrirRanking();
                    } else {
                        Toast.makeText(this, task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void entrar() {
        if (edtEmail.getText().toString().isEmpty()) {
            edtEmail.setError("Informe seu e-mail.");
            return;
        }
        if (edtSenha.getText().toString().isEmpty()) {
            edtSenha.setError("Informe sua senha.");
            return;
        }

        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        abrirRanking();
                    } else {
                        Toast.makeText(this, task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void abrirRanking() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void recuperar() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = edtEmail.getText().toString();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                        Toast.makeText(LoginActivity.this, "Email enviado", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void google (){
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        signInLauncher.launch(signInIntent);
    }
}