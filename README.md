# Core Extensions Library [![](https://jitpack.io/v/zeesofttechlibraries/core.svg)](https://jitpack.io/#zeesofttechlibraries/core)

## How to Install

To get this project into your build, follow these steps:

**Step 1. Add the JitPack repository to your build file**

Add it in your root `settings.gradle.kts` (or `settings.gradle`) file:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

**Step 2. Add the dependency**

Add the dependency to your module's `build.gradle.kts` (or `build.gradle`) file:

```kotlin
dependencies {
    implementation("com.github.zeesofttechlibraries:core:1.2.1")
}
```

---

## Available Utilities

This document provides a comprehensive overview of the utility classes and extension functions available. 

### Table of Contents

- [**ActivityNavigator** (`ActivityNavigator.kt`)](#activitynavigator)
- [**ClickExtension** (`ClickExtension.kt`)](#clickextension)
- [**CopyData** (`CopyData.kt`)](#copydata)
- [**CustomLoading** (`CustomLoading.kt`)](#customloading)
- [**DateTimeExtensions** (`DateTimeExtensions.kt`)](#datetimeextensions)
- [**FragmentNavigator** (`FragmentNavigator.kt`)](#fragmentnavigator)
- [**GenerateRandomString** (`GenerateRandomString.kt`)](#generaterandomstring)
- [**GetClipboardData** (`GetClipboardData.kt`)](#getclipboarddata)
- [**KeyboardExtensions** (`KeyboardExtensions.kt`)](#keyboardextensions)
- [**LoadingDialogManager** (`LoadingDialogManager.kt`)](#loadingdialogmanager)
- [**NetworkExtension** (`NetworkExtension.kt`)](#networkextension)
- [**ResourceExtensions** (`ResourceExtensions.kt`)](#resourceextensions)
- [**RotateAnimationUtil** (`RotateAnimationUtil.kt`)](#rotateanimationutil)
- [**SharePlanText** (`SharePlanText.kt`)](#shareplantext)
- [**ShowCustomToast** (`ShowCustomToast.kt`)](#showcustomtoast)
- [**ToastExtensions** (`ToastExtensions.kt`)](#toastextensions)
- [**ToolbarManager** (`ToolbarManager.kt`)](#toolbarmanager)
- [**ValidateEmail** (`ValidateEmail.kt`)](#validateemail)
- [**ValidatePhoneNumber** (`ValidatePhoneNumber.kt`)](#validatephonenumber)
- [**ViewAnimationExtensions** (`ViewAnimationExtensions.kt`)](#viewanimationextensions)
- [**ViewVisibility** (`ViewVisibility.kt`)](#viewvisibility)

---

### ActivityNavigator
An extension function on `Context` to navigate to another `Activity` from anywhere.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.navigateToActivity
```
**Usage:**
```kotlin
// From an Activity
navigateToActivity(DetailActivity::class.java, mapOf("id" to 123))

// From a Service or BroadcastReceiver
context.navigateToActivity(HomeActivity::class.java)
```

---

### ClickExtension
An extension function for `View` that prevents rapid, repeated clicks (debouncing).

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.setDebouncedClickListener
```
**Usage:**
```kotlin
myButton.setDebouncedClickListener(delaySeconds = 2) {
    // This click action will only trigger once every 2 seconds.
}
```

---

### CopyData
A utility to copy a `String` to the clipboard and show a confirmation toast.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.CopyData.copyToClipboard
```
**Usage:**
```kotlin
"Text to be copied".copyToClipboard(context)
```

---

### CustomLoading
A highly customizable, full-screen loading dialog with optional background blur and Lottie animation support.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.CustomLoading.showCustomLoading
import com.zeesofttechlibraries.core.extensions.CustomLoading.dismissDialog
```
**Usage:**
```kotlin
// Show the loading dialog from an Activity or Fragment
showCustomLoading(
    lifeCycleOwner = this,
    loadingMessage = "Processing your request...",
    isBlurred = true
)

// Dismiss the dialog
dismissDialog(this)
```

---

### DateTimeExtensions
Extension functions for `Date` and `String` to simplify date and time manipulation.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.formatTo
import com.zeesofttechlibraries.core.extensions.toDate
import com.zeesofttechlibraries.core.extensions.toRelativeTime
```
**Usage:**
```kotlin
val formattedDate = Date().formatTo("dd MMM yyyy")
val dateObject = "2023-10-21".toDate("yyyy-MM-dd")
val timeAgo = dateObject?.toRelativeTime()
```

---

### FragmentNavigator
An extension function for navigating from a `Fragment` to an `Activity`.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.navigateToActivity
```
**Usage:**
```kotlin
// Inside a Fragment class
navigateToActivity(DetailActivity::class.java, mapOf("id" to 123))
```

---

### GenerateRandomString
Generates a random alphanumeric string of a specified length.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.generateRandomString
```
**Usage:**
```kotlin
val randomId = generateRandomString(12) // Generates a 12-character random string
```

---

### GetClipboardData
An extension function on `Context` to safely retrieve text from the clipboard.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.getClipboardText
```
**Usage:**
```kotlin
val copiedText = context.getClipboardText()
```

---

### KeyboardExtensions
Utility functions to programmatically show or hide the soft keyboard.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.hideKeyboard
import com.zeesofttechlibraries.core.extensions.showKeyboard
```
**Usage:**
```kotlin
// From an Activity
hideKeyboard()

// From a Context (e.g., in a Fragment)
requireContext().showKeyboard(myEditText)
```

---

### LoadingDialogManager
A lifecycle-aware manager for a simple, non-customizable loading dialog.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.LoadingDialogManager
```
**Usage:**
```kotlin
LoadingDialogManager.showLoadingDialog(this)
LoadingDialogManager.dismissLoadingDialog(this)
```

---

### NetworkExtension
A utility to check for an active internet connection.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.isConnectedToInternet
```
**Usage:**
```kotlin
if (context.isConnectedToInternet()) {
    // Network is available
}
```

---

### ResourceExtensions
Extension functions to safely and concisely fetch resources like colors, drawables, and strings.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.getColorResource
import com.zeesofttechlibraries.core.extensions.getDrawableResource
// ...and so on
```
**Usage:**
```kotlin
val color = context.getColorResource(R.color.primary)
val icon = context.getDrawableResource(R.drawable.ic_user)
```

---

### RotateAnimationUtil
A utility object to create `RotateAnimation` instances with custom parameters.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.RotateAnimationUtil
```
**Usage:**
```kotlin
val rotateAnim = RotateAnimationUtil.getRotateAnimation(toDegrees = 360f)
myImageView.startAnimation(rotateAnim)
```

---

### SharePlanText
An extension function on `Context` to easily share plain text via the standard Android share sheet.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.shareText
```
**Usage:**
```kotlin
context.shareText("Check out this awesome library!")
```

---

### ShowCustomToast
An object to display a fully customizable `Toast` message with a specific layout.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.ShowCustomToast.showCustomToast
```
**Usage:**
```kotlin
// Must be called from a Context (e.g., an Activity or Fragment)
showCustomToast(
    message = "Profile updated!",
    icon = R.drawable.ic_success,
    bgColor = R.color.green
)
```

---

### ToastExtensions
Provides a lifecycle-aware `Toast` that prevents queuing.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.showToast
```
**Usage:**
```kotlin
context.showToast("Operation successful")
```

---

### ToolbarManager
A utility for setting up a standardized `AppCompatActivity` toolbar.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.ToolbarManager
```
**Usage:**
```kotlin
// Assuming the toolbar layout is included in your activity's XML
ToolbarManager.setupToolbar(
    activity = this,
    title = "My Screen",
    customAction = { /* Custom back action */ }
)
```

---

### ValidateEmail
An extension function to validate if a string is a proper email address.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.isValidEmail
```
**Usage:**
```kotlin
if ("test@example.com".isValidEmail()) {
    // Proceed with valid email
}
```

---

### ValidatePhoneNumber
An extension function to validate if a string is a valid phone number.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.isValidPhoneNumber
```
**Usage:**
```kotlin
if ("1234567890".isValidPhoneNumber()) {
    // Proceed with valid phone number
}
```

---

### ViewAnimationExtensions
One-line extension functions for common view animations like fade and slide.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.fadeIn
import com.zeesofttechlibraries.core.extensions.slideDown
// etc.
```
**Usage:**
```kotlin
myView.fadeIn()
myOtherView.slideDown(500)
```

---

### ViewVisibility
Convenience functions to change a view's visibility with `makeVisible()`, `makeGone()`, and `makeInvisible()`.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.makeVisible
import com.zeesofttechlibraries.core.extensions.makeGone
```
**Usage:**
```kotlin
myProgressBar.makeVisible()
myContent.makeGone()
```


**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.makeVisible
import com.zeesofttechlibraries.core.extensions.makeGone
```
**Usage:**
```kotlin
myView.makeVisible()
myProgressBar.makeGone()
```
