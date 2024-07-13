package com.example.myfirstapp;
import java.util.Arrays;
import java.util.Random;

import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

public class GameMgr {
	
	public static Mine_obj [] mineObj = new Mine_obj[81];
	static int bomb_arr[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	public static boolean gameOver = false;
	public static boolean lvlComplt = false;
	//public static int openCellCount = 0; 
	
	public static void bomb_gen() {
		Random r_num = new Random();
		int randm;
		
		for(int i=0;i < 10; i++ )
		{
			bomb_arr[i] = -1;
		}
		
		for(int i = 0;i < 10;)
		{
			int j;
			randm = r_num.nextInt(81);
//			if(Arrays.asList(bomb_arr).contains(randm)!=true)
//			{
//				bomb_arr[i] = randm;
//				i++;
//			}
			for(j = 0; j < 10;j++)
			{
				if(randm == bomb_arr[j])
					break;
			}
			if(j == 10)
			{
				bomb_arr[i] = randm;
				i++;
			}
		}
		for(int i = 0 ; i < 81; i++)
		{
			mineObj[i] = new Mine_obj();
		}
		for(int i = 0; i < 10 ; i++)
		{
			mineObj[bomb_arr[i]].isMine = true;
		}
		for(int i:bomb_arr)
			Log.i("Bomb_Array", "Cell num : "+i); 		//number duplication BUG
	}
	
	public static void set_mine_num()
	{
		int count = 0;
		int temp = 0;
		for(int i = 0;i < 81; i++ )			//fix Bug in 81st cell and 1st cell
		{
			if((i % 9) != 8) 
			{
				//Log.d("MineCalculator", "Column 9"+i);
				temp = i+1;
				if(temp>=0 && temp < 81)
				{
					if(GameMgr.mineObj[temp].isMine == true)
						count++;
				}
				temp = i + 10;
				if(temp>=0 && temp < 81)
				{
					if(GameMgr.mineObj[temp].isMine == true)
						count++;
				}
				temp = i - 8;
				if(temp>=0 && temp < 81)
				{
					if(GameMgr.mineObj[temp].isMine == true)
						count++;
				}
			}
			if((i % 9) != 0) 
			{
			//	Log.d("MineCalculator", "Column 0"+i);
				temp = i-1;
				if(temp>=0 && temp < 81)
				{
					if(GameMgr.mineObj[temp].isMine == true)
						count++;
				}
				temp = i - 10;
				if(temp>=0 && temp < 81)
				{
					if(GameMgr.mineObj[temp].isMine == true)
						count++;
				}
				temp = i + 8;
				if(temp>=0 && temp < 81)
				{
					if(GameMgr.mineObj[temp].isMine == true)
						count++;
				}
			}
			temp = i + 9;
			if(temp>=0 && temp < 81)
			{
				if(GameMgr.mineObj[temp].isMine == true)
					count++;
			}
			temp = i -9;
			if(temp>=0 && temp < 81)
			{
				if(GameMgr.mineObj[temp].isMine == true)
					count++;
			}
			
			GameMgr.mineObj[i].adjMines = count;
			count = 0;
		}
	}
	
	public static void set_flag(View view, int pos)
	{
		ImageView img_chg;
		img_chg = (ImageView) view;
		if(GameMgr.mineObj[pos].isOpen)
			return;
		if(GameMgr.mineObj[pos].isFlag == false)
		{
			img_chg.setImageResource(R.drawable.mine_button_flag);
			GameMgr.mineObj[pos].isFlag = true;
		}
		else
		{
			img_chg.setImageResource(R.drawable.mine_button);
			GameMgr.mineObj[pos].isFlag = false;
		}
	}
	
	public static void mine_open(View view, int pos)
	{
		ImageView img_chg;
		img_chg = (ImageView) view;
		if(GameMgr.mineObj[pos].isFlag == true)
		{
			img_chg.setImageResource(R.drawable.mine_button);
			GameMgr.mineObj[pos].isFlag = false;
			return;
		}
		else
		{
			open_cell(view, pos);
		}
		
	}
	
	public static void open_cell(View view, int pos)
	{
		ImageView img_chg;
		img_chg = (ImageView) view;
		
		if(GameMgr.mineObj[pos].isMine == true)
		{
			//img_chg.setImageResource(R.drawable.mine_button_mine);
			 game_over();
			return;
		}
		else
		{
			if(GameMgr.mineObj[pos].isOpen == true)
				return;
			
			switch (GameMgr.mineObj[pos].adjMines)
			{
			case 0:
				img_chg.setImageResource(R.drawable.mine_button_nill);
				break;
			case 1:
				img_chg.setImageResource(R.drawable.mine_button_num1);
				break;
			case 2:
				img_chg.setImageResource(R.drawable.mine_button_num2);
				break;
			case 3:
				img_chg.setImageResource(R.drawable.mine_button_num3);
				break;
			case 4:
				img_chg.setImageResource(R.drawable.mine_button_num4);
				break;
			case 5:
				img_chg.setImageResource(R.drawable.mine_button_num5);
				break;
			case 6:
				img_chg.setImageResource(R.drawable.mine_button_num6);
				break;
			case 7:
				img_chg.setImageResource(R.drawable.mine_button_num7);
				break;
			case 8:
				img_chg.setImageResource(R.drawable.mine_button_num8);
				break;
			default:
				break;
			}
			GameMgr.mineObj[pos].isOpen = true;
			if(GameMgr.mineObj[pos].adjMines == 0)
			{
				GameMgr.mineObj[pos].toOpen = true;
				recursive_open(pos);
				bulk_open();
			}
			return;
		}
	}
	
	public static void set_empty_arr() {
		int temp = 0;
		for(int i = 0;i < 81; i++ )			
		{
			if((i % 9) != 8) 
			{
				Log.d("Set empty arr", "Column 9"+i);
				temp = i+1;
				if(temp>=0 && temp < 81)
				{
					//if(GameMgr.mineObj[temp].adjMines == 0)
						GameMgr.mineObj[i].empty_arr[0] = temp;
				}
				temp = i + 10;
				if(temp>=0 && temp < 81)
				{
					//if(GameMgr.mineObj[temp].adjMines == 0)
						GameMgr.mineObj[i].empty_arr[1] = temp;
				}
				temp = i - 8;
				if(temp>=0 && temp < 81)
				{
					//if(GameMgr.mineObj[temp].adjMines == 0)
						GameMgr.mineObj[i].empty_arr[2] = temp;
				}
			}
			if((i % 9) != 0) 
			{
				Log.d("Set empty array", "Column 0"+i);
				temp = i-1;
				if(temp>=0 && temp < 81)
				{
					//if(GameMgr.mineObj[temp].adjMines == 0)
						GameMgr.mineObj[i].empty_arr[3] = temp;
				}
				temp = i - 10;
				if(temp>=0 && temp < 81)
				{
					//if(GameMgr.mineObj[temp].isMine == true)
					GameMgr.mineObj[i].empty_arr[4] = temp;
				}
				temp = i + 8;
				if(temp>=0 && temp < 81)
				{
					//if(GameMgr.mineObj[temp].isMine == true)
					GameMgr.mineObj[i].empty_arr[5] = temp;
				}
			}
			temp = i + 9;
			if(temp>=0 && temp < 81)
			{
				//if(GameMgr.mineObj[temp].isMine == true)
				GameMgr.mineObj[i].empty_arr[6] = temp;
			}
			temp = i -9;
			if(temp>=0 && temp < 81)
			{
				//if(GameMgr.mineObj[temp].isMine == true)
				GameMgr.mineObj[i].empty_arr[7] = temp;
			}
			
		}
		
	}
	
	public static void recursive_open(int pos)
	{
		Log.d("Recurrsive", "RECURSIVE FUN CALLED"+pos);
		for(int i = 0;i < 8;i++)
		{
			if(GameMgr.mineObj[pos].empty_arr[i] != -1)
			{
				int temp = GameMgr.mineObj[pos].empty_arr[i];
				if(GameMgr.mineObj[temp].isOpen == false && GameMgr.mineObj[temp].toOpen == false)
				{
					GameMgr.mineObj[temp].toOpen = true;
					if(GameMgr.mineObj[temp].adjMines == 0)
						recursive_open(temp);
				}
			}
		}
	}
	
	public static void bulk_open()
	{
		for(int i = 0; i < 81; i++)
		{
			if(GameMgr.mineObj[i].toOpen == true)
			{
				ImageView img_chg = (ImageView) MainActivity.grid_view.getChildAt(i);
				switch (GameMgr.mineObj[i].adjMines)
				{
				case 0:
					img_chg.setImageResource(R.drawable.mine_button_nill);
					break;
				case 1:
					img_chg.setImageResource(R.drawable.mine_button_num1);
					break;
				case 2:
					img_chg.setImageResource(R.drawable.mine_button_num2);
					break;
				case 3:
					img_chg.setImageResource(R.drawable.mine_button_num3);
					break;
				case 4:
					img_chg.setImageResource(R.drawable.mine_button_num4);
					break;
				case 5:
					img_chg.setImageResource(R.drawable.mine_button_num5);
					break;
				case 6:
					img_chg.setImageResource(R.drawable.mine_button_num6);
					break;
				case 7:
					img_chg.setImageResource(R.drawable.mine_button_num7);
					break;
				case 8:
					img_chg.setImageResource(R.drawable.mine_button_num8);
					break;
				default:
					break;
				}
				GameMgr.mineObj[i].toOpen = false;
				GameMgr.mineObj[i].isOpen = true;
			}
		}
	}

	public static void game_over()
	{
		gameOver = true;
		for(int i:bomb_arr)
		{
			if(GameMgr.mineObj[i].isFlag == false)
			{
				ImageView img_chg = (ImageView) MainActivity.grid_view.getChildAt(i);
				img_chg.setImageResource(R.drawable.mine_button_mine);
			}
		}
	}
	
	public static void reset_game()
	{
		for(int i = 0; i < 81; i++)
		{
			
			ImageView img_chg = (ImageView) MainActivity.grid_view.getChildAt(i);
			img_chg.setImageResource(R.drawable.mine_button);
			
			GameMgr.mineObj[i].toOpen = false;
			GameMgr.mineObj[i].isOpen = false;
			GameMgr.mineObj[i].isFlag = false;
			GameMgr.mineObj[i].isMine = false;
			GameMgr.mineObj[i].adjMines = 0;
			//for(int j:GameMgr.mineObj[i].empty_arr)
				//GameMgr.mineObj[i].empty_arr[j] = -1;
		}
		gameOver = false;
		//for(int i:bomb_arr)
			//bomb_arr[i] = -1;
	}
	
	public static void gameStatus()
	{
		for(int i = 0; i < 81 ; i++)
		{
			if(GameMgr.mineObj[i].isMine == true)
				continue;
			else
			{
				if(GameMgr.mineObj[i].isOpen != true)
				{
					lvlComplt = false;
					break;
				}
			}
			lvlComplt = true;
		}
	}
}
