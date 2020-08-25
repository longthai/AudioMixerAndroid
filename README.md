<h1>Audio Mixer Documentation</h1>
<ol>
  <li><h3>About the app</h3></li>
  <li><h3>OAuth2 authentication (IMPORTANT TO RUN THE APP)</h3></li>
</ol>

<h3>About the app</h3>
<p>Audio Mixer is a simple Android application for mixing individual audio files, using Kotlin with MVVM architecture and Freesound API. The application has these following funtionalites:
  <ul>
    <li>Searching for audio from Freesound database</li>
    <li>Listening to individual audio</li>
    <li>Mixing two audio files</li>
    <li>Listening to the mixed audio file on app and saving the file locally (Download folder)</li>
  </ul>
</p>
<p>Libraries used for the app
  <ul>
    <li><b>com.github.rajib010:android_audio_mixer:v1.0</b></br>
        This library helps mixing two URI of audio into one and downloading it to the specific storage on mobile.
    </li>
    <li><b>com.github.rygelouv:android-audio-sensei:0.1.2</b></br>
        This library handles playback control (pause, play) on the MediaPlayer.
    </li>
    <li><b>com.intuit.sdp:sdp-android:1.0.6</b></br>
        This library helps building application for different Android screen sizes
    </li>
    <li>Other libraries:</br>
        <b>com.squareup.retrofit2:retrofit:2.5.0</b></br>
        <b>com.squareup.retrofit2:converter-gson:2.5.0</b></br>
        <b>androidx.recyclerview:recyclerview:1.1.0</b>
    </li>
  </ul>
</p>
<p>Classes explanation:
  <ul>
    <li>In <b>repository</b> package consists of those classes which handle only remote data source and when viewmodel calls them, they get data from servers.</li>
    <li>In <b>ui</b> package consists of those classes with their ViewModel in viewmodel and called by Main classes.</li>
    <li>In <b>data</b> package consists of model classes which help to plot data (coming from servers) into views.</li>
    <li>In <b>adapter</b> package consists of recyclerview adapter which used as a helping bridge between data and view for plotting and handling some queries.</li>
  </ul>
</p>

<h3>OAuth2 authentication</h3>
<p>You need to change the bearer token (each token valid for 24 hours) for the Freesound API with the following steps (More information and instruction <a href="shorturl.at/fDU17">here</a>):</p> 
<p><b>Step 1:</b> Create your Freesound account, where you get your client_id and client_secret.</p>
<p><b>Step 2:</b> In Postman, send a GET request to Freesound to get authorization code. Copy the link (in the red box) to your browser and grant permission to the app to get the code.</br>
<img src="https://i.imgur.com/aKimMuw.png" alt="step 2" style="vertical-align:middle;margin:0px 50px"></p>
<p><b>Step 3:</b> In Postmen, send a POST request to get your access_token for the app with the "code" you get on the website in step 2</br>
<img src="https://i.imgur.com/PXIrIRY.png" alt="step 3" style="vertical-align:middle;margin:0px 50px"></p>
<p><b>Step 4: </b> Copy the new access_token and change in RemoteMusicDetailRepository.kt and RemoteRepository.kt in "repository" package. After you change the token, the app should be running.</br>
<img src="https://i.imgur.com/tHfqwfL.png" alt="step 4" style="vertical-align:middle;margin:0px 50px"></p>
  
  
