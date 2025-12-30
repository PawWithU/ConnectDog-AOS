package com.kusitms.connectdog.core.data.api

import com.kusitms.connectdog.core.data.api.model.AdditionalAuthBody
import com.kusitms.connectdog.core.data.api.model.FcmTokenRequestBody
import com.kusitms.connectdog.core.data.api.model.MyInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.Response
import com.kusitms.connectdog.core.data.api.model.VolunteerResponse
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.AnnouncementHomeResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.AnnouncementSearchResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.ApplicationCompletedResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.ApplicationInProgressResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.ApplicationWaitingResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.ApplyBody
import com.kusitms.connectdog.core.data.api.model.volunteer.BadgeResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.BasicInformationResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.BookmarkResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.NoticeDetailResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.PasswordCheckResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.PasswordDto
import com.kusitms.connectdog.core.data.api.model.volunteer.ReviewDetailResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.ReviewDetailWithId
import com.kusitms.connectdog.core.data.api.model.volunteer.UserInfoResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.VolunteerAccountInfo
import com.kusitms.connectdog.core.model.auth.AuthCodeWithAccessToken
import com.kusitms.connectdog.core.model.auth.Email
import com.kusitms.connectdog.core.model.auth.EmailAuthCode
import com.kusitms.connectdog.core.model.auth.Phone
import com.kusitms.connectdog.core.model.auth.PhoneNumberDuplication
import com.kusitms.connectdog.core.model.login.LoginResult
import com.kusitms.connectdog.core.model.login.NormalLogin
import com.kusitms.connectdog.core.model.login.SocialLogin
import com.kusitms.connectdog.core.model.notification.Notification
import com.kusitms.connectdog.core.model.signup.IsDuplicated
import com.kusitms.connectdog.core.model.signup.Nickname
import com.kusitms.connectdog.core.model.signup.NormalVolunteerDetail
import com.kusitms.connectdog.core.model.signup.SocialVolunteerDetail
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ApiService {

    /**
     * 홈
     */
    @GET("/volunteers/posts/home")
    suspend fun getAnnouncementPostsHome(): List<AnnouncementHomeResponseItem>

    @GET("/volunteers/posts")
    suspend fun getAnnouncementFilterPosts(
        @Query("postStatus") postStatus: String?,
        @Query("departureLoc") departureLoc: String?,
        @Query("arrivalLoc") arrivalLoc: String?,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?,
        @Query("dogSize") dogSize: String?,
        @Query("isKennel") isKennel: Boolean?,
        @Query("intermediaryName") intermediaryName: String?,
        @Query("orderCondition") orderCondition: String?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<AnnouncementSearchResponseItem>

    @GET("/volunteers/reviews")
    suspend fun getReviewsHome(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<ReviewDetailWithId>

    /**
     * 회원가입
     */
    @POST("/volunteers/nickname/isDuplicated")
    suspend fun postNickname(
        @Body body: Nickname
    ): IsDuplicated

    @POST("/volunteers/sign-up/email")
    suspend fun getEmailAuthCode(
        @Body body: Email
    ): EmailAuthCode

    @POST("/volunteers/sign-up")
    suspend fun postNormalVolunteerSignUp(
        @Body body: NormalVolunteerDetail
    )

    @PATCH("/volunteers/sign-up/social")
    suspend fun postSocialVolunteerSignUp(
        @Body socialVolunteerSignUpBody: SocialVolunteerDetail
    )

    @POST("/volunteers/phone/isDuplicated")
    suspend fun getPhoneNumberDuplication(
        @Body body: Phone
    ): PhoneNumberDuplication

    /**
     * 봉사관리
     */
    @GET("/volunteers/applications/waiting")
    suspend fun getApplicationWaiting(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<ApplicationWaitingResponseItem>

    @GET("/volunteers/applications/progressing")
    suspend fun getApplicationInProgress(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<ApplicationInProgressResponseItem>

    @GET("/volunteers/applications/completed")
    suspend fun getApplicationCompleted(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<ApplicationCompletedResponseItem>

    @GET("/volunteers/applications/{applicationId}")
    suspend fun getMyApplication(
        @Path("applicationId") applicationId: Long
    ): VolunteerResponse

    @DELETE("/volunteers/applications/{applicationId}")
    suspend fun deleteMyApplication(
        @Path("applicationId") applicationId: Long
    ): Response

    @GET("/volunteers/reviews/{reviewId}")
    suspend fun getReviewDetail(
        @Path("reviewId") reviewId: Long
    ): ReviewDetailResponse

    /**
     * 로그인
     */
    @Headers("Content-Type: application/json")
    @POST("/volunteers/login")
    suspend fun normalLogin(
        @Body loginBody: NormalLogin
    ): LoginResult

    @POST("/volunteers/login/social")
    suspend fun postSocialLoginData(
        @Body socialLoginBody: SocialLogin
    ): LoginResult

    @POST("/volunteers/search/send-email")
    suspend fun volunteerPasswordSearchAuth(
        @Body body: Email
    ): AuthCodeWithAccessToken

    /**s
     * 이동봉사자 > 마이페이지
     */
    @GET("/volunteers/my/info")
    suspend fun getMyInfo(): MyInfoResponseItem

    @GET("/volunteers/my/profile")
    suspend fun getUserInfo(): UserInfoResponse

    @GET("/volunteers/my/badges")
    suspend fun getBadge(): List<BadgeResponse>

    @GET("/volunteers/my/bookmarks")
    suspend fun getBookmarkData(): List<BookmarkResponseItem>

    @PATCH("/volunteers/my/profile")
    suspend fun updateUserInfo(
        @Body userInfo: UserInfoResponse
    )

    @DELETE("/volunteers/logout")
    suspend fun logout()

    /**
     * 이동봉사자 > 공고 상세조회
     */
    @GET("/volunteers/posts/{postId}")
    suspend fun getNoticeDetail(
        @Path("postId") postId: Long
    ): NoticeDetailResponseItem

    @POST("/volunteers/posts/{postId}/bookmarks")
    suspend fun postBookmark(
        @Path("postId") postId: Long
    )

    @DELETE("/volunteers/posts/{postId}/bookmarks")
    suspend fun deleteBookmark(
        @Path("postId") postId: Long
    )

    @POST("/volunteers/additional-auth")
    suspend fun postAdditionalAuth(
        @Body additionalAuthBody: AdditionalAuthBody
    )

    @GET("/volunteers/applications/my-info")
    suspend fun getAdditionalAuth(): AdditionalAuthBody

    /**
     * 공고 신청
     */
    @GET("/volunteers/applications/my-info")
    suspend fun getBasicInformation(): BasicInformationResponse

    /**
     * 이동봉사자 > 공고 상세조회 > 중개자 프로필 조회
     */
    @GET("/volunteers/intermediaries/{intermediaryId}")
    suspend fun getIntermediatorInfo(
        @Path("intermediaryId") intermediaryId: Long
    ): IntermediatorInfoResponseItem

    @GET("/volunteers/intermediaries/{intermediaryId}/posts")
    suspend fun getIntermediatorNotice(
        @Path("intermediaryId") intermediaryId: Long
    ): List<BookmarkResponseItem>

    @POST("/volunteers/posts/{postId}/applications")
    suspend fun postApplyVolunteer(
        @Path("postId") postId: Long,
        @Body applyBody: ApplyBody
    )

    @GET("/volunteers/intermediaries/{intermediaryId}/reviews")
    suspend fun getIntermediatorReview(
        @Path("intermediaryId") intermediaryId: Long,
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<ReviewDetailResponse>

    @Multipart
    @POST("/volunteers/posts/{postId}/reviews")
    suspend fun postReview(
        @Path("postId") postId: Long,
        @Part("request") json: RequestBody,
        @Part files: List<MultipartBody.Part>
    )

    @POST("/volunteers/password/check")
    suspend fun checkVolunteerPassword(
        @Body password: PasswordDto
    ): PasswordCheckResponse

    @PATCH("/volunteers/password")
    suspend fun changeVolunteerPassword(
        @Body password: PasswordDto
    )

    @DELETE("/volunteers/my")
    suspend fun volunteerWithdraw()

    /**
     * fcm
     */
    @POST("/volunteers/fcm")
    suspend fun postFcmToken(
        @Body fcmToken: FcmTokenRequestBody
    )

    @PATCH("/volunteers/notifications/setting")
    suspend fun patchNotification()

    @GET("/volunteers/setting/my/info")
    suspend fun getVolunteerAccountInfo(): VolunteerAccountInfo

    @POST("/volunteers/search/email")
    suspend fun searchVolunteerEmail(
        @Body body: Phone
    ): Email

    @GET("/volunteers/notifications/my")
    suspend fun getNotifications(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<Notification>
}
