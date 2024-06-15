package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int PLAYER1 = 0;
    private static final int PLAYER2 = 1;
    private static final int EMPTY = 2;

    private int activePlayer = PLAYER1;
    private int[][] winPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Horizontal lines
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Vertical lines
            {0, 4, 8}, {2, 4, 6}             // Diagonal lines
    };
    private int[] gameState = {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY};
    private boolean gameActive = true;
    private int moveCount = 0;

    public void playerTap(View view) {
        if (gameActive) {
            ImageView img = (ImageView) view;
            int tag = Integer.parseInt(img.getTag().toString());

            if (gameState[tag] == EMPTY) {
                gameState[tag] = activePlayer;
                moveCount++;

                img.setImageResource(activePlayer == PLAYER1 ? R.drawable.ze : R.drawable.cr);
                img.setTranslationY(-1500);
                img.animate().translationYBy(1500).setDuration(500);

                if (checkWinner()) {
                    gameActive = false;
                    String winner = activePlayer == PLAYER1 ? "PLAYER2" : "PLAYER1";
                    Toast.makeText(this, winner + " won", Toast.LENGTH_SHORT).show();
                } else if (moveCount == 9) {
                    gameActive = false;
                    Toast.makeText(this, "It's a tie", Toast.LENGTH_SHORT).show();
                } else {
                    activePlayer = (activePlayer == PLAYER1) ? PLAYER2 : PLAYER1;
                }
            }
        }
    }

    private boolean checkWinner() {
        for (int i = 0; i < winPositions.length; i++) {
            int[] winPosition = winPositions[i];
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != EMPTY) {

                // Show the winning line
                showWinningLine(i);
                return true;
            }
        }
        return false;
    }

    private void showWinningLine(int winPositionIndex) {
        ImageView line;
        switch (winPositionIndex) {
            case 0:
                line = findViewById(R.id.line1);
                break;
            case 1:
                line = findViewById(R.id.line2);
                break;
            case 2:
                line = findViewById(R.id.line3);
                break;
            case 3:
                line = findViewById(R.id.line4);
                break;
            case 4:
                line = findViewById(R.id.line5);
                break;
            case 5:
                line = findViewById(R.id.line6);
                break;
            case 6:
                line = findViewById(R.id.dig1);
                break;
            case 7:
                line = findViewById(R.id.dig2);
                break;
            default:
                return;
        }
        line.setVisibility(View.VISIBLE);
    }

    public void resetGame(View view) {
        activePlayer = PLAYER1;
        moveCount = 0;
        gameActive = true;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = EMPTY;
        }
        ((ImageView) findViewById(R.id.img1)).setImageResource(0);
        ((ImageView) findViewById(R.id.img2)).setImageResource(0);
        ((ImageView) findViewById(R.id.img3)).setImageResource(0);
        ((ImageView) findViewById(R.id.img4)).setImageResource(0);
        ((ImageView) findViewById(R.id.img5)).setImageResource(0);
        ((ImageView) findViewById(R.id.img6)).setImageResource(0);
        ((ImageView) findViewById(R.id.img7)).setImageResource(0);
        ((ImageView) findViewById(R.id.img8)).setImageResource(0);
        ((ImageView) findViewById(R.id.img9)).setImageResource(0);

        findViewById(R.id.line1).setVisibility(View.GONE);
        findViewById(R.id.line2).setVisibility(View.GONE);
        findViewById(R.id.line3).setVisibility(View.GONE);
        findViewById(R.id.line4).setVisibility(View.GONE);
        findViewById(R.id.line5).setVisibility(View.GONE);
        findViewById(R.id.line6).setVisibility(View.GONE);
        findViewById(R.id.dig1).setVisibility(View.GONE);
        findViewById(R.id.dig2).setVisibility(View.GONE);

        Toast.makeText(this, "Game Reset", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView resetTextView = findViewById(R.id.restart);
        resetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame(v);
            }
        });
    }
}
