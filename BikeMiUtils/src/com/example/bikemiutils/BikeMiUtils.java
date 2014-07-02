package com.example.bikemiutils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



import model.Stallo;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;


public class BikeMiUtils {
	
	public static LatLng currentPosition;

	public static void getBikeMiStations(BikeMiCallBack callback,LatLng myPosition) {
		BikeMiAsyncRestCall asyncRest = new BikeMiAsyncRestCall(callback);
		currentPosition=myPosition;
		asyncRest.execute();
		
	}

	public static String getJsonBikeMi() {
		// The url for the http request
		String url = "http://api.citybik.es/v2/networks/bikemi";
		// The response body
		String responseBody = null;
		// The HTTP get method send to the URL
		HttpGet getMethod = new HttpGet(url);
		// The basic response handler
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		// Instantiate the HTTP communication
		HttpClient client = new DefaultHttpClient();
		// Call the URL and get the response body
		try {
			responseBody = client.execute(getMethod, responseHandler);
		} catch (ClientProtocolException e) {
			Log.e("BikeMiUtils", e.getMessage());
		} catch (IOException e) {
			Log.e("BikeMiUtils", e.getMessage());
		}
		if (responseBody != null) {
			Log.e("BikeMiUtils", responseBody);
		}
		// Parse the response body
		return responseBody;
	}
	
	public static List<Stallo> parseJsonBikeMi(String json) {
		JSONObject jObject;
		List<Stallo> stations=new ArrayList<Stallo>();
		Stallo station;
		if (json != null) {
			try {
				jObject = new JSONObject(json);
				JSONObject network = jObject.optJSONObject("network");
				JSONArray resultsArray=network.optJSONArray("stations");
				for(int i=0;i<resultsArray.length();i++){
					JSONObject resultsObj = resultsArray.optJSONObject(i);
					int empty_slots = resultsObj.optInt("empty_slots");
					int free_bikes = resultsObj.optInt("free_bikes");
					double latitude =resultsObj.optDouble("latitude");
					double longitude =resultsObj.optDouble("longitude");
					String name =resultsObj.optString("name");
					
					station=new Stallo();
					station.setEmptySlots(empty_slots);
					station.setFreeBikes(free_bikes);
					station.setPosition(new LatLng(latitude, longitude));
					station.setName(name);
					station.setDistance(currentPosition);
										
					stations.add(station);
				}
				
			} catch (Exception e) {
				Log.e("BikeMiUtils", "Parsing JSon from BikeMi Api failed, see stack trace below:", e);
			}
		}
		Collections.sort(stations, new Comparator<Stallo>() {

			@Override
			public int compare(Stallo lhs, Stallo rhs) {
				Double d1= lhs.getDistance();
				Double d2= rhs.getDistance();
				return d1.compareTo(d2);
			}

			
		});
		return stations.subList(0, 5);
	}

	public static final class BikeMiAsyncRestCall extends AsyncTask<Void, Void, List<Stallo>>{
		
		private BikeMiCallBack callBack;

		public BikeMiAsyncRestCall(BikeMiCallBack callback) {
			super();
			this.callBack=callback;
		}

		@Override
		protected List<Stallo> doInBackground(Void... arg0) {
			String json=getJsonBikeMi();
			return parseJsonBikeMi(json);
		}
		
		@Override
		protected void onPostExecute(List<Stallo> result) {
			super.onPostExecute(result);
			callBack.onBikeMiStationsComputed(result);
		}

		
	}

}
