package AdSOAP.CALENDAR;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TimeZone;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.VEvent;

import org.xml.sax.SAXException;

import advancedTask.geoConverter.Converter;
import advancedTask.geoConverter.KKJ_ZONE_INFO;
import advancedTask.geoConverter.KKJxy;
import advancedTask.geoConverter.WGS84lalo;
import fi.jyu.ties532.advanced.locator.JYULocator;
import fi.jyu.ties532.advanced.locator.LocationNotFoundException;
import fi.jyu.ties532.advanced.locator.Locator;

public class eventRemind {
	
	@SuppressWarnings("null")
	public static content getReminder(double userLat, double userLng,String url)
			throws IOException, ParserException, LocationNotFoundException,
			XPathExpressionException, ParserConfigurationException,
			SAXException, ParseException {

		List<VEvent> list = new ArrayList<VEvent>();

		// url
		URL URL=new URL(url);
		InputStream calendarInfor = URL.openStream();
		CalendarBuilder cBuilder = new CalendarBuilder();
		Calendar calendarDetail = cBuilder.build(calendarInfor);
		// sort the calender
		ComponentList listEvent = calendarDetail.getComponents(Component.VEVENT);
		Comparator<VEvent> comparator = new veventComparator();
		PriorityQueue<VEvent> eventInOrder = new PriorityQueue<VEvent>(15,
				(Comparator<? super VEvent>) comparator);
		for (Object elem : listEvent) {
			VEvent event = (VEvent) elem;
			eventInOrder.add(event);
		}
		while (eventInOrder.size() != 0) {
			list.add((VEvent) eventInOrder.remove());// add data from queue to list
		}

		// get the course to remind which is the after current time 
		int JthEvent = 0;
		for (int i = 0; i < list.size(); i++) {

			VEvent event = (VEvent) list.get(i);
			Date date = event.getStartDate().getDate();
			Date current = new Date();
			DateFormat df = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
			df.setTimeZone(TimeZone.getTimeZone("Europe/Jyvaskyla"));
			int compare = df.format(current).compareTo(df.format(date));
			
			if (compare >= 1) {
				JthEvent ++;
			}
		}
		
		System.out.println(list.get(JthEvent));
		VEvent nextEvent= (VEvent) list.get(JthEvent);

		String eventLocation = nextEvent.getLocation().getValue();// get location in event
															
		Locator JYULocation = new JYULocator();
		double JYUlat = JYULocation.locate(eventLocation).getLatitude();
		double JYUlng = JYULocation.locate(eventLocation).getLongitude();
		
		//use converter to convert between KKJ3 and WGS84
		KKJxy tikkurilaKKJ3start = Converter.WGS84lalo_to_KKJxy(new WGS84lalo(
				userLat, userLng), KKJ_ZONE_INFO.ZONE3);
		
		KKJxy tikkurilaKKJ3des = Converter.WGS84lalo_to_KKJxy(new WGS84lalo(
				JYUlat, JYUlng), KKJ_ZONE_INFO.ZONE3);

		double latOfBegin = tikkurilaKKJ3start.getX();
		double lngOfBegin = tikkurilaKKJ3start.getY();
		double latOfEnd = tikkurilaKKJ3des.getX();
		double lngofEnd = tikkurilaKKJ3des.getY();
        // get info from matka api
		String routeInfo = calendarReader.calendaerRea(latOfBegin, lngOfBegin, latOfEnd, lngofEnd) + "," + eventLocation;
		String[] routeInfoArray = routeInfo.split(",", 2);
		long currentUTC = new Date().getTime();
		long DateUTC = nextEvent.getStartDate().getDate().getTime();
		long timeDifference = DateUTC - currentUTC;
		String left = String.valueOf((timeDifference  / 60000)
				- Long.parseLong(routeInfoArray[0].trim()));

		
		//String inforToRemind = routeInfo + "," + left;
		
		content information=new content(routeInfoArray[0],routeInfoArray[1],eventLocation,left);

		return information;
	}

}
