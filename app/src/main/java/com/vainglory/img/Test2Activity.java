package com.vainglory.img;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import org.json.*;
import java.text.*;
import android.os.*;
import android.content.*;
import android.util.*;
import java.io.*;
import java.util.*;
import javax.net.ssl.*;

public class Test2Activity extends Activity
{
	private ImageView mSourImage;
	private ImageView mWartermarkImage;
    private Button button1;
	private Button button2;
	private EditText editText1;
	private EditText editText2;
	private EditText editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
		if (android.os.Build.VERSION.SDK_INT > 9)
		{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
        button1 = (Button) findViewById(R.id.contenttest2Button1Create);
		button2 = (Button) findViewById(R.id.contenttest2Button1save);
		editText1 = (EditText) findViewById(R.id.contenttest2EditText1id);
		editText2 = (EditText) findViewById(R.id.contenttest2EditText1page);
		editText3 = (EditText) findViewById(R.id.contenttest2EditText1path);
        initView();

		button1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					if (String.valueOf(editText1.getText()).equals("") != true && String.valueOf(editText2.getText()).equals("") != true)
					{
					    Toast.makeText(Test2Activity.this, "Started", Toast.LENGTH_SHORT).show();
					    boolean result = start_struct_img(String.valueOf(editText1.getText()), String.valueOf(editText2.getText()));
						Toast.makeText(Test2Activity.this, String.valueOf(result), Toast.LENGTH_SHORT).show();

					}
					else
					{
						Toast.makeText(Test2Activity.this, "Must not be empty", Toast.LENGTH_SHORT).show();
					}
				}
			});

		button2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					if (String.valueOf(editText3.getText()).equals("") != true)
					{

					    Toast.makeText(Test2Activity.this, "Saved", Toast.LENGTH_SHORT).show();
						mWartermarkImage = (ImageView) findViewById(R.id.contenttest2ImageView1);
						mWartermarkImage.setDrawingCacheEnabled(true);
						Bitmap bitmap = Bitmap.createBitmap(mWartermarkImage.getDrawingCache());
						mWartermarkImage.setDrawingCacheEnabled(false);
						try
						{
							Util.saveBitmap(bitmap, String.valueOf(editText3.getText()));
						}
						catch (IOException e)
						{}

					}
					else
					{
						Toast.makeText(Test2Activity.this, "Must not be empty", Toast.LENGTH_SHORT).show();
					}
				}
			});

    }

    private void initView()
	{


    	mWartermarkImage = (ImageView) findViewById(R.id.contenttest2ImageView1);


    	Bitmap sourBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ground_match_history);



    	mWartermarkImage.setImageBitmap(sourBitmap);
    }

	public Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			switch (msg.what)
			{
				case 0:
					//完成主界面更新,拿到数据
					Bitmap data = (Bitmap)msg.obj;
					mWartermarkImage.setImageBitmap(data);

					break;
				default:
					break;
			}
		}

	};
	
	
	
	public boolean start_struct_img(final String player_name, final String page)
	{
		
		
		final Context context = getApplicationContext();
		new Thread(new Runnable(){

				@Override
				public void run()
				{
        Bitmap ground_bitmap = Util.get_item_bitmap(context, "ground_match_history");
    	Bitmap win_bitmap = Util.get_item_bitmap(context, "win");
		Bitmap loss_bitmap = Util.get_item_bitmap(context, "loss");
		Bitmap title_bitmap = Util.get_item_bitmap(context, "title");
		ground_bitmap = ImageUtil.createWaterMaskLeftTop(context, ground_bitmap, title_bitmap, 0, 10);
		
		String game_mode = "";
		
	try
					{
						Util.trustAllHttpsCertificates();
						HttpsURLConnection.setDefaultHostnameVerifier(Util.hv);
					}
					catch (Exception e)
					{}
		String message = Util.getUrlContent("https://45.77.188.201/matches/" + player_name + "?gameMode=" + game_mode + "&limit=5&page=" + page + "&season=");
		if (message.equals("[]"))
		{
			return ;
		}
		else
		{
			int kills = 0;
			int deaths = 0;
			int assists =0;
			//int kda=0;
			float gold =0;
			int minionkills = 0;
			int game_type = 1;
			String players = null;
			String winners_xml = "";
			String losers_xml = "";
			String gamemode =null;
			String player_region = null;
			String createdate = null;
			String duration = null;
			String hero = null;
			boolean winner = false;
			JSONArray json = null;
			JSONArray lefts_list =null;
			JSONArray rights_list = null;
			JSONArray player_list=null;
			JSONArray items_list = null;
			//String tier = null

			DecimalFormat df = new DecimalFormat("0.0");		
			StringBuilder left_list = new StringBuilder("");
			StringBuilder right_list = new StringBuilder("");
			try
			{

				json = new JSONArray(message);
				int data_start_position = 130;
				int struct_start_position = 125;
				int items_start_position = 200;
				int hero_start_position = 145;
				int date_start_position = 180;
				for (int time = 0;time < 5;time++)
				{
					
					JSONObject match = json.getJSONObject(time);
					player_list = match.getJSONArray("players");
					gamemode = match.getString("gameMode").replace(" ", "_");
					gamemode = Util.match_translate(gamemode);
					player_region = match.getString("shardId");
					duration = String.valueOf((int)match.getInt("duration") / 60);

					String createdAt = match.getString("createdAt").replaceAll("[A-Z]", " ");
					try
					{
						SimpleDateFormat lsdStrFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date st = lsdStrFormat.parse(createdAt);
						long datelong = st.getTime();
						datelong = datelong + 28800000;
						createdate = new SimpleDateFormat("yyyy-MM-dd_HH:mm").format(new Date(datelong)).replaceAll("20[0-9]*-", "");

					}
					catch (ParseException e)
					{
						Log.d("Shit",e.toString());
					}


					
					for (int time_1 = 0;time_1 < player_list.length();time_1++)
					{
						
						JSONObject player=new JSONObject(player_list.getString(time_1));
						if (player.getBoolean("me"))
						{

							kills = player.getInt("kills");

							deaths = player.getInt("deaths");
							assists = player.getInt("assists");

							hero = player.getString("hero").toLowerCase();

							winner = player.getBoolean("winner");
							items_list = player.getJSONArray("items");
							minionkills = player.getInt("minionKills");
							gold = player.getInt("gold");
							gold = Float.parseFloat(df.format((gold / 1000)));
							if (winner)
							{
								Log.d("Fuck",String.valueOf(struct_start_position));
								ground_bitmap = ImageUtil.createWaterMaskLeftTop(context, ground_bitmap, win_bitmap, 0, struct_start_position);
								
							}
							else
							{
								Log.d("Fuck",String.valueOf(struct_start_position));
								
								ground_bitmap = ImageUtil.createWaterMaskLeftTop(context, ground_bitmap, loss_bitmap, 0, struct_start_position);

							}
							ground_bitmap = ImageUtil.drawTextToLeftTop(context, ImageUtil.drawTextToLeftTop(context, ground_bitmap, String.valueOf(gold)+"k", 40, Color.rgb(193, 210, 240), 550, data_start_position), String.valueOf(minionkills), 40, Color.rgb(193, 210, 240), 730, data_start_position);
							ground_bitmap = ImageUtil.drawTextToLeftTop(context, ground_bitmap, gamemode+" "+duration+"分钟", 40, Color.rgb(193, 210, 240), 0, data_start_position);
							ground_bitmap = ImageUtil.drawTextToLeftTop(context, ground_bitmap,createdate , 40, Color.rgb(193, 210, 240), 0, date_start_position);
							
							
							Log.d("ggggggg", String.valueOf(data_start_position)+" "+String.valueOf(kills) + "/" + String.valueOf(deaths) + "/" + String.valueOf(assists));
							ground_bitmap = ImageUtil.drawTextToLeftTop(context, ground_bitmap, String.valueOf(kills) + "/" + String.valueOf(deaths) + "/" + String.valueOf(assists), 40, Color.rgb(193, 210, 240), 370, data_start_position);
							if (items_list.length() != 0)
							{
								int item_start_position = 250;
								for (int time_2 = 0; time_2 < items_list.length(); time_2++)
								{
									Bitmap item_bitmap = Util.get_item_bitmap(context, items_list.getString(time_2).replace(" ", "").replace("'", "").toLowerCase());

									ground_bitmap = ImageUtil.createWaterMaskLeftTop(context, ground_bitmap, item_bitmap, item_start_position, items_start_position);
									item_start_position = item_start_position + 90 ;
								}
							}
							Bitmap hero_bitmap = Util.get_item_bitmap(context, hero);
							ground_bitmap = ImageUtil.createWaterMaskLeftTop(context, ground_bitmap, hero_bitmap, 805, hero_start_position);
							ground_bitmap = ImageUtil.drawTextToRightBottom(context, ImageUtil.drawTextToRightBottom(context, ground_bitmap, "Powered by Tick Tock & Guild Belove(Her)", 40, Color.rgb(193, 210, 240), 5, 10), "Generated by Gwen QQ_VG_Robot", 40, Color.rgb(193, 210, 240), 5, 60);
							
						}
						



					}

					data_start_position = data_start_position + 167;
					date_start_position = date_start_position + 167;
					struct_start_position = struct_start_position + 167;
					items_start_position = items_start_position + 167;
					hero_start_position = hero_start_position + 167;
					

				}
				

			}
			catch (JSONException e)
			{
				return ;
			}

		}


		mHandler.sendEmptyMessage(0);

		//需要数据传递，用下面方法；
		Message msg =new Message();
		msg.obj = ground_bitmap;//可以是基本类型，可以是对象，可以是List、map等；
		mHandler.sendMessage(msg);
		return ;
    }
	
	
	}).start();
	
		return true;
	}
}
