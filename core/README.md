# Core Library

This core library provides a set of utility classes to simplify common Android development tasks.

## Installation

To use this library, add the following to your `build.gradle` file:

```groovy
dependencies {
    implementation 'com.github.yourusername:core:1.0.0'
}
```

## Usage

### ToastManager

`ToastManager` provides a safe way to show toasts that are lifecycle-aware. It prevents toasts from showing if the activity is finishing or destroyed.

**Example:**

```kotlin
ToastManager.showToast(this, "Your message here")
```

### DebounceClickListener

`DebounceClickListener` prevents multiple rapid clicks on a view, which can be a common source of bugs.

**Example:**

```kotlin
DebounceClickListener.setDebouncedClickListener(myButton) {
    // Your click action here
}
```

### NetworkUtils

`NetworkUtils` provides a simple way to check for internet connectivity.

**Example:**

```kotlin
if (NetworkUtils.isConnectedToInternet(this)) {
    // Internet is available
} else {
    // No internet connection
}
```

### Navigator

`Navigator` simplifies starting new activities.

**Example:**

```kotlin
Navigator.navigateToActivity(this, MyNewActivity::class.java)

// With extras
Navigator.navigateToActivity(
    this,
    MyNewActivity::class.java,
    mapOf("id" to 123, "name" to "John Doe")
)
```

### ToolbarManager

`ToolbarManager` simplifies the setup of a custom toolbar.

**Example:**

```kotlin
ToolbarManager.setupToolbar(this, "My Toolbar Title")
```

### LoadingDialogManager

`LoadingDialogManager` provides a simple way to show and dismiss a loading dialog.

**Example:**

```kotlin
// Show the dialog
LoadingDialogManager.showLoadingDialog(this)

// Dismiss the dialog
LoadingDialogManager.dismissLoadingDialog(this)
```
