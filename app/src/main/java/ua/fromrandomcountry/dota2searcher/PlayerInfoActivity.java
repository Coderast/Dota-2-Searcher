package ua.fromrandomcountry.dota2searcher;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.security.InvalidParameterException;

import ua.fromrandomcountry.dota2searcher.api.DotaRequestBuilder;
import ua.fromrandomcountry.dota2searcher.api.JsonParser;
import ua.fromrandomcountry.dota2searcher.dota.Player;

public class PlayerInfoActivity extends AppCompatActivity {

    ImageView viewAvatar;
    TextView viewUsername;
    TextView viewSoloRank;
    TextView viewGroupRank;
    TextView viewCheese;
    TextView viewWins;
    TextView viewLoses;
    TextView viewWinsPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);
        //
        viewAvatar = (ImageView) findViewById(R.id.infoAvatar);
        viewUsername = (TextView) findViewById(R.id.infoUsername);
        viewSoloRank = (TextView) findViewById(R.id.infoSoloRankValue);
        viewGroupRank = (TextView) findViewById(R.id.infoGroupRankValue);
        viewCheese = (TextView) findViewById(R.id.infoCheeseCount);
        viewWins = (TextView) findViewById(R.id.infoWinsCount);
        viewLoses = (TextView) findViewById(R.id.infoLosesCount);
        viewWinsPercent = (TextView) findViewById(R.id.infoWinRateCount);
        //

        Intent intent = getIntent();
        Long accountId = intent.getLongExtra("account_id", -1);

        if (accountId == -1) {
            throw new InvalidParameterException("Hasn't account id!");
        }
        try {
            Player player = new Player(JsonParser.parseJSONObjectByURL(
                    DotaRequestBuilder.buildPlayerInfoById(accountId)
            ));
            drawPlayer(player);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawPlayer(Player player) {
        viewUsername.setText(player.getUsername());
        viewSoloRank.setText(player.getSoloRank());
        viewGroupRank.setText(player.getGroupRank());
        viewCheese.setText(player.getCheeseCount());
        viewWins.setText(player.getWinCount());
        viewLoses.setText(player.getLoseCount());
        double wins = Double.parseDouble(player.getWinCount());
        double loses = Double.parseDouble(player.getLoseCount());
        viewWinsPercent.setText(
            String.format(
                    "%.2f",
                    wins
                    / (wins+loses)
                    * 100
            )
        );
    }
}
