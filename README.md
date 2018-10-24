# KeyReply
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![Download](https://api.bintray.com/packages/tumapu/KeyReply/keyreply/images/download.svg)](https://bintray.com/tumapu/KeyReply/keyreply/_latestVersion)



## Example

Demo application is included in the `demo` folder. To run it, clone the repo, and run `demo` module with Android Studio

![KeyReplySDK Demo](https://github.com/keyreply/keyreply-ios/blob/master/example_screenshot.png?raw=true)



## Adding KeyReplySDK to your app

### Gradle
```gradle
dependencies {
  compile 'com.keyreply:keyreply:1.2'
}
``` 


### Manual installation
- Clone the repo
- Import 'keyreply' folder as new module in your project


## Requirements
* Android SDK 16+


## Usage
KeyReplySDK uses a default client ID out of the box for demo purpose. Please obtain your own Client ID from KeyReply reprensentative directly.  
Add widget in your xml layout and replace clientId with your own Client Id, like this:

```xml
    <com.keyreply.KeyReplyView
        android:id="@+id/keyReplyView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:clientId="your_client_id"
        />
```

## Customizations

### Collapse on load

By default, KeyReplySDK will show expanded UI on load. This can be disabled by:

```xml
    <com.keyreply.KeyReplyView
        android:id="@+id/keyReplyView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:clientId="your_client_id"
        app:expanded="false"
        />
```

### Appearance

All customization of appearance are to be done via KeyReply's web console.



## Action

### Expand/Collapse/Toggle chat window

```kotin
keyReplyView.setExpanded(true)
keyReplyView.setExpanded(false)
keyReplyView.toggle()
```

### Send a chat message programmatically

Chat message can be sent via KeyReplySDK UI or done programmatically as followed:

```kotlin
keyReplyView.sendMessage("your_message_content")
```

## Attributes
|attr|format|description|
|---|:---|:---:|
|clientId|string|Your Client Id from KeyReply|
|expanded|boolean|Default expand state of chat window|
