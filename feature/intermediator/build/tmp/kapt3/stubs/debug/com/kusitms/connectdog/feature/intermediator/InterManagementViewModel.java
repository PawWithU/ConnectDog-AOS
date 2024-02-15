package com.kusitms.connectdog.feature.intermediator;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorProfileInfoResponseItem;
import com.kusitms.connectdog.core.data.repository.InterManagementRepository;
import com.kusitms.connectdog.core.model.DataUiState;
import com.kusitms.connectdog.core.model.InterApplication;
import com.kusitms.connectdog.core.model.Volunteer;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@dagger.hilt.android.lifecycle.HiltViewModel
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\r\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u00105\u001a\u0002062\u0006\u00107\u001a\u000208J\u000e\u00109\u001a\u0002062\u0006\u00107\u001a\u000208J:\u0010:\u001a\b\u0012\u0004\u0012\u00020\t0\u00182\"\u0010;\u001a\u001e\b\u0001\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020,0>0=\u0012\u0006\u0012\u0004\u0018\u00010?0<H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010@J\u0006\u0010A\u001a\u000206J\u000e\u0010B\u001a\u0002062\u0006\u00107\u001a\u000208J\u0006\u0010C\u001a\u000206J\u0006\u0010D\u001a\u000206JK\u0010E\u001a\u0002062\"\u0010F\u001a\u001e\b\u0001\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020,0>0=\u0012\u0006\u0012\u0004\u0018\u00010?0<2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010H\u001a\u00020\u0006H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010IJ\u0006\u0010J\u001a\u000206J\u000e\u0010K\u001a\u0002062\u0006\u00107\u001a\u000208R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\t0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\f0\u001c8F\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001aR\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00110\"\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001aR\u0017\u0010\'\u001a\b\u0012\u0004\u0012\u00020\t0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001aR\u0017\u0010)\u001a\b\u0012\u0004\u0012\u00020\t0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001aR\u001c\u0010+\u001a\u0004\u0018\u00010,X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u0017\u00101\u001a\b\u0012\u0004\u0012\u00020\u00150\"8F\u00a2\u0006\u0006\u001a\u0004\b2\u0010$R\u0017\u00103\u001a\b\u0012\u0004\u0012\u00020\t0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010\u001a\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006L"}, d2 = {"Lcom/kusitms/connectdog/feature/intermediator/InterManagementViewModel;", "Landroidx/lifecycle/ViewModel;", "managementRepository", "Lcom/kusitms/connectdog/core/data/repository/InterManagementRepository;", "(Lcom/kusitms/connectdog/core/data/repository/InterManagementRepository;)V", "TAG", "", "_completedUiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/kusitms/connectdog/feature/intermediator/InterApplicationUiState;", "_errorFlow", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "", "_pendingDataState", "Lcom/kusitms/connectdog/core/model/DataUiState;", "_profile", "Landroidx/lifecycle/MutableLiveData;", "Lcom/kusitms/connectdog/core/data/api/model/intermediator/IntermediatorProfileInfoResponseItem;", "_progressDataState", "_progressUiState", "_volunteerResponse", "Lcom/kusitms/connectdog/core/model/Volunteer;", "_waitingUiState", "completedUiState", "Lkotlinx/coroutines/flow/StateFlow;", "getCompletedUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "errorFlow", "Lkotlinx/coroutines/flow/SharedFlow;", "getErrorFlow", "()Lkotlinx/coroutines/flow/SharedFlow;", "pendingDataState", "getPendingDataState", "profile", "Landroidx/lifecycle/LiveData;", "getProfile", "()Landroidx/lifecycle/LiveData;", "progressDataState", "getProgressDataState", "progressUiState", "getProgressUiState", "recruitingUiState", "getRecruitingUiState", "selectedApplication", "Lcom/kusitms/connectdog/core/model/InterApplication;", "getSelectedApplication", "()Lcom/kusitms/connectdog/core/model/InterApplication;", "setSelectedApplication", "(Lcom/kusitms/connectdog/core/model/InterApplication;)V", "volunteerResponse", "getVolunteerResponse", "waitingUiState", "getWaitingUiState", "completeApplication", "", "applicationId", "", "confirmVolunteer", "createUiStateFlow", "getApplication", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/flow/StateFlow;", "getIntermediatorInfo", "getVolunteer", "refreshCompletedUiState", "refreshInProgressUiState", "refreshUiState", "getApplications", "uiState", "tag", "(Lkotlin/jvm/functions/Function1;Lkotlinx/coroutines/flow/MutableStateFlow;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "refreshWaitingUiState", "rejectVolunteer", "intermediator_debug"})
public final class InterManagementViewModel extends androidx.lifecycle.ViewModel {
    private final com.kusitms.connectdog.core.data.repository.InterManagementRepository managementRepository = null;
    private final java.lang.String TAG = "InterManagementViewModel";
    private final kotlinx.coroutines.flow.MutableSharedFlow<java.lang.Throwable> _errorFlow = null;
    private final androidx.lifecycle.MutableLiveData<com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorProfileInfoResponseItem> _profile = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorProfileInfoResponseItem> profile = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> recruitingUiState = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> _waitingUiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> waitingUiState = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> _progressUiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> progressUiState = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> _completedUiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> completedUiState = null;
    private final androidx.lifecycle.MutableLiveData<com.kusitms.connectdog.core.model.Volunteer> _volunteerResponse = null;
    @org.jetbrains.annotations.Nullable
    private com.kusitms.connectdog.core.model.InterApplication selectedApplication;
    private final kotlinx.coroutines.flow.MutableStateFlow<com.kusitms.connectdog.core.model.DataUiState> _pendingDataState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.core.model.DataUiState> pendingDataState = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<com.kusitms.connectdog.core.model.DataUiState> _progressDataState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.core.model.DataUiState> progressDataState = null;
    
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
    public final androidx.lifecycle.LiveData<com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorProfileInfoResponseItem> getProfile() {
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
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.core.model.DataUiState> getPendingDataState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.core.model.DataUiState> getProgressDataState() {
        return null;
    }
    
    public final void getIntermediatorInfo() {
    }
    
    public final void getVolunteer(long applicationId) {
    }
    
    public final void confirmVolunteer(long applicationId) {
    }
    
    public final void rejectVolunteer(long applicationId) {
    }
    
    public final void completeApplication(long applicationId) {
    }
    
    public final void refreshWaitingUiState() {
    }
    
    public final void refreshInProgressUiState() {
    }
    
    public final void refreshCompletedUiState() {
    }
    
    private final java.lang.Object refreshUiState(kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super java.util.List<com.kusitms.connectdog.core.model.InterApplication>>, ? extends java.lang.Object> getApplications, kotlinx.coroutines.flow.MutableStateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> uiState, java.lang.String tag, kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    private final kotlinx.coroutines.flow.StateFlow<com.kusitms.connectdog.feature.intermediator.InterApplicationUiState> createUiStateFlow(kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super java.util.List<com.kusitms.connectdog.core.model.InterApplication>>, ? extends java.lang.Object> getApplication) {
        return null;
    }
}