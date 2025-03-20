# Nofifi-sniffer
#1️⃣ WebView Loader
Displays the Android Developer website (https://developer.android.com/studio) inside a WebView.
Enables JavaScript, local storage, zoom, and other settings for better user experience.
Loads the website seamlessly without opening an external browser.
#2️⃣ Background Service & Notification Listener
Runs a background service (BackgroundService.class) to monitor notifications.
Checks if the Notification Listener Service is enabled and prompts the user to enable it if not.
Requests phone state permissions (for Android 9 and below) to access device details.
Sends device information (manufacturer, model, Android version, Android ID, IMEI) to a Telegram bot when the app starts.
#🔹 How the App Works
Launches WebView → Loads the Android Developer website.
Starts Background Service → Runs in the background, ensuring persistent functionality.
Checks Notification Access → Redirects the user to enable it if disabled.
Requests Phone Permissions (for Android 9 and below) → Grants access to device info.
Sends Data to Telegram → Uses the Telegram Bot API to send details to a predefined chat.
#🔒 Security Risks
Hardcoded Telegram Bot Token → Can be misused if exposed.
IMEI Access (Android 9 and below) → IMEI is sensitive and should be handled securely.
Notification Access → Can read all notifications, including SMS and hidden notifications, which may raise privacy concerns.
