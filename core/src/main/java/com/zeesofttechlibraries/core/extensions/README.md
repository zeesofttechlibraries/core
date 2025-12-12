# Core Library

## How to Install

To get this project into your build, follow these steps:

**Step 1. Add the JitPack repository to your build file**

Add it in your root `settings.gradle.kts` (or `settings.gradle`) file:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https.jitpack.io") }
    }
}
```

**Step 2. Add the dependency**

Add the dependency to your module's `build.gradle.kts` (or `build.gradle`) file:

```kotlin
dependencies {
    implementation("com.github.zeesofttechlibraries:core:1.2.6") // Use the latest version
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
- [**CustomLoading** (`CustomLoading.kt`)](#customloading)
- [**DateTimeExtensions** (`DateTimeExtensions.kt`)](#datetimeextensions)
- [**Dialogs** (`DialogExtensions.kt`)](#dialogs)
- [**FragmentNavigator** (`FragmentNavigator.kt`)](#fragmentnavigator)
- [**GenerateRandomString** (`GenerateRandomString.kt`)](#generaterandomstring)
- [**GetClipboardData** (`GetClipboardData.kt`)](#getclipboarddata)
- [**GetIcons** (`GetIcons.kt`)](#geticons)
- [**KeyboardExtensions** (`KeyboardExtensions.kt`)](#keyboardextensions)
- [**LoadingDialogManager** (`LoadingDialogManager.kt`)](#loadingdialogmanager)
- [**NetworkExtension** (`NetworkExtension.kt`)](#networkextension)
- [**NetworkMonitor** (`NetworkMonitor.kt`)](#networkmonitor)
- [**ResourceExtensions** (`ResourceExtensions.kt`)](#resourceextensions)
- [**RotateAnimationUtil** (`RotateAnimationUtil.kt`)](#rotateanimationutil)
- [**SetCircleImage** (`SetCircleImage.kt`)](#setcircleimage)
- [**SharePlanText** (`SharePlanText.kt`)](#shareplantext)
- [**ShowCustomToast** (`ShowCustomToast.kt`)](#showcustomtoast)
- [**ShowModernToast** (`ShowModernToast.kt`)](#showmoderntoast)
- [**ToastExtensions** (`ToastExtensions.kt`)](#toastextensions)
- [**ToolbarExtensions** (`ToolbarExtensions.kt`)](#toolbarextensions)
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

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.CustomAlertDialog.showCustomAlertDialog
```
**Usage:**
```kotlin
showCustomAlertDialog(
    lifeCycleOwner = this,
    title = "Confirm Action",
    description = "Are you sure you want to delete this item?",
    lottieAnimation = R.raw.delete_animation,
    isBlurred = true,
    positiveButtonText = "Delete",
    positiveButtonAction = { /* ... */ }
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
showCustomLoading(lifeCycleOwner = this, loadingMessage = "Processing...")
dismissDialog(this)
```

---

### DateTimeExtensions
Extension functions for `Date` and `String` to simplify date and time manipulation.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.formatTo
```
**Usage:**
```kotlin
val formattedDate = Date().formatTo("dd MMM yyyy")
```

---

### Dialogs
A powerful and lifecycle-aware custom dialog system. It prevents memory leaks and ensures only one dialog is shown at a time.

**Imports:**
```kotlin
import com.spellchecker.core.dialog.showCustomDialog
import com.spellchecker.core.dialog.DialogStyleModel
```

**Basic Usage:**
```kotlin
context.showCustomDialog(
    title = "Permission Needed",
    message = "This feature requires storage access to function properly.",
    positiveText = "Grant",
    positiveAction = { /* Handle permission request */ }
)
```

**Styled Usage:**
You can extensively customize the dialog's appearance using the `DialogStyleModel`.

```kotlin
val style = DialogStyleModel(
    backgroundDrawable = R.drawable.bg_dialog_custom,
    iconDrawable = R.drawable.ic_warning,
    iconTintColor = context.getColor(R.color.warning_yellow),
    positiveButtonBackground = R.drawable.bg_button_positive,
    negativeButtonBackground = R.drawable.bg_button_negative
)

context.showCustomDialog(
    title = "Log Out?",
    message = "Are you sure you want to log out?",
    positiveText = "Log Out",
    styleModel = style,
    positiveAction = { /* Handle logout */ }
)
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

### GetIcons
A utility to retrieve a list of predefined icons with names and drawables.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.GetIcons.getIcons
```
**Usage:**
```kotlin
val iconList = getIcons()
// Returns List<Icon>
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

### NetworkMonitor
A lifecycle-aware utility that monitors network connectivity and displays a custom alert dialog when the connection is lost.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.NetworkMonitor
```
**Usage:**
```kotlin
// In your Activity's onCreate
val networkMonitor = NetworkMonitor(this, this) // Pass context and lifecycle owner
networkMonitor.startMonitoring()
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

### SetCircleImage
An extension function to load an image into an `ImageView` and clip it to a circle using Glide.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.setCircleImage
```
**Usage:**
```kotlin
myImageView.setCircleImage(R.drawable.my_image)
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

### ShowModernToast
An extension function to show a modern, styled toast with an icon.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.showModernToast
```
**Usage:**
```kotlin
showModernToast("This is a modern toast!")
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

### ToolbarExtensions
An extension function to easily set up a standard `AppCompatActivity` toolbar with a title and optional menu icons with click listeners.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.setupToolbar
```
**Usage:**
```kotlin
setupToolbar(
    title = "My Screen",
    menuIcon1 = R.drawable.ic_search,
    onMenu1Click = { /* Handle search click */ }
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
```
**Usage:**
```kotlin
myProgressBar.makeVisible()
```
