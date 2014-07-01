package com.hink.hinkling;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

import com.google.android.gms.games.multiplayer.Participant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class XMPPChat extends Activity {

	public static final String HOST = "talk.google.com";
	public static final int PORT = 5222;
	public static final String SERVICE = "gmail.com";
	public static  String USERNAME = "";
	public static  String PASSWORD = "";
	public static int inc = 0;
	public static String User;
	public static String Name;
	public static String Status;
	public static String Type;
	public static String to;
	public static boolean activate = false;
	public static Map<String,String> Rec = new HashMap<String, String>();
    private ArrayList<String> messages = new ArrayList<String>();
    private Handler mHandler = new Handler();
    private SettingsDialog mDialog;
    private String mRecipient;
    private EditText mSendText;
    private ListView mList;
    private XMPPConnection connection;

    /**
     * Called with the activity is first created.
     */

    

    public void connect() {

		final ProgressDialog dialog = ProgressDialog.show(this,
				"Connecting...", "Please wait...", false);

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// Create a connection
				ConnectionConfiguration connConfig = new ConnectionConfiguration(
						HOST, PORT, SERVICE);
				XMPPConnection connection = new XMPPConnection(connConfig);

				try {
					connection.connect();
					Log.i("XMPPChat",
							"Connected to " + connection.getHost());
				} catch (XMPPException ex) {
					Log.e("XMPPChat", "Failed to connect to "
							+ connection.getHost());
					Log.e("XMPPChat", ex.toString());
					setConnection(null);
				}
				try {
					// SASLAuthentication.supportSASLMechanism("PLAIN", 0);
					connection.login(USERNAME, PASSWORD);
					Log.i("XMPPChat",
							"Logged in as " + connection.getUser());
					

					// Set the status to available
					Presence presence = new Presence(Presence.Type.available);
					connection.sendPacket(presence);
					setConnection(connection);
					
					Roster roster = connection.getRoster();
					Collection<RosterEntry> entries = roster.getEntries();
					for (RosterEntry entry : entries) {

						Rec.put(entry.getName(), entry.getUser());
						
						Status = "Status" + inc + ": " + entry.getStatus();
						Type = "Type" + inc+ ": " + entry.getType();
						
						Log.d("XMPPChat",
								"--------------------------------------");
						Log.d("XMPPChat", "RosterEntry " + entry);
						Log.d("XMPPChat","User" + inc +": " + entry.getUser());
						Log.d("XMPPChat","Name" + inc + ": " + entry.getName());
						Log.d("XMPPChat",Status);
						Log.d("XMPPChat",Type);
						Presence entryPresence = roster.getPresence(entry.getUser());

						Log.d("XMPPChat", "Presence Status: "
								+ entryPresence.getStatus());
						Log.d("XMPPChat", "Presence Type: "
								+ entryPresence.getType());
						Presence.Type type = entryPresence.getType();
						if (type == Presence.Type.available)
							Log.d("XMPPChat", "Presence AVIALABLE");
						Log.d("XMPPChat", "Presence : "
								+ entryPresence);
						
					}
					
				} catch (XMPPException ex) {
					Log.e("XMPPChat", "Failed to log in as "
							+ USERNAME);
					Log.e("XMPPChat", ex.toString());
					setConnection(null);
				}

				dialog.dismiss();
			}
		});
		t.start();
		dialog.show();
	}
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Log.i("XMPPChat", "onCreate called");
        setContentView(R.layout.activity_xmppchat);

        //mRecipient = (EditText) this.findViewById(R.id.recipient);
        Log.i("XMPPChat", "mRecipient = " + mRecipient);
        mSendText = (EditText) this.findViewById(R.id.sendText);
        Log.i("XMPPChat", "mSendText = " + mSendText);
        mList = (ListView) this.findViewById(R.id.listMessages);
        Log.i("XMPPChat", "mList = " + mList);
        setListAdapter();
        
        // Dialog for getting the xmpp settings
        mDialog = new SettingsDialog(this);


        // Set a listener to send a chat text message
        Button send = (Button) this.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	int participantCount = 0;
            	while(MainActivity.mParticipants.size() > participantCount){
            		Participant recip = MainActivity.mParticipants.get(participantCount);
            		mRecipient = recip.getDisplayName();
            		if(!recip.getParticipantId().equals(MainActivity.mMyId)){
            		to = mRecipient;
                String text = mSendText.getText().toString();
                 Log.i("XMPPChat", "Sending text [" + text + "] to [" +  to + "]");
                 
                 
               Message msg = new Message( Rec.get(to), Message.Type.chat);
                msg.setBody(text);
                if (connection != null) {
                	connection.sendPacket(msg);
                	messages.add(connection.getUser() + ":");
                	messages.add(text);
                	setListAdapter();
                }
            		}
                participantCount++;
            	
                }
            }
        });
        connect();
    }

    /**
     * Called by Settings dialog when a connection is establised with the XMPP server
     *
     * @param connection
     */
    public void setConnection
            (XMPPConnection
                    connection) {
        this.connection = connection;
        if (connection != null) {
            // Add a packet listener to get messages sent to us
            PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
            connection.addPacketListener(new PacketListener() {
                public void processPacket(Packet packet) {
                    Message message = (Message) packet;
                    if (message.getBody() != null) {
                        String fromName = StringUtils.parseBareAddress(message.getFrom());
                        Log.i("XMPPChat", "Got text [" + message.getBody() + "] from [" + fromName + "]");
                        messages.add(fromName + ":");
                        messages.add(message.getBody());
                        // Add the incoming message to the list view
                        mHandler.post(new Runnable() {
                            public void run() {
                                setListAdapter();
                            }
                        });
                    }
                }
            }, filter);
        }
    }

    private void setListAdapter
            () {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.multi_line_list_item,
                messages);
        mList.setAdapter(adapter);
    }
    

}
