# intellij-plugin-translator
Translate selected text with [papago API](https://developers.naver.com/docs/papago/papago-detectlangs-overview.md).

<img width="513" alt="sample" src="https://user-images.githubusercontent.com/2020337/126896587-6f06404d-9372-4001-8f78-fdc5a6e69a83.png">

# Getting start
1. Get API client ID and secrent of Naver Open API.
  1. Register Application at https://developers.naver.com/apps/#/list .
  1. Select API.
    1. Papago translate.
    1. Papago language detection (this API is not used now, but it will be used to support multiple languages.)
  1. Register.
  1. You can get API client ID and secrent.
1. Open IntelliJ preference > Tools > Translation Plugin Settings
1. Set API client ID and secrent of Naver Open API.</br><img width="700" alt="setting screen" src="https://user-images.githubusercontent.com/2020337/126896580-2c250091-3d8d-45a2-9c4d-4c25d4571c88.png">
1. Now you're ready to use this plugin!
1. Select a text you want to translate and type "alt + T", then you can get the translated text.

# Loadmap
* support more languages (English, Chinese, ...)
* support language detection
