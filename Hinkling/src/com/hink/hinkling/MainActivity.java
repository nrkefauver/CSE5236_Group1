package com.hink.hinkling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import android.os.AsyncTask;
import android.content.Context;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.hink.hinkling.chat.GuestbookActivity;

public class MainActivity extends BaseGameActivity implements
        View.OnClickListener, RealTimeMessageReceivedListener,
        RoomStatusUpdateListener, RoomUpdateListener,
        OnInvitationReceivedListener {

    /*
     * API INTEGRATION SECTION. This section contains the code that integrates
     * the game with the Google Play game services API.
     */

    // Debug tag
    final static boolean ENABLE_DEBUG = true;
    final static String TAG = "Hinkling";
    public static  String USERNAME = "";
    // Request codes for the UIs that we show with startActivityForResult:
    final static int RC_SELECT_PLAYERS = 10000;
    final static int RC_INVITATION_INBOX = 10001;
    final static int RC_WAITING_ROOM = 10002;
    public static String subjectMaster = "";
    public static String subject ="";
    Random rnd = new Random(); 
    public static List<String> dictionaryWords = new ArrayList<String>();
    
    // Room ID where the currently active game is taking place; null if we're
    // not playing.
    String mRoomId = null;

    // Are we playing in multiplayer mode?
    boolean mMultiplayer = true;

    // The participants in the currently active game
   public static ArrayList<Participant> mParticipants = null;

    // My participant ID in the currently active game
    public static String mMyId = null;

    // If non-null, this is the id of the invitation we received via the
    // invitation listener
    String mIncomingInvitationId = null;

    // Message buffer for sending messages
    byte[] mMsgBuf = new byte[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        SharedPreferences prefs = this.getSharedPreferences(
        	      "com.hink.hinkling", Context.MODE_PRIVATE);

    
        readingFile();
        // set up a click listener for everything we care about
        for (int id : CLICKABLES) {
            this.findViewById(id).setOnClickListener(this);
        }
        
        
        final Button Hinkalize = (Button)findViewById(R.id.buttonHinkalize);
        Hinkalize.setOnClickListener(new Button.OnClickListener(){

   @Override
   public void onClick(View arg0) {
    LayoutInflater layoutInflater 
     = (LayoutInflater)getBaseContext()
      .getSystemService(LAYOUT_INFLATER_SERVICE);  
    View popupView = layoutInflater.inflate(R.layout.activity_hinkalize, null);  
             final PopupWindow popupWindow = new PopupWindow(
               popupView, 
               LayoutParams.WRAP_CONTENT,  
                     LayoutParams.WRAP_CONTENT,true);  
			    
		     final View btnA=findViewById(R.id.buttonSoloPlay);
             final View btnB=findViewById(R.id.buttonPlayFriends);
           final View btnC=findViewById(R.id.buttonChat);
             final View btnD=findViewById(R.id.buttonExit);
             final View backMain = findViewById(R.id.screen_main);
             Button changeBackground = (Button)popupView.findViewById(R.id.changeBackground);
             changeBackground.setOnClickListener(new Button.OnClickListener(){
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)); 
            	 
     @Override
     public void onClick(View v) {
 	     
 	
    	 backMain.setBackgroundColor(color);
    	 
      
     }});
             
             
            final Button colorMain = (Button)popupView.findViewById(R.id.colorMain);
            colorMain.setOnTouchListener(new Button.OnTouchListener(){
            	 int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)); 
           	 boolean ButCol = false;

            public boolean onTouch(View v, MotionEvent me) {
                switch(me.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    btnA.setBackgroundColor(color);
                   	 btnB.setBackgroundColor(color);
                   	 btnC.setBackgroundColor(color);
                   	 btnD.setBackgroundColor(color);
                   	 break;
                    case MotionEvent.ACTION_UP:  
                    	
                       break;
                }
                return false;
                
            }
  

});
             Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
             btnDismiss.setOnClickListener(new Button.OnClickListener(){
            	 
            	 
     @Override
     public void onClick(View v) {
 	     
 	
      popupWindow.dismiss();
   
      
     }});
               
             popupWindow.showAsDropDown(Hinkalize, 50, -30);
         
   }});
    }
        

    private void quitApplication() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.exit)
                .setMessage("Quit Hinkling??? WHY?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                System.exit(0);
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main, menu);
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
      super.onSaveInstanceState(savedInstanceState);

     // savedInstanceState.

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
      super.onRestoreInstanceState(savedInstanceState);
      // Restore UI state from the savedInstanceState.
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            return rootView;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.button_sign_in:
                // user wants to sign in
                this.beginUserInitiatedSignIn();
                break;
            case R.id.buttonExit:
                // user wants to sign out
                this.signOut();
                this.switchToScreen(R.id.screen_sign_in);
                break;
            case R.id.buttonSoloPlay:
            	this.startActivity(new Intent(this, PlayerWaiting.class));
                break;
            case R.id.buttonPlayFriends:
                // show list of invitable players
                intent = Games.RealTimeMultiplayer.getSelectOpponentsIntent(
                        this.getApiClient(), 1, 3);
                this.switchToScreen(R.id.screen_wait);
                this.startActivityForResult(intent, RC_SELECT_PLAYERS);
                break;
            case R.id.buttonInvites:
                // show list of pending invitations
                intent = Games.Invitations.getInvitationInboxIntent(this
                        .getApiClient());
                this.switchToScreen(R.id.screen_wait);
                this.startActivityForResult(intent, RC_INVITATION_INBOX);
                break;
            case R.id.button_accept_popup_invitation:
                // user wants to accept the invitation shown on the invitation popup
                // (the one we got through the OnInvitationReceivedListener).
            	
                this.acceptInviteToRoom(this.mIncomingInvitationId);
                this.mIncomingInvitationId = null;
                intent = Games.Invitations.getInvitationInboxIntent(this
                        .getApiClient());
                this.switchToScreen(R.id.screen_wait);
                this.startActivityForResult(intent, RC_INVITATION_INBOX);
                break;
            case R.id.buttonChat:
            	this.startActivity(new Intent(this, Help.class));
                break;
            case R.id.buttonSettings:
            	this.startActivity(new Intent(this, GuestbookActivity.class));
                break;
            case R.id.buttonQuickPlay:
                // user wants to play against a random opponent right now
                this.startQuickGame();
                break;
        }
        }


    /**
     * Called by the base class (BaseGameActivity) when sign-in succeeded. We
     * react by going to our main screen.
     */
    @Override
    public void onSignInSucceeded() {
        Log.d(TAG, "Sign-in succeeded.");

        // register listener so we are notified if we receive an invitation to play
        // while we are in the game
        Games.Invitations.registerInvitationListener(this.getApiClient(), this);

        // if we received an invite via notification, accept it; otherwise, go to main screen
        if (this.getInvitationId() != null) {
            this.acceptInviteToRoom(this.getInvitationId());
            return;
        }
        this.switchToMainScreen();
    }

    void startQuickGame() {
        // quick-start a game with 1 randomly selected opponent
        final int MIN_OPPONENTS = 3, MAX_OPPONENTS = 3;
        Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
                MIN_OPPONENTS, MAX_OPPONENTS, 0);
        RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(this);
        rtmConfigBuilder.setMessageReceivedListener(this);
        rtmConfigBuilder.setRoomStatusUpdateListener(this);
        rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
        this.switchToScreen(R.id.screen_wait);
        this.keepScreenOn();
        //this.resetGameVars();
        Games.RealTimeMultiplayer.create(this.getApiClient(),
                rtmConfigBuilder.build());
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode,
            Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);

        switch (requestCode) {
            case RC_SELECT_PLAYERS:
                // we got the result from the "select players" UI -- ready to create the room
                this.handleSelectPlayersResult(responseCode, intent);
                break;
            case RC_INVITATION_INBOX:
                // we got the result from the "select invitation" UI (invitation inbox). We're
                // ready to accept the selected invitation:
                this.handleInvitationInboxResult(responseCode, intent);
                break;
            case RC_WAITING_ROOM:
                // we got the result from the "waiting room" UI.
                if (responseCode == Activity.RESULT_OK) {
                    // ready to start playing
                    Log.d(TAG, "Starting game (waiting room returned OK).");
                    this.startGame(true);
                  
                } else if (responseCode == GamesActivityResultCodes.RESULT_LEFT_ROOM) {
                    // player indicated that they want to leave the room
                    this.leaveRoom();
                } else if (responseCode == Activity.RESULT_CANCELED) {
                    // Dialog was cancelled (user pressed back key, for instance). In our game,
                    // this means leaving the room too. In more elaborate games, this could mean
                    // something else (like minimizing the waiting room UI).
                    this.leaveRoom();
                }
                break;
        }
    }

    // Handle the result of the "Select players UI" we launched when the user clicked the
    // "Invite friends" button. We react by creating a room with those players.
    private void handleSelectPlayersResult(int response, Intent data) {
        if (response != Activity.RESULT_OK) {
            Log.w(TAG, "*** select players UI cancelled, " + response);
            this.switchToMainScreen();
            return;
        }

        Log.d(TAG, "Select players UI succeeded.");

        // get the invitee list
        final ArrayList<String> invitees = data
                .getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);
        Log.d(TAG, "Invitee count: " + invitees.size());

        // get the automatch criteria
        Bundle autoMatchCriteria = null;
        int minAutoMatchPlayers = data.getIntExtra(
                Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
        int maxAutoMatchPlayers = data.getIntExtra(
                Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);
        if (minAutoMatchPlayers > 0 || maxAutoMatchPlayers > 0) {
            autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
                    minAutoMatchPlayers, maxAutoMatchPlayers, 0);
            Log.d(TAG, "Automatch criteria: " + autoMatchCriteria);
        }

        // create the room
        Log.d(TAG, "Creating room...");
        RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(this);
        rtmConfigBuilder.addPlayersToInvite(invitees);
        rtmConfigBuilder.setMessageReceivedListener(this);
        rtmConfigBuilder.setRoomStatusUpdateListener(this);
        if (autoMatchCriteria != null) {
            rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
        }
        this.switchToScreen(R.id.screen_wait);
        this.keepScreenOn();
        //this.resetGameVars();
        Games.RealTimeMultiplayer.create(this.getApiClient(),
                rtmConfigBuilder.build());
        Log.d(TAG, "Room created, waiting for it to be ready...");
    }

    // Handle the result of the invitation inbox UI, where the player can pick an invitation
    // to accept. We react by accepting the selected invitation, if any.
    private void handleInvitationInboxResult(int response, Intent data) {
        if (response != Activity.RESULT_OK) {
            Log.w(TAG, "*** invitation inbox UI cancelled, " + response);
            this.switchToMainScreen();
            return;
        }

        Log.d(TAG, "Invitation inbox UI succeeded.");
        Invitation inv = data.getExtras().getParcelable(
                Multiplayer.EXTRA_INVITATION);

        // accept invitation
        this.acceptInviteToRoom(inv.getInvitationId());
    }

    // Accept the given invitation.
    void acceptInviteToRoom(String invId) {
        // accept the invitation
        Log.d(TAG, "Accepting invitation: " + invId);
        RoomConfig.Builder roomConfigBuilder = RoomConfig.builder(this);
        roomConfigBuilder.setInvitationIdToAccept(invId)
                .setMessageReceivedListener(this)
                .setRoomStatusUpdateListener(this);
        this.switchToScreen(R.id.screen_wait);
        this.keepScreenOn();
        //this.resetGameVars();
        Games.RealTimeMultiplayer.join(this.getApiClient(),
                roomConfigBuilder.build());
    }

    // Activity is going to the background. We have to leave the current room.
    @Override
    public void onStop() {
        Log.d(TAG, "**** got onStop");

        // if we're in a room, leave it.
        this.leaveRoom();

        // stop trying to keep the screen on
        this.stopKeepingScreenOn();

        this.switchToScreen(R.id.screen_wait);
        super.onStop();
    }

    // Activity just got to the foreground. We switch to the wait screen because we will now
    // go through the sign-in flow (remember that, yes, every time the Activity comes back to the
    // foreground we go through the sign-in flow -- but if the user is already authenticated,
    // this flow simply succeeds and is imperceptible).
    @Override
    public void onStart() {
        this.switchToScreen(R.id.screen_wait);
        super.onStart();
    }

    // Handle back key to make sure we cleanly leave a game if we are in the middle of one
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e) {
        if (keyCode == KeyEvent.KEYCODE_BACK
         && this.mCurScreen == R.id.screen_game ) {
            this.leaveRoom();
            return true;
        }
        return super.onKeyDown(keyCode, e);
    }

    // Leave the room.
    void leaveRoom() {
        Log.d(TAG, "Leaving room.");
        //this.mSecondsLeft = 0;
        this.stopKeepingScreenOn();
        if (this.mRoomId != null) {
            Games.RealTimeMultiplayer.leave(this.getApiClient(), this,
                    this.mRoomId);
            this.mRoomId = null;
            this.switchToScreen(R.id.screen_wait);
        } else {
            this.switchToMainScreen();
        }
    }

    // Show the waiting room UI to track the progress of other players as they enter the
    // room and get connected.
    void showWaitingRoom(Room room) {
        // minimum number of players required for our game
        // For simplicity, we require everyone to join the game before we start it
        // (this is signaled by Integer.MAX_VALUE).
        final int MIN_PLAYERS = Integer.MAX_VALUE;
        Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(
                this.getApiClient(), room, MIN_PLAYERS);

        // show waiting room UI
        this.startActivityForResult(i, RC_WAITING_ROOM);
    }

    // Called when we get an invitation to play a game. We react by showing that to the user.
    @Override
    public void onInvitationReceived(Invitation invitation) {
        // We got an invitation to play a game! So, store it in
        // mIncomingInvitationId
        // and show the popup on the screen.
        this.mIncomingInvitationId = invitation.getInvitationId();
        ((TextView) this.findViewById(R.id.incoming_invitation_text))
                .setText(invitation.getInviter().getDisplayName() + " "
                        + this.getString(R.string.is_inviting_you));
        this.switchToScreen(this.mCurScreen); // This will show the invitation popup
    }

    @Override
    public void onInvitationRemoved(String invitationId) {
        if (this.mIncomingInvitationId.equals(invitationId)) {
            this.mIncomingInvitationId = null;
            this.switchToScreen(this.mCurScreen); // This will hide the invitation popup
        }
    }

    /*
     * CALLBACKS SECTION. This section shows how we implement the several games
     * API callbacks.
     */

    // Called when we are connected to the room. We're not ready to play yet! (maybe not everybody
    // is connected yet).
    @Override
    public void onConnectedToRoom(Room room) {
        Log.d(TAG, "onConnectedToRoom.");

        // get room ID, participants and my ID:
        this.mRoomId = room.getRoomId();
        this.mParticipants = room.getParticipants();
        this.mMyId = room.getParticipantId(Games.Players
                .getCurrentPlayerId(this.getApiClient()));

        // print out the list of participants (for debug purposes)
        Log.d(TAG, "Room ID: " + this.mRoomId);
        Log.d(TAG, "My ID " + this.mMyId);
        Log.d(TAG, "<< CONNECTED TO ROOM>>");
    }

    // Called when we've successfully left the room (this happens a result of voluntarily leaving
    // via a call to leaveRoom(). If we get disconnected, we get onDisconnectedFromRoom()).
    @Override
    public void onLeftRoom(int statusCode, String roomId) {
        // we have left the room; return to main screen.
        Log.d(TAG, "onLeftRoom, code " + statusCode);
        this.switchToMainScreen();
    }

    // Called when we get disconnected from the room. We return to the main screen.
    @Override
    public void onDisconnectedFromRoom(Room room) {
        this.mRoomId = null;
        this.showGameError();
    }

    // Show error message about game being cancelled and return to main screen.
    void showGameError() {
        this.showAlert(this.getString(R.string.game_problem));
        this.switchToMainScreen();
    }

    // Called when room has been created
    @Override
    public void onRoomCreated(int statusCode, Room room) {
        Log.d(TAG, "onRoomCreated(" + statusCode + ", " + room + ")");
        if (statusCode != GamesStatusCodes.STATUS_OK) {
            Log.e(TAG, "*** Error: onRoomCreated, status " + statusCode);
            this.showGameError();
            return;
        }

        // show the waiting room UI
        this.showWaitingRoom(room);
    }

    // Called when room is fully connected.
    @Override
    public void onRoomConnected(int statusCode, Room room) {
        Log.d(TAG, "onRoomConnected(" + statusCode + ", " + room + ")");
        if (statusCode != GamesStatusCodes.STATUS_OK) {
            Log.e(TAG, "*** Error: onRoomConnected, status " + statusCode);
            this.showGameError();
            return;
        }
        this.updateRoom(room);
    }

    @Override
    public void onJoinedRoom(int statusCode, Room room) {
        Log.d(TAG, "onJoinedRoom(" + statusCode + ", " + room + ")");
        if (statusCode != GamesStatusCodes.STATUS_OK) {
            Log.e(TAG, "*** Error: onRoomConnected, status " + statusCode);
            this.showGameError();
            return;
        }

        // show the waiting room UI
        this.showWaitingRoom(room);
    }

    // We treat most of the room update callbacks in the same way: we update our list of
    // participants and update the display. In a real game we would also have to check if that
    // change requires some action like removing the corresponding player avatar from the screen,
    // etc.
    @Override
    public void onPeerDeclined(Room room, List<String> arg1) {
        this.updateRoom(room);
    }

    @Override
    public void onPeerInvitedToRoom(Room room, List<String> arg1) {
        this.updateRoom(room);
    }

    @Override
    public void onP2PDisconnected(String participant) {
    }

    @Override
    public void onP2PConnected(String participant) {
    }

    @Override
    public void onPeerJoined(Room room, List<String> arg1) {
        this.updateRoom(room);
    }

    @Override
    public void onPeerLeft(Room room, List<String> peersWhoLeft) {
        this.updateRoom(room);
    }

    @Override
    public void onRoomAutoMatching(Room room) {
        this.updateRoom(room);
    }

    @Override
    public void onRoomConnecting(Room room) {
        this.updateRoom(room);
    }

    @Override
    public void onPeersConnected(Room room, List<String> peers) {
        this.updateRoom(room);
    }

    @Override
    public void onPeersDisconnected(Room room, List<String> peers) {
        this.updateRoom(room);
    }

    void updateRoom(Room room) {
        if (room != null) {
            this.mParticipants = room.getParticipants();
        }
        if (this.mParticipants != null) {
            //this.updatePeerScoresDisplay();
        }
    }
 // Current state of the game:
    int mSecondsLeft = -1; // how long until the game ends (seconds)
    final static int GAME_DURATION = 20; // game duration, seconds.
    int mScore = 0; // user's current score

    // Reset game variables in preparation for a new game.
    void resetGameVars() {
        mSecondsLeft = GAME_DURATION;
        mScore = 0;
        mParticipantScore.clear();
        mFinishedParticipants.clear();
    }

    
   
    // Start the gameplay phase of the game.
    void startGame(boolean multiplayer) {
        mMultiplayer = multiplayer;
        broadcastScore(false);
        switchToScreen(R.id.screen_game);
       subject = dictionaryWords.get(rnd.nextInt(dictionaryWords.size()));
        /* The following commented out code is when we implement subject master rounds*/
        //Participant sM = mParticipants.get(1);
        //subjectMaster = sM.getParticipantId();
        //if (subjectMaster.contains(mMyId)){
        //this.startActivity(new Intent(this, WelcomeSM.class));
        //} else {
        this.startActivity(new Intent(this, PlayerWaiting.class));
        //}
       
        // run the gameTick() method every second to update the game.
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSecondsLeft <= 0)
                    return;        
                h.postDelayed(this, 1000);
            }
        }, 1000);
    }



    Map<String, Integer> mParticipantScore = new HashMap<String, Integer>();

    // Participants who sent us their final score.
    Set<String> mFinishedParticipants = new HashSet<String>();

    // Called when we receive a real-time message from the network.
    // Messages in our game are made up of 2 bytes: the first one is 'F' or 'U'
    // indicating
    // whether it's a final or interim score. The second byte is the score.
    // There is also the
    // 'S' message, which indicates that the game should start.
    @Override
    public void onRealTimeMessageReceived(RealTimeMessage rtm) {
        byte[] buf = rtm.getMessageData();
        String sender = rtm.getSenderParticipantId();
        Log.d(TAG, "Message received: " + (char) buf[0] + "/" + (int) buf[1]);

        if (buf[0] == 'F' || buf[0] == 'U') {
            // score update.
            int existingScore = mParticipantScore.containsKey(sender) ?
                    mParticipantScore.get(sender) : 0;
            int thisScore = (int) buf[1];
            if (thisScore > existingScore) {
                // this check is necessary because packets may arrive out of
                // order, so we
                // should only ever consider the highest score we received, as
                // we know in our
                // game there is no way to lose points. If there was a way to
                // lose points,
                // we'd have to add a "serial number" to the packet.
                mParticipantScore.put(sender, thisScore);
            }

            // if it's a final score, mark this participant as having finished
            // the game
            if ((char) buf[0] == 'F') {
                mFinishedParticipants.add(rtm.getSenderParticipantId());
            }
        }
    }

    // Broadcast my score to everybody else.
    void broadcastScore(boolean finalScore) {
        if (!mMultiplayer)
            return; // playing single-player mode

        // First byte in message indicates whether it's a final score or not
        mMsgBuf[0] = (byte) (finalScore ? 'F' : 'U');

        // Second byte is the score.
        mMsgBuf[1] = (byte) mScore;

        // Send to every other participant.
        for (Participant p : mParticipants) {
            if (p.getParticipantId().equals(mMyId))
                continue;
            if (p.getStatus() != Participant.STATUS_JOINED)
                continue;
            if (finalScore) {
                // final score notification must be sent via reliable message
                Games.RealTimeMultiplayer.sendReliableMessage(getApiClient(), null, mMsgBuf,
                        mRoomId, p.getParticipantId());
            } else {
                // it's an interim score notification, so we can use unreliable
                Games.RealTimeMultiplayer.sendUnreliableMessage(getApiClient(), mMsgBuf, mRoomId,
                        p.getParticipantId());
            }
        }
    }


    // This array lists everything that's clickable, so we can install click
    // event handlers.
    final static int[] CLICKABLES = { R.id.buttonQuickPlay, R.id.buttonProfile,
            R.id.buttonSettings, R.id.buttonPlayFriends, R.id.buttonExit,
            R.id.buttonInvites,R.id.buttonChat,R.id.buttonSoloPlay };

    // This array lists all the individual screens our game has.
    final static int[] SCREENS = {
            R.id.screen_game, R.id.screen_main, R.id.screen_sign_in,
            R.id.screen_wait
    };
    int mCurScreen = -1;

    void switchToScreen(int screenId) {
        // make the requested screen visible; hide all others.
        for (int id : SCREENS) {
            findViewById(id).setVisibility(screenId == id ? View.VISIBLE : View.GONE);
        }
        mCurScreen = screenId;

        // should we show the invitation popup?
        boolean showInvPopup;
        if (mIncomingInvitationId == null) {
            // no invitation, so no popup
            showInvPopup = false;
        } else if (mMultiplayer) {
            // if in multiplayer, only show invitation on main screen
            showInvPopup = (mCurScreen == R.id.screen_main);
        } else {
            showInvPopup = (mCurScreen == R.id.screen_main);
        }
        findViewById(R.id.invitation_popup).setVisibility(showInvPopup ? View.VISIBLE : View.GONE);
    }

    void switchToMainScreen() {
        this.switchToScreen(this.isSignedIn() ? R.id.screen_main
                : R.id.screen_sign_in);
    }

    /**
     * Called by the base class (BaseGameActivity) when sign-in has failed. For
     * example, because the user hasn't authenticated yet. We react to this by
     * showing the sign-in button.
     */
    @Override
    public void onSignInFailed() {
        Log.d(TAG, "Sign-in failed.");
        this.switchToScreen(R.id.screen_sign_in);
    }

    // Sets the flag to keep this screen on. It's recommended to do that during
    // the
    // handshake when setting up a game, because if the screen turns off, the
    // game will be
    // cancelled.
    void keepScreenOn() {
        this.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    // Clears the flag that keeps the screen on.
    void stopKeepingScreenOn() {
        this.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static void readingFile(){
        try 
        { 
      
            BufferedReader in = new BufferedReader(new FileReader("EnglishDictionary.txt")); 
            String str; 
            while((str = in.readLine()) != null){
                dictionaryWords.add(str);
            }
        }
    catch (IOException e) {
			
				e.printStackTrace();
			}
    }


}
