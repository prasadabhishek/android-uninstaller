# Bulk Uninstaller: No Ads — Specification

**Version:** 1.0
**Date:** 2026-04-20
**Status:** Draft

---

## 1. Overview

**App Name:** Bulk Uninstaller: No Ads
**Type:** Android Native Application
**Core Functionality:** Batch uninstall multiple Android apps simultaneously with a clean, ad-free experience.

**Differentiator:** Fully free, no ads, no in-app purchases, no bloatware. Just clean app management.

---

## 2. Technology Stack

- **Language:** Kotlin 1.9.x
- **Min SDK:** 26 (Android 8.0)
- **Target SDK:** 35 (Android 15)
- **UI Framework:** Jetpack Compose with Material Design 3
- **Architecture:** MVVM + Clean Architecture
- **Dependency Injection:** Hilt
- **Async:** Kotlin Coroutines + Flow
- **Build System:** Gradle 8.x with Kotlin DSL

### Key Libraries
- Jetpack Compose BOM (latest stable)
- Hilt for DI
- Accompanist (system UI controller for dark theme)
- AndroidX Lifecycle ViewModel Compose

---

## 3. UI/UX Specification

### 3.1 Screen Structure

**Single-screen app with modal dialogs:**
- `MainScreen` — App list with toolbar and FAB
- `SettingsSheet` — Bottom sheet for settings
- `ConfirmUninstallDialog` — Pre-uninstall confirmation dialog
- `SuccessDialog` — Post-uninstall summary dialog
- `AppDetailsDialog` — App info when tapping system app (disabled state)

### 3.2 Navigation Structure

```
MainScreen
├── TopAppBar (title, search, sort, settings)
├── SearchBar (expandable)
├── FilterChips (User Apps | System Apps | All)
├── AppList (LazyColumn)
│   └── AppListItem (icon, name, size, dates, checkbox)
├── SelectionFAB (appears when 1+ selected)
└── BottomSheet → Settings
```

### 3.3 Visual Design

**Color Palette (Dark Theme)**
- Background: `#121212`
- Surface: `#1E1E1E`
- SurfaceVariant: `#2D2D2D`
- Primary: `#4DD0E1` (Cyan accent)
- OnPrimary: `#000000`
- Secondary: `#80CBC4` (Teal)
- OnSurface: `#FFFFFF`
- OnSurfaceVariant: `#B3B3B3`
- Error: `#CF6679`
- Disabled: `#666666`

**Typography (Material 3)**
- Display: Roboto 32sp, Regular
- HeadlineMedium: Roboto 24sp, Medium
- TitleLarge: Roboto 20sp, Medium
- TitleMedium: Roboto 16sp, Medium
- BodyLarge: Roboto 16sp, Regular
- BodyMedium: Roboto 14sp, Regular
- LabelLarge: Roboto 14sp, Medium
- LabelSmall: Roboto 11sp, Medium

**Spacing (8pt grid)**
- xs: 4dp
- sm: 8dp
- md: 16dp
- lg: 24dp
- xl: 32dp

**App Icon**
- Minimalist outlined trash can
- Solid fill: Primary color (#4DD0E1)
- Transparent background
- Simple geometric shape, scales well at all sizes

### 3.4 Component Specifications

#### TopAppBar
- Title: "Bulk Uninstaller"
- Search icon (magnifying glass) → expands to SearchBar
- Sort icon (chevron with dots) → dropdown menu
- Settings icon (gear) → opens SettingsSheet

#### SearchBar
- Placeholder: "Search apps..."
- Clear button when text present
- Filters list in real-time by app name

#### FilterChips
- Three chips: "User Apps" | "System Apps" | "All"
- Single selection, one active at a time
- "User Apps" selected by default

#### AppListItem
- Leading: App icon (48dp)
- Primary text: App name
- Secondary text: Size | Installed: date | Used: date
- Trailing: Checkbox (visible in selection mode)
- System apps: Lock badge overlay on icon, text color dimmed
- Click (non-selection mode): Opens AppDetailsDialog
- Click (selection mode): Toggles checkbox

#### SelectionFAB
- Appears with slide-up animation when 1+ apps selected
- Shows: "Uninstall (X)" where X is count
- Color: Error (red) to indicate destructive action
- Click: Opens ConfirmUninstallDialog

#### ConfirmUninstallDialog
- Title: "Uninstall X apps?"
- Content: Scrollable list of app names
- Footer: Total size being freed
- Buttons: "Cancel" (text), "Uninstall" (filled, error color)

#### SuccessDialog
- Title: "Uninstall Complete"
- Content: "X apps uninstalled"
- Footer: "Y MB freed"
- Button: "Done" (filled, primary color)

#### AppDetailsDialog (for disabled system apps)
- Title: App name + lock badge
- Content: "System app — cannot be uninstalled"
- Button: "OK"

#### SettingsSheet
- "Show system apps" toggle switch
- "Default sort" dropdown (Name, Size, Date Installed)
- App version info at bottom

---

## 4. Functionality Specification

### 4.1 Core Features

**F1: App Loading & Display**
- Load all installed apps via PackageManager
- Query: package name, app name, icon, install time, update time, source dir size
- Estimate "last used" from UsageStatsManager (request permission)
- Separate user apps vs system apps via ApplicationInfo.FLAG_SYSTEM
- Hide system apps by default

**F2: Selection Mode**
- Default: tap app → selection mode activates, checkbox appears
- Checkbox visibility tied to selection state
- Track selected packages in ViewModel
- Selection FAB count updates reactively

**F3: Sort Options**
- Name (A-Z): Alphabetical by app name
- Size (largest first): Descending by APK size
- Date Installed (newest): Most recent install first
- Date Installed (oldest): Oldest install first
- Persist sort preference via DataStore

**F4: Filter Options**
- User Apps: Only show user-installed (FLAG_SYSTEM false)
- System Apps: Only show system apps (FLAG_SYSTEM true)
- All: Show both
- Persist filter preference via DataStore

**F5: Search**
- Real-time filtering as user types
- Case-insensitive match on app name
- Clear button resets search

**F6: Uninstall Flow**
1. User taps SelectionFAB
2. ConfirmUninstallDialog shows list of selected apps + total size
3. User confirms → call PackageManager.ACTION_DELETE for each
4. Wait for all uninstall intents to complete (onActivityResult)
5. Show SuccessDialog with count and freed size

**F7: Settings Persistence**
- DataStore Preferences for:
  - `show_system_apps`: Boolean (default: false)
  - `default_sort`: String (default: "name")

### 4.2 State Management

```
AppState:
  apps: List<AppInfo>
  filteredApps: List<AppInfo> (computed from apps + search + filter)
  selectedPackages: Set<String>
  searchQuery: String
  activeFilter: FilterType (USER | SYSTEM | ALL)
  activeSort: SortType
  isSelectionMode: Boolean (derived from selectedPackages.isNotEmpty())
  isLoading: Boolean
```

### 4.3 Edge Cases & Error Handling

- **No apps found:** Show empty state illustration + "No apps to uninstall"
- **Search no results:** Show "No apps match your search"
- **UsageStats permission denied:** Hide "Used" date, show "Install date" only
- **Uninstall cancelled by user:** Silent, no action
- **Some uninstalls failed:** Show partial success in SuccessDialog
- **Package removed during list display:** Refresh list, remove from selected

---

## 5. Data Model

```kotlin
data class AppInfo(
    val packageName: String,
    val appName: String,
    val icon: Drawable,
    val sizeBytes: Long,
    val installTimeMillis: Long,
    val updateTimeMillis: Long,
    val lastUsedTimeMillis: Long?,
    val isSystemApp: Boolean,
    val versionName: String,
    val versionCode: Long
)

enum class FilterType { USER, SYSTEM, ALL }
enum class SortType { NAME, SIZE_DESC, DATE_INSTALLED_NEW, DATE_INSTALLED_OLD }

enum class SortType(val displayName: String) {
    NAME("Name (A-Z)"),
    SIZE_DESC("Size (largest first)"),
    DATE_INSTALLED_NEW("Date installed (newest)"),
    DATE_INSTALLED_OLD("Date installed (oldest)")
}
```

---

## 6. Testing Strategy

### Unit Tests
- `AppListViewModelTest`: Filter, sort, search logic
- `AppInfoMapperTest`: PackageManager → AppInfo mapping
- `PreferencesManagerTest`: DataStore read/write

### Integration Tests
- `UninstallFlowTest`: Full uninstall flow with mock PackageManager

### UI Tests (Compose)
- `MainScreenTest`: List display, selection, FAB visibility
- `FilterChipsTest`: Filter state transitions
- `SortDropdownTest`: Sort options

---

## 7. File Structure

```
app/
├── src/main/
│   ├── java/com/bulkuninstall/noads/
│   │   ├── BulkUninstallApp.kt (Application class)
│   │   ├── MainActivity.kt
│   │   ├── di/
│   │   │   └── AppModule.kt
│   │   ├── data/
│   │   │   ├── AppRepository.kt
│   │   │   ├── model/
│   │   │   │   └── AppInfo.kt
│   │   │   └── datastore/
│   │   │       └── PreferencesManager.kt
│   │   ├── domain/
│   │   │   └── usecase/
│   │   │       ├── GetInstalledAppsUseCase.kt
│   │   │       └── UninstallAppsUseCase.kt
│   │   └── ui/
│   │       ├── MainScreen.kt
│   │       ├── MainViewModel.kt
│   │       ├── components/
│   │       │   ├── AppListItem.kt
│   │       │   ├── FilterChips.kt
│   │       │   ├── SearchBar.kt
│   │       │   └── SelectionFab.kt
│   │       ├── dialogs/
│   │       │   ├── ConfirmUninstallDialog.kt
│   │       │   ├── SuccessDialog.kt
│   │       │   └── AppDetailsDialog.kt
│   │       ├── sheets/
│   │       │   └── SettingsSheet.kt
│   │       └── theme/
│   │           ├── Theme.kt
│   │           └── Color.kt
│   └── res/
│       ├── drawable/
│       │   └── app_icon.xml
│       └── values/
│           └── strings.xml
├── src/test/java/com/bulkuninstall/noads/
│   └── ...
└── src/androidTest/java/com/bulkuninstall/noads/
    └── ...
```

---

## 8. Permissions Required

```xml
<uses-permission android:name="android.permission.QUERY_ALL_PACKAGES" />
<uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />
<uses-permission android:name="android.permission.REQUEST_DELETE_PACKAGES" />
```

**Runtime Permission:**
- `PACKAGE_USAGE_STATS` — Required for "last used" data. Show rationale dialog if denied, degrade gracefully.

---

## 9. Non-Goals (Out of Scope)

- App backup functionality
- App disable (system apps)
- Cache cleaning
- APK file management
- Any cloud/sync features
- Any network features
- Any monetization

---

## 10. Success Criteria

- [ ] App loads and displays installed apps within 2 seconds
- [ ] Multi-select uninstall works correctly
- [ ] Sort and filter work correctly
- [ ] Search filters in real-time
- [ ] Settings persist across app restarts
- [ ] Dark theme renders correctly
- [ ] All edge cases show appropriate empty/error states
- [ ] Unit tests cover filter/sort/search logic
- [ ] UI tests cover critical flows
- [ ] No ads, no in-app purchases, no bloat
