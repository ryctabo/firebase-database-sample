package com.github.ryctabo.firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The <strong>MainActivity</strong> class contains all
 * events to manage the resource activity_main.xml.
 *
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
public class MainActivity extends AppCompatActivity implements ValueEventListener {

    private static final String TAG = MainActivity.class.getName();

    @BindView(R.id.text_message)
    TextView textMessage;

    @BindView(R.id.edit_message)
    EditText editMessage;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    DatabaseReference messageRef = ref.child("message");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.messageRef.addValueEventListener(this);
    }

    /**
     * Handler to manage the event of "UPDATE" button.
     *
     * @param view button view
     */
    public void onClickUpdate(View view) {
        String message = this.editMessage.getText().toString();
        messageRef.setValue(message);
        this.editMessage.setText(null);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String message = dataSnapshot.getValue(String.class);
        this.textMessage.setText(message);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e(TAG, databaseError.getMessage());
    }
}
