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

public class Test1Activity extends Activity
{
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
        setContentView(R.layout.activity_test1);
		if (android.os.Build.VERSION.SDK_INT > 9)
		{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
        button1 = (Button) findViewById(R.id.contenttest1Button1Create);
		button2 = (Button) findViewById(R.id.contenttest1Button2save);
		editText1 = (EditText) findViewById(R.id.contenttest1EditText1id);
		editText2 = (EditText) findViewById(R.id.contenttest1EditText2page);
		editText3 = (EditText) findViewById(R.id.contenttest1EditText3path);
        initView();

		button1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					if (String.valueOf(editText1.getText()).equals("") != true && String.valueOf(editText2.getText()).equals("") != true)
					{
					    Toast.makeText(Test1Activity.this, "Started", Toast.LENGTH_SHORT).show();
					    boolean result = start_struct_img(String.valueOf(editText1.getText()), String.valueOf(editText2.getText()));
						Toast.makeText(Test1Activity.this, String.valueOf(result), Toast.LENGTH_SHORT).show();

					}
					else
					{
						Toast.makeText(Test1Activity.this, "Must not be empty", Toast.LENGTH_SHORT).show();
					}
				}
			});

		button2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					if (String.valueOf(editText3.getText()).equals("") != true)
					{

					    Toast.makeText(Test1Activity.this, "Saved", Toast.LENGTH_SHORT).show();
						mWartermarkImage = (ImageView) findViewById(R.id.contenttest1ImageView1);
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
						Toast.makeText(Test1Activity.this, "Must not be empty", Toast.LENGTH_SHORT).show();
					}
				}
			});

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
	
	
	
    private void initView()
	{


    	mWartermarkImage = (ImageView) findViewById(R.id.contenttest1ImageView1);


    	Bitmap sourBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ground_match_detail);



    	mWartermarkImage.setImageBitmap(sourBitmap);
    }




	public boolean start_struct(final String player_name, final String page)
	{

		new Thread(new Runnable(){
				Context context = getApplicationContext();
				@Override
				public void run()
				{
					//耗时操作，完成之后发送消息给Handler，完成UI更新；
					int data_start_position = 5;
					String name = "godandmonster";
					int kills = 10;
					int deaths = 9;
					int assists =10;
					//int kda=0;
					float gold = (float) 1.3;
					int minionkills = 100;

					Context context = getApplicationContext();

					Bitmap ground_bitmap = Util.get_item_bitmap(this.context, "left_player");
					Bitmap item_bitmap = Util.get_item_bitmap(this.context, "breakingpoint");
					Bitmap hero_bitmap = Util.get_item_bitmap(this.context, "blackfeather");
					ground_bitmap = ImageUtil.drawTextToLeftTop(context, ImageUtil.drawTextToLeftTop(context, ground_bitmap, String.valueOf(gold) + "k", 40, Color.rgb(193, 210, 240), 540, data_start_position), String.valueOf(minionkills), 40, Color.rgb(193, 210, 240), 730, data_start_position);
					ground_bitmap = ImageUtil.drawTextToLeftTop(context, ground_bitmap, name, 40, Color.rgb(193, 210, 240), 0, data_start_position);
					Log.d("ggggggg", String.valueOf(data_start_position) + " " + String.valueOf(kills) + "/" + String.valueOf(deaths) + "/" + String.valueOf(assists));
					ground_bitmap = ImageUtil.drawTextToLeftTop(context, ground_bitmap, String.valueOf(kills) + "/" + String.valueOf(deaths) + "/" + String.valueOf(assists), 40, Color.rgb(193, 210, 240), 350, data_start_position);
					ground_bitmap = ImageUtil.createWaterMaskLeftTop(context, ground_bitmap, item_bitmap, 250, 75);
					ground_bitmap = ImageUtil.createWaterMaskLeftTop(context, ground_bitmap, hero_bitmap, 805, 20);


					mHandler.sendEmptyMessage(0);

					//需要数据传递，用下面方法；
					Message msg =new Message();
					msg.obj = ground_bitmap;//可以是基本类型，可以是对象，可以是List、map等；
					mHandler.sendMessage(msg);

				}
			}).start();


		return true;
	}



	public boolean start_struct_img(final String player_name, final String page)
	{


		new Thread(new Runnable(){
				
				@Override
				public void run()
				{
				
					//耗时操作，完成之后发送消息给Handler，完成UI更新；
					Context context = getApplicationContext();

					Bitmap ground_bitmap = Util.get_item_bitmap(context, "ground_match_detail");


					final Bitmap left_player_bitmap = Util.get_item_bitmap(context, "left_player");
		            final Bitmap left_player_me_bitmap = Util.get_item_bitmap(context, "left_player_me");
					Bitmap right_player_bitmap = Util.get_item_bitmap(context, "right_player");
					Bitmap right_player_me_bitmap = Util.get_item_bitmap(context, "right_player_me");

					try
					{
						Util.trustAllHttpsCertificates();
						HttpsURLConnection.setDefaultHostnameVerifier(Util.hv);
					}
					catch (Exception e)
					{}
					String message = Util.getUrlContent("https://45.77.188.201/matches/" + player_name + "?gameMode=&limit=5&page=" + page + "&season=");
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
						String region = null;
						String createdat = null;
						String duration = null;
						JSONArray json = null;
						JSONArray lefts_list =null;
						JSONArray rights_list = null;
						JSONArray player_list=null;
						//String tier = null

						DecimalFormat df = new DecimalFormat("0.0");		
						StringBuilder left_list = new StringBuilder("");
						StringBuilder right_list = new StringBuilder("");
						try
						{
							json = new JSONArray(message);
							JSONObject match = json.getJSONObject(0);
							players = match.getString("players");
							gamemode = match.getString("gameMode").replace(" ", "_");
							gamemode = Util.match_translate(gamemode);
							region = match.getString("shardId");
							String createdAt = match.getString("createdAt").replaceAll("[A-Z]", " ");
							try
							{
								SimpleDateFormat lsdStrFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date st = lsdStrFormat.parse(createdAt);
								long datelong = st.getTime();
								datelong = datelong + 28800000;
								createdat = new SimpleDateFormat("yyyy-MM-dd_HH:mm").format(new Date(datelong)).replaceAll("20[0-9]*-", "");

							}
							catch (ParseException e)
							{}
							duration = String.valueOf((int)match.getInt("duration") / 60);
							ground_bitmap = ImageUtil.drawTextToLeftBottom(context, ImageUtil.drawTextToLeftBottom(context, ImageUtil.drawTextToLeftBottom(context, ground_bitmap, gamemode, 40, Color.rgb(193, 210, 240), 0, 60), duration + "分钟" , 40, Color.rgb(193, 210, 240), 320, 10), "日期 " + createdat, 40, Color.rgb(193, 210, 240), 0, 10);
							ground_bitmap = ImageUtil.drawTextToRightBottom(context, ImageUtil.drawTextToRightBottom(context, ground_bitmap, "Powered by Tick Tock & Guild Belove(Her)", 40, Color.rgb(193, 210, 240), 5, 10), "Generated by Gwen QQ_VG_Robot", 40, Color.rgb(193, 210, 240), 5, 60);
							player_list = new JSONArray(players);
							JSONArray rosters_list = match.getJSONArray("rosters");
							for (int time = 0; time < rosters_list.length(); time++)
							{
								JSONObject data = rosters_list.getJSONObject(time);
								String team_gold = String.valueOf(data.getInt("gold") / 1000);
								String team_kill = data.getString("heroKills");
								String team_turret = data.getString("turretKills");
								if (time == 0)
								{
									ground_bitmap = ImageUtil.drawTextToLeftTop(context, ImageUtil.drawTextToLeftTop( context,ImageUtil.drawTextToLeftTop( context,ground_bitmap, team_gold, 40, Color.rgb(193, 210, 240), 700, 45), team_turret, 40, Color.rgb(193, 210, 240), 550, 45), team_kill, 60, Color.rgb(193, 210, 240), 860, 45);
								}
								else
								{
									ground_bitmap = ImageUtil.drawTextToRightTop(context, ImageUtil.drawTextToRightTop( context,ImageUtil.drawTextToRightTop(context, ground_bitmap, team_gold + "k", 40, Color.rgb(193, 210, 240), 700, 45), team_turret, 40, Color.rgb(193, 210, 240), 550, 45), team_kill, 60, Color.rgb(193, 210, 240), 860, 45);
								}
							}



							for (int time =0 ; time < player_list.length(); time++)
							{
								JSONObject player=new JSONObject(player_list.getString(time));
								if (player.getString("side").equals("left/blue"))
								{
									left_list.append(player + ",");
								}
								else
								{
									right_list.append(player + ",");
								}
							}
							left_list.append("@@@@");
							right_list.append("@@@@");
							lefts_list = new JSONArray("[" + left_list.toString().replace(",@@@@", "") + "]");
							rights_list = new JSONArray("[" + right_list.toString().replace(",@@@@", "") + "]");
							if (left_list.length() + right_list.length() == 6)
							{
								game_type = 1;
							}
							else
							{
								game_type = 2;
							}

							if (game_type == 2)
							{


								int data_start_position = 130;
								int struct_start_position = 125;
								int items_start_position = 200;
								int hero_start_position = 145;
								for (int time =0 ; time < lefts_list.length(); time++)
								{
									JSONObject player=new JSONObject(lefts_list.getString(time));
									if (time == 0)
									{
										if (player.getBoolean("winner"))
										{
											ground_bitmap = ImageUtil.drawTextToLeftTop(context, ground_bitmap, "胜利方", 40, Color.rgb(193, 210, 240), 0, 45);
										}
										else
										{
											ground_bitmap = ImageUtil.drawTextToLeftTop(context, ground_bitmap, "失败方", 40, Color.rgb(193, 210, 240), 0, 45);	
										}
									}
									Boolean mvp =player.getBoolean("mvp");
									Boolean me =player.getBoolean("me");
									Boolean afk =player.getBoolean("afk");
									String name = player.getString("name");
									String hero = player.getString("hero").toLowerCase();
									//hero = Util.hero_translate(hero);
									kills = player.getInt("kills");
									deaths = player.getInt("deaths");
									assists = player.getInt("assists");
									//kda = player.getInt("kda");
									minionkills = player.getInt("minionKills");
									gold = player.getInt("gold");
									gold = Float.parseFloat(df.format((gold / 1000)));
									//tier = String.valueOf(player.getInt("tier"));
									//tier = Util.translate_tier(tier);
									if (me)
									{
										ground_bitmap = ImageUtil.createWaterMaskLeftTop( context,ground_bitmap, left_player_me_bitmap, 0, struct_start_position);
									}
									//else if (afk == false)
									//{

									//}
									//else if (mvp)
									//{

									//}
									else
									{

										ground_bitmap = ImageUtil.createWaterMaskLeftTop(context, ground_bitmap, left_player_bitmap, 0, struct_start_position);

									}
									ground_bitmap = ImageUtil.drawTextToLeftTop( context,ImageUtil.drawTextToLeftTop(context, ground_bitmap, String.valueOf(gold) + "k", 40, Color.rgb(193, 210, 240), 550, data_start_position), String.valueOf(minionkills), 40, Color.rgb(193, 210, 240), 730, data_start_position);
									ground_bitmap = ImageUtil.drawTextToLeftTop( context,ground_bitmap, name, 40, Color.rgb(193, 210, 240), 0, data_start_position);
									Log.d("ggggggg", String.valueOf(data_start_position) + " " + String.valueOf(kills) + "/" + String.valueOf(deaths) + "/" + String.valueOf(assists));
									ground_bitmap = ImageUtil.drawTextToLeftTop( context,ground_bitmap, String.valueOf(kills) + "/" + String.valueOf(deaths) + "/" + String.valueOf(assists), 40, Color.rgb(193, 210, 240), 370, data_start_position);
									JSONArray items_list = player.getJSONArray("items");
									if (items_list.length() != 0)
									{
										int item_start_position = 250;
										for (int time_1 = 0; time_1 < items_list.length(); time_1++)
										{
											Bitmap item_bitmap = Util.get_item_bitmap(context, items_list.getString(time_1).replace(" ", "").replace("'", "").toLowerCase());

											ground_bitmap = ImageUtil.createWaterMaskLeftTop(context, ground_bitmap, item_bitmap, item_start_position, items_start_position);
											item_start_position = item_start_position + 90 ;
										}
									}
									Bitmap hero_bitmap = Util.get_item_bitmap(context, hero);
									ground_bitmap = ImageUtil.createWaterMaskLeftTop(context, ground_bitmap, hero_bitmap, 805, hero_start_position);

									data_start_position = data_start_position + 167;
									struct_start_position = struct_start_position + 167;
									items_start_position = items_start_position + 167;
									hero_start_position = hero_start_position + 167;

								}

								data_start_position = 130;
								struct_start_position = 125;
								items_start_position = 200;
								hero_start_position = 145;
								//右边玩家开始处理
								for (int time =0 ; time < rights_list.length(); time++)
								{
									JSONObject player=new JSONObject(rights_list.getString(time));
									if (time == 0)
									{
										if (player.getBoolean("winner"))
										{
											ground_bitmap = ImageUtil.drawTextToRightTop(context, ground_bitmap, "胜利方", 40, Color.rgb(193, 210, 240), 0, 45);
										}
										else
										{
											ground_bitmap = ImageUtil.drawTextToRightTop( context,ground_bitmap, "失败方", 40, Color.rgb(193, 210, 240), 0, 45);	
										}
									}
									Boolean mvp =player.getBoolean("mvp");
									Boolean me =player.getBoolean("me");
									Boolean afk =player.getBoolean("afk");
									String name = player.getString("name");
									String hero = player.getString("hero").toLowerCase();
									//hero = Util.hero_translate(hero);
									kills = player.getInt("kills");
									deaths = player.getInt("deaths");
									assists = player.getInt("assists");
									//kda = player.getInt("kda");
									minionkills = player.getInt("minionKills");
									gold = player.getInt("gold");
									gold = Float.parseFloat(df.format((gold / 1000)));
									//tier = String.valueOf(player.getInt("tier"));
									//tier = Util.translate_tier(tier);
									if (me)
									{
										ground_bitmap = ImageUtil.createWaterMaskRightTop(context, ground_bitmap, right_player_me_bitmap, 0, struct_start_position);
									}
									//else if (afk == false)
									//{

									//}
									//else if (mvp)
									//{

									//}
									else
									{

										ground_bitmap = ImageUtil.createWaterMaskRightTop( context,ground_bitmap, right_player_bitmap, 0, struct_start_position);

									}
									ground_bitmap = ImageUtil.drawTextToRightTop( context,ImageUtil.drawTextToRightTop(context, ground_bitmap, String.valueOf(gold) + "k", 40, Color.rgb(193, 210, 240), 550, data_start_position), String.valueOf(minionkills), 40, Color.rgb(193, 210, 240), 730, data_start_position);
									ground_bitmap = ImageUtil.drawTextToRightTop(context, ground_bitmap, name, 40, Color.rgb(193, 210, 240), 5, data_start_position);
									Log.d("ggggggg", String.valueOf(data_start_position) + " " + String.valueOf(kills) + "/" + String.valueOf(deaths) + "/" + String.valueOf(assists));
									ground_bitmap = ImageUtil.drawTextToRightTop( context,ground_bitmap, String.valueOf(kills) + "/" + String.valueOf(deaths) + "/" + String.valueOf(assists), 40, Color.rgb(193, 210, 240), 370, data_start_position);
									JSONArray items_list = player.getJSONArray("items");
									if (items_list.length() != 0)
									{
										int item_start_position = 250;
										for (int time_1 = 0; time_1 < items_list.length(); time_1++)
										{
											Bitmap item_bitmap = Util.get_item_bitmap(context, items_list.getString(time_1).replace(" ", "").replace("'", "").toLowerCase());

											ground_bitmap = ImageUtil.createWaterMaskRightTop( context,ground_bitmap, item_bitmap, item_start_position, items_start_position);
											item_start_position = item_start_position + 90 ;
										}
									}
									Bitmap hero_bitmap = Util.get_item_bitmap(context, hero);
									ground_bitmap = ImageUtil.createWaterMaskRightTop( context,ground_bitmap, hero_bitmap, 805, hero_start_position);

									data_start_position = data_start_position + 167;
									struct_start_position = struct_start_position + 167;
									items_start_position = items_start_position + 167;
									hero_start_position = hero_start_position + 167;

								}


							}
						}
						catch (JSONException e)
						{
							Log.d("ggggggg",e.toString());
						}

					

					}
					mHandler.sendEmptyMessage(0);

					//需要数据传递，用下面方法；
					Message msg =new Message();
					msg.obj = ground_bitmap;//可以是基本类型，可以是对象，可以是List、map等；
					mHandler.sendMessage(msg);
				}		
			}).start();



		return true;
    }
}
