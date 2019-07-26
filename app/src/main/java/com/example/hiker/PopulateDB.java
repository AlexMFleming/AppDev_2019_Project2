package com.example.hiker;

import java.util.*;

public class PopulateDB {
    private TrailDatabase TrailDb;

    public ArrayList<Trail> createTrails() {

        ArrayList<Trail> trailList = new ArrayList<>();

        //23 trails from https://www.fodors.com/news/photos/these-are-the-25-most-breathtaking-hikes-in-us-national-parks
        trailList.add(new Trail ("Art Loeb", 29, 8257, 0, 1, 1, "Traverses through many different biomes and summits the highest peak of the Appalachian Mountains and the 4th most isolated peak in the country, Mt. Mitchell",1));
        trailList.add(new Trail ("Blood Mountain", 6, 1545, 0, 0, 1, "Steep climb on the north Georgia AT with a great view at the top", 2));
        trailList.add(new Trail ("Bear Creek", 6, 1108, 0, 1, 1, "a beautiful, mossy, fern-filled creek valley to the Gennett Poplar, the second largest living tree in Georgia.", 3));
        trailList.add(new Trail ("Iceberg Lake Trail", 10, 1200, 0, 1, 1, "This trail through alpine valleys ends at the stunning, sky blue Iceberg Lake, surrounded by 2,000-foot cliffs. Though this trail is popular, you should be able to find a quiet space to yourself if you start walking along the lakes furthest edges.", 6));
        trailList.add(new Trail ("Tower Arch Trail",  3, 600, 0, 0, 1, "Utahs Arches National Park is known for its stunning rock formations that lend the park its name, but it be can hard to find space to yourself with everyone scrambling (pun intended) to take photos of this Instagram-worthy park.", 8));
        trailList.add(new Trail ("The Boardwalk Loop", 2, 0, 0, 1, 1, "This hike, though its really more of a walk, features an elevated boardwalk through old-growth swampland. Though the lush, green trees are beautiful in their own right, the trail really shines at night (literally!) when thousands of fireflies come out and fill the area.", 9));
        trailList.add(new Trail ("The Hoh River Trail", 33, 3700, 1, 1, 1, "If a rainforest hike is on your bucket list but you don’t have time to head to South America, head to the Hoh River Trail in Washingtons Olympic National Park.", 11));
        trailList.add(new Trail ("The Wonderland Trail", 93, 23000, 0, 1, 1, "The Wonderland Trails sweeping valley and snow-capped mountain views may make hikers think they’ve stepped out of the U.S. and into the Alps.", 14));
        trailList.add(new Trail ("Slaughter Canyon Cave", 2, 800, 1, 0, 1,"For hikers who prefer stalagmites to summits, this underground hike deep inside the Guadalupe Mountains goes past some of the largest cave rock formations in the world.", 16));
        trailList.add(new Trail ("Angel Landing", 5, 1488, 0, 0, 1, "One of the most famous hikes in the state, thanks to its 360-degree views of the entirety of Zion National Park’s red, orange, and yellow-tinted canyons.", 17));
        trailList.add(new Trail ("Tall Trees Trail", 4, 777, 0, 0, 1, "This trail takes hikers past the tallest redwood trees in the world, the scale of which can only be understood in person.", 19));
        trailList.add(new Trail ("Cadillac Mountain", 8, 2247, 0, 0, 1, "If you fancy the idea of being the first person in the country to view the sunrise, aim to summit Cadillac Mountain in October in Acadia National Park.", 21));
        trailList.add(new Trail ("Lost Mine Trail", 5, 1000, 0, 0, 1, "As one of the highest trails in the park, the Lost Mine Trail is often several degrees cooler than other places in Big Bend, one of the countrys most remote national parks.",23));
        trailList.add(new Trail ("Sierra Point", 1, 1000, 1, 1, 1, "Yosemite may be famous for well-known hikes like Half Dome or the waterfall-laden Mist Trail, but when it comes to beauty, its hard to beat the short-but-steep Sierra Point Lookout Trail.", 25));
        trailList.add(new Trail ("Mt. Katmai Caldera", 50, 4000, 0, 1, 1, "For expert hikers and alpinists only, the trek to the snow-covered Katmai Caldera involves glacier crossing over crevasses–significant ice-climbing experience is required.",27));
        trailList.add(new Trail ("Kalapana Lava Viewing", 8, 230, 0, 0, 0, "The full fury of Mother Nature is always on display on this hike, where red-hot lava can be seen seeping into Hawaiis blue ocean.", 28));
        trailList.add(new Trail ("The Narrows", 2, 150, 1, 1, 1, "Though the Narrows is a relatively easy, flat hike, flash floods can turn this area very deadly, very quickly.",18));
        trailList.add(new Trail ("Fairyland Loop", 8, 1550, 0, 0, 1, "These unique rock columns can be found throughout the trail, eventually culminating in Fairyland Canyon, a valley of staggeringly large and vast formations as tall as 150 feet.", 29));
        trailList.add(new Trail ("North Kaibab Trail", 28, 5850, 1, 1, 1, "As the least visited and most difficult trail on the north rim, the North Kaibab Trail offers a rare chance for solitude in one of the countrys most popular parks.", 32));
        trailList.add(new Trail ("Exit Glacier", 2, 0, 1, 1, 0, "If you like the idea of up close and personal glacier viewing but are a novice hiker at best, the Exit Glacier Trail should check off all the boxes.",33));
        trailList.add(new Trail ("Moraine Lake", 2, 100, 1, 1, 1, "To explore this stunningly beautiful alpine lake, start in the town of Talkeetna, the gateway to Denali National Park.", 34));
        trailList.add(new Trail ("Lower Bear Gulch", 2, 275, 0, 1, 0, "The easy Lower Bear Gulch Cave trail takes hikers under moss-covered boulders and across alpine springs, often at the same time.", 35));
        trailList.add(new Trail ("Cascades Pass Trail", 7, 1700, 0, 1, 1, "The Cascades Pass Trail is really more of a series of trails, with routes ranging from mid-level day trips to extended, difficult overnight treks.", 37));
        trailList.add(new Trail ("Greenstone Ridge Trail", 40, 3973, 0, 0, 1, "This trail is a wildlife photographers dream, with rare birds, wolves, and moose on the island.",38));

        return trailList;
    }

    public ArrayList<Trip> createTrips() {
        ArrayList<Trip> tripList = new ArrayList<>();


        return tripList;
    }

    public ArrayList<Park> createParks() {
        ArrayList<Park> parkList = new ArrayList<Park>();

        parkList.add(new Park("Pisgah National Forest", "NC", "Asheville"));
        parkList.add(new Park ("Chattahoochee National Forest", "GA", "Newport"));
        parkList.add(new Park ("Glacier National Park", "MT", "St. Mary"));
        parkList.add(new Park ("Arches National Park", "UT", "Moab"));
        parkList.add(new Park ("Congaree National Park", "SC", "Hopkins"));
        parkList.add(new Park ("Olympic National Park", "WA", "Quinault"));
        parkList.add(new Park ("Mt. Ranier National Park", "WA", "Longmire"));
        parkList.add(new Park ("Carlsbad Caverns National Park", "NM", "Carlsbad"));
        parkList.add(new Park ("Zion National Park", "UT", "Springdale"));
        parkList.add(new Park ("Redwood National Park", "CA", "Orick"));
        parkList.add(new Park ("Acadia National Park", "ME", "Bar Harbor"));
        parkList.add(new Park ("Big Bend National Park", "TX", "Terlingua"));
        parkList.add(new Park ("Yosemite National Park", "CA", "Yosemite Valley"));
        parkList.add(new Park ("Katmai National Park", "AK", "King Salmon"));
        parkList.add(new Park ("Hawaii Volcanoes National Park", "HA", "Kalapana"));
        parkList.add(new Park ("Bryce Canyon National Park", "UT", "Bryce"));
        parkList.add(new Park ("Grand Canyon National Park", "AZ", "Grand Canyon Village"));
        parkList.add(new Park ("Kenai Fjords National Park", "AK", "Seward"));
        parkList.add(new Park ("Banff National Park Of Canada","AB","Castle Junction"));
        parkList.add(new Park ("Pinnacles National Park","CA","Pinnacles"));
        parkList.add(new Park ("North Cascades National Park","WA","Marblemount"));
        parkList.add(new Park ("Isle Royale National Park","MI","Houghton Township"));


        return parkList;

    }

    public ArrayList<Station> createStations() {
        ArrayList<Station> stationList = new ArrayList<Station>();

        stationList.add(new Station (1, "Pisgah Ranger Station", "828-877-3265"));
        stationList.add(new Station(2, "Blue Ridge District Station", "706-745-6928"));
        stationList.add(new Station(2, "Chattooga River District Station", "706-754-6221"));
        stationList.add(new Station(2 ,"Conasauga District","706-695-6736 "));
        stationList.add(new Station(2,"Oconee District","706-485-7110"));
        stationList.add(new Station(3,"Many Glacier Ranger Station","406-888-7800"));
        stationList.add(new Station(3,"Polebridge Ranger Station","406-888-7800"));
        stationList.add(new Station(4,"Arches National Park Visitor Center and Ranger Station","435-719-2299"));
        stationList.add(new Station(5,"Harry Hampton Visitor Center","803-776-4396"));
        stationList.add(new Station(6,"Olympic National Park Visitor Center","360-565-3130"));
        stationList.add(new Station(6,"Hoh Rain Forest Visitor Center","360-374-6925"));
        stationList.add(new Station(6,"Kalaloch Ranger Station","360-962-2283"));
        stationList.add(new Station(7,"Paradise Ranger Station","360-569-2211"));
        stationList.add(new Station(7,"Carbon River Ranger Station","360-829-9639"));
        stationList.add(new Station(7,"White River Ranger Station","360-569-6670"));
        stationList.add(new Station(8,"Rattlesnake Springs Ranger Station","575-785-2232"));
        stationList.add(new Station(9,"East Entrance Ranger Station","435-772-3256"));
        stationList.add(new Station(9,"Zion National Park Visitor Center","435-772-3256"));
        stationList.add(new Station(10,"Thomas H. Kuchel Visitor Center","707-465-7765"));
        stationList.add(new Station(10,"Prairie Creek Visitor Center","707-488-2039"));
        stationList.add(new Station(11,"Acadia National Park Headquarters","207-288-3338"));
        stationList.add(new Station(11,"Blackwoods Ranger Station","207-288-3338"));
        stationList.add(new Station(12,"Castolon Ranger Station","432-477-2225"));
        stationList.add(new Station(12,"Chisos Basin Visitor Center","432-477-2251"));
        stationList.add(new Station(13,"Little Yosemite Valley Ranger Station","209-372-0200"));
        stationList.add(new Station(13,"Devil's Postpile Ranger Station","760-934-2289"));
        stationList.add(new Station(14,"Brooks Camp","907-246-3305"));
        stationList.add(new Station(15,"Hawaiʻi Volcanoes National Park Visitor Center","808) 985-6000"));
        stationList.add(new Station(16,"Bryce Canyon National Park Visitor Center","435-834-5322"));
        stationList.add(new Station(17,"North Rim Ranger Station","928-638-7888"));
        stationList.add(new Station(17,"Indian Garden Ranger Station","928-638-7888"));
        stationList.add(new Station(17,"Grand Canyon Visitor Center","928-638-7888"));
        stationList.add(new Station(18,"Kenai Fjords National Park Visitor Center","907-422-0535"));
        stationList.add(new Station(19,"Banff Visitor Centre","403-762-9421"));
        stationList.add(new Station(20,"West Pinnacles Visitor Contact Station","831-389-4427"));
        stationList.add(new Station(20,"Pinnacles Visitor Center","831-389-4485"));
        stationList.add(new Station(21,"North Cascades National Park Wilderness Visitor & Information Center","360-854-7245"));
        stationList.add(new Station(22,"Isle Royale National Park Visitor Center","906-482-0984"));

        return stationList;
    }
}
