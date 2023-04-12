package com.example.chestnut6.Fragments;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chestnut6.MainActivity;
import com.example.chestnut6.R;
import com.example.chestnut6.SelectDeviceActivity;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class SwitchesFragment extends Fragment {


    ///////////VOICE COMMAND///////////////////////////////////////////////////////
    private SpeechRecognizer speechRecognizer;
    private RelativeLayout ParentRelative;
    public static final Integer RecordAudioRequestCode = 1;
    private String keeper= "";
    private Button voiceEnabled;
    private String mode1 = "OFF";
    private String mode2 = "OFF";
    private String mode3 = "OFF";
    ///////////VOICE COMMAND//////////////////////////////////////////////////////


    private String deviceName = null;
    private String deviceAddress;
    public static Handler handler;
    public static BluetoothSocket mmSocket;
    public static ConnectedThread connectedThread;
    public static CreateConnectThread createConnectThread;

    private final static int CONNECTING_STATUS = 1; // used in bluetooth handler to identify message status
    private final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update


    RelativeLayout controllingRelativeL, RLSwitch1, RLSwitch2, RLSwitch3, RLSwitch4, RLSwitch5, RLSwitch6,
            RLSwitch7, alarmLayout;
    LinearLayout Overall;
    TextView controlTitle1, controlTitle2, controlTitle3, controlTitle4, controlTitle5, controlTitle6,
            controlTitle7;
    Switch theSwitch1, theSwitch2, theSwitch3, theSwitch4, theSwitch5, theSwitch6, theSwitch7;
    ImageView img_1, img_2, img_3, img_4, img_5, img_6, img_7, alarm;
    TextView State1, State2, State3, State4, State5, State6, State7;

    TextView arduinoText;

    @SuppressLint({"WrongConstant", "ResourceAsColor"})


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_switches, container, false);



        /////////VOICE COMMAND//////////////////////////////////////////
        if(ContextCompat.checkSelfPermission(getContext().getApplicationContext(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }

        ParentRelative = view.findViewById(R.id.controllingRelativeL);
        voiceEnabled = view.findViewById(R.id.voiceEnabled);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());

        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());


        if(!SpeechRecognizer.isRecognitionAvailable(getContext())){

            String appPackageName = "com.google.android.googlequicksearchbox";
            try{this.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appPackageName)));

            }catch (android.content.ActivityNotFoundException activityNotFoundException){
                this.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResults(Bundle bundle) {



                ArrayList<String> matchesFound =
                        bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if(matchesFound!=null){
                    keeper = matchesFound.get(0);
                    Toast.makeText(getContext(), "Result = " + keeper, Toast.LENGTH_LONG).show();



                    if(keeper.equals("Stop Listening")){
                        speechRecognizer.stopListening();
                    }

                    else{

                        if (keeper.equals("number one")){
                            controllingRelativeL.
                                    setBackgroundColor(getActivity().getColor(R.color.teal_200));
                            String cmdText = null;
                            if(mode1 == "ON"){
                                cmdText = "<turn off>";
                                }
                            else {
                                cmdText = "<turn on>";
                            }
                            connectedThread.write(cmdText);
                        }



                        if (keeper.equals("number two")){
                            controllingRelativeL.
                                    setBackgroundColor(getActivity().getColor(R.color.teal_200));
                            String cmdTexts = null;
                            if(mode2 == "ON"){
                                cmdTexts = "<turns off>";
                            }
                            else {
                                cmdTexts = "<turns on>";
                            }
                            connectedThread.write(cmdTexts);
                        }


                        if (keeper.equals("number three")){
                            controllingRelativeL.
                                    setBackgroundColor(getActivity().getColor(R.color.teal_200));
                            String cmdTextx = null;
                            if(mode3 == "ON"){
                                cmdTextx = "<turnx off>";
                            }
                            else {
                                cmdTextx = "<turnx on>";
                            }
                            connectedThread.write(cmdTextx);
                        }


                        if (keeper.equals("black")){
                            controllingRelativeL.
                                    setBackgroundColor(getActivity().getColor(R.color.black));}

                        if (keeper.equals("yellow")){
                            controllingRelativeL.
                                    setBackgroundColor(getActivity().getColor(R.color.yellow));}

                        if (keeper.equals("pimp")){
                            controllingRelativeL.
                                    setBackgroundColor(getActivity().getColor(R.color.colorAccents));}

                        if (keeper.equals("green")){
                            controllingRelativeL.
                                    setBackgroundColor(getActivity().getColor(R.color.colorOn));}

                    }
                }

            }

            @Override
            public void onPartialResults(Bundle bundle) {
            }

            @Override
            public void onEvent(int i, Bundle bundle) {
            }
        });

//
//        voiceEnabled.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(mode.equals("ON")){
//                    mode = "OFF";
//                    speechRecognizer.stopListening();
//                    voiceEnabled.setText("Voice Enabled Mode = OFF");
//                }
//
//                else{
//                    mode = "ON";
//                    voiceEnabled.setText("Voice Enabled Mode = ON");
//                    speechRecognizer.startListening(speechRecognizerIntent);
//                }
//            }
//        });

        voiceEnabled.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    speechRecognizer.stopListening();
                    keeper = "";
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
//                    micButton.setImageResource(R.drawable.ic_mic_black_24dp);
                    speechRecognizer.startListening(speechRecognizerIntent);
                }
                return false;
            }
        });


        /////////////////////////////////VOICE COMMAND////////////////////////////////














        arduinoText = view.findViewById(R.id.mTxtReceive);



        // UI Initialization
        final ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        //State Initialization
        State1 = view.findViewById(R.id.textViewInfo);
        State2 = view.findViewById(R.id.textViewInfo2);
        State3 = view.findViewById(R.id.textViewInfo3);
        State4 = view.findViewById(R.id.textViewInfo4);
        State5 = view.findViewById(R.id.textViewInfo5);
        State6 = view.findViewById(R.id.textViewInfo6);
        State7 = view.findViewById(R.id.textViewInfo7);

        //Switch Initialization
        theSwitch1 = view.findViewById(R.id.buttonToggle);
        theSwitch2 = view.findViewById(R.id.buttonToggle2);
        theSwitch3 = view.findViewById(R.id.buttonToggle3);
        theSwitch4 = view.findViewById(R.id.buttonToggle4);
        theSwitch5 = view.findViewById(R.id.buttonToggle5);
        theSwitch6 = view.findViewById(R.id.buttonToggle6);
        theSwitch7 = view.findViewById(R.id.buttonToggle7);


        //Relative Layout Initialization
        alarmLayout = view.findViewById(R.id.alarmLayout);
        RLSwitch1 = view.findViewById(R.id.RLSwitch1);
        RLSwitch2 = view.findViewById(R.id.RLSwitch2);
        RLSwitch3 = view.findViewById(R.id.RLSwitch3);
        RLSwitch4 = view.findViewById(R.id.RLSwitch4);
        RLSwitch5 = view.findViewById(R.id.RLSwitch5);
        RLSwitch6 = view.findViewById(R.id.RLSwitch6);
        RLSwitch7 = view.findViewById(R.id.RLSwitch7);



        //Control Title Initialization
        controlTitle1 = view.findViewById(R.id.switch1);
        controlTitle2 = view.findViewById(R.id.switch2);
        controlTitle3 = view.findViewById(R.id.switch3);
        controlTitle4 = view.findViewById(R.id.switch4);
        controlTitle5 = view.findViewById(R.id.switch5);
        controlTitle6 = view.findViewById(R.id.switch6);
        controlTitle7 = view.findViewById(R.id.switch7);


        //Image View Initialization
        img_1 = view.findViewById(R.id.img_1);
        img_2 = view.findViewById(R.id.img_2);
        img_3 = view.findViewById(R.id.img_3);
        img_4 = view.findViewById(R.id.img_4);
        img_5 = view.findViewById(R.id.img_5);
        img_6 = view.findViewById(R.id.img_6);
        img_7 = view.findViewById(R.id.img_7);
        alarm = view.findViewById(R.id.alarm);


        //Layout Initialization
        Overall = view.findViewById(R.id.overallLinearLayout);
        controllingRelativeL = view.findViewById(R.id.controllingRelativeL);
        final RelativeLayout switches = view.findViewById(R.id.controllingRelativeL);
        final RelativeLayout noDevice = view.findViewById(R.id.noDeviceLayout);
        switches.setVisibility(View.GONE);


        //Relative Layout Initialization
        RLSwitch1 = view.findViewById(R.id.RLSwitch1);
        RLSwitch2 = view.findViewById(R.id.RLSwitch2);
        RLSwitch3 = view.findViewById(R.id.RLSwitch3);
        RLSwitch4 = view.findViewById(R.id.RLSwitch4);
        RLSwitch5 = view.findViewById(R.id.RLSwitch5);
        RLSwitch6 = view.findViewById(R.id.RLSwitch6);
        RLSwitch7 = view.findViewById(R.id.RLSwitch7);



//        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.alarm);


        //Switch Deactivation
        theSwitch1.setEnabled(false);
        theSwitch2.setEnabled(false);
        theSwitch3.setEnabled(false);
        theSwitch4.setEnabled(false);
        theSwitch5.setEnabled(false);
        theSwitch6.setEnabled(false);
        theSwitch7.setEnabled(false);




        //Alert Animation
        ObjectAnimator anim = ObjectAnimator.ofInt(controllingRelativeL, "backgroundColor", Color.WHITE, Color.RED,
                Color.WHITE);
        anim.setDuration(200);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);


//////////////////////////////////////////////////////////////////////////////////////////////////


        // If a bluetooth device has been selected from SelectDeviceActivity
        deviceName = getActivity().getIntent().getStringExtra("deviceName");
        if (deviceName != null) {
            // Get the device address to make BT Connection
            deviceAddress = getActivity().getIntent().getStringExtra("deviceAddress");
            // Show progress and connection status

            progressBar.setVisibility(View.VISIBLE);


            /*
            This is the most important piece of code. When "deviceName" is found
            the code will call a new thread to create a bluetooth connection to the
            selected device (see the thread code below)
             */
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            createConnectThread = new CreateConnectThread(bluetoothAdapter, deviceAddress);
            createConnectThread.start();
        }

        /*
        Second most important piece of Code. GUI Handler
         */
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case CONNECTING_STATUS:
                        switch (msg.arg1) {
                            case 1:

                                noDevice.setVisibility(View.GONE);
                                switches.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Connection to " + deviceName + " successful", Toast.LENGTH_SHORT).show();

                                //Switch Activation
//                                buttonConnect.setEnabled(true);
                                theSwitch1.setEnabled(true);
                                theSwitch2.setEnabled(true);
                                theSwitch3.setEnabled(true);
                                theSwitch4.setEnabled(true);
                                theSwitch5.setEnabled(true);
                                theSwitch6.setEnabled(true);
                                theSwitch7.setEnabled(true);
                                break;

                            case -1:

                                noDevice.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Device fails to Connect", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
//                                buttonConnect.setEnabled(true);
                                break;
                        }
                        break;

                    case MESSAGE_READ:
                        String arduinoMsg = msg.obj.toString(); // Read message from Arduino

                        if(arduinoMsg.contains("Input Voltage")){
                         arduinoText.setText(arduinoMsg);


                            Bundle bundle =new Bundle();
                            bundle.putString("key", arduinoMsg);

                            AnalysisFragment analysisFragment = new AnalysisFragment();
                            analysisFragment.setArguments(bundle);



                        }

                        switch (arduinoMsg.toLowerCase()) {
                            case "led is turned on":
                                mode1 = "ON";
                                State1.setText("Switch is On");
                                controlTitle1.setTextColor(getResources().getColor(R.color.black));
                                img_1.setBackground(getActivity().getDrawable(R.drawable.switch_drawable_on));
                                RLSwitch1.setBackground(getActivity().getDrawable(R.drawable.switch_gradient_on));
                                State1.setTextColor(getResources().getColor(R.color.black));

                                break;

                            case "led is turned off":

                                mode1 = "OFF";
                                img_1.setBackgroundResource(0);
                                State1.setTextColor(getResources().getColor(R.color.grey));
                                controlTitle1.setTextColor(getResources().getColor(R.color.grey));
                                RLSwitch1.setBackground(getActivity().getDrawable(R.drawable.switch_gradient));
                                State1.setText("Switch is Off");
                                break;


                            case "led2 is turned on":
                                mode2 = "ON";
                                State2.setText("Switch is On");
                                controlTitle2.setTextColor(getResources().getColor(R.color.black));
                                img_2.setBackground(getActivity().getDrawable(R.drawable.switch_drawable_on));
                                RLSwitch2.setBackground(getActivity().getDrawable(R.drawable.switch_gradient_on));
                                State2.setTextColor(getResources().getColor(R.color.black));

                                break;

                            case "led2 is turned off":

                                mode2 = "OFF";
                                img_2.setBackgroundResource(0);
                                State2.setTextColor(getResources().getColor(R.color.grey));
                                controlTitle2.setTextColor(getResources().getColor(R.color.grey));
                                RLSwitch2.setBackground(getActivity().getDrawable(R.drawable.switch_gradient));
                                State2.setText("Switch is Off");
                                break;

                            case "led3 is turned on":
                                mode3 = "ON";
                                State3.setText("Switch is On");
                                controlTitle3.setTextColor(getResources().getColor(R.color.black));
                                img_3.setBackground(getActivity().getDrawable(R.drawable.switch_drawable_on));
                                RLSwitch3.setBackground(getActivity().getDrawable(R.drawable.switch_gradient_on));
                                State3.setTextColor(getResources().getColor(R.color.black));

                                break;

                            case "led3 is turned off":
                                mode3 = "OFF";
                                img_3.setBackgroundResource(0);
                                State3.setTextColor(getResources().getColor(R.color.grey));
                                controlTitle3.setTextColor(getResources().getColor(R.color.grey));
                                RLSwitch3.setBackground(getActivity().getDrawable(R.drawable.switch_gradient));
                                State3.setText("Switch is Off");
                                break;

                            /* ==================================================================== ULTRASONIC SENSORS ============================================================= */
//                            case :
//                                State4.setText("Arduino Message : " + arduinoMsg);
//                                anim.start();
//                                mediaPlayer.start();
//                                alarmLayout.setVisibility(View.VISIBLE);
//                                break;
//
//                            case "no warning":
//                                State4.setText("Arduino Message : " + arduinoMsg);

//                                break;
                            /* ==================================================================== ULTRASONIC SENSORS ============================================================= */
                        }
                        break;

                }
            }
        };





        // Button to ON/OFF LED on Arduino Board
        theSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                String cmdText = null;
                if (isChecked) {
                    cmdText = "<turn on>";
                } else {
                    cmdText = "<turn off>";
                }
                connectedThread.write(cmdText);
            }
        });


        // Button to ON/OFF LED on Arduino Board
        theSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String cmdTexts = null;
                if (isChecked) {
                    cmdTexts = "<turns on>";
                } else {
                    cmdTexts = "<turns off>";
                }
                connectedThread.write(cmdTexts);
            }
        });


        // Button to ON/OFF LED on Arduino Board
        theSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String cmdTextx = null;
                if (isChecked) {
                    cmdTextx = "<turnx on>";
                } else {
                    cmdTextx = "<turnx off>";
                }
                connectedThread.write(cmdTextx);
            }
        });


        RLSwitch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


//        alarm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mediaPlayer.stop();
//                anim.end();
//                alarmLayout.setVisibility(View.GONE);
//            }
//        });




        return view;
    }


    /* ============================ Thread to Create Bluetooth Connection =================================== */
    public static class CreateConnectThread extends Thread {

        public CreateConnectThread(BluetoothAdapter bluetoothAdapter, String address) {
            /*
            Use a temporary object that is later assigned to mmSocket
            because mmSocket is final.
             */
            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);
            BluetoothSocket tmp = null;
            UUID uuid = bluetoothDevice.getUuids()[0].getUuid();

            try {
                /*
                Get a BluetoothSocket to connect with the given BluetoothDevice.
                Due to Android device varieties,the method below may not work fo different devices.
                You should try using other methods i.e. :
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
                 */
                tmp = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid);

            } catch (IOException e) {
                Log.e(TAG, "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothAdapter.cancelDiscovery();
            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
                Log.e("Status", "Device connected");
                handler.obtainMessage(CONNECTING_STATUS, 1, -1).sendToTarget();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                    Log.e("Status", "Cannot connect to device");
                    handler.obtainMessage(CONNECTING_STATUS, -1, -1).sendToTarget();
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            connectedThread = new ConnectedThread(mmSocket);
            connectedThread.run();
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }
    }

    /* =============================== Thread for Data Transfer =========================================== */
    public static class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            }


            catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[2048];  // buffer store for the stream
            int bytes = 0; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    /*
                    Read from the InputStream from Arduino until termination character is reached.
                    Then send the whole String message to GUI Handler.
                     */
                    buffer[bytes] = (byte) mmInStream.read();
                    String readMessage;
                    if (buffer[bytes] == '\n') {
                        readMessage = new String(buffer, 0, bytes);
                        Log.e("Arduino Message", readMessage);
                        handler.obtainMessage(MESSAGE_READ, readMessage).sendToTarget();
                        bytes = 0;
                    } else {
                        bytes++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String input) {
            byte[] bytes = input.getBytes(); //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e("Send Error", "Unable to send message", e);
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

    /* ============================ Terminate Connection at BackPress ====================== */


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(getContext(),"Permission Granted",Toast.LENGTH_SHORT).show();
        }
    }




}