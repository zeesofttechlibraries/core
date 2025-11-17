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
<br><br>`Latest Version:` [![](https://jitpack.io/v/zeesofttechlibraries/core.svg)](https://jitpack.io/#zeesofttechlibraries/core)

```kotlin
dependencies {
    implementation("com.github.zeesofttechlibraries:core:1.2.4")
}
```

---

## Available Utilities

This document provides a comprehensive overview of the utility classes and extension functions available. 

### Table of Contents

- [**ActivityNavigator** (`ActivityNavigator.kt`)](#activitynavigator)
- [**ClickExtension** (`ClickExtension.kt`)](#clickextension)
- [**CopyData** (`CopyData.kt`)](#copydata)
- [**CustomAlertDialog** (`CustomAlertDialog.kt`)](#customalertdialog)
- [**CustomDialog** (`dialog/` package)](#customdialog)
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
myButton.setDebouncedClickListener(delaySeconds = 2) { /* ... */ }
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

### CustomAlertDialog
A powerful, all-in-one alert dialog with support for Lottie animations, background blur, and extensive customization.
<p align="center">
  <img src="https://github.com/user-attachments/assets/06f6fa80-2c63-46db-a513-887467ac7021" width="300"/>
  <img src="https://github.com/user-attachments/assets/e82fe793-8465-43bf-b962-6fd147ad3dcc" width="300"/>
</p>



**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.CustomAlertDialog.showCustomAlertDialog
```
**Usage:**
```kotlin
// Show the dialog from an Activity or Fragment
showCustomAlertDialog(
    lifeCycleOwner = this,
    title = "Confirm Action",
    description = "Are you sure you want to delete this item?",
    lottieAnimation = R.raw.delete_animation,
    isBlurred = true,
    positiveButtonText = "Delete",
    negativeButtonText = "Cancel",
    positiveButtonAction = { /* Handle delete */ },
    negativeButtonAction = { /* Handle cancel */ }
)
```

---

### CustomDialog
A flexible, lifecycle-aware dialog system for creating custom-styled dialogs.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.dialog.showCustomDialog
import com.zeesofttechlibraries.core.extensions.dialog.DialogStyleModel
```
**Usage:**
```kotlin
val successStyle = DialogStyleModel(iconDrawable = R.drawable.ic_success)
showCustomDialog(
    title = "Success!",
    message = "Your profile has been updated.",
    positiveText = "Got it",
    styleModel = successStyle
)
```

---

### CustomLoading
A customizable, full-screen loading dialog with background blur and Lottie animation support.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.CustomLoading.showCustomLoading
import com.zeesofttechlibraries.core.extensions.CustomLoading.dismissDialog
```
**Usage:**
```kotlin
// Show the loading dialog
showCustomLoading(
    lifeCycleOwner = this,
    loadingMessage = "Processing...",
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
import com.zeesofttechlibraries.core.extensions.toRelativeTime
```
**Usage:**
```kotlin
val formattedDate = Date().formatTo("dd MMM yyyy")
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
navigateToActivity(DetailActivity::class.java)
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
val randomId = generateRandomString(12)
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
```
**Usage:**
```kotlin
// From an Activity
hideKeyboard()
```

---

### LoadingDialogManager
A manager for a simple, non-customizable loading dialog.

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
if (context.isConnectedToInternet()) { /* ... */ }
```

---

### ResourceExtensions
Extension functions to safely and concisely fetch resources.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.getColorResource
```
**Usage:**
```kotlin
val color = context.getColorResource(R.color.primary)
```

---

### RotateAnimationUtil
A utility object to create `RotateAnimation` instances.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.RotateAnimationUtil
```
**Usage:**
```kotlin
val rotateAnim = RotateAnimationUtil.getRotateAnimation()
myImageView.startAnimation(rotateAnim)
```

---

### SharePlanText
An extension function on `Context` to easily share plain text.

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
An object to display a fully customizable `Toast` message.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.ShowCustomToast.showCustomToast
```
**Usage:**
```kotlin
showCustomToast(message = "Profile updated!")
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
ToolbarManager.setupToolbar(activity = this, title = "My Screen")
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
if ("test@example.com".isValidEmail()) { /* ... */ }
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
if ("1234567890".isValidPhoneNumber()) { /* ... */ }
```

---

### ViewAnimationExtensions
One-line extension functions for common view animations.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.fadeIn
```
**Usage:**
```kotlin
myView.fadeIn()
```

---

### ViewVisibility
Convenience functions to change a view's visibility.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.makeVisible
import com.zeesofttechlibraries.core.extensions.makeGone
```
**Usage:**
```kotlin
myProgressBar.makeVisible()
```

