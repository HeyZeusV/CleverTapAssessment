//
//  CreateLoginForm.swift
//  ctios
//
//  Created by Jesus Valdes on 6/18/25.
//

import SwiftUI
import CleverTapSDK

private var ct = CleverTap.sharedInstance()

struct CreateLoginForm: View {
    @State private var accountId: String = ct?.profileGetID() ?? "Unknown"
    @State private var identity: String = ""
    
    var body: some View {
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
        }
    }
}

private extension CreateLoginForm {
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
}
