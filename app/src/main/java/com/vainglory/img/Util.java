package com.vainglory.img;
import java.io.*;
import java.net.*;
import android.graphics.*;
import android.content.*;
import java.lang.reflect.*;
import android.util.*;
import org.json.*;
import javax.net.ssl.*;

public class Util
{
	public static String getUrlContent(String url)
	{
		try
		{
			InputStream is=new URL(url).openStream();
			ByteArrayOutputStream buffer=new ByteArrayOutputStream();
			int b=-1;
			while ((b = is.read()) != -1)
				buffer.write(b);
			return new String(buffer.toByteArray());
		}
		catch (MalformedURLException e)
		{
		}
		catch (IOException e)
		{
		}
		return null;
	}


	public static Bitmap get_item_bitmap(Context context, String item_name)
	{
		int resId = getResourceId(item_name);
		Log.d("ggggggg",item_name+" "+String.valueOf(resId));
        Bitmap bitmap_to_send = BitmapFactory.decodeResource(context.getResources(), resId);
		return bitmap_to_send;
	}
	public static int getResourceId(String fileName)
	{ try
		{ Field field = R.drawable.class.getField(fileName);
		return Integer.parseInt(field.get(null).toString()); }
		catch (Exception e)
		{ e.printStackTrace(); } return 0; }
		
		
	public static void saveBitmap(Bitmap bitmap,String filename) throws IOException
    {
        File file = new File(filename);
        if(file.exists()){
            file.delete();
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, out))
            {
                out.flush();
                out.close();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
	
	
	public static String match_translate(String match)
	{
		String hero_json = "{   \"Ranked\":\"3v3排位赛\",   \"Casual\":\"3v3匹配赛\",     \"Casual_5v5\":\"5v5匹配赛\",    \"Ranked_5v5\":\"5v5排位赛\",     \"Battle_Royale\":\"大乱斗\",       \"Blitz\":\"闪电战\"   }";
		try
		{
			JSONObject json=new JSONObject(hero_json);
			return json.getString(match);

		}
		catch (JSONException e)
		{}
		return "其他比赛";
	}
	
	public static HostnameVerifier hv = new HostnameVerifier() {
        public boolean verify(String urlHostName, SSLSession session)
		{
            System.out.println("Warning: URL Host: " + urlHostName + " vs. "
                               + session.getPeerHost());
            return true;
        }
    };


	public static void trustAllHttpsCertificates() throws Exception
	{
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
			.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
																	.getSocketFactory());
	}

	public static class miTM implements javax.net.ssl.TrustManager,
	javax.net.ssl.X509TrustManager
	{
		public java.security.cert.X509Certificate[] getAcceptedIssuers()
		{
			return null;
		}

		public static boolean isServerTrusted(
			java.security.cert.X509Certificate[] certs)
		{
			return true;
		}

		public static boolean isClientTrusted(
			java.security.cert.X509Certificate[] certs)
		{
			return true;
		}

		public void checkServerTrusted(
			java.security.cert.X509Certificate[] certs, String authType)
		throws java.security.cert.CertificateException
		{
			return;
		}

		public void checkClientTrusted(
			java.security.cert.X509Certificate[] certs, String authType)
		throws java.security.cert.CertificateException
		{
			return;
		}
	}
	
	
}
