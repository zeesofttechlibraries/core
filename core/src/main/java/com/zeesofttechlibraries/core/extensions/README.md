# Core Extensions Library

This document provides an overview of the utility classes and extension functions available in the `com.zeesofttechlibraries.core.extensions` package. These utilities are designed to simplify common Android development tasks, promote code reuse, and improve application stability.

## Table of Contents

- [Animation Utilities](#animation-utilities)
- [Clipboard Utilities](#clipboard-utilities)
- [Date & Time Utilities](#date--time-utilities)
- [Navigation Utilities](#navigation-utilities)
- [Resource Utilities](#resource-utilities)
- [UI & View Utilities](#ui--view-utilities)
- [Validation Utilities](#validation-utilities)
- [Other Utilities](#other-utilities)

---

## Animation Utilities

### ViewAnimationExtensions

Extension functions to perform simple view animations.

- `fadeIn(duration: Long = 300)`: Smoothly fades in the view.
- `fadeOut(duration: Long = 300)`: Smoothly fades out the view.
- `slideUp(duration: Long = 300)`: Slides the view upward into visibility.
- `slideDown(duration: Long = 300)`: Slides the view downward out of visibility.

**Usage:**
```kotlin
myView.fadeIn()
myOtherView.slideDown(500)
```

### RotateAnimationUtil

A utility object to create `RotateAnimation` instances easily.

**Usage:**
```kotlin
val rotate = RotateAnimationUtil.getRotateAnimation(toDegrees = 360f)
myImageView.startAnimation(rotate)
```

---

## Clipboard Utilities

### CopyData

A utility to copy text to the clipboard and show a confirmation toast.

**Usage:**
```kotlin
import com.zeesofttechlibraries.core.extensions.CopyData.copyToClipboard

"Text to copy".copyToClipboard(context)
```

### GetClipboardData

An extension function to safely retrieve text from the clipboard.

**Usage:**
```kotlin
val copiedText = context.getClipboardText()
```

---

## Date & Time Utilities

### DateTimeExtensions

- `Date.formatTo(pattern: String)`: Formats a `Date` into a string.
- `String.toDate(pattern: String)`: Parses a string into a `Date`.
- `Date.toRelativeTime()`: Converts a date to a "time ago" string.

**Usage:**
```kotlin
val formatted = Date().formatTo("dd MMM yyyy")
val date = "2023-10-21".toDate("yyyy-MM-dd")
val timeAgo = date.toRelativeTime()
```

---

## Navigation Utilities

### Navigator

A centralized object for handling `Activity` navigation.

**Usage:**
```kotlin
// Simple navigation
Navigator.navigateToActivity(this, HomeActivity::class.java)

// With extras
val params = mapOf("userId" to 123)
Navigator.navigateToActivity(this, ProfileActivity::class.java, params)
```

### FragmentNavigator

An extension function for navigating from a `Fragment` to an `Activity`.

**Usage:**
```kotlin
// Inside a Fragment
navigateToActivity(DetailActivity::class.java)
```

---

## Resource Utilities

### ResourceExtensions

Extension functions to safely fetch resources from a `Context`.

- `getColorResource(@ColorRes resId: Int)`: Fetches a color.
- `getDrawableResource(@DrawableRes resId: Int)`: Fetches a drawable.
- `getStringResource(@StringRes resId: Int)`: Fetches a string.

**Usage:**
```kotlin
val color = context.getColorResource(R.color.primary)
val icon = context.getDrawableResource(R.drawable.ic_user)
```

---

## UI & View Utilities

### DebounceClickListener

Prevents rapid, repeated clicks on a `View`.

**Usage:**
```kotlin
DebounceClickListener.setDebouncedClickListener(myButton, delaySeconds = 2) {
    // Click action
}
```

### KeyboardExtensions

- `hideKeyboard()`: Hides the keyboard from an `Activity`.
- `hideKeyboard(view: View)`: Hides the keyboard from a `Context`.
- `showKeyboard(view: View)`: Shows the keyboard and focuses a `View`.

**Usage:**
```kotlin
// From an Activity
hideKeyboard()

// From a Context
context.showKeyboard(myEditText)
```

### LoadingDialogManager

A lifecycle-aware manager for a loading dialog.

**Usage:**
```kotlin
LoadingDialogManager.showLoadingDialog(this)
LoadingDialogManager.dismissLoadingDialog(this)
```

### ToastManager

A lifecycle-aware `Toast` manager that prevents queuing.

**Usage:**
```kotlin
ToastManager.showToast(this, "Operation successful")
```

### ToolbarManager

A utility for setting up a standardized `AppCompatActivity` toolbar.

**Usage:**
```kotlin
ToolbarManager.setupToolbar(this, "My Title")
```

### ViewVisibility

- `makeVisible()`: Sets view visibility to `VISIBLE`.
- `makeGone()`: Sets view visibility to `GONE`.
- `makeInvisible()`: Sets view visibility to `INVISIBLE`.

**Usage:**
```kotlin
myView.makeVisible()
myOtherView.makeGone()
```

---

## Validation Utilities

### ValidateEmail

An extension function to validate an email address string.

**Usage:**
```kotlin
val isValid = "test@example.com".isValidEmail()
```

### ValidatePhoneNumber

An extension function to validate a phone number string.

**Usage:**
```kotlin
val isValid = "1234567890".isValidPhoneNumber()
```

---

## Other Utilities

### GenerateRandomString

Generates a random alphanumeric string of a given length.

**Usage:**
```kotlin
val randomId = generateRandomString(12)
```

### NetworkUtils

Checks for an active internet connection.

**Usage:**
```kotlin
if (NetworkUtils.isConnectedToInternet(this)) {
    // Network is available
}
```

### SharePlanText

An extension function to easily share plain text via the Android share sheet.

**Usage:**
```kotlin
context.shareText("Check out this awesome library!")
```
