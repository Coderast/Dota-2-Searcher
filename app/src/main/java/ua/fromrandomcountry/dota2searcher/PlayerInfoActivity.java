package ua.fromrandomcountry.dota2searcher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PlayerInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);
        //
        Intent intent = getIntent();
        Long accountId = intent.getLongExtra("account_id", 0);




    }
}
