package com.kusitms.connectdog.feature.intermediator.screen;

import android.util.Log;
import androidx.annotation.StringRes;
import androidx.compose.foundation.ExperimentalFoundationApi;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType;
import com.kusitms.connectdog.core.model.InterApplication;
import com.kusitms.connectdog.feature.intermediator.InterApplicationUiState;
import com.kusitms.connectdog.feature.intermediator.InterManagementViewModel;
import com.kusitms.connectdog.feature.intermediator.R;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a$\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\tH\u0003\u001a*\u0010\u000b\u001a\u00020\u00012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0001\u001a\\\u0010\u0011\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0011\u0010\u0012\u001a\r\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\u0002\b\u00132\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\u0002\b\u00132\u0011\u0010\u0015\u001a\r\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\u0002\b\u00132\u0011\u0010\u0016\u001a\r\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\u0002\b\u0013H\u0003\u001a$\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\tH\u0003\u001a\u0010\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a \u0010\u0019\u001a\u00020\u00012\b\b\u0001\u0010\u001a\u001a\u00020\u000e2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u00a8\u0006\u001b"}, d2 = {"Completed", "", "uiState", "Lcom/kusitms/connectdog/feature/intermediator/InterApplicationUiState;", "onClickReview", "Lkotlin/Function0;", "onClickRecent", "InProgress", "onClick", "Lkotlin/Function1;", "Lcom/kusitms/connectdog/core/model/InterApplication;", "InterManagementRoute", "onBackClick", "tabIndex", "", "viewModel", "Lcom/kusitms/connectdog/feature/intermediator/InterManagementViewModel;", "ManagementScreen", "firstContent", "Landroidx/compose/runtime/Composable;", "secondContent", "thirdContent", "fourthContent", "PendingApproval", "Recruiting", "TopAppBar", "titleRes", "intermediator_debug"})
public final class InterManagementScreenKt {
    
    @androidx.compose.runtime.Composable
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    public static final void InterManagementRoute(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackClick, int tabIndex, @org.jetbrains.annotations.NotNull
    com.kusitms.connectdog.feature.intermediator.InterManagementViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void TopAppBar(@androidx.annotation.StringRes
    int titleRes, kotlin.jvm.functions.Function0<kotlin.Unit> onBackClick) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void Recruiting(com.kusitms.connectdog.feature.intermediator.InterApplicationUiState uiState) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void PendingApproval(com.kusitms.connectdog.feature.intermediator.InterApplicationUiState uiState, kotlin.jvm.functions.Function1<? super com.kusitms.connectdog.core.model.InterApplication, kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void InProgress(com.kusitms.connectdog.feature.intermediator.InterApplicationUiState uiState, kotlin.jvm.functions.Function1<? super com.kusitms.connectdog.core.model.InterApplication, kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void Completed(com.kusitms.connectdog.feature.intermediator.InterApplicationUiState uiState, kotlin.jvm.functions.Function0<kotlin.Unit> onClickReview, kotlin.jvm.functions.Function0<kotlin.Unit> onClickRecent) {
    }
    
    @androidx.compose.runtime.Composable
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    private static final void ManagementScreen(int tabIndex, kotlin.jvm.functions.Function0<kotlin.Unit> firstContent, kotlin.jvm.functions.Function0<kotlin.Unit> secondContent, kotlin.jvm.functions.Function0<kotlin.Unit> thirdContent, kotlin.jvm.functions.Function0<kotlin.Unit> fourthContent) {
    }
}