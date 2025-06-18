//
//  AppDelegate.swift
//  ios
//
//  Created by Jesus Valdes on 6/10/25.
//
import UIKit
import CleverTapSDK

class AppDelegate: UIResponder, UIApplicationDelegate, UNUserNotificationCenterDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        print("AppDelegate is being called")
        setUpCleverTap()
        return true
    }
    
    private func setUpCleverTap() {
        // call this before autoIntegrate
        setCleverTapCredentials()
        // creates instance of CleverTap class
        CleverTap.autoIntegrate()
        // turn on debugging, SHOULD BE SET TO IN PRODUCTION: CleverTapLogLevel.off.rawValue
        CleverTap.setDebugLevel(CleverTapLogLevel.debug.rawValue)
    }
    
    // assigns project id and token from dashboard with SDK
    private func setCleverTapCredentials() {
        CleverTap.setCredentialsWithAccountID(Credentials.ctProjectID, andToken: Credentials.ctProjectToken)
    }
}
