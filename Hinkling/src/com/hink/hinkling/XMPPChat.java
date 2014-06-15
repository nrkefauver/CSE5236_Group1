package com.hink.hinkling;

import java.util.ArrayList;
import java.util.Collection;

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

public class XMPPChat extends Activity implements OnClickListener {

    public static final String HOST = "talk.google.com";
    public static final int PORT = 5222;
    public static final String SERVICE = "gmail.com";
    public static final String USERNAME = "sammoney13@gmail.com";
    public static final String PASSWORD = "Monkey13!";

    private ArrayList<String> messages = new ArrayList<String>();
    private Handler mHandler = new Handler();
    private SettingsDialog mDialog;
    private EditText mRecipient;
    private EditText mSendText;
    private ListView mList;
    private XMPPConnection connection;

    /**
     * Called with the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Log.i("XMPPChat", "onCreate called");
        this.setContentView(R.layout.activity_xmppchat);

        this.mRecipient = (EditText) this.findViewById(R.id.recipient);
        Log.i("XMPPChat", "mRecipient = " + this.mRecipient);
        this.mSendText = (EditText) this.findViewById(R.id.sendText);
        Log.i("XMPPChat", "mSendText = " + this.mSendText);
        this.mList = (ListView) this.findViewById(R.id.listMessages);
        Log.i("XMPPChat", "mList = " + this.mList);
        this.setListAdapter();

        // Dialog for getting the xmpp settings
        this.mDialog = new SettingsDialog(this);

        // Set a listener to show the settings dialog
        Button setup = (Button) this.findViewById(R.id.setup);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XMPPChat.this.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        XMPPChat.this.mDialog.show();
                    }
                });
            }
        });

        // Set a listener to send a chat text message
        Button send = (Button) this.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to = XMPPChat.this.mRecipient.getText().toString();
                String text = XMPPChat.this.mSendText.getText().toString();
                Log.i("XMPPChat", "Sending text [" + text + "] to [" + to + "]");
                Message msg = new Message(to, Message.Type.chat);
                msg.setBody(text);
                if (XMPPChat.this.connection != null) {
                    XMPPChat.this.connection.sendPacket(msg);
                    XMPPChat.this.messages.add(XMPPChat.this.connection
                            .getUser() + ":");
                    XMPPChat.this.messages.add(text);
                    XMPPChat.this.setListAdapter();
                }
            }
        });
        this.connect();
    }

    /**
     * Called by Settings dialog when a connection is establised with the XMPP
     * server
     * 
     * @param connection
     */
    public void setConnection(XMPPConnection connection) {
        this.connection = connection;
        if (connection != null) {
            // Add a packet listener to get messages sent to us
            PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
            connection.addPacketListener(new PacketListener() {
                @Override
                public void processPacket(Packet packet) {
                    Message message = (Message) packet;
                    if (message.getBody() != null) {
                        String fromName = StringUtils.parseBareAddress(message
                                .getFrom());
                        Log.i("XMPPChat", "Got text [" + message.getBody()
                                + "] from [" + fromName + "]");
                        XMPPChat.this.messages.add(fromName + ":");
                        XMPPChat.this.messages.add(message.getBody());
                        // Add the incoming message to the list view
                        XMPPChat.this.mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                XMPPChat.this.setListAdapter();
                            }
                        });
                    }
                }
            }, filter);
        }
    }

    private void setListAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.multi_line_list_item, this.messages);
        this.mList.setAdapter(adapter);
    }

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
                    Log.i("XMPPChat", "Connected to " + connection.getHost());
                } catch (XMPPException ex) {
                    Log.e("XMPPChat",
                            "Failed to connect to " + connection.getHost());
                    Log.e("XMPPChat", ex.toString());
                    XMPPChat.this.setConnection(null);
                }
                try {
                    // SASLAuthentication.supportSASLMechanism("PLAIN", 0);
                    connection.login(USERNAME, PASSWORD);
                    Log.i("XMPPChat", "Logged in as " + connection.getUser());

                    // Set the status to available
                    Presence presence = new Presence(Presence.Type.available);
                    connection.sendPacket(presence);
                    XMPPChat.this.setConnection(connection);

                    Roster roster = connection.getRoster();
                    Collection<RosterEntry> entries = roster.getEntries();
                    for (RosterEntry entry : entries) {
                        Log.d("XMPPChat",
                                "--------------------------------------");
                        Log.d("XMPPChat", "RosterEntry " + entry);
                        Log.d("XMPPChat", "User: " + entry.getUser());
                        Log.d("XMPPChat", "Name: " + entry.getName());
                        Log.d("XMPPChat", "Status: " + entry.getStatus());
                        Log.d("XMPPChat", "Type: " + entry.getType());
                        Presence entryPresence = roster.getPresence(entry
                                .getUser());

                        Log.d("XMPPChat",
                                "Presence Status: " + entryPresence.getStatus());
                        Log.d("XMPPChat",
                                "Presence Type: " + entryPresence.getType());
                        Presence.Type type = entryPresence.getType();
                        if (type == Presence.Type.available) {
                            Log.d("XMPPChat", "Presence AVIALABLE");
                        }
                        Log.d("XMPPChat", "Presence : " + entryPresence);

                    }
                } catch (XMPPException ex) {
                    Log.e("XMPPChat", "Failed to log in as " + USERNAME);
                    Log.e("XMPPChat", ex.toString());
                    XMPPChat.this.setConnection(null);
                }

                dialog.dismiss();
            }
        });
        t.start();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }
}
