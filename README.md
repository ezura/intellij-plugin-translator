# intellij-plugin-translator
Translate selected text with [papago API](https://developers.naver.com/docs/papago/papago-detectlangs-overview.md).

<img width="513" alt="sample" src="https://user-images.githubusercontent.com/2020337/126896587-6f06404d-9372-4001-8f78-fdc5a6e69a83.png">

# How to install
1. Download zip file(intellij-plugin-translator-x.x.zip
) from [Release note](https://github.com/ezura/intellij-plugin-translator/releases).
1. Open your Intellij IDE.
1. Select: Plugin > settings icon > Install Plugin from Disk...
1. Select the downloaded zip file.

# Getting start
1. Get API client ID and secret of Naver Open API.
    1. Register Application at https://developers.naver.com/apps/#/list.
    1. Select the below APIs.
        * Papago translation
        * Papago language detection
    1. Click the "Register" button.
    1. You can get an API client ID and secret.
1. Open IntelliJ preference > Tools > Translation Plugin Settings
1. Set the API client ID and the secret of Naver Open API.
1. Set language settings. You can see selectable languages at [Papago translation API reference](https://developers.naver.com/docs/papago/papago-nmt-api-reference.md).
   </br><img width="700" alt="setting screen" src="https://user-images.githubusercontent.com/2020337/127078602-515cd04b-1165-452d-a971-3a18dd04e217.png">
1. Now you're ready to use this plugin!
1. Select a text you want to translate and type "alt + T", then you can get the translated text.
