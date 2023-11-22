package com.kusitms.connectdog.feature.intermediator;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.kusitms.connectdog.core.data.repository.InterManagementRepository;
import com.kusitms.connectdog.core.model.InterApplication;
import com.kusitms.connectdog.core.model.Volunteer;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@dagger.hilt.android.lifecycle.HiltViewModel
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J:\u0010$\u001a\b\u0012\u0004\u0012\u00020\r0\f2\"\u0010%\u001a\u001e\b\u0001\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190(0\'\u0012\u0006\u0012\u0004\u0018\u00010)0&H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010*J\u000e\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u00118F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000fR\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\n0\u001f8F\u00a2\u0006\u0006\u001a\u0004\b \u0010!R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u000f\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006/"}, d2 = {"Lcom/kusitms/connectdog/feature/intermediator/InterManagementViewModel;", "Landroidx/lifecycle/ViewModel;", "managementRepository", "Lcom/kusitms/connectdog/core/data/repository/InterManagementRepository;", "(Lcom/kusitms/connectdog/core/data/repository/InterManagementRepository;)V", "_errorFlow", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "", "_volunteerResponse", "Landroidx/lifecycle/MutableLiveData;", "Lcom/kusitms/connectdog/core/model/Volunteer;", "completedUiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/kusitms/connectdog/feature/intermediator/InterApplicationUiState;", "getCompletedUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "errorFlow", "Lkotlinx/coroutines/flow/SharedFlow;", "getErrorFlow", "()Lkotlinx/coroutines/flow/SharedFlow;", "progressUiState", "getProgressUiState", "recruitingUiState", "getRecruitingUiState", "selectedApplication", "Lcom/kusitms/connectdog/core/model/InterApplication;", "getSelectedApplication", "()Lcom/kusitms/connectdog/core/model/InterApplication;", "setSelectedApplication", "(Lcom/kusitms/connectdog/core/model/InterApplication;)V", "volunteerResponse", "Landroidx/lifecycle/LiveData;", "getVolunteerResponse", "()Landroidx/lifecycle/LiveData;", "waitingUiState", "getWaitingUiState", "createUiStateFlow", "getApplication", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/flow/StateFlow;", "getVolunteer", "", "applicationId", "", "intermediator_debug"})
public final class InterManagementViewModel extends androidx.lifecycle.ViewModel {
    private final com.kusitms.connectdog.core.data.repository.InterManagementRepository managementRepository = null;
    private final kotlinx.coroutines.flow.MutableSharedFlow<java.lang.Throwable> _errorFlow = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> recruitingUiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> waitingUiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> progressUiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> completedUiState = null;
    private final androidx.lifecycle.MutableLiveData<com.kusitms.connectdog.core.model.Volunteer> _volunteerResponse = null;
    @org.jetbrains.annotations.Nullable
    private com.kusitms.connectdog.core.model.InterApplication selectedApplication;
    
    @javax.inject.Inject
    public InterManagementViewModel(@org.jetbrains.annotations.NotNull
    com.kusitms.connectdog.core.data.repository.InterManagementRepository managementRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.SharedFlow<java.lang.Throwable> getErrorFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> getRecruitingUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> getWaitingUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> getProgressUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> getCompletedUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<com.kusitms.connectdog.core.model.Volunteer> getVolunteerResponse() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.kusitms.connectdog.core.model.InterApplication getSelectedApplication() {
        return null;
    }
    
    public final void setSelectedApplication(@org.jetbrains.annotations.Nullable
    com.kusitms.connectdog.core.model.InterApplication p0) {
    }
    
    public final void getVolunteer(long applicationId) {
    }
    
    private final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> createUiStateFlow(kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super java.util.List<com.kusitms.connectdog.core.model.InterApplication>>, ? extends java.lang.Object> getApplication) {
        return null;
    }
}