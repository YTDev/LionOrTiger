package com.example.lionortiger;

import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    private Button btnReset;
    private GridLayout gridLayout;
    enum Player {
        ONE, TWO, NO
    }

    Player currentPlayer = Player.ONE;
    Player[] playerChoices = new Player[9];
    int[][] winnerRowsColumns = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        btnReset = findViewById(R.id.btnReset);
        gridLayout=findViewById(R.id.gridLayout);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.animate().rotation(3600).setDuration(1000);

            }
        });
        for (int i = 0; i < 9; i++) {
            playerChoices[i] = Player.NO;
        }

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    public void imageViewIsTapped(View imageView) {


        ImageView tappedImageView = (ImageView) imageView;
        int tiTag = Integer.parseInt(imageView.getTag().toString());
        if (playerChoices[tiTag] == Player.NO && gameOver == false) {
            tappedImageView.setTranslationX(-20);
tappedImageView.setRotation(0);

            playerChoices[tiTag] = currentPlayer;
            if (currentPlayer == Player.ONE) {
                tappedImageView.setImageResource(R.drawable.lion);
                currentPlayer = Player.TWO;
            } else if (currentPlayer == Player.TWO) {
                tappedImageView.setImageResource(R.drawable.tiger);
                currentPlayer = Player.ONE;
            }


            tappedImageView.animate().translationXBy(20).alpha(1).rotation(3600).setDuration(7000);

            for (int[] winningPosition : winnerRowsColumns) {
                if (playerChoices[winningPosition[0]] == playerChoices[winningPosition[1]] &&
                        playerChoices[winningPosition[1]] == playerChoices[winningPosition[2]]
                        && playerChoices[winningPosition[0]] != Player.NO &&
                        playerChoices[winningPosition[1]] != Player.NO &&
                        playerChoices[winningPosition[2]] != Player.NO) {
                    gameOver = true;
                    btnReset.setVisibility(View.VISIBLE);
                    if (currentPlayer == Player.ONE)
                        Toast.makeText(this, "Player two is the winner", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "Player one is the winner", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    private void resetGame() {
        for (int i = 0; i < 9; i++) {
            playerChoices[i] = Player.NO;
        }

        gameOver = false;

        currentPlayer = Player.ONE;


        for(int i=0;i<gridLayout.getChildCount();i++){
            ImageView imageView=(ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.1f);
            imageView.setRotation(0);
        }
        btnReset.setVisibility(View.GONE);
    }
}
