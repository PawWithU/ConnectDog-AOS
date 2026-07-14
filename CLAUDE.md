# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 프로젝트 개요

ConnectDog(코넥독) — 유기동물 이동봉사 매칭 Android 앱. 하나의 앱이 세 가지 모드로 동작한다:

- `AppMode.VOLUNTEER` — 이동봉사자 모드 (feature:home, management, mypage)
- `AppMode.INTERMEDIATOR` — 중개자(단체) 모드 (feature:intermediator)
- `AppMode.LOGIN` — 미로그인 상태 (feature:login, signup)

AppMode는 DataStore에 저장되며(`domain/usecase/login/UpdateAppModeUseCase.kt`의 enum), `MainViewModel`이 이를 구독해 시작 화면을 결정한다.

## 빌드 방법

- JDK 21 필수. Kotlin 2.0.21, AGP 8.9.1, Gradle 8.11.1, compileSdk/targetSdk 34, minSdk 30. jvmTarget도 21.
- compileSdk/minSdk는 `build-logic/convention`의 `Configuration.kt` + `KotlinAndroid.kt` 컨벤션 플러그인 헬퍼에서 중앙 관리한다(`connectdog.android.application`/`connectdog.android.library` 플러그인을 적용한 모듈에 한함). versionCode/versionName/targetSdk는 아직 각 모듈(주로 `app`)에 개별 선언되어 있다. `build-logic`은 루트 `settings.gradle.kts`의 `pluginManagement { includeBuild("build-logic") }`로 연결된 별도의 컴포지트 빌드다.
- 의존성은 `gradle/libs.versions.toml` (Version Catalog)로 관리. 새 라이브러리는 반드시 여기에 추가 후 `libs.xxx`로 참조.
- `local.properties`에 아래 키가 없으면 빌드는 되지만 BuildConfig에 빈 문자열이 들어가 런타임에 실패한다:
  - `BASE_URL` (core:data → `BuildConfig.BASE_URL`)
  - `kakao_app_key` (app → manifestPlaceholder `KAKAO_APP_KEY`)
  - `NAVER_CLIENT_ID`, `NAVER_CLIENT_SECRET` (feature:login)
- `app/google-services.json` 필요 (Firebase Auth/FCM).

```bash
./gradlew assembleDebug          # 디버그 APK 빌드
./gradlew :app:installDebug      # 기기 설치
./gradlew ktlintCheck            # 린트 검사 (CI와 동일)
./gradlew ktlintFormat           # 린트 자동 수정
```

## 테스트 방법

```bash
./gradlew testDebugUnitTest                      # 전체 유닛 테스트
./gradlew :feature:home:testDebugUnitTest        # 모듈 단위
./gradlew :feature:home:testDebugUnitTest --tests "com.kusitms.connectdog.feature.home.ExampleUnitTest"  # 단일 클래스
./gradlew connectedDebugAndroidTest              # 계측 테스트 (기기 필요)
```

현재 저장소에는 템플릿 그대로인 `ExampleUnitTest`/`ExampleInstrumentedTest`만 존재하며 실질적인 테스트 코드는 없다. CI(`.github/workflows/ktlint-check.yml`)는 main/develop 대상 push/PR에서 `ktlintCheck`만 실행한다.

## 프로젝트 구조 / 모듈 설명

```
app                    # 진입점. ConnectDogApplication(@HiltAndroidApp)만 있고 Activity는 feature:main에 있음
build-logic/convention  # 컨벤션 플러그인(connectdog.android.application/library/compose/hilt) + Configuration.kt
domain                 # 순수 Kotlin(java-library) 모듈. UseCase + 일부 Repository 인터페이스
core:model             # UI에서 쓰는 도메인 모델 (Announcement, Review, Volunteer 등)
core:data              # Retrofit API, Repository 구현, Hilt DI 모듈, DTO→모델 매퍼, DataStore
core:designsystem      # ConnectDogTheme + 공용 Compose 컴포넌트 (Button, TextField, TopAppBar 등)
core:util              # UserType/AccountType enum, Formatter, 확장 함수, localDateGson
feature:main           # MainActivity, MainScreen(NavHost), MainNavigator, MainTab, FCM 서비스
feature:home           # 봉사자 홈/검색/필터/공고상세/신청
feature:management     # 봉사자 봉사 관리/후기 작성
feature:mypage         # 봉사자 마이페이지
feature:intermediator  # 중개자 전용 전체 화면 (홈/공고 생성/관리/프로필)
feature:login          # 로그인, 이메일/비밀번호 찾기 (카카오/네이버 소셜 로그인 포함)
feature:signup         # 회원가입 (봉사자/중개자 공용)
```

의존 방향: `app → feature:main → 나머지 feature 모듈` / `feature → domain, core:data, core:model, core:designsystem, core:util` / `core:data → domain, core:model, core:util` / `domain → core:model`.

`settings.gradle.kts`에 `TYPESAFE_PROJECT_ACCESSORS`가 켜져 있어 `implementation(projects.domain)` 형태와 `project(":core:model")` 형태가 혼용된다.

`build-logic` 컨벤션 플러그인 전환은 현재 `core:designsystem`에만 적용된 상태다(`id("connectdog.android.library")` + `id("connectdog.android.compose")`). 나머지 12개 모듈은 아직 `alias(libs.plugins.com.android.library)` + 수동 `android { compileSdk = 34; ... }` 블록을 그대로 쓴다. 다른 모듈을 옮길 땐 `core:designsystem/build.gradle.kts`를 참고할 것.

## 아키텍처

단일 Activity + 멀티모듈 Compose 앱. 레이어는 UI(feature) → domain(UseCase) → data(core:data) 구조이나, **완전한 클린 아키텍처는 아니다**:

- 로그인/회원가입/인증 관련 Repository 인터페이스(`AuthRepository`, `LoginRepository`, `SignUpRepository`, `DataStoreRepository`)만 `domain/repository`에 있고 UseCase를 거친다.
- 그 외 화면용 Repository 인터페이스(`HomeRepository`, `ManagementRepository`, `MyPageRepository` 등)는 `core:data/repository`에 인터페이스+Impl이 함께 있으며, ViewModel이 core:data의 인터페이스를 **직접 주입**받는다 (예: `HomeViewModel`).
- 새 기능 추가 시 이 두 패턴 중 해당 도메인(인증 계열이면 domain 경유, 화면 데이터 계열이면 core:data 직접)을 따라간다.

### ViewModel 패턴 (2가지 공존)

1. **StateFlow 패턴** — home, management, mypage, intermediator:
   - `flow { emit(repository.xxx()) }.map { ... }.catch { _errorFlow.emit(it) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), XxxUiState.Loading)`
   - UiState는 feature의 `state/` 패키지에 sealed interface로 정의 (`Loading` / `Empty` / 데이터 보유 상태)
   - 에러는 `MutableSharedFlow<Throwable>` → `errorFlow`로 노출하고 화면에서 `onShowErrorSnackBar` 호출
2. **Orbit MVI 패턴** — login, signup, main:
   - `ContainerHost<XxxUiState, XxxSideEffect>` 구현, `container(XxxUiState.empty())` 초기화
   - 상태 변경은 `intent { reduce { state.copy(...) } }`, 이벤트는 `postSideEffect(...)`
   - 화면에서는 `collectAsState()` / `collectSideEffect` 사용

### 내비게이션

- `feature:main`의 `MainScreen`이 NavHost를 소유하고, `MainNavigator`가 모든 화면 이동 함수를 래핑한다. 새 화면을 추가하면 `MainNavigator`에도 위임 함수를 추가해야 한다.
- 각 feature는 `navigation/XxxNavigation.kt`에 다음 3가지를 정의한다:
  - `NavController.navigateXxx(...)` 확장 함수
  - `NavGraphBuilder.xxxNavGraph(...)` — 콜백(람다)을 파라미터로 받아 화면에 전달
  - `object XxxRoute { const val route = "..." }` — 라우트 문자열 상수
- 화면 인자는 라우트 문자열로 전달한다. 원시 타입은 `navArgument` + `NavType`, 복합 객체(`Filter` 등)는 `core:util`의 `localDateGson`으로 JSON 직렬화해 라우트에 붙인다.
- 시작 destination은 `MainNavigator`가 AppMode에 따라 결정한다 (VOLUNTEER→home, INTERMEDIATOR→인터미디에이터 홈, LOGIN→로그인).

## DI 구조 (Hilt)

- `app`의 `ConnectDogApplication`에 `@HiltAndroidApp`, `MainActivity`에 `@AndroidEntryPoint`. KSP 사용 (`alias(libs.plugins.ksp)` + `ksp(libs.hilt.compiler)` — 모든 모듈 공통, kapt에서 전환됨).
- DI 모듈은 전부 `core/data/di`에 있고 모두 `SingletonComponent`:
  - `ApiModule` — OkHttp/Retrofit/Moshi/인증 인터셉터 제공
  - `VolunteerDataModule` — 봉사자·공용 Repository 바인딩 (`@Provides`로 Impl 생성, `@Binds` 미사용)
  - `InterDataModule` — 중개자 Repository 바인딩
  - `DataStoreModule` — `DataStoreRepository` 바인딩
- DI 모듈과 Impl 클래스, ApiService는 모두 `internal` — core:data 외부에는 인터페이스만 노출된다.
- domain의 UseCase는 Hilt 모듈 없이 `@Singleton class XxxUseCase @Inject constructor(...)`로 생성자 주입되며, `suspend operator fun invoke(...)` 하나만 갖는다. domain은 순수 JVM 모듈이라 `javax.inject`만 의존한다.

## 네트워크 구조

- Retrofit + Moshi(`KotlinJsonAdapterFactory`) + OkHttp. Base URL은 `core:data`의 `BuildConfig.BASE_URL`.
- API 인터페이스는 2개, 둘 다 같은 BASE_URL 사용:
  - `ApiService` — 봉사자/공용 엔드포인트 (`/volunteers/...`)
  - `InterApiService` — 중개자 엔드포인트
- 인증: `ApiModule.provideNetworkInterceptor`가 모든 요청에 DataStore의 accessToken을 `Authorization: Bearer` 헤더로 추가한다 (`runBlocking`으로 동기 조회). 예외 발생 시 code 1001의 가짜 Response를 반환한다. 토큰 자동 갱신(refresh) 로직은 없다.
- 로깅: `HttpLoggingInterceptor` BODY 레벨. 타임아웃은 connect/read/write 모두 10초.
- 토큰/앱모드 저장은 DataStore Preferences (`DataStoreRepositoryImpl`, 키는 `PreferenceKeys`).

## 데이터 흐름

```
Screen(Compose) → ViewModel → [UseCase(domain) →] Repository 인터페이스
  → RepositoryImpl(core:data) → ApiService → XxxResponse(DTO)
  → mapper의 toData() 확장 함수 → core:model 도메인 모델 → UiState → Screen
```

- DTO는 `core/data/api/model/`(공용·`volunteer/`·`intermediator/` 하위), 매퍼는 `core/data/mapper/`에 `internal fun XxxResponse.toData(): Xxx` 확장 함수로 작성한다.
- 실패 처리 방식 2가지: 인증 계열 Repository는 `runCatching`으로 `Result<T>`를 반환하고 UseCase 호출부에서 `.onSuccess/.onFailure` 처리. 그 외 Repository는 예외를 그대로 던지고 ViewModel의 `.catch`에서 `errorFlow`로 흘린다.

## 코딩 컨벤션

- Ktlint(`org.jlleitschuh.gradle.ktlint` 12.1.1) + Kotlin 공식 스타일(`kotlin.code.style=official`). 커밋 전 `./gradlew ktlintFormat` 실행 권장 — CI가 `ktlintCheck`로 검증한다. `@Composable` 함수의 PascalCase 네이밍과 `XxxRoute` 객체의 소문자/스네이크케이스 상수는 루트 `.editorconfig`에서 관련 ktlint 룰을 프로젝트 컨벤션에 맞게 조정해뒀다.
- 커밋 메시지 타입 (README 협업 규칙): `feat`(신규 기능/UI), `style`(이미지·폰트 등 리소스), `update`(리팩토링·코드 수정), `delete`, `docs`, `fix`(버그), `rename`, `build`(의존성), `chore`. 예: `fix: 비밀번호 검증 로직 추가`
- 브랜치: `main`(출시), `develop`(기본 브랜치, 다음 버전), `feature/**`, `release`, `hotfix/**`. 이슈 생성 → 이슈에서 브랜치 생성 → PR → 전원 approve 후 본인이 develop에 머지. PR은 FIFO 순서로 머지한다.
- UI 문자열, 토스트 메시지, 로그 일부는 한국어를 사용한다.

## 네이밍 규칙

- DTO: 응답은 `XxxResponse` / 리스트 항목은 `XxxResponseItem`, 요청 바디는 `XxxBody` (예: `ApplyBody`, `FcmTokenRequestBody`)
- 매퍼: `toData()` 확장 함수
- Repository: 인터페이스 `XxxRepository` + 구현 `XxxRepositoryImpl`, 중개자용은 `Inter` 접두사 (`InterHomeRepository`)
- UseCase: `동사+명사+UseCase` (`GetAppModeUseCase`, `UpdateFcmTokenUseCase`)
- 화면: 파일 `screen/XxxScreen.kt`, 진입 컴포저블은 `XxxRoute`(ViewModel 주입) → 내부 `XxxScreen`(상태만 받음) 구조. `internal` 가시성 사용
- 상태: `state/XxxUiState.kt` (sealed interface), Orbit 쪽은 `XxxUiState` + `XxxSideEffect`
- 내비게이션: `navigateXxx` 확장 함수, `xxxNavGraph`, `object XxxRoute`
- 디자인시스템 컴포넌트: `ConnectDog` 접두사 (`ConnectDogTheme`, `ConnectDogToast`)
- ViewModel 로그 태그: `private const val TAG = "XxxViewModel"` 후 `Log.d(TAG, ...)`

## 개발 시 주의사항

- **Kotlin 2.0.21 / Compose BOM 2024.09.00**. Compose Compiler는 Kotlin과 버전이 통합되어(`org.jetbrains.kotlin.plugin.compose` 플러그인) 더 이상 `composeOptions.kotlinCompilerExtensionVersion`을 개별 지정하지 않는다.
- Material3는 1.3.0(BOM이 관리). `ModalBottomSheet`의 `windowInsets` 파라미터, 구버전 pull-to-refresh(`rememberPullToRefreshState().isRefreshing/endRefresh`) 등 1.2.x 시절 API는 제거/변경되었으니 새 코드 작성 시 최신 시그니처를 확인할 것.
- accompanist-pager는 제거되었고 `androidx.compose.foundation.pager`(`HorizontalPager`/`rememberPagerState(pageCount = {...})`)를 사용한다.
- `SignUpRepository` 인터페이스가 **domain과 core:data에 각각 존재**하며 `VolunteerDataModule`이 둘 다 `SignUpRepositoryImpl`로 바인딩한다. import 시 패키지를 정확히 확인할 것.
- `DataStoreRepositoryImpl.updateRefreshToken`은 refreshToken을 `accessToken` 키에 저장하고 있다(기존 코드의 동작). 토큰 관련 수정 시 이 부분을 확인할 것.
- 인증 인터셉터가 `runBlocking`으로 DataStore를 읽으므로 네트워크 계층 수정 시 메인 스레드 차단에 주의.
- 화면 간 복합 객체 전달은 Gson JSON 문자열 라우트 방식이므로, 모델 필드 변경 시 해당 라우트를 쓰는 화면이 함께 깨질 수 있다.
- 키보드(IME) 높이는 `MainActivity`가 WindowInsets로 계산해 `imeHeight`로 각 화면에 내려준다. 입력 화면 추가 시 이 파라미터를 전달받는 기존 패턴을 따를 것.
- Firebase 전화번호 인증(`sendVerificationCode`/`verifyCode`)도 `MainActivity`에 있으며 콜백으로 화면까지 전달된다.
- release 빌드는 현재 debug 서명(`signingConfig = signingConfigs.getByName("debug")`)을 사용하고 minify가 꺼져 있다.

# Claude Working Rules
### Before implementing any change:
1. Analyze the existing implementation first.
2. Search for similar patterns before introducing new code.
3. Reuse existing components, repositories, and utilities whenever possible.
4. Prefer consistency with the existing codebase over introducing newer patterns.
5. For changes spanning multiple modules, explain the affected modules before editing.

### When implementing:
- Keep changes minimal.
- Do not refactor unrelated code.
- Preserve existing architecture.
- Follow existing naming conventions.
- Avoid creating new abstractions unless they are clearly justified.

### ㅈAfter implementing:
- Summarize changed files.
- Explain why each file changed.
- Mention possible side effects.
- Suggest missing tests if applicable.