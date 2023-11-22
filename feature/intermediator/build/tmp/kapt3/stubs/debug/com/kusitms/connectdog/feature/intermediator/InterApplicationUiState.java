package com.kusitms.connectdog.feature.intermediator;

import com.kusitms.connectdog.core.model.InterApplication;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u0082\u0001\u0003\u0005\u0006\u0007\u00a8\u0006\b"}, d2 = {"Lcom/kusitms/connectdog/feature/intermediator/InterApplicationUiState;", "", "Empty", "InterApplications", "Loading", "Lcom/kusitms/connectdog/feature/intermediator/InterApplicationUiState$Empty;", "Lcom/kusitms/connectdog/feature/intermediator/InterApplicationUiState$InterApplications;", "Lcom/kusitms/connectdog/feature/intermediator/InterApplicationUiState$Loading;", "intermediator_debug"})
public abstract interface InterApplicationUiState {
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/kusitms/connectdog/feature/intermediator/InterApplicationUiState$Loading;", "Lcom/kusitms/connectdog/feature/intermediator/InterApplicationUiState;", "()V", "intermediator_debug"})
    public static final class Loading implements com.kusitms.connectdog.feature.intermediator.InterApplicationUiState {
        @org.jetbrains.annotations.NotNull
        public static final com.kusitms.connectdog.feature.intermediator.InterApplicationUiState.Loading INSTANCE = null;
        
        private Loading() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/kusitms/connectdog/feature/intermediator/InterApplicationUiState$Empty;", "Lcom/kusitms/connectdog/feature/intermediator/InterApplicationUiState;", "()V", "intermediator_debug"})
    public static final class Empty implements com.kusitms.connectdog.feature.intermediator.InterApplicationUiState {
        @org.jetbrains.annotations.NotNull
        public static final com.kusitms.connectdog.feature.intermediator.InterApplicationUiState.Empty INSTANCE = null;
        
        private Empty() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u0019\u0010\t\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u00d6\u0003J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/kusitms/connectdog/feature/intermediator/InterApplicationUiState$InterApplications;", "Lcom/kusitms/connectdog/feature/intermediator/InterApplicationUiState;", "applications", "", "Lcom/kusitms/connectdog/core/model/InterApplication;", "(Ljava/util/List;)V", "getApplications", "()Ljava/util/List;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "intermediator_debug"})
    public static final class InterApplications implements com.kusitms.connectdog.feature.intermediator.InterApplicationUiState {
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.kusitms.connectdog.core.model.InterApplication> applications = null;
        
        @org.jetbrains.annotations.NotNull
        public final com.kusitms.connectdog.feature.intermediator.InterApplicationUiState.InterApplications copy(@org.jetbrains.annotations.NotNull
        java.util.List<com.kusitms.connectdog.core.model.InterApplication> applications) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        @java.lang.Override
        public java.lang.String toString() {
            return null;
        }
        
        public InterApplications(@org.jetbrains.annotations.NotNull
        java.util.List<com.kusitms.connectdog.core.model.InterApplication> applications) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.kusitms.connectdog.core.model.InterApplication> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.kusitms.connectdog.core.model.InterApplication> getApplications() {
            return null;
        }
    }
}