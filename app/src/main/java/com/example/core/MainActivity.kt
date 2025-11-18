package com.example.core

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.core.databinding.ActivityMainBinding
import com.zeesofttechlibraries.core.extensions.CopyData.copyToClipboard
import com.zeesofttechlibraries.core.extensions.CustomAlertDialog.showCustomAlertDialog
import com.zeesofttechlibraries.core.extensions.CustomLoading
import com.zeesofttechlibraries.core.extensions.CustomLoading.showCustomLoading
import com.zeesofttechlibraries.core.extensions.LoadingDialogManager.showLoadingDialog
import com.zeesofttechlibraries.core.extensions.RotateAnimationUtil
import com.zeesofttechlibraries.core.extensions.ShowCustomToast.showCustomToast
import com.zeesofttechlibraries.core.extensions.ShowModernToast.showModernToast
import com.zeesofttechlibraries.core.extensions.ToastManager.showToast
import com.zeesofttechlibraries.core.extensions.formatTo
import com.zeesofttechlibraries.core.extensions.generateRandomString
import com.zeesofttechlibraries.core.extensions.getClipboardText
import com.zeesofttechlibraries.core.extensions.getColorResource
import com.zeesofttechlibraries.core.extensions.hideKeyboard
import com.zeesofttechlibraries.core.extensions.isConnectedToInternet
import com.zeesofttechlibraries.core.extensions.isValidEmail
import com.zeesofttechlibraries.core.extensions.isValidPhoneNumber
import com.zeesofttechlibraries.core.extensions.makeGone
import com.zeesofttechlibraries.core.extensions.makeVisible
import com.zeesofttechlibraries.core.extensions.setDebouncedClickListener
import com.zeesofttechlibraries.core.extensions.setupToolbar
import com.zeesofttechlibraries.core.extensions.shareText
import com.zeesofttechlibraries.core.extensions.slideUp
import com.zeesofttechlibraries.core.extensions.toDate
import com.zeesofttechlibraries.core.extensions.toRelativeTime
import java.util.Date
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showCustomAlertDialog(this@MainActivity,"title","description", negativeButtonText = "Cancel", topBgColor = com.zeesofttechlibraries.core.R.color.alertRed, positiveButtonAction = {}, isBlurred = true)
        showCustomToast("This is a custom toast!",)
        showModernToast("This is a modern toast!")
        setupToolbar("Test Toolbar Test Toolbar Test Toolbar", backgroundDrawable = R.drawable.bg_mixed_shape, menuIcon1 = com.zeesofttechlibraries.core.R.drawable.default_toast_icon, menuIcon2 = com.zeesofttechlibraries.core.R.drawable.share_ic, onMenu1Click = {showModernToast("Menu clicked!")})





        // The tests will now run automatically when the app starts.
        // The logs will appear in your Logcat window under the "TEST_CORE" tag.
//        Log.d("TEST_CORE", "--- STARTING CORE LIBRARY TESTS ---")
//        testUiAndViewUtilities()
//        testLoadingDialogs()
//        testDataAndValidation()
//        testClipboardUtilities()
//        testSystemAndNavigation()
//        testDateTimeExtensions()
//        testResourceExtensions()
//        testOtherUtilities()
    }

    private fun testUiAndViewUtilities() {
        Log.d("TEST_CORE", "--- Testing UI & View Utilities ---")

        // 1. DebounceClickListener (replaces setOnClickListener)
        // To test, tap the root view multiple times quickly. The log will only appear once per second.
        binding.root.setDebouncedClickListener {
            Log.d("TEST_CORE", "Debounced click triggered!")
        }

        // 2. ShowCustomToast
        showCustomToast(
            message = "This is a custom toast!",
            icon = com.zeesofttechlibraries.core.R.drawable.default_toast_icon, // Make sure you have a valid drawable
            bgColor = com.zeesofttechlibraries.core.R.color.mainColor
        )

        // 3. ToastManager (showToast)
        showToast(this, "This is a standard toast.")

        // 4. ViewAnimationExtensions
        binding.root.slideUp()

        // 5. ViewVisibility
        lifecycleScope.launch {
            delay(1000)
            Log.d("TEST_CORE", "View GONE")
            binding.root.makeGone()
            delay(1000)
            Log.d("TEST_CORE", "View VISIBLE")
            binding.root.makeVisible()
        }

        // 6. RotateAnimationUtil
        val rotate = RotateAnimationUtil.getRotateAnimation()
        binding.root.startAnimation(rotate) // Best applied to a specific view like an ImageView

        // 7. KeyboardExtensions
        // Requires an EditText in your layout to be effective.
        // showKeyboard(binding.myEditText)
        hideKeyboard()
    }

    private fun testLoadingDialogs() {
        Log.d("TEST_CORE", "--- Testing Loading Dialogs ---")
        // 8. CustomLoading
        lifecycleScope.launch {
            showCustomLoading(
                lifeCycleOwner = this@MainActivity,
                loadingMessage = "Testing custom loading...",
                isBlurred = true
            )
            delay(2500)
            CustomLoading.dismissDialog(this@MainActivity)
        }
    }

    private fun testDataAndValidation() {
        Log.d("TEST_CORE", "--- Testing Data & Validation ---")
        // 9. GenerateRandomString
        val randomString = generateRandomString(16)
        Log.d("TEST_CORE", "Generated Random String: $randomString")

        // 10. ValidateEmail
        val isValidEmail = "test@example.com".isValidEmail()
        Log.d("TEST_CORE", "Email 'test@example.com' is valid: $isValidEmail")
        val isInvalidEmail = "test@.com".isValidEmail()
        Log.d("TEST_CORE", "Email 'test@.com' is valid: $isInvalidEmail")

        // 11. ValidatePhoneNumber
        val isValidPhone = "1234567890".isValidPhoneNumber()
        Log.d("TEST_CORE", "Phone '1234567890' is valid: $isValidPhone")
    }

    private fun testClipboardUtilities() {
        Log.d("TEST_CORE", "--- Testing Clipboard Utilities ---")
        // 12. CopyData & GetClipboardData
        "Hello from Core Library!".copyToClipboard(this)
        val clipboardText = getClipboardText()
        Log.d("TEST_CORE", "Clipboard Text: $clipboardText")
    }

    private fun testSystemAndNavigation() {
        Log.d("TEST_CORE", "--- Testing System & Navigation ---")
        // 13. NetworkExtension
        val isConnected = isConnectedToInternet()
        Log.d("TEST_CORE", "Is connected to Internet: $isConnected")

        // 14. SharePlanText
        // This will open the Android share sheet.
        shareText("Check out this awesome Core library!")

        // 15. ActivityNavigator
        // Uncomment to test. This will navigate to MainActivity again.
        // com.zeesofttechlibraries.core.extensions.navigateToActivity(MainActivity::class.java, mapOf("test_extra" to "Hello!"))
    }

    private fun testDateTimeExtensions() {
        Log.d("TEST_CORE", "--- Testing Date & Time ---")
        // 16. DateTimeExtensions
        val now = Date()
        val formattedDate = now.formatTo("dd-MM-yyyy HH:mm")
        val dateFromString = "2023-01-01 10:00:00".toDate()
        val relativeTime = dateFromString?.toRelativeTime()
        Log.d("TEST_CORE", "Formatted Date: $formattedDate | Relative Time: $relativeTime")
    }

    private fun testResourceExtensions() {
        Log.d("TEST_CORE", "--- Testing Resource Utilities ---")
        // 17. ResourceExtensions
        try {
            val mainColor = getColorResource(com.zeesofttechlibraries.core.R.color.mainColor)
            Log.d("TEST_CORE", "Got color resource: $mainColor")
        } catch (e: Exception) {
            Log.e("TEST_CORE", "Could not get color resource. Make sure it exists.")
        }
    }

    private fun testOtherUtilities() {
        Log.d("TEST_CORE", "--- Testing Other Utilities ---")
        // 18. ToolbarManager
        // Requires a toolbar with specific IDs (toolbarLayout, toolbarTitle, btnBack) in your layout.
        // com.zeesofttechlibraries.core.extensions.ToolbarManager.setupToolbar(this, "Test Toolbar")

        // 19. FragmentNavigator
        // This is used inside a Fragment, not an Activity.
        // Example from a Fragment: navigateToActivity(SomeActivity::class.java)
    }
}
