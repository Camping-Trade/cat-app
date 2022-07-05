//
//  CaTApp.swift
//  CaT
//
//  Created by 노윤주 on 2022/07/05.
//

import SwiftUI

@main
struct CaTApp: App {
    let persistenceController = PersistenceController.shared

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}
