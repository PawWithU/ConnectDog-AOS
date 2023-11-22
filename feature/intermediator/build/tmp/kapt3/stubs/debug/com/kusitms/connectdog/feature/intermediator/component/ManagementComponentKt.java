package com.kusitms.connectdog.feature.intermediator.component;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.style.TextAlign;
import com.kusitms.connectdog.core.model.InterApplication;
import com.kusitms.connectdog.feature.intermediator.R;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0001\u001a\u0016\u0010\u0007\u001a\u00020\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0001\u001a\u001e\u0010\t\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0001\u001a\u001e\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0001\u001a\u0010\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u001a>\u0010\r\u001a\u00020\u00012\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u00a8\u0006\u0013"}, d2 = {"CompletedContent", "", "application", "Lcom/kusitms/connectdog/core/model/InterApplication;", "onClickReview", "Lkotlin/Function0;", "onClickRecent", "CompletedDialog", "onDismissRequest", "InProgressContent", "onClick", "PendingContent", "RecruitingContent", "ReviewRecentButton", "modifier", "Landroidx/compose/ui/Modifier;", "hasReview", "", "hasRecent", "intermediator_debug"})
public final class ManagementComponentKt {
    
    @androidx.compose.runtime.Composable
    public static final void RecruitingContent(@org.jetbrains.annotations.NotNull
    com.kusitms.connectdog.core.model.InterApplication application) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void PendingContent(@org.jetbrains.annotations.NotNull
    com.kusitms.connectdog.core.model.InterApplication application, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void InProgressContent(@org.jetbrains.annotations.NotNull
    com.kusitms.connectdog.core.model.InterApplication application, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void CompletedContent(@org.jetbrains.annotations.NotNull
    com.kusitms.connectdog.core.model.InterApplication application, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClickReview, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClickRecent) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void ReviewRecentButton(androidx.compose.ui.Modifier modifier, boolean hasReview, boolean hasRecent, kotlin.jvm.functions.Function0<kotlin.Unit> onClickReview, kotlin.jvm.functions.Function0<kotlin.Unit> onClickRecent) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void CompletedDialog(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismissRequest) {
    }
}