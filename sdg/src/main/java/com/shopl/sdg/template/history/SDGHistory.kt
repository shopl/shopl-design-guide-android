package com.shopl.sdg.template.history

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg.template.profile.SDGProfileSecond
import com.shopl.sdg_common.enums.DisplayType
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.IOText

enum class HistoryPosition {
    FIRST, MIDDLE, LAST, SOLO
}

@Composable
fun SDGHistoryHeader01(
    roleType: String,
    userRegImg: String?,
    userName: String,
    groupName: String,
    date: String,
    history: String,
    textColor: Color,
    backgroundColor: Color,
    historyPosition: HistoryPosition,
    onClickAvatar: () -> Unit,
    horizontalPadding: Dp = 0.dp
) {
    val density = LocalDensity.current
    var bottomLineHeight by remember { mutableStateOf(0.dp) }

    Column(
        modifier = Modifier
            .background(color = SDGColor.Neutral0)
            .padding(horizontal = horizontalPadding)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(3.5.dp))
            if (historyPosition == HistoryPosition.MIDDLE || historyPosition == HistoryPosition.LAST) {
                Divider(
                    modifier = Modifier
                        .height(20.dp)
                        .width(1.dp),
                    color = SDGColor.Neutral200
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        Box(Modifier.fillMaxWidth()) {
            Row {
                Column() {
                    Spacer(modifier = Modifier.height(5.dp))
                    Canvas(modifier = Modifier.size(8.dp)) {
                        drawCircle(
                            color = SDGColor.Neutral300
                        )
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                IOText(
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            bottomLineHeight = with(density) { coordinates.size.height.toDp() } - 18.dp
                        },
                    text = date,
                    textColor = SDGColor.Neutral400,
                    fontSize = 14.sp
                )
            }
            if (historyPosition == HistoryPosition.MIDDLE || historyPosition == HistoryPosition.FIRST) {
                Divider(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 3.5.dp, top = 18.dp)
                        .height(bottomLineHeight)
                        .width(1.dp),
                    color = SDGColor.Neutral200
                )
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            if (historyPosition == HistoryPosition.MIDDLE || historyPosition == HistoryPosition.FIRST) {
                Divider(
                    modifier = Modifier
                        .padding(horizontal = 3.5.dp)
                        .width(1.dp)
                        .fillMaxHeight(),
                    color = SDGColor.Neutral200
                )
            } else {
                Spacer(modifier = Modifier.width(8.dp))
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column() {
                Spacer(modifier = Modifier.height(20.dp))
                IOText(text = history, textColor = textColor, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(10.dp))
                SDGProfileSecond(
                    roleType = roleType,
                    userRegImg = userRegImg,
                    userName = userName,
                    groupName = groupName,
                    backgroundColor = backgroundColor,
                    paddingValues = PaddingValues(
                        vertical = 10.dp,
                        horizontal = 12.dp,
                    ),
                    radius = 12.dp,
                    onClickAvatar = onClickAvatar
                )
            }
        }
    }
}

@Composable
fun SDGHistoryHeader(
    circleColor: Color = SDGColor.Neutral300,
    date: String,
    title: String,
    titleColor: Color,
    historyPosition: HistoryPosition,
    body: @Composable () -> Unit,
    horizontalPadding: Dp = 0.dp
) {
    val density = LocalDensity.current
    var bottomLineHeight by remember { mutableStateOf(0.dp) }

    Column(
        modifier = Modifier
            .background(color = SDGColor.Neutral0)
            .padding(horizontal = horizontalPadding)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(3.5.dp))
            if (historyPosition == HistoryPosition.MIDDLE || historyPosition == HistoryPosition.LAST) {
                Divider(
                    modifier = Modifier
                        .height(20.dp)
                        .width(1.dp),
                    color = SDGColor.Neutral200
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        Box(Modifier.fillMaxWidth()) {
            Row {
                Column() {
                    Spacer(modifier = Modifier.height(5.dp))
                    Canvas(modifier = Modifier.size(8.dp)) {
                        drawCircle(
                            color = circleColor
                        )
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                IOText(
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            bottomLineHeight = with(density) { coordinates.size.height.toDp() } - 18.dp
                        },
                    text = date,
                    textColor = SDGColor.Neutral400,
                    fontSize = 14.sp
                )
            }
            if (historyPosition == HistoryPosition.MIDDLE || historyPosition == HistoryPosition.FIRST) {
                Divider(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 3.5.dp, top = 18.dp)
                        .height(bottomLineHeight)
                        .width(1.dp),
                    color = SDGColor.Neutral200
                )
            }
        }
        var rowHeight by remember { mutableIntStateOf(0) }
        Row(
            Modifier
                .onSizeChanged { rowHeight = it.height }
                .fillMaxWidth()
        ) {
            if (historyPosition == HistoryPosition.MIDDLE || historyPosition == HistoryPosition.FIRST) {
                Divider(
                    modifier = Modifier
                        .padding(horizontal = 3.5.dp)
                        .width(1.dp)
                        .height(with(LocalDensity.current) { rowHeight.toDp() }),
                    color = SDGColor.Neutral200
                )
            } else {
                Spacer(modifier = Modifier.width(8.dp))
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                IOText(text = title, textColor = titleColor, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(10.dp))
                body()
            }
        }
    }
}

@Composable
fun SDGHistoryTop(
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .height(20.dp)
            .fillMaxWidth()
            .background(
                color = SDGColor.Neutral0,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
    )
}

@Composable
fun SDGHistoryBottom(
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .height(20.dp)
            .fillMaxWidth()
            .background(
                color = SDGColor.Neutral0,
                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
            )
    )
}

@Preview
@Composable
private fun PrevHistoryHeader() {
    Surface() {
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            SDGHistoryHeader01(
                roleType = "2",
                userRegImg = null,
                userName = "Gren",
                groupName = "Android",
                date = "2023.02.10(금) 11:00",
                history = "초대 완료",
                textColor = SDGColor.Neutral700,
                backgroundColor = SDGColor.Neutral50,
                historyPosition = HistoryPosition.FIRST,
                onClickAvatar = {}
            )
            SDGHistoryHeader01(
                roleType = "2",
                userRegImg = null,
                userName = "Gren",
                groupName = "Android",
                date = "2023.02.10(금) 11:00",
                history = "초대 완료",
                textColor = SDGColor.Neutral700,
                backgroundColor = SDGColor.Neutral50,
                historyPosition = HistoryPosition.MIDDLE,
                onClickAvatar = {}
            )
            SDGHistoryHeader01(
                roleType = "2",
                userRegImg = null,
                userName = "Gren",
                groupName = "Android",
                date = "2023.02.10(금) 11:00",
                history = "초대 완료",
                textColor = SDGColor.Neutral700,
                backgroundColor = SDGColor.Neutral50,
                historyPosition = HistoryPosition.LAST,
                onClickAvatar = {}
            )
            SDGHistoryHeader01(
                roleType = "2",
                userRegImg = null,
                userName = "Gren",
                groupName = "Android",
                date = "2023.02.10(금) 11:00",
                history = "초대 완료",
                textColor = SDGColor.Neutral700,
                backgroundColor = SDGColor.Neutral50,
                historyPosition = HistoryPosition.SOLO,
                onClickAvatar = {}
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview
@Composable
private fun PrevHistory() {
    Surface() {
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            SDGHistoryHeader(
                date = "11:00",
                title = "기본 근무 일정 변경에 의해 스케줄 변경됨",
                titleColor = SDGColor.Neutral700,
                historyPosition = HistoryPosition.SOLO,
                body = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = SDGColor.Neutral50,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(
                                vertical = 10.dp,
                                horizontal = 12.dp
                            ),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SDGProfileSecond(
                            roleType = "2",
                            userRegImg = null,
                            userName = "Gren",
                            groupName = "Android",
                            backgroundColor = SDGColor.Transparent,
                            profileType = DisplayType.NORMAL,
                        )
                        SDGProfileSecond(
                            roleType = "2",
                            userRegImg = null,
                            userName = "Gren",
                            groupName = "Android",
                            backgroundColor = SDGColor.Transparent,
                            profileType = DisplayType.NORMAL,
                        )
                        SDGProfileSecond(
                            roleType = "2",
                            userRegImg = null,
                            userName = "Gren",
                            groupName = "Android",
                            backgroundColor = SDGColor.Transparent,
                            profileType = DisplayType.NORMAL,
                        )
                        SDGProfileSecond(
                            roleType = "2",
                            userRegImg = null,
                            userName = "Gren",
                            groupName = "Android",
                            backgroundColor = SDGColor.Transparent,
                            profileType = DisplayType.NORMAL,
                        )
                        SDGProfileSecond(
                            roleType = "2",
                            userRegImg = null,
                            userName = "Gren",
                            groupName = "Android",
                            backgroundColor = SDGColor.Transparent,
                            profileType = DisplayType.NORMAL,
                        )
                        SDGProfileSecond(
                            roleType = "2",
                            userRegImg = null,
                            userName = "Gren",
                            groupName = "Android",
                            backgroundColor = SDGColor.Transparent,
                            profileType = DisplayType.NORMAL,
                        )
                        SDGProfileSecond(
                            roleType = "2",
                            userRegImg = null,
                            userName = "Gren",
                            groupName = "Android",
                            backgroundColor = SDGColor.Transparent,
                            profileType = DisplayType.NORMAL,
                        )
                        SDGProfileSecond(
                            roleType = "2",
                            userRegImg = null,
                            userName = "Gren",
                            groupName = "Android",
                            backgroundColor = SDGColor.Transparent,
                            profileType = DisplayType.NORMAL,
                        )
                        SDGProfileSecond(
                            roleType = "2",
                            userRegImg = null,
                            userName = "Gren",
                            groupName = "Android",
                            backgroundColor = SDGColor.Transparent,
                            profileType = DisplayType.NORMAL,
                        )
                        SDGProfileSecond(
                            roleType = "2",
                            userRegImg = null,
                            userName = "Gren",
                            groupName = "Android",
                            backgroundColor = SDGColor.Transparent,
                            profileType = DisplayType.NORMAL,
                        )
                    }
                },
            )
        }
    }
}