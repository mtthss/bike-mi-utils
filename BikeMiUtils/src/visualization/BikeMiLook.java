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
			if(!addedYet(st)){
			markers.add(map.addMarker(st));
			}
		}
	}
	
	private static boolean addedYet(MarkerOptions st) {
		boolean result=false;
		if(!(markers.size()==0)){
			for(Marker m:markers){
				if(m.getTitle().equals(st.getTitle())){
					result=true;
					return result;
				}
			}
		}
		return result;
	}

	public static boolean removeMarkers(){
		boolean result=false;
		if(!(markers.size()==0)){
			for(Marker m:markers){
				m.remove();
			}
			markers.clear();
			result=true;
			return result;
		}
		return result;
	}

}
