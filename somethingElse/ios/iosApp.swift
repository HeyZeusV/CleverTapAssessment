//
//  iosApp.swift
//  ios
//
//  Created by Jesus Valdes on 6/10/25.
//

import SwiftUI

@main
struct iosApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
