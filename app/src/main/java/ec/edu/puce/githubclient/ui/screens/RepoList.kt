package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ec.edu.puce.githubclient.ui.components.RepoItem

@Composable
fun RepoList () {
    Column (
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 48.dp)
    ){
        RepoItem(
            name = "Lewis Hamilton",
            description = "Proyecto de Ferrari",
            avatarImg = "https://ichef.bbci.co.uk/ace/standard/3840/cpsprodpb/cea1/live/1de105b0-f5a5-11ef-bcea-7b70a14a5556.jpg",
            language = "Python"
        )
        RepoItem(
            name = "HOLA COMO ESTAS",
            description = "Proyecto de ARQUITECTURA",
            avatarImg = "https://www.shutterstock.com/image-vector/cute-cartoon-owl-outline-vector-600nw-2692589859.jpg",
            language = "Python"
        )
        RepoItem(
            name = "Aplicacion Movil",
            description = "Lista de prueba",
            avatarImg = "https://www.shutterstock.com/image-vector/cute-cartoon-owl-outline-vector-600nw-2692589859.jpg",
            language = "Python"
        )

    }
}

@Preview(showBackground = true)
@Composable
fun RepoListPreview() {
    RepoList()
}