package com.cheftonic.filmappchef3.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.cheftonic.filmappchef3.R
import com.cheftonic.filmappchef3.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavHostController) {
    // Declaración de variables de estado para correo electrónico y contraseña
    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    // Declaración del context
    val context = LocalContext.current

    Scaffold (
        bottomBar = {null},
        content =
    {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(240.dp),
                painter = painterResource(id = R.drawable.logo_film),
                contentDescription = "logo"
            )

            Spacer(Modifier.height(30.dp))

            LoginForm(emailState, passwordState, context, navController)
        }
    })
}

//Creamos el formulario
@Composable
fun LoginForm(
    emailState: MutableState<String>,
    passwordState: MutableState<String>,
    context: Context,
    navController: NavController) {
    //Estado de la visibilidad de la contraseña
    val passwordVisible = rememberSaveable { mutableStateOf(false)
    }
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        //Campo de entrada para el usuario
        UserInput(emailState = emailState)
        //Campo de entrada para la contraseña
        PasswordInput(
            passwordState = passwordState,
            labelId = "Contraseña",
            passwordVisible = passwordVisible
        )
        //Botones de Iniciar Sesión y Registrarse
        FormButtons(emailState, passwordState, context, navController)
    }
}
//Creamos la base del Input
@Composable
fun InputField(
    valueState: MutableState<String>,
    labelId: String,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {valueState.value = it},
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

//Creamos un Input Field para el usuario

@Composable
fun UserInput(
    emailState: MutableState<String>,
    labelId: String = "Email"
) {
    InputField(
        valueState = emailState,
        labelId = labelId,
        keyboardType = KeyboardType.Email
    )
}
//Creamos un Input Field para la contraseña

@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    labelId: String,
    passwordVisible: MutableState<Boolean>
) {
    //Manejamos la visibilidad de la contraseña

    val visualTrasnformation =
        if (passwordVisible.value)
            VisualTransformation.None
        else
            PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = labelId) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        visualTransformation = visualTrasnformation,
        //Icono de la contraseña
        trailingIcon = {
            if(passwordState.value.isNotBlank())
                PasswordVisibleIcon(passwordVisible)
        }
    )
}

//Creamos el icono para la visibilidad del password
@Composable
fun PasswordVisibleIcon(passwordVisible: MutableState<Boolean>) {
    val image =
        if (passwordVisible.value){
            Icons.Default.VisibilityOff
        } else {
            Icons.Default.Visibility
        }
    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
        Icon(imageVector = image, contentDescription = "Ver Contraseña")
    }
}

//Alineamos los botones del formulario
@Composable
fun FormButtons(emailState: MutableState<String>,
                passwordState: MutableState<String>,
                context: Context,
                navController: NavController)
{

        Row {
            LoginButton(
                emailState,
                passwordState,
                context,
                navController
            )
            Spacer(modifier = Modifier.width(15.dp))
            RegisterButton(
                emailState,
                passwordState,
                context,
                navController
            )
        }

}


@Composable
fun LoginButton(emailState: MutableState<String>,
                passwordState: MutableState<String>,
                context: Context,
                navController: NavController){



        OutlinedButton(
            onClick = {
                LoginUser(
                    emailState.value,
                    passwordState.value,
                    context,
                    navController
                )
            },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ){
            Text(
                text = "Iniciar Sesión",
                color = MaterialTheme.colorScheme.surface
            )
        }


}

//Crear el botón para el Registro
@Composable
fun RegisterButton(emailState: MutableState<String>,
                   passwordState: MutableState<String>,
                   context: Context,
                   navController: NavController){


        OutlinedButton(
            onClick = {
                RegisterUser(
                    emailState.value,
                    passwordState.value,
                    context
                )
            },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ){
            Text(
                text = "Registrarse",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
}

/*Autenticar el usuario con mail y contraseña y redirigirle a HomeActivity*/
fun LoginUser(email: String, password: String, context: Context, navController: NavController) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Si la autenticación es exitosa, dirigir al usuario a la HomeScreen
                navController.navigate(AppScreens.Home.route)
            } else {
                // Si la autenticación falla, mostrar un mensaje de error al usuario
                Toast.makeText(
                    context,
                    "Inicio de sesión fallido. Usuario no registrado.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}

/*Registrar el nuevo usuario con su mail y contraseña mediante Firebase*/
fun RegisterUser(email: String, password: String, context: Context) {
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Si el registro es exitoso, mostrar un mensaje de éxito al usuario
                Toast.makeText(context, "Te has registrado correctamente", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Si el registro falla, mostrar un mensaje de error al usuario
                Toast.makeText(
                    context,
                    "Error al registrar. El usuario ya existe.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}