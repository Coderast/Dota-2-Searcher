package ua.fromrandomcountry.dota2searcher;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.security.InvalidParameterException;

import ua.fromrandomcountry.dota2searcher.api.DotaRequestBuilder;
import ua.fromrandomcountry.dota2searcher.api.JsonParser;
import ua.fromrandomcountry.dota2searcher.dota.Match;
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

    TextView viewMatchId;
    TextView viewTeamWinner;
    TextView viewHero;
    TextView viewKda;
    TextView viewXpPerMin;
    TextView viewGoldPerMin;
    TextView viewHeroDamage;
    TextView viewTowerDamage;
    TextView viewHeroHeal;
    TextView viewLastHitsCount;

    Button btnProfileLink;


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

        viewMatchId = (TextView) findViewById(R.id.infoMatchIdValue);
        viewTeamWinner = (TextView) findViewById(R.id.infoTeamWinnerValue);
        viewHero = (TextView) findViewById(R.id.infoHeroValue);
        viewKda = (TextView) findViewById(R.id.infoKDAValue);
        viewXpPerMin = (TextView) findViewById(R.id.infoXpPerMinValue);
        viewGoldPerMin = (TextView) findViewById(R.id.infoGoldPerMinValue);
        viewHeroDamage = (TextView) findViewById(R.id.infoHeroDamageValue);
        viewTowerDamage = (TextView) findViewById(R.id.infoTowerDamageValue);
        viewHeroHeal = (TextView) findViewById(R.id.infoHeroHealValue);
        viewLastHitsCount = (TextView) findViewById(R.id.infoLastHitsValue);

        btnProfileLink = (Button) findViewById(R.id.infoProfileLink);
        //



        Intent intent = getIntent();
        Long accountId = intent.getLongExtra("account_id", -1);

        if (accountId == -1) {
            throw new InvalidParameterException("Hasn't account id!");
        }
        try {
            final Player player = new Player(JsonParser.parseJSONObjectByURL(
                    DotaRequestBuilder.buildPlayerInfoById(accountId)
            ));
            drawPlayer(player);

            btnProfileLink.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(player.getProfileUrl()));
                    startActivity(browserIntent);
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawPlayer(Player player) {

        Picasso.get().load(
                player.getAvatarUrl()
        ).into(viewAvatar);
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

        Match lastMatch = player.getLastMatch();

        viewMatchId.setText(String.valueOf(lastMatch.getMatchId()));
        viewTeamWinner.setText(lastMatch.isRadiantWin() ? "Radiant" : "Dire");
        viewHero.setText(lastMatch.getHeroName());
        viewKda.setText(
                String.format(
                        "%d / %d / %d",
                        lastMatch.getKills(),
                        lastMatch.getDeaths(),
                        lastMatch.getAssists()
                )
        );
        viewXpPerMin.setText(String.valueOf(
                lastMatch.getXpPerMin()
        ));
        viewGoldPerMin.setText(String.valueOf(
                lastMatch.getGoldPerMin()
        ));
        viewHeroDamage.setText(String.valueOf(
                lastMatch.getHeroDamage()
        ));
        viewTowerDamage.setText(String.valueOf(
                lastMatch.getTowerDamage()
        ));
        viewHeroHeal.setText(String.valueOf(
                lastMatch.getHeroHealing()
        ));
        viewLastHitsCount.setText(String.valueOf(
                lastMatch.getLastHits()
        ));

    }
}
