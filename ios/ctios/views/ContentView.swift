//
//  ContentView.swift
//  ctios
//
//  Created by Jesus Valdes on 6/10/25.
//

import SwiftUI
import CleverTapSDK
import Combine

enum Destinations: String {
    case features = "features"
    case appInbox = "appinbox"
}

struct ContentView: View {
    @State private var navPath = NavigationPath()
    private var ct = CleverTap.sharedInstance()
    
    var body: some View {
        NavigationStack(path: $navPath) {
            VStack {
                NavigationLink("Features") {
                    FeaturesView()
                }
                .foregroundColor(.white)
                .padding(EdgeInsets(top: 12, leading: 16, bottom: 12, trailing: 16))
                .frame(maxWidth: .infinity)
                .background(.purple)
                .clipShape(Capsule())
            }.padding()
        }
        .onAppear() { initilizeAppInbox() }
    }
}

private extension ContentView {
    func initilizeAppInbox() {
        //Initialize the CleverTap App Inbox
        ct?.initializeInbox(callback: ({ (success) in
                let messageCount = ct?.getInboxMessageCount()
                let unreadCount = ct?.getInboxMessageUnreadCount()
                print("Inbox Message:\(String(describing: messageCount))/\(String(describing: unreadCount)) unread")
         }))
    }
}

#Preview {
    ContentView()
}

struct CTButton: View {
    var text: String
    var onClick: () async -> Void
    
    var body: some View {
        Button(text) {
            Task {
                await onClick()
            }
        }
        .foregroundColor(.white)
        .padding(EdgeInsets(top: 12, leading: 16, bottom: 12, trailing: 16))
        .frame(maxWidth: .infinity)
        .background(.purple)
        .clipShape(Capsule())
    }
}
