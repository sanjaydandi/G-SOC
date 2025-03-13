package in.ascentrasolutions.krishi;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceService extends Service {
    private SpeechRecognizer speechRecognizer;
    private boolean isListening = false; // Prevents multiple instances
    private final Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        startListening();
    }

    private void startListening() {
        if (isListening) return; // Prevents multiple simultaneous listeners

        isListening = true;
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.d("VoiceService", "Listening...");
            }

            @Override
            public void onBeginningOfSpeech() {}

            @Override
            public void onRmsChanged(float rmsdB) {}

            @Override
            public void onBufferReceived(byte[] buffer) {}

            @Override
            public void onEndOfSpeech() {
                restartListening();
            }

            @Override
            public void onError(int error) {
                Log.e("VoiceService", "Speech error: " + error);

                if (error == SpeechRecognizer.ERROR_RECOGNIZER_BUSY) {
                    stopListening(); // Stop and restart to prevent overload
                }

                // Restart listening with a delay to prevent excessive calls
                handler.postDelayed(() -> restartListening(), 2000);
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null) {
                    String command = matches.get(0).toLowerCase();
                    Log.d("VoiceService", "Heard: " + command);
                    if (command.contains("show text")) {
                        Intent intent = new Intent("com.example.voicecommand.SHOW_TEXT");
                        sendBroadcast(intent);
                    }
                }
                restartListening();
            }

            @Override
            public void onPartialResults(Bundle partialResults) {}

            @Override
            public void onEvent(int eventType, Bundle params) {}
        });

        speechRecognizer.startListening(intent);
    }

    private void restartListening() {
        stopListening();
        handler.postDelayed(this::startListening, 1000); // Prevents instant re-triggering
    }

    private void stopListening() {
        if (speechRecognizer != null) {
            speechRecognizer.stopListening();
            speechRecognizer.cancel();
            speechRecognizer.destroy();
            speechRecognizer = null;
        }
        isListening = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        stopListening();
        super.onDestroy();
    }
}
