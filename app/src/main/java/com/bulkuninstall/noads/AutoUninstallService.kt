package com.bulkuninstall.noads

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class AutoUninstallService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED || 
            event.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            
            val rootNode = rootInActiveWindow ?: return
            
            // Look for buttons that match "Uninstall" or "OK" in system dialogs
            findAndClickButton(rootNode, "Uninstall")
            findAndClickButton(rootNode, "OK")
            findAndClickButton(rootNode, "DELETE")
        }
    }

    private fun findAndClickButton(node: AccessibilityNodeInfo, text: String) {
        val nodes = node.findAccessibilityNodeInfosByText(text)
        for (n in nodes) {
            if (n.className == "android.widget.Button" && n.isEnabled && n.isClickable) {
                n.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
            n.recycle()
        }
    }

    override fun onInterrupt() {}
}
