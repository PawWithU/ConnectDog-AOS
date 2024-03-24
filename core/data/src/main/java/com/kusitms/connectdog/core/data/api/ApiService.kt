package com.kusitms.connectdog.core.data.api

import com.kusitms.connectdog.core.data.api.model.AdditionalAuthBody
import com.kusitms.connectdog.core.data.api.model.DeleteAccountResponse
import com.kusitms.connectdog.core.data.api.model.IsDuplicateNicknameResponse
import com.kusitms.connectdog.core.data.api.model.LoginResponseItem
import com.kusitms.connectdog.core.data.api.model.MyInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.NormalLoginBody
import com.kusitms.connectdog.core.data.api.model.Response
import com.kusitms.connectdog.core.data.api.model.ReviewResponseItem
import com.kusitms.connectdog.core.data.api.model.SocialLoginBody
import com.kusitms.connectdog.core.data.api.model.VolunteerResponse
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.AnnouncementHomeResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.AnnouncementSearchResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.ApplicationCompletedResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.ApplicationInProgressResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.ApplicationWaitingResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.ApplyBody
import com.kusitms.connectdog.core.data.api.model.volunteer.BadgeResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.BookmarkResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.EmailCertificationBody
import com.kusitms.connectdog.core.data.api.model.volunteer.EmailCertificationResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.IsDuplicateNicknameBody
import com.kusitms.connectdog.core.data.api.model.volunteer.NormalVolunteerSignUpBody
import com.kusitms.connectdog.core.data.api.model.volunteer.NoticeDetailResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.SocialVolunteerSignUpBody
import com.kusitms.connectdog.core.data.api.model.volunteer.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
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
    ): List<ReviewResponseItem>

    /**
     * 회원가입
     */
    @POST("/volunteers/nickname/isDuplicated")
    suspend fun postNickname(
        @Body nickname: IsDuplicateNicknameBody
    ): IsDuplicateNicknameResponse

    @POST("/volunteers/sign-up/email")
    suspend fun postEmail(
        @Body emailCertificationBody: EmailCertificationBody
    ): EmailCertificationResponseItem

    @POST("/volunteers/sign-up")
    suspend fun postNormalVolunteerSignUp(
        @Body normalVolunteerSignUpBody: NormalVolunteerSignUpBody
    )

    @PATCH("/volunteers/sign-up/social")
    suspend fun postSocialVolunteerSignUp(
        @Body socialVolunteerSignUpBody: SocialVolunteerSignUpBody
    )

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

    /**
     * 로그인
     */
    @Headers("Content-Type: application/json")
    @POST("/volunteers/login")
    suspend fun postLoginData(
        @Body loginBody: NormalLoginBody
    ): LoginResponseItem

    @Headers("Content-Type: application/json")
    @POST("/intermediaries/login")
    suspend fun postIntermediatorLoginData(
        @Body loginBody: NormalLoginBody
    ): LoginResponseItem

    @POST("/volunteers/login/social")
    suspend fun postSocialLoginData(
        @Body socialLoginBody: SocialLoginBody
    ): LoginResponseItem

    /**
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

    @DELETE("/volunteers/my/delete")
    suspend fun deleteAccount(): DeleteAccountResponse

    @PATCH("/volunteers/my/profile")
    suspend fun updateUserInfo(
        @Body userInfo: UserInfoResponse
    )

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
    ): List<ReviewResponseItem>
}
