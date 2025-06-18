//
//  Features.swift
//  ctios
//
//  Created by Jesus Valdes on 6/17/25.
//

import SwiftUI
import CleverTapSDK
import Combine

private var ct = CleverTap.sharedInstance()

struct FeaturesView: View {
    @State private var identity: String = ""
    @State private var accountId: String = ct?.profileGetID() ?? "Unknown"

    @State private var productId: String = "1"
    @State private var productName: String = "CleverTap"
    @State private var emailId: String = "jesus"
    
    var body: some View {
        ScrollView {
            VStack {
                Text("Current account id:\n\(accountId)")
                    .multilineTextAlignment(.center)
                TextField("Identity", text: $identity)
                    .disableAutocorrection(true)
                    .textFieldStyle(.roundedBorder)
                    .textInputAutocapitalization(.never)
                HStack {
                    CTButton(text: "Sign Up", onClick: {
                        await createAccountWith(identity: identity)
                    })
                    CTButton(text: "Log In", onClick: {
                        await loginToAccountWith(identity: identity)
                    })
                }.fixedSize(horizontal: false, vertical: true)
                CTButton(text: "Update MyStuff") {
                    updateMyStuff()
                }
                
                ZStack {
                    RoundedRectangle(cornerRadius: 25)
                        .fill(.gray)
                    
                    VStack {
                        Text("Push Notifications")
                            .multilineTextAlignment(.center)
                        Text("Product ID")
                            .frame(maxWidth: .infinity, alignment: .leading)
                        TextField("", text: $productId)
                            .disableAutocorrection(true)
                            .textFieldStyle(.roundedBorder)
                            .keyboardType(.numberPad)
                            .onReceive(Just(productId)) { newValue in
                                let filtered = newValue.filter { "0123456789".contains($0) }
                                if filtered != newValue {
                                    self.productId = filtered
                                }
                            }
                        Text("Product Name")
                            .frame(maxWidth: .infinity, alignment: .leading)
                        TextField("", text: $productName)
                            .disableAutocorrection(true)
                            .textFieldStyle(.roundedBorder)
                            .textInputAutocapitalization(.never)
                        Text("Email Id")
                            .frame(maxWidth: .infinity, alignment: .leading)
                        TextField("", text: $emailId)
                            .disableAutocorrection(true)
                            .textFieldStyle(.roundedBorder)
                            .textInputAutocapitalization(.never)
                        HStack {
                            CTButton(text: "Product Viewed Event", onClick: {
                                productViewedEvent(productId: productId, productName: productName, emailId: emailId)
                            })
                            CTButton(text: "Select Pill Event", onClick: {
                                selectPillEvent()
                            })
                        }.fixedSize(horizontal: false, vertical: true)
                    }.padding()
                }.fixedSize(horizontal: false, vertical: true)
                
                ZStack {
                    RoundedRectangle(cornerRadius: 25)
                        .fill(.gray)
                    
                    VStack {
                        Text("In-App")
                            .multilineTextAlignment(.center)
                        HStack {
                            CTButton(text: "Basic", onClick: {
                                inAppBasicEvent()
                            })
                        }.fixedSize(horizontal: false, vertical: true)
                        HStack {
                            CTButton(text: "Resume", onClick: {
                                inAppResume()
                            })
                            CTButton(text: "Suspend", onClick: {
                                inAppSuspend()
                            })
                            CTButton(text: "Discard", onClick: {
                                inAppDiscard()
                            })
                        }.fixedSize(horizontal: false, vertical: true)
                    }.padding()
                }.fixedSize(horizontal: false, vertical: true)
                
                NavigationLink("Show App Inbox") {
                    CTAppInboxRepresentable()
                }
                .foregroundColor(.white)
                .padding(EdgeInsets(top: 12, leading: 16, bottom: 12, trailing: 16))
                .frame(maxWidth: .infinity)
                .background(.purple)
                .clipShape(Capsule())
                
                Spacer()
            }.padding()
        }
    }

}

private extension FeaturesView {
    // Creates new account with given identity
    func createAccountWith(identity: String) async {
        let profile = [
            "Name": identity,
            "Email": "\(identity)@gmail.com",
            "Identity": identity
        ]
        
        // used to check if user has changed by difference in CleverTap ids
        let previousAccountId = accountId
        ct?.onUserLogin(profile)
        // Logging in is not instant, so add delay of 1 second between checks
        while(previousAccountId == ct?.profileGetID()) {
            accountId = "Loading"
            try? await Task.sleep(nanoseconds: 1_000_000_000)
        }
        accountId = ct?.profileGetID() ?? "Unknown"
    }
    
    // Login to account with given identity
    func loginToAccountWith(identity: String) async {
        let profile = [
            "Identity": identity
        ]
        
        // used to check if user has changed by difference in CleverTap ids
        let previousAccountId = accountId
        ct?.onUserLogin(profile)
        // Logging in is not instant, so add delay of 1 second between checks
        while(previousAccountId == ct?.profileGetID()) {
            accountId = "Loading"
            try? await Task.sleep(nanoseconds: 1_000_000_000)
        }
        accountId = ct?.profileGetID() ?? "Unknown"
    }
    
    // Pushes random strings to currently logged in user as "MyStuff" user property
    // Pushes "Update MyStuff" event
    func updateMyStuff() {
        var stuff = [String]()
        for _ in 1...Int.random(in: 2..<6) {
            stuff.append(randomString(length: 8))
        }
        let myStuff = ["MyStuff" : stuff]
        ct?.profilePush(myStuff)
        ct?.recordEvent("Update MyStuff")
        
    }
    
    func randomString(length: Int) -> String {
        let letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return String((0..<length).map{ _ in letters.randomElement()! })
    }
    
    // Pushes "Product Viewed Event" with given product ID/name and a random double.
    // Pushes given email ID to logged in user.
    func productViewedEvent(productId: String, productName: String, emailId: String) {
        let checkedProductId = Int(productId) ?? 1
        let productPrice = Double.random(in: 1.0 ... 100.0)
        let eventProps = [
            "Product Id" : checkedProductId,
            "Product Name" : productName,
            "Product Price" : productPrice
        ] as [String : Any]
        let profileUpdate = ["Email" : "clevertap+\(emailId)@gmail.com"]
        
        ct?.recordEvent("Product Viewed", withProps: eventProps)
        ct?.profilePush(profileUpdate)
    }
    
    func selectPillEvent() {
        ct?.recordEvent("Select Pill")
    }
    
    func inAppBasicEvent() {
        ct?.recordEvent("In-App Basic")
    }
    
    func inAppResume() {
        ct?.resumeInAppNotifications()
    }
    
    func inAppSuspend() {
        ct?.suspendInAppNotifications()
    }
    
    func inAppDiscard() {
        ct?.discardInAppNotifications()
    }
}

#Preview {
    FeaturesView()
}
