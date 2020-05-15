package com.github.flickergallery.utilities;

public class ApiUrls {
    public static String flicker_url="https://api.flickr.com/services/rest/" +
            "?method=flickr.photos.getRecent" +
            "&per_page=24" +
            "&api_key=6f102c62f41998d151e5a1b48713cf13" +
            "&format=json&nojsoncallback=1" +
            "&extras=url_s" ;
    public static  String flicker_search="https://api.flickr.com/services/rest/" +
            "?method=flickr.photos.search" +
            "&api_key=6f102c62f41998d151e5a1b48713cf13" +
            "&format=json" +
            "&nojsoncallback=1" +
            "&extras=url_s";
}
