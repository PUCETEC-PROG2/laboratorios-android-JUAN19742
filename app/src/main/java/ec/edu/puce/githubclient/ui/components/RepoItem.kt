package ec.edu.puce.githubclient.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ec.edu.puce.githubclient.models.GithubUser
import ec.edu.puce.githubclient.models.Repository

@Composable
fun RepoItem(
    repository: Repository,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding( all = 8.dp )
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding( all = 16.dp )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = repository.owner.avatarUrl,
                    contentDescription = "Imagen de ${repository.owner.login}",
                    modifier = Modifier.size ( size = 80.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = Color(0xFF2196F3),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onEdit() }
                    )
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color(0xFFF44336),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onDelete() }
                    )
                }
            }

            Spacer(modifier = Modifier.width( width = 16.dp))
            Column {
                Text(
                    text = "Nombre del repositorio",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = repository.name,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height( height = 8.dp))

                Text(
                    text = "Descripción del repositorio",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = repository.description ?: "Sin descripción",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height( height = 8.dp))

                Text(
                    text = "Lenguaje",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = repository.language ?: "No especificado",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoItemPreview() {
    val repository: Repository = Repository(
        id = "123214",
        name = "Repoositorio de Juan",
        description = "Proyecto de Juan",
        language = "Python",
        owner = GithubUser(
            id = "1232213",
            login = "juanrueda",
            avatarUrl = "https://www.shutterstock.com/image-vector/cute-cartoon-owl-outline-vector-600nw-2692589859.jpg",
        )
    )
    RepoItem(repository)
}
