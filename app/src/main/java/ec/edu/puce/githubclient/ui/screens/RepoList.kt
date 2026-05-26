package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.ui.components.RepoItem
import ec.edu.puce.githubclient.viewmodels.RepoListViewModels

@Composable
fun RepoList(
    modifier: Modifier = Modifier,
    viewModels: RepoListViewModels = viewModel(),
    onNavigateToForm: () -> Unit = {}
) {

    val repos by viewModels.repos.collectAsState()
    val isLoading by viewModels.isLoading.collectAsState()
    val errorMsg by viewModels.errorMsg.collectAsState()

    Scaffold (
        floatingActionButton = {
            if (!isLoading && errorMsg == null) {
                FloatingActionButton(
                    onClick = onNavigateToForm,
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Añadir repositorio"
                    )
                }
            }
        }

    ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                errorMsg?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }

                if (!isLoading && errorMsg == null) {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        items(repos) { repo ->

                            RepoItem(
                                repository = repo
                            )

                        }
                    }
                }
        }
    }
}