# Hex input library
This is an Android library that provides a simple and user friendly way to input hexadecimal content in an Android application. Its core component is a `HexEditText` view which is a specific type of Android's `EditText` view that automatically formats its content using a selected formatter and uses a special type of keyboard that is specifically designed for input of hexadecimal content.

## How to use
The library is very simple to use. There are only two steps required to integrate it in your app.

**Step 1:** Add `HexEditText` view (one or more of them, depending on your needs) into your activity/fragment layout, just like you would add an `EditText` if you needed a plain text input instead of hex input.

For example, add this snippet into you activity xml layout:

```
<com.troido.hexinput.ui.editor.HexEditText
    android:id="@+id/demo_hex_input"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```


**Step 2:** Include `HexKeyboardView` into your activity content view. This can be achieved in two ways. 

The first way is to use an extension function `Activity.setContentViewWithHexKeyboardAutoAdded()` instead of `Activity.setContentView()` to set content view, like this:

```
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentViewWithHexKeyboardAutoAdded(R.layout.activity_hex_input_demo)

    ...
}
```

The other way is to manually include a `HexKeyboardView` into your activity layout. You can place it wherever you want in your activity layout (it does not need to be attached to the bottom of the screen). For example, add this snippet into your activity xml layout:

```
<com.troido.hexinput.ui.keyboard.HexKeyboardView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```

And that is all you have to do to use the library in you app. But, there are also additional features you can use. You can read about some of them in the follow-up.

### Back press handling
If you want hex keyboard to automatically close when user presses back button, there is an extension function you can use to achieve that - `Activity.handleBackPressedWithHexKeyboardInContentView()`. This function closes the hex keyboard if it is open, or calls `Activity.finish()` if it is not open. Use it in you `Activity.onBackPressed()` method if this is the behaviour that you need (close hex keyboard if open, or finish activity otherwise). Your `Activity.onBackPressed()` should look like this:

```
override fun onBackPressed() {
    handleBackPressedWithHexKeyboardInContentView()
}
```

There are also some other extension functions that can help you implement custom back press handling. Here is an example how your `Activity.onBackPressed()` could look like:

```
override fun onBackPressed() {
    val hexKeyboardView = getHexKeyboardView()
    if(hexKeyboardView.visibility == View.VISIBLE) {
        KeyboardManager.hideHexKeyboard(hexKeyboardView)
    } else {
        // default back press handling
        ...
    }
}
```

### Formatters
Set a hex formatter to a `HexEditText` view. If you don't like the default formatter, you can use some other formatter or even implement a custom one. To set a hex formatter to a `HexEditText` view, use `HexEditText.setFormatter()` method. The easiest way to get an instance of one of the formatters contained in the library is to use `HexFormatters.getFormatter()` method. Here is an example:

```
val formatter = HexFormatters.getFormatter(HexFormatters.FormatterType.BYTE_PAIRS_HEX_FORMATTER)
demo_hex_input.setFormatter(formatter)
```

Or you can achieve the same thing by setting the formatter in an xml file, like this:

```
<com.troido.hexinput.ui.editor.HexEditText
    android:id="@+id/demo_hex_input"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" 
	app:formatter="byte_pairs_hex_formatter" />
```

If you want to implement a custom formatter, you can do this by either implementing `HexFormatter` interface or by extending some of the formatters contained in the library. For more information, read `HexFormatter` interface documentation.

### Hex content manipulation
You can manipulate `HexEditText` content using either `HexEditText.setText()` or `HexEditText.setContent()` method. What's more, you can use `HexEditText.getContentModel()` to get a `HexContentModel` object that enables you to manipulate `HexEditText` content.


For more information, look at class documentation of class you want to find out more about.
