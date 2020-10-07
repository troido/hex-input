# Hex Input library
Android library that provides a simple way to input hexadecimal content in an Android application. Its core component is a `HexEditText` view which uses a custom keyboard that is specifically designed for hexadecimal input. Additionally, `HexEditText` automatically formats its content using a specific formatter.

# Setup

## Current Version
```
// latest version
def hexinput_version = '1.0.0'

// latest snapshot
def hexinput_snapshot = '1.0.1-SNAPSHOT'
```

## Github Credentials
You need to have the following credentials:
* Github username
* Github personal access token with `read:packages` scope
    * You can generate your Github's personal access token at: Github Profile -> Settings -> Developer settings -> Personal access tokens -> Generate new token
    * Don't forget to check `read:packages` scope when generating the token

## Gradle

### Github Repository
Make sure that you have the Hex Input Github repository in the list of your repositories.
```
// Add Hex Input Github Repository
repositories {
    maven {
        url "https://maven.pkg.github.com/troido/hex-input"
        credentials {
            username = GITHUB_USERNAME
            password = GITHUB_READ_PACKAGES_ACCESS_TOKEN
        }
    }
}
```
*Note: You can set up your credentials as Gradle environment variables.*

### Dependencies
```
// Hex Input library
implementation "com.troido.hexinput:hex-input:$hexinput_version"
```

# Quick Start

## 1. Add HexEditView to your layout
Add `HexEditText` view (or more of them) into your Activity or Fragment layout:
```
<com.troido.hexinput.ui.editor.HexEditText
    android:id="@+id/hex_edit_text"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

## 2. Add HexKeyboardView to your Activity's content view
Add `HexKeyboardView` to your Activity's content view. This can be achieved in two ways: 

* The first way is to use an extension function `Activity.setContentViewWithHexKeyboardAutoAdded()` instead of `Activity.setContentView()` to set content view:
```
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentViewWithHexKeyboardAutoAdded(R.layout.activity_main)
    ...
}
```
* The second way is to manually add `HexKeyboardView` to your Activity or Fragment layout:
```
<com.troido.hexinput.ui.keyboard.HexKeyboardView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```
*Note: You can place the keyboard wherever you want in your layout. It does not need to be attached to the bottom of the screen.*

# Additional Features

## Format
You can set a formatter for `HexEditText` view:
```
val formatter = HexFormatters.getFormatter(HexFormatters.FormatterType.BYTE_PAIRS_HEX_FORMATTER)
hex_edit_text.setFormatter(formatter)
```
You can achieve the same thing by setting the formatter in an XML file:
```
<com.troido.hexinput.ui.editor.HexEditText
    android:id="@+id/hex_edit_text"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" 
    app:formatter="byte_pairs_hex_formatter" />
```
