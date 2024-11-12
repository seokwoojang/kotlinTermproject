package kr.ac.kumoh.ce.s20190995.termproject23

import android.widget.RatingBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import coil.compose.AsyncImage
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

enum class LoaScreen {
    List,
    Detail
}

@Composable
fun LoaApp(loaList: List<Loa>) {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = LoaScreen.List.name)
    {
        composable(route = LoaScreen.List.name) {
            LoaList(loaList) {
                navController.navigate(it)
            }
        }
        composable(route = LoaScreen.Detail.name + "/{index}",
            arguments = listOf(navArgument("index"){
                type = NavType.IntType
            })
        ) {
            val index = it.arguments?.getInt("index") ?: -1
            if(index >= 0)
                LoaDetail(loaList[index])
        }
    }
}

@Composable
fun LoaList(list: List<Loa>, onNavigateToDetail: (String) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(list.size){
            LoaItem(it, list[it], onNavigateToDetail)
        }
    }
}

@Composable
fun LoaItem(index: Int, loa: Loa, onNavigateToDetail: (String) -> Unit) {
    Card(
        modifier = Modifier.clickable {
            onNavigateToDetail(LoaScreen.Detail.name + "/$index") },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(8.dp)
        ) {
            AsyncImage(model = loa.ImageUrl,
                contentDescription = "직업 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(percent = 10)))
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                , verticalArrangement = Arrangement.SpaceEvenly
            ) {
                TextJob(loa.job)
                TextClass(loa.clas)
            }
        }
    }

}

@Composable
fun TextJob(job: String) {
    Text(job, fontSize = 30.sp)
}

@Composable
fun TextClass(clas : String){
    Text(clas, fontSize = 20.sp)
}

@Composable
fun LoaDetail(loa: Loa) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RatingBar(loa.rating)
        Spacer(modifier = Modifier.height(16.dp))

        Text(loa.job,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            lineHeight = 45.sp)
        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(model = loa.ImageUrl
            , contentDescription = "직업 이미지",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(400.dp))
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = loa.jobImage,
                contentDescription = "직업 마크 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Text(loa.clas, fontSize = 30.sp)
        }
        Spacer(modifier = Modifier.height(32.dp))

        loa.introduce?.let{
            Text(it,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp)
        }
    }
}

@Composable
fun RatingBar(stars: Int){
    Row {
        repeat(stars){
            Icon(imageVector = Icons.Filled.Star,
                contentDescription = "stars",
                modifier = Modifier.size(48.dp),
                tint = Color.Red
            )
        }
    }
}