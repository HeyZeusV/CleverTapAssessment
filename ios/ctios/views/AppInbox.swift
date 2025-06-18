//
//  AppInbox.swift
//  ctios
//
//  Created by Jesus Valdes on 6/17/25.
//

import SwiftUI
import CleverTapSDK

struct CTAppInboxRepresentable: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> CleverTapInboxViewController {
        let style = CleverTapInboxStyleConfig()
        style.backgroundColor = UIColor.lightGray
        let inboxVC: CleverTapInboxViewController = (CleverTap.sharedInstance()?.newInboxViewController(with: style, andDelegate: context.coordinator))!
        return inboxVC
    }
    
    func updateUIViewController(_ uiViewController: CleverTapInboxViewController, context: Context) {
        // Updates the state of the specified view controller with new information from SwiftUI.
    }
    
    func makeCoordinator() -> CTAppInboxCoordinator {
        CTAppInboxCoordinator()
    }
}

class CTAppInboxCoordinator: NSObject, CleverTapInboxViewControllerDelegate {
    func messageDidSelect(_ message: CleverTapInboxMessage, at index: Int32, withButtonIndex buttonIndex: Int32) {
        // This is called when an inbox message or button is clicked
    }
}
