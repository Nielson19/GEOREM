package com.locationapptest.georemcompose

import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.locationapptest.georemcompose.ui.theme.GeoremComposeTheme
import com.locationapptest.georemcompose.ui.theme.fontFamilyBeon
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // map variables



            GeoremComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

@Composable
fun TopTitle(
    @StringRes title: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, bottom = (0.dp)),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = stringResource(title),
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = fontFamilyBeon,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier,
        )



    }
}

@Composable
fun SearchBar(){
    Surface(
        color = Color.Transparent
    ){

        TextField(
            value = "",
            onValueChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.colors(

                // color changes when focus

                unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                focusedContainerColor = MaterialTheme.colorScheme.onSecondary,

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,

                focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.outline,

                focusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.outline

            ),
            placeholder = {Text(stringResource(R.string.placeholder_search))},

            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(15.dp)
                .clip(CircleShape)


        )
    }

}

@Composable
fun ReminderGroupElement(
    @StringRes groupName: Int,
    groupIcon: ImageVector,
    @DrawableRes background: Int,
    scrollState: LazyListState,
    modifier: Modifier = Modifier
    ) {


    Box(
        modifier = Modifier
    )
    {
        Surface(
            modifier = Modifier
                .clickable { /* TODO */ },
            color = MaterialTheme.colorScheme.onSecondary,
            shape = MaterialTheme.shapes.large,

        ) {

            Box(
                modifier = Modifier
                    .paint(
                        painter = painterResource(background),
                        contentScale = ContentScale.FillBounds,
                    )
                    .size(height = 100.dp, width = 190.dp)
                    .padding(start = 15.dp, end = 25.dp, top = 10.dp)
                )
            {
                Column(
                    modifier = Modifier

                ) {
                    Spacer(Modifier.height(30.dp))
                    Text(
                        text = stringResource(groupName),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = fontFamilyBeon,
                        fontSize = 15.sp,
                        modifier = Modifier
                    )
                }
                Icon(
                    imageVector = groupIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 8.dp)
                )
            }
        }

    }
}

@Composable
fun ReminderGroupList() {

    val listState = rememberLazyListState()

    LazyVerticalGrid(
        modifier = Modifier
            .height(360.dp),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 13.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),

    ){
       items(reminderGroupData){
           item -> ReminderGroupElement(item.text, item.icon, item.background, listState)
       }
    }

}

@Composable
fun GoogleMapView() {
    val newYork = LatLng(40.7128,-73.935242)
    val newYorkState = MarkerState(position = newYork)
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(newYork, 10f)
    }
    val uiSettings by remember {mutableStateOf(MapUiSettings()) } // maintains the same state when it recompose
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL
            ))
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .height(400.dp)
            .fillMaxWidth()

    ){
        GoogleMap(
            modifier = Modifier
                .matchParentSize(),
            googleMapOptionsFactory = {
                GoogleMapOptions().mapId("1b5333b92ea1af56")
            },
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
        ) {
            Marker(
                tag = "This",
                state = newYorkState,
                title = "Reminder"
            )
        }
    }

}

@Composable
fun HomeScreenLayout(){
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
    ){
        TopTitle(
            title = R.string.home_screen_title,
        );
        Spacer(Modifier
            .height(0.dp))
        SearchBar()
        Spacer(Modifier
            .height(0.dp))
        ReminderGroupList()
        GoogleMapView()
    }
}



////////////////////////////ANIMATIONS//////////////////////////////////////////

private fun calculateFadingFraction(scrollState: LazyListState): Float {
    val fadingRange = 5.0f
    val distanceToScrollIndex =
        (scrollState.firstVisibleItemIndex - scrollState.layoutInfo.visibleItemsInfo.first().index).absoluteValue
    val normalizeDistance = distanceToScrollIndex.toFloat() / fadingRange
    val clampedDistance = normalizeDistance.coerceIn(0f, 1f)
    return 1f - clampedDistance
}


////////////////////////////DATA//////////////////////////////////////////

private val reminderGroupData = listOf(
    Triple(R.string.SampleText_1, Icons.Default.LocationOn, R.drawable.bg_rem_grp_elem_fav),
    Triple(R.string.SampleText_2, Icons.Default.LocationOn, R.drawable.bg_rem_grp_elem_schdld),
    Triple(R.string.SampleText_3, Icons.Default.LocationOn, R.drawable.bg_rem_grp_elem_fav),
    Triple(R.string.SampleText_1, Icons.Default.LocationOn, R.drawable.bg_rem_grp_elem_fav),
    Triple(R.string.SampleText_2, Icons.Default.LocationOn, R.drawable.bg_rem_grp_elem_schdld),
    Triple(R.string.SampleText_3, Icons.Default.LocationOn, R.drawable.bg_rem_grp_elem_fav),
    Triple(R.string.SampleText_1, Icons.Default.LocationOn, R.drawable.bg_rem_grp_elem_fav),
    Triple(R.string.SampleText_2, Icons.Default.LocationOn, R.drawable.bg_rem_grp_elem_schdld),
    Triple(R.string.SampleText_3, Icons.Default.LocationOn, R.drawable.bg_rem_grp_elem_fav)


).map{ triple -> GroupCollectionList(
    text = triple.first,
    icon = triple.second,
    background = triple.third,
)}

private data class GroupCollectionList(
    @StringRes val text: Int,
    val icon: ImageVector,
    @DrawableRes val background: Int,


)

////////////////////////////PREVIEWS/////////////////////////////////
@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    GeoremComposeTheme {
       SearchBar();
        }

    }

@Preview(showBackground = true)
@Composable
fun ReminderGroupElementPreview() {
    GeoremComposeTheme {
            ReminderGroupElement(
                modifier = Modifier,
                groupName = R.string.reminder_placeholder,
                groupIcon = Icons.Default.FavoriteBorder,
                background = R.drawable.bg_rem_grp_elem_fav,
                scrollState = LazyListState()

            );
        }

    }

@Preview(showBackground = true)
@Composable
fun ReminderGroupListPreview() {
    GeoremComposeTheme {
        ReminderGroupList();
    }

}

@Preview(showBackground = true)
@Composable
fun TopTitlePreview(
){
    GeoremComposeTheme {
        TopTitle(
            title = R.string.home_screen_title,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GoogleMapViewPreview(){
    GeoremComposeTheme {
        GoogleMapView()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenLayoutPreview(){
    GeoremComposeTheme {
        HomeScreenLayout()
    }
}




