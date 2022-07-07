//AppDelegate

import KakaoSDKCommon
import KakaoSDKAuth
import KakaoSDKUser
import SwiftUI

@main
struct CaTApp: App {
    init() {
        // Kakao SDK 초기화
        KakaoSDK.initSDK(appKey:"95f7adfa8bc0659abc8cbdcb5941725a")
        
        /*UserApi.shared.me { User, Error in
            if let name = User?.kakaoAccount?.profile?.nickname{
                userName = name //let 사용 안 하는건가...
                //print(userName)
            }
            if let mail = User?.kakaoAccount?.email{
                userMail = mail
            }
            if let profile = User?.kakaoAccount?.profile?.profileImageUrl{
                profileImage = profile
            }
        }*/
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .onOpenURL { url in
                    if (AuthApi.isKakaoTalkLoginUrl(url)) {
                        _ = AuthController.handleOpenUrl(url: url)
                    }
                }
        }
    }
}
