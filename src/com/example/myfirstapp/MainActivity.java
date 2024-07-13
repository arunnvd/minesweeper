package com.example.myfirstapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	public static GridView grid_view;
	boolean long_press = false;
	boolean first_open = true;
	public static int scrWidth;
	public static int scrHeight;
	boolean msgDisplayed = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		scrWidth = size.x;
		scrHeight = size.y;
		grid_view = (GridView) findViewById(R.id.gridview);
		grid_view.setAdapter(new ImageAdapter(this));
		grid_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				
				if(GameMgr.gameOver == true)
				{
					Toast.makeText(MainActivity.this, "Setting Bombs, Starting NewGame", Toast.LENGTH_LONG).show();
					GameMgr.reset_game();
					first_open = true;
					return;
				}
				if(long_press == true)
				{
					GameMgr.set_flag(arg1, arg2);
					long_press = false;
				}
				else
				{
					if(first_open == true)
					{
						while(true)
						{
							GameMgr.bomb_gen();
							GameMgr.set_mine_num();
							if((GameMgr.mineObj[arg2].isMine == false) && (GameMgr.mineObj[arg2].adjMines ==0))
								break;
						}
							
						GameMgr.set_empty_arr();
						first_open = false;
					}
					GameMgr.mine_open(arg1, arg2);
					GameMgr.gameStatus();
					if(GameMgr.lvlComplt == true)
						game_ovr_alert(false);
					if(GameMgr.gameOver == true)
					{
						//Toast.makeText(MainActivity.this, "Oooooooooopss!!!\n PLAYER DIED!!!!", Toast.LENGTH_LONG).show();
						// ToDo : Code for game over screen	
						game_ovr_alert(true);
					}
				}
				
			}
		});
		grid_view.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if(GameMgr.gameOver == true || first_open ==true)
				{
					//GameMgr.reset_game();
					return false;
				}
				Vibrator flagVibe = (Vibrator)MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
				flagVibe.vibrate(40);
				long_press = true;
				return false;
			}
		});
		
	}
	public void game_ovr_alert(boolean isGameOvr)
	{
		AlertDialog.Builder game_ovr_alert = new AlertDialog.Builder(this);
		if(isGameOvr)
		{
			game_ovr_alert.setMessage("Oooooooooopss!!!\n PLAYER DIED!!!!");
			game_ovr_alert.setCancelable(true);
		}
		else
		{
			game_ovr_alert.setMessage("Congratulations, You WON the Game");
			game_ovr_alert.setCancelable(false);
		}
		
		game_ovr_alert.setIcon(R.drawable.ic_launcher);
		game_ovr_alert.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				GameMgr.reset_game();
				first_open = true;
			}
		});
		game_ovr_alert.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
				GameMgr.reset_game();
				first_open = true;
			}
		});
		AlertDialog alertDialog = game_ovr_alert.create();
		alertDialog.show();
	}
	

}
