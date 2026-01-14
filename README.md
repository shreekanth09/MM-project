# WorkPulse Android - Employee Performance Tracking System

Complete Android application converted from React to Kotlin + XML.

## 📱 Features

### Admin Features
- Dashboard with workforce metrics
- Employee Hub (view, search, add employees)
- Performance Review tracking
- Workload & Tasks management
- Attendance Center with leave approvals
- Department Analytics
- Alerts & Notices

### Employee Features
- Personal Dashboard
- My Performance metrics
- My Tasks (To Do, Doing, Completed)
- Attendance & Leave management
- My Profile
- Notifications
- Reports (Download PDFs)

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI**: XML Layouts (No Jetpack Compose)
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Architecture**: Activities + ViewBinding
- **Material Design**: Material Components

## 📂 Project Structure

```
WorkPulseAndroid/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/workpulse/
│   │       │   ├── activities/     # All Activity files
│   │       │   ├── adapters/       # RecyclerView adapters
│   │       │   ├── models/         # Data models
│   │       │   └── utils/          # Utility classes
│   │       ├── res/
│   │       │   ├── layout/         # XML layouts
│   │       │   ├── values/         # colors, strings, themes
│   │       │   └── drawable/       # drawables
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
└── settings.gradle
```

## 🚀 How to Run

### Step 1: Open in Android Studio
1. Open Android Studio (Hedgehog/Ladybug or later)
2. Click **File → Open**
3. Navigate to `WorkPulseAndroid` folder
4. Click **OK**

### Step 2: Sync Gradle
- Android Studio will automatically sync Gradle
- Wait for dependencies to download

### Step 3: Run on Emulator
1. Create an emulator (if not exists):
   - Tools → Device Manager → Create Device
   - Select Pixel 5 or any device
   - Select Android 14 (API 34) or Android 11 (API 30)
2. Click **Run** button (green play icon)
3. Select your emulator
4. App will install and launch

### Step 4: Run on Physical Device
1. Enable Developer Options on your Android phone
2. Enable USB Debugging
3. Connect phone via USB
4. Click **Run** and select your device

## 🎯 App Flow

### Welcome Screen
- Choose **Admin Login** or **Employee Login**

### Admin Login
- Enter any email/password (demo mode)
- Access Admin Dashboard
- Navigate to:
  - Employee Hub → Add/View employees
  - Performance Review → View metrics
  - Workload & Tasks → Create tasks
  - Attendance Center → Approve leaves
  - Dept Analytics → View department stats
  - Alerts & Notices → Create announcements

### Employee Login
- Enter any email/password (demo mode)
- Access Employee Dashboard
- Navigate to:
  - My Performance → View personal metrics
  - My Tasks → Manage tasks
  - Attendance & Leave → Check in/out, request leave
  - My Profile → View/Edit profile
  - Notifications → View notifications
  - Reports → Download reports

## 🎨 Design

- **Color Scheme**: Purple, Teal, Blue gradients
- **Dark Theme**: Dark background with colorful cards
- **Material Design**: Following Material Design 3 guidelines
- **Responsive**: Works on all screen sizes

## ⚠️ Important Notes

1. **Demo Mode**: App accepts any login credentials
2. **No Backend**: All data is static/mock data
3. **UI Only**: No Firebase, API calls, or cloud services
4. **ViewBinding**: Enabled for type-safe view access
5. **Compilation**: App compiles and runs without errors

## 📝 Login Credentials

**Admin**: Any email + any password
**Employee**: Any email + any password

## 🔧 Troubleshooting

### Gradle Sync Failed
- Check internet connection
- File → Invalidate Caches → Restart

### Build Errors
- Clean Project: Build → Clean Project
- Rebuild: Build → Rebuild Project

### Emulator Issues
- Wipe emulator data
- Create new emulator with API 30+

## 📦 Dependencies

- AndroidX Core KTX
- AppCompat
- Material Components
- ConstraintLayout
- CardView
- RecyclerView
- ViewPager2
- MPAndroidChart (for charts)

## 🎓 Next Steps (Optional Enhancements)

- Add Room Database for local storage
- Implement MVVM architecture with ViewModel
- Add Firebase Authentication
- Integrate REST APIs
- Add data persistence
- Implement real-time updates
- Add push notifications

## 📄 License

This is a demo project for educational purposes.

---

**Developed by**: WorkPulse Team
**Version**: 1.0
**Last Updated**: 2024
