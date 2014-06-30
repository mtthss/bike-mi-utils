package visualization;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class BikeMiLook {
	
	private static List<Marker> markers=new ArrayList<Marker>();
	
	public static void drawMarkers(GoogleMap map, List<MarkerOptions> stations){
		for(MarkerOptions st:stations){
			markers.add(map.addMarker(st));
		}
	}

}
