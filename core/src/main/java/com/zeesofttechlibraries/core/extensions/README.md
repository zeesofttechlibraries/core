# Core Extensions Library

This document provides a comprehensive overview of the utility classes and extension functions available in the `com.zeesofttechlibraries.core.extensions` package. These utilities are designed to simplify common Android development tasks, promote code reuse, and improve application stability.

## Table of Contents

- [**CopyData** (`CopyData.kt`)](#copydata)
- [**DateTimeExtensions** (`DateTimeExtensions.kt`)](#datetimeextensions)
- [**DebounceClickListener** (`DebounceClickListener.kt`)](#debounceclicklistener)
- [**FragmentNavigator** (`FragmentNavigator.kt`)](#fragmentnavigator)
- [**GenerateRandomString** (`GenerateRandomString.kt`)](#generaterandomstring)
- [**GetClipboardData** (`GetClipboardData.kt`)](#getclipboarddata)
- [**KeyboardExtensions** (`KeyboardExtensions.kt`)](#keyboardextensions)
- [**LoadingDialogManager** (`LoadingDialogManager.kt`)](#loadingdialogmanager)
- [**Navigator** (`Navigator.kt`)](#navigator)
- [**NetworkUtils** (`NetworkUtils.kt`)](#networkutils)
- [**ResourceExtensions** (`ResourceExtensions.kt`)](#resourceextensions)
- [**RotateAnimationUtil** (`RotateAnimationUtil.kt`)](#rotateanimationutil)
- [**SharePlanText** (`SharePlanText.kt`)](#shareplantext)
- [**ToastManager** (`ToastManager.kt`)](#toastmanager)
- [**ValidateEmail** (`ValidateEmail.kt`)](#validateemail)
- [**ValidatePhoneNumber** (`ValidatePhoneNumber.kt`)](#validatephonenumber)
- [**ViewAnimationExtensions** (`ViewAnimationExtensions.kt`)](#viewanimationextensions)
- [**ViewVisibility** (`ViewVisibility.kt`)](#viewvisibility)

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
val formatted = Date().formatTo("dd MMM yyyy")
val date = "2023-10-21".toDate("yyyy-MM-dd")
val timeAgo = date.toRelativeTime()
```

---

### DebounceClickListener
Prevents rapid, repeated clicks on a `View`. This object replaces simple `setOnClickListener` calls to avoid unintended behavior.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.DebounceClickListener
```
**Usage:**
```kotlin
DebounceClickListener.setDebouncedClickListener(myButton, delaySeconds = 2) {
    // This code will only execute if 2 seconds have passed since the last click.
}
```

---

### FragmentNavigator
An extension function for navigating from a `Fragment` to an `Activity`, with support for passing extras.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.navigateToActivity
```
**Usage:**
```kotlin
// Inside a Fragment
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

// From a Context, e.g., in a Fragment
requireContext().showKeyboard(myEditText)
```

---

### LoadingDialogManager
A lifecycle-aware manager for a loading dialog that prevents window leaks.

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

### Navigator
A centralized object for handling `Activity` navigation from any `Context`.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.Navigator
```
**Usage:**
```kotlin
// Simple navigation from an Activity or Service
Navigator.navigateToActivity(this, HomeActivity::class.java)

// With extras
val params = mapOf("userId" to 123)
Navigator.navigateToActivity(context, ProfileActivity::class.java, params)
```

---

### NetworkUtils
A utility object to check for an active internet connection.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.NetworkUtils
```
**Usage:**
```kotlin
if (NetworkUtils.isConnectedToInternet(this)) {
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
import com.zeesofttechlibraries.core.extensions.getStringResource
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
val rotate = RotateAnimationUtil.getRotateAnimation(toDegrees = 360f)
myImageView.startAnimation(rotate)
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

### ToastManager
A lifecycle-aware `Toast` manager that prevents queuing and avoids showing toasts if the `Activity` is not visible.

**Import:**
```kotlin
import com.zeesofttechlibraries.core.extensions.ToastManager
```
**Usage:**
```kotlin
ToastManager.showToast(this, "Operation successful")
```

---

### ValidateEmail
An extension function to validate if a string is a proper email address using `Patterns.EMAIL_ADDRESS`.

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
An extension function to validate if a string is a valid phone number using `Patterns.PHONE`.

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
myView.makeVisible()
myProgressBar.makeGone()
```
