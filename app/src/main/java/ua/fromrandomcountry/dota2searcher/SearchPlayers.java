package ua.fromrandomcountry.dota2searcher;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HttpsURLConnection;

import ua.fromrandomcountry.dota2searcher.api.DotaRequestBuilder;
import ua.fromrandomcountry.dota2searcher.api.JsonParser;
import ua.fromrandomcountry.dota2searcher.api.SingletonRequestQueue;
import ua.fromrandomcountry.dota2searcher.dota.Player;
import ua.fromrandomcountry.dota2searcher.netconnection.HttpParser;

public class SearchPlayers extends AppCompatActivity {

    // Layouts
    private final int PLAYER_LAYOUT_WIDTH = LinearLayout.LayoutParams.MATCH_PARENT;
    private final int PLAYER_LAYOUT_HEIGHT = LinearLayout.LayoutParams.MATCH_PARENT;
    private final int PLAYER_LAYOUT_ORIENTATION = LinearLayout.HORIZONTAL;
    private final String PLAYER_LAYOUT_COLOR = "#212121";
    private final int PLAYER_LAYOUT_VERTICAL_PADDING = 20;

    private final int PLAYER_TEXT_WIDTH = LinearLayout.LayoutParams.WRAP_CONTENT;
    private final int PLAYER_TEXT_HEIGHT = LinearLayout.LayoutParams.MATCH_PARENT;
    private final float PLAYER_TEXT_WEIGHT = 6.0f;
    private final int PLAYER_TEXT_ORIENTATION = LinearLayout.VERTICAL;

    private final int PLAYER_MMR_WIDTH = LinearLayout.LayoutParams.MATCH_PARENT;
    private final int PLAYER_MMR_HEIGHT = LinearLayout.LayoutParams.WRAP_CONTENT;
    private final float PLAYER_MMR_WEIGHT = 2.0f;
    private final int PLAYER_MMR_MARGIN_TOP = 5;
    private final int PLAYER_MMR_MARGIN_BOTTOM = 5;
    private final int PLAYER_MMR_ORIENTATION = LinearLayout.HORIZONTAL;

    private final int PLAYER_SOLOMMR_WIDTH = LinearLayout.LayoutParams.MATCH_PARENT;
    private final int PLAYER_SOLOMMR_HEIGHT = LinearLayout.LayoutParams.MATCH_PARENT;
    private final float PLAYER_SOLOMMR_WEIGHT = 1.0f;
    private final int PLAYER_SOLOMMR_ORIENTATION = LinearLayout.HORIZONTAL;

    private final int PLAYER_GROUPMMR_WIDTH = LinearLayout.LayoutParams.MATCH_PARENT;
    private final int PLAYER_GROUPMMR_HEIGHT = LinearLayout.LayoutParams.MATCH_PARENT;
    private final float PLAYER_GROUPMMR_WEIGHT = 1.0f;
    private final int PLAYER_GROUPMMR_ORIENTATION = LinearLayout.HORIZONTAL;

    // Views
    private final int PLAYER_AVATAR_WIDTH = 100;
    private final int PLAYER_AVATAR_HEIGHT = 100;
    private final float PLAYER_AVATAR_WEIGHT = 1.0f;

    private final int PLAYER_USERNAME_WIDTH = LinearLayout.LayoutParams.MATCH_PARENT;
    private final int PLAYER_USERNAME_HEIGHT = LinearLayout.LayoutParams.WRAP_CONTENT;
    private final int PLAYER_USERNAME_MARGIN_TOP = 5;
    private final float PLAYER_USERNAME_WEIGHT = 1.0f;

    private final int PLAYER_SOLOMMR_TITLE_WIDTH = LinearLayout.LayoutParams.WRAP_CONTENT;
    private final int PLAYER_SOLOMMR_TITLE_HEIGHT = LinearLayout.LayoutParams.WRAP_CONTENT;
    private final float PLAYER_SOLOMMR_TITLE_WEIGHT = 1.0f;

    private final int PLAYER_SOLOMMR_VALUE_WIDTH = LinearLayout.LayoutParams.WRAP_CONTENT;
    private final int PLAYER_SOLOMMR_VALUE_HEIGHT = LinearLayout.LayoutParams.WRAP_CONTENT;
    private final float PLAYER_SOLOMMR_VALUE_WEIGHT = 2.0f;

    private final int PLAYER_GROUPMMR_TITLE_WIDTH = LinearLayout.LayoutParams.WRAP_CONTENT;
    private final int PLAYER_GROUPMMR_TITLE_HEIGHT = LinearLayout.LayoutParams.WRAP_CONTENT;
    private final float PLAYER_GROUPMMR_TITLE_WEIGHT = 1.0f;

    private final int PLAYER_GROUPMMR_VALUE_WIDTH = LinearLayout.LayoutParams.WRAP_CONTENT;
    private final int PLAYER_GROUPMMR_VALUE_HEIGHT = LinearLayout.LayoutParams.WRAP_CONTENT;
    private final float PLAYER_GROUPMMR_VALUE_WEIGHT = 2.0f;

// ==================================================


    // =========================================

    Button btnSearch;
    private ScrollView scrollView;
    LinearLayout scrollViewVerticalLayout;
    EditText searchField;
    private SingletonRequestQueue reqQueue;


    private ArrayList<Player> playersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_players);
        //
        btnSearch = (Button) findViewById(R.id.btnSearch);
        searchField = (EditText) findViewById(R.id.searchField);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollViewVerticalLayout = (LinearLayout) findViewById(R.id.scrollViewVerticalLayout);
        playersList = new ArrayList<Player>();

        btnSearch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlToPlayers = DotaRequestBuilder.buildSearchByName(
                        searchField.getText().toString()
                );

                try {
                    JSONArray players = JsonParser.parseJSONArrayByURL(urlToPlayers);
                    drawPlayersOnScrollView(players);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initPlayerLayoutsParams(int number,
                                         LinearLayout playerLayout,
                                         LinearLayout playerTextLayout,
                                         LinearLayout playerMmrLayout,
                                         LinearLayout playerSoloMmrLayout,
                                         LinearLayout playerGroupMmrLayout) {

        LinearLayout.LayoutParams layoutParams;

        //playerLayout
        layoutParams = new LinearLayout.LayoutParams(
                PLAYER_LAYOUT_WIDTH,
                PLAYER_LAYOUT_HEIGHT
        );
        playerLayout.setLayoutParams(layoutParams);
        playerLayout.setOrientation(PLAYER_LAYOUT_ORIENTATION);
        playerLayout.setPadding(0, PLAYER_LAYOUT_VERTICAL_PADDING, 0, PLAYER_LAYOUT_VERTICAL_PADDING);
        if (number % 2 == 0) {
            playerLayout.setBackgroundColor(getResources().getColor(R.color.colorPlayerViewEven));
        }
        else {
            playerLayout.setBackgroundColor(getResources().getColor(R.color.colorPlayerViewOdd));
        }

        //playerTextLayout
        layoutParams = new LinearLayout.LayoutParams(
                PLAYER_TEXT_WIDTH,
                PLAYER_TEXT_HEIGHT,
                PLAYER_TEXT_WEIGHT
        );
        playerTextLayout.setLayoutParams(layoutParams);
        playerTextLayout.setOrientation(PLAYER_TEXT_ORIENTATION);

        //playerMmrLayout
        layoutParams = new LinearLayout.LayoutParams(
                PLAYER_MMR_WIDTH,
                PLAYER_MMR_HEIGHT,
                PLAYER_MMR_WEIGHT
        );
        layoutParams.topMargin = PLAYER_MMR_MARGIN_TOP;
        layoutParams.bottomMargin = PLAYER_MMR_MARGIN_BOTTOM;
        playerMmrLayout.setLayoutParams(layoutParams);
        playerMmrLayout.setOrientation(PLAYER_MMR_ORIENTATION);

        //playerSoloMmrLayout
        layoutParams = new LinearLayout.LayoutParams(
                PLAYER_SOLOMMR_WIDTH,
                PLAYER_SOLOMMR_HEIGHT,
                PLAYER_SOLOMMR_WEIGHT
        );
        playerSoloMmrLayout.setLayoutParams(layoutParams);
        playerSoloMmrLayout.setOrientation(PLAYER_SOLOMMR_ORIENTATION);

        //playerGroupMmrLayout
        layoutParams = new LinearLayout.LayoutParams(
                PLAYER_GROUPMMR_WIDTH,
                PLAYER_GROUPMMR_HEIGHT,
                PLAYER_GROUPMMR_WEIGHT
        );
        playerGroupMmrLayout.setLayoutParams(layoutParams);
        playerGroupMmrLayout.setOrientation(PLAYER_GROUPMMR_ORIENTATION);
    }

    private void initPlayerViewsParams(ImageView playerAvatar,
                                       TextView playerUsername,
                                       TextView playerSoloMmrValue,
                                       TextView playerSoloMmrTitle,
                                       TextView playerGroupMmrValue,
                                       TextView playerGroupMmrTitle) {
        LinearLayout.LayoutParams layoutParams;

        // Avatar
        layoutParams = new LinearLayout.LayoutParams(
                PLAYER_AVATAR_WIDTH,
                PLAYER_AVATAR_HEIGHT,
                PLAYER_AVATAR_WEIGHT
        );
        playerAvatar.setLayoutParams(layoutParams);

        // Username
        layoutParams = new LinearLayout.LayoutParams(
                PLAYER_USERNAME_WIDTH,
                PLAYER_USERNAME_HEIGHT,
                PLAYER_USERNAME_WEIGHT
        );
        layoutParams.topMargin = PLAYER_USERNAME_MARGIN_TOP;
        playerUsername.setLayoutParams(layoutParams);

        // Solo MMR
        layoutParams = new LinearLayout.LayoutParams(
                PLAYER_SOLOMMR_VALUE_WIDTH,
                PLAYER_SOLOMMR_VALUE_HEIGHT,
                PLAYER_SOLOMMR_VALUE_WEIGHT
        );
        playerSoloMmrValue.setLayoutParams(layoutParams);

        // Solo MMR TITLE
        layoutParams = new LinearLayout.LayoutParams(
                PLAYER_SOLOMMR_TITLE_WIDTH,
                PLAYER_SOLOMMR_TITLE_HEIGHT,
                PLAYER_SOLOMMR_TITLE_WEIGHT
        );
        playerSoloMmrTitle.setLayoutParams(layoutParams);

        // Group MMR
        layoutParams = new LinearLayout.LayoutParams(
                PLAYER_GROUPMMR_VALUE_WIDTH,
                PLAYER_GROUPMMR_VALUE_HEIGHT,
                PLAYER_GROUPMMR_VALUE_WEIGHT
        );
        playerGroupMmrValue.setLayoutParams(layoutParams);

        // GROUP MMR TITLE
        layoutParams = new LinearLayout.LayoutParams(
                PLAYER_GROUPMMR_TITLE_WIDTH,
                PLAYER_GROUPMMR_TITLE_HEIGHT,
                PLAYER_GROUPMMR_TITLE_WEIGHT
        );
        playerGroupMmrTitle.setLayoutParams(layoutParams);


    }

    private void drawPlayersOnScrollView(JSONArray players) throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        LinearLayout playerLayout,
                     playerTextLayout,
                     playerMmrLayout,
                     playerSoloMmrLayout,
                     playerGroupMmrLayout;
        ImageView playerAvatar;

        TextView playerUsername,
                playerSoloMmrValue,
                playerSoloMmrTitle,
                playerGroupMmrValue,
                playerGroupMmrTitle;
        System.out.println("======================");
        System.out.println(players.length());
        System.out.println("======================");
        playersList.clear();
        for (int id = 0; id < players.length(); id++) {
            JSONObject playerJson;
            boolean requestAgain;
            String requestUrl = DotaRequestBuilder.buildPlayerInfoById(
                    players.getJSONObject(id)
                            .getLong("account_id")
            );
            do {
                playerJson = JsonParser.parseJSONObjectByURL(requestUrl);
                requestAgain = playerJson.has("error") && playerJson.getString("error").equals("rate limit exceeded");
                if (requestAgain) {
                    TimeUnit.MILLISECONDS.sleep(10);
                }
            } while (requestAgain);

            playersList.add(
                    new Player(playerJson)
            );

            playerLayout = new LinearLayout(SearchPlayers.this);
            playerTextLayout = new LinearLayout(SearchPlayers.this);
            playerMmrLayout = new LinearLayout(SearchPlayers.this);
            playerSoloMmrLayout = new LinearLayout(SearchPlayers.this);
            playerGroupMmrLayout = new LinearLayout(SearchPlayers.this);

            initPlayerLayoutsParams(id, playerLayout, playerTextLayout, playerMmrLayout, playerSoloMmrLayout, playerGroupMmrLayout);

            playerAvatar = new ImageView(SearchPlayers.this);
            playerUsername = new TextView(SearchPlayers.this);
            playerSoloMmrValue = new TextView(SearchPlayers.this);
            playerSoloMmrTitle = new TextView(SearchPlayers.this);
            playerGroupMmrValue = new TextView(SearchPlayers.this);
            playerGroupMmrTitle = new TextView(SearchPlayers.this);

            initPlayerViewsParams(playerAvatar, playerUsername, playerSoloMmrValue, playerSoloMmrTitle, playerGroupMmrValue, playerGroupMmrTitle);
            // Username Block
            playerUsername.setText(
                    playersList.get(id).getUsername()
            );

            // Solo MMR Block
            playerSoloMmrTitle.setText(
                    "Solo MMR:"
            );
            playerSoloMmrValue.setText(
                    playersList.get(id).getSoloRank()
            );

            // Group MMR Block

            playerGroupMmrTitle.setText(
                    "Group MMR:"
            );
            playerGroupMmrValue.setText(
                    playersList.get(id).getGroupRank()
            );

            // Avatar Block
            Picasso.get().load(
                    playersList.get(id).getAvatarUrl()
            ).into(playerAvatar);

            playerSoloMmrLayout.addView(playerSoloMmrTitle);
            playerSoloMmrLayout.addView(playerSoloMmrValue);

            playerGroupMmrLayout.addView(playerGroupMmrTitle);
            playerGroupMmrLayout.addView(playerGroupMmrValue);

            playerMmrLayout.addView(playerSoloMmrLayout);
            playerMmrLayout.addView(playerGroupMmrLayout);

            playerTextLayout.addView(playerUsername);
            playerTextLayout.addView(playerMmrLayout);

            playerLayout.addView(playerAvatar);
            playerLayout.addView(playerTextLayout);

            scrollViewVerticalLayout.addView(playerLayout);
        }
    }
}
