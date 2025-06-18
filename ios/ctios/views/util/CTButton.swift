//
//  CTButton.swift
//  ctios
//
//  Created by Jesus Valdes on 6/18/25.
//

import SwiftUI

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
