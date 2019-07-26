package com.example.hiker;

import java.util.*;

public class PopulateDB {
    private TrailDatabase TrailDb;

    public ArrayList<Trail> createTrails() {

        ArrayList<Trail> trailList = new ArrayList<>();

        //23 trails from https://www.fodors.com/news/photos/these-are-the-25-most-breathtaking-hikes-in-us-national-parks
        trailList.add(new Trail ("Art Loeb", 29, 8257, 0, 1, 1, "Traverses through many different biomes and summits the highest peak of the Appalachian Mountains and the 4th most isolated peak in the country, Mt. Mitchell"));
        trailList.add(new Trail ("Blood Mountain", 6, 1545, 0, 0, 1, "Steep climb on the north Georgia AT with a great view at the top"));
        trailList.add(new Trail ("Bear Creek", 6, 1108, 0, 1, 1, "a beautiful, mossy, fern-filled creek valley to the Gennett Poplar, the second largest living tree in Georgia."));
        trailList.add(new Trail ("Iceberg Lake Trail", 10, 1200, 0, 1, 1, "This trail through alpine valleys ends at the stunning, sky blue Iceberg Lake, surrounded by 2,000-foot cliffs. Though this trail is popular, you should be able to find a quiet space to yourself if you start walking along the lakes furthest edges."));
        trailList.add(new Trail ("Tower Arch Trail",  3, 600, 0, 0, 1, "Utahs Arches National Park is known for its stunning rock formations that lend the park its name, but it be can hard to find space to yourself with everyone scrambling (pun intended) to take photos of this Instagram-worthy park."));
        trailList.add(new Trail ("The Boardwalk Loop", 2, 0, 0, 1, 1, "This hike, though its really more of a walk, features an elevated boardwalk through old-growth swampland. Though the lush, green trees are beautiful in their own right, the trail really shines at night (literally!) when thousands of fireflies come out and fill the area."));
        trailList.add(new Trail ("The Hoh River Trail", 33, 3700, 1, 1, 1, "If a rainforest hike is on your bucket list but you don’t have time to head to South America, head to the Hoh River Trail in Washingtons Olympic National Park."));
        trailList.add(new Trail ("The Wonderland Trail", 93, 23000, 0, 1, 1, "The Wonderland Trails sweeping valley and snow-capped mountain views may make hikers think they’ve stepped out of the U.S. and into the Alps."));
        trailList.add(new Trail ("Slaughter Canyon Cave", 2, 800, 1, 0, 1,"For hikers who prefer stalagmites to summits, this underground hike deep inside the Guadalupe Mountains goes past some of the largest cave rock formations in the world."));
        trailList.add(new Trail ("Angel Landing", 5, 1488, 0, 0, 1, "One of the most famous hikes in the state, thanks to its 360-degree views of the entirety of Zion National Park’s red, orange, and yellow-tinted canyons."));
        trailList.add(new Trail ("Tall Trees Trail", 4, 777, 0, 0, 1, "This trail takes hikers past the tallest redwood trees in the world, the scale of which can only be understood in person."));
        trailList.add(new Trail ("Cadillac Mountain", 8, 2247, 0, 0, 1, "If you fancy the idea of being the first person in the country to view the sunrise, aim to summit Cadillac Mountain in October in Acadia National Park."));
        trailList.add(new Trail ("Lost Mine Trail", 5, 1000, 0, 0, 1, "As one of the highest trails in the park, the Lost Mine Trail is often several degrees cooler than other places in Big Bend, one of the countrys most remote national parks."));
        trailList.add(new Trail ("Sierra Point", 1, 1000, 1, 1, 1, "Yosemite may be famous for well-known hikes like Half Dome or the waterfall-laden Mist Trail, but when it comes to beauty, its hard to beat the short-but-steep Sierra Point Lookout Trail."));
        trailList.add(new Trail ("Mt. Katmai Caldera", 50, 4000, 0, 1, 1, "For expert hikers and alpinists only, the trek to the snow-covered Katmai Caldera involves glacier crossing over crevasses–significant ice-climbing experience is required."));
        trailList.add(new Trail ("Kalapana Lava Viewing", 8, 230, 0, 0, 0, "The full fury of Mother Nature is always on display on this hike, where red-hot lava can be seen seeping into Hawaiis blue ocean."));
        trailList.add(new Trail ("The Narrows", 2, 150, 1, 1, 1, "Though the Narrows is a relatively easy, flat hike, flash floods can turn this area very deadly, very quickly."));
        trailList.add(new Trail ("Fairyland Loop", 8, 1550, 0, 0, 1, "These unique rock columns can be found throughout the trail, eventually culminating in Fairyland Canyon, a valley of staggeringly large and vast formations as tall as 150 feet."));
        trailList.add(new Trail ("North Kaibab Trail", 28, 5850, 1, 1, 1, "As the least visited and most difficult trail on the north rim, the North Kaibab Trail offers a rare chance for solitude in one of the countrys most popular parks."));
        trailList.add(new Trail ("Exit Glacier", 2, 0, 1, 1, 0, "If you like the idea of up close and personal glacier viewing but are a novice hiker at best, the Exit Glacier Trail should check off all the boxes."));
        trailList.add(new Trail ("Moraine Lake", 2, 100, 1, 1, 1, "To explore this stunningly beautiful alpine lake, start in the town of Talkeetna, the gateway to Denali National Park."));
        trailList.add(new Trail ("Lower Bear Gulch", 2, 275, 0, 1, 0, "The easy Lower Bear Gulch Cave trail takes hikers under moss-covered boulders and across alpine springs, often at the same time."));
        trailList.add(new Trail ("Cascades Pass Trail", 7, 1700, 0, 1, 1, "The Cascades Pass Trail is really more of a series of trails, with routes ranging from mid-level day trips to extended, difficult overnight treks."));
        trailList.add(new Trail ("Greenstone Ridge Trail", 40, 3973, 0, 0, 1, "This trail is a wildlife photographers dream, with rare birds, wolves, and moose on the island."));

        return trailList;
    }

    public ArrayList<Trip> createTrips() {
        ArrayList<Trip> tripList = new ArrayList<>();


        return tripList;
    }
}
