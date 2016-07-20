package ru.onyanov.yesorno;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class JsonHelper {
	
	private JSONArray json;
	private Context context;
	private int id;

	public JsonHelper(Context context){
		this.context = context;
		try {
			ReadFromfile("riddles.json");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		id = 0;
	}
	
	public JSONObject getRiddle(){
		JSONObject riddle = null;
		try {
			riddle = (JSONObject) json.get(id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		id++;
		return riddle;
	}
	

	public void ReadFromfile(String fileName) throws JSONException {
	    StringBuilder ReturnString = new StringBuilder();
	    InputStream fIn = null;
	    InputStreamReader isr = null;
	    BufferedReader input = null;
	    try {
	        fIn = context.getResources().getAssets()
	                .open(fileName, context.MODE_WORLD_READABLE);
	        isr = new InputStreamReader(fIn);
	        input = new BufferedReader(isr);
	        String line = "";
	        while ((line = input.readLine()) != null) {
	            ReturnString.append(line);
	        }
	    } catch (Exception e) {
	        e.getMessage();
	    } finally {
	        try {
	            if (isr != null)
	                isr.close();
	            if (fIn != null)
	                fIn.close();
	            if (input != null)
	                input.close();
	        } catch (Exception e2) {
	            e2.getMessage();
	        }
	    }
	    json = new JSONArray(ReturnString.toString());
	}
}
