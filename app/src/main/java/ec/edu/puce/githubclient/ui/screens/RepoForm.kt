package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.ui.theme.GithubClientTheme
import ec.edu.puce.githubclient.viewmodels.RepoFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoForm (
    repository: Repository? = null,
    onBackClick: () -> Unit = {},
    onSaveSuccess:() -> Unit = {},
    viewModel: RepoFormViewModel = viewModel ()
) {

    val isLoading by viewModel.isLoading.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()

    var name by remember { mutableStateOf(repository?.name ?: "") }
    var description by remember { mutableStateOf(repository?.description ?: "") }

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            onSaveSuccess()
            viewModel.resetSuccess()
        }
    }


    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text( text = if (repository == null) "Nuevo Repositorio" else "Editar Repositorio" ) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack ,
                            contentDescription = "Regresar",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),

            contentAlignment = Alignment.Center,
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy( space = 16.dp ),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                if (isLoading){
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else  {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre del Repositorio") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Descripcion del Repositorio") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4
                    )

                    if (!errorMsg.isNullOrBlank()) {
                        Text(
                            text = errorMsg!!,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    Button(
                        onClick = {
                            if (repository == null) {
                                viewModel.createRepository(name, description)
                            } else {
                                viewModel.updateRepository(
                                    owner = repository.owner.login,
                                    oldName = repository.name,
                                    newName = name,
                                    description = description
                                )
                            }
                        },
                        enabled = name.isNotBlank(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Guardar"
                        )
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        Text("Guardar")
                    }
                }
            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun RepoFormPreview() {
    GithubClientTheme {
        RepoForm()
    }
}