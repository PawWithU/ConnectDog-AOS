package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray6
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.designsystem.theme.Orange10

@Composable
fun ConnectDogRegions(
    onSelected: (String) -> Unit
) {
    var selectedRegion by remember { mutableStateOf("") }
    SiDoList { sido, gugun ->
        selectedRegion = "$sido $gugun"
        onSelected(selectedRegion)
    }
}

@Composable
fun SiDoList(
    onRegionSelected: (String, String) -> Unit
) {
    var selectedSiDo by remember { mutableStateOf(regions.keys.first()) }

    Row(modifier = Modifier.fillMaxWidth()) {
        LazyColumn {
            items(regions.keys.toList()) { item ->
                SiDoItem(text = item, isSelected = selectedSiDo == item) {
                    selectedSiDo = item
                }
            }
        }

        regions[selectedSiDo]?.let { cities ->
            GuGunList(list = cities) {
                onRegionSelected(selectedSiDo, it)
            }
        }
    }
}

@Composable
private fun GuGunList(
    list: List<String>,
    onGuGunSelected: (String) -> Unit
) {
    var selected by remember { mutableStateOf("") }
    LazyColumn {
        items(list) {
            GuGunItem(text = it, isSelected = selected == it) {
                selected = it
                onGuGunSelected(it)
            }
        }
    }
}

@Composable
private fun SiDoItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(width = 110.dp, height = 45.dp)
            .background(color = if (isSelected) MaterialTheme.colorScheme.primary else Gray7)
            .border(BorderStroke(width = 1.dp, color = Gray6))
            .clickable { onClick() }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            style = MaterialTheme.typography.titleSmall,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else Gray3
        )
    }
}

@Composable
private fun GuGunItem(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.height(45.dp).fillMaxWidth()
            .background(if (isSelected) Orange10 else MaterialTheme.colorScheme.surface)
            .clickable { onClick() }
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart).padding(horizontal = 20.dp),
            text = text,
            style = MaterialTheme.typography.titleSmall,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            color = if (isSelected) Gray1 else Gray3
        )
    }
}

@Preview
@Composable
private fun ConnectDogRegionPreview() {
    ConnectDogRegions({})
}

val regions = linkedMapOf(
    "서울" to listOf(
        "서울 전체",
        "강남구",
        "강동구",
        "강북구",
        "강서구",
        "관악구",
        "광진구",
        "구로구",
        "금천구",
        "노원구",
        "도봉구",
        "동대문구",
        "동작구",
        "마포구",
        "서대문구",
        "서초구",
        "성동구",
        "성북구",
        "송파구",
        "양천구",
        "영등포구",
        "용산구",
        "은평구",
        "종로구",
        "중구",
        "중랑구"
    ),
    "경기" to listOf(
        "경기 전체", "가평군", "고양시", "과천시", "광명시", "광주시", "구리시", "군포시", "김포시", "남양주시",
        "동두천시", "부천시", "성남시", "수원시", "시흥시", "안산시", "안성시", "안양시", "양주시", "양평군",
        "여주시", "연천군", "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시",
        "하남시", "화성시"
    ),
    "인천" to listOf("인천 전체", "강화군", "계양구", "남동구", "동구", "미추홀구", "부평구", "서구", "연수구", "옹진군", "중구"),
    "부산" to listOf(
        "부산 전체",
        "강서구",
        "금정구",
        "기장군",
        "남구",
        "동구",
        "동래구",
        "진구",
        "북구",
        "사상구",
        "사하구",
        "서구",
        "수영구",
        "연제구",
        "영도구",
        "중구",
        "해운"
    ),
    "대구" to listOf("대구 전체", "군위군", "남구", "달서구", "달성군", "동구", "북구", "서구", "수성구", "중구"),
    "광주" to listOf("광주 전체", "광산구", "남구", "동구", "북구", "서구"),
    "세종" to listOf("세종 전체"),
    "대전" to listOf("대전 전체", "대덕구", "동구", "서구", "유성구", "중구"),
    "울산" to listOf("울산 전체", "남구", "동구", "북구", "울주군", "중구"),
    "강원" to listOf(
        "강원 전체", "강릉시", "고성군", "동해시", "삼척시", "속초시", "양구군", "양양군", "영월군",
        "원주시", "인제군", "정선군", "철원군", "춘천시", "태백시", "평창군", "홍천군", "화천군", "횡성군"
    ),
    "충북" to listOf(
        "충북 전체",
        "괴산군",
        "단양군",
        "보은군",
        "영동군",
        "옥천군",
        "음성군",
        "제천시",
        "증평군",
        "진천군",
        "청주시",
        "충주시"
    ),
    "충남" to listOf(
        "충남 전체",
        "계룡시",
        "공주시",
        "금산군",
        "논산시",
        "당진시",
        "보령시",
        "부여군",
        "서산시",
        "서천군",
        "아산시",
        "예산군",
        "천안시",
        "청양군",
        "태안군",
        "홍성군"
    ),
    "전북" to listOf(
        "전북 전체",
        "고창군",
        "군산시",
        "김제시",
        "남원시",
        "무주군",
        "부안군",
        "순창군",
        "완주군",
        "익산시",
        "임실군",
        "장수군",
        "전주시",
        "정읍시",
        "진안군"
    ),
    "전남" to listOf(
        "전남 전체", "강진군", "고흥군", "곡성군", "광양시", "구례군", "나주시", "담양군", "목포시", "무안군", "보성군", "순천시", "신안군",
        "여수시", "영광군", "영암군", "완도군", "장성군", "장흥군", "진도군", "함평군", "해남군", "화순군"
    ),
    "경북" to listOf(
        "경북 전체", "경산시", "경주시", "고령군", "구미시", "김천시", "문경시", "봉화군", "상주시", "성주군", "안동시", "영덕군", "영양군",
        "영주시", "영천시", "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군", "포항시"
    ),
    "경남" to listOf(
        "경남 전체", "창원시", "의창구", "성산구", "거제시", "거창군", "고성군", "김해시", "남해군", "밀양시", "사천시", "산청군", "양산시",
        "의령군", "진주시", "진해구", "창녕군", "창원시", "통영시", "하동군", "함안군", "함양군", "합천군"
    ),
    "제주" to listOf("제주 전체"),
    "" to listOf()
)
