package Bean;

import java.util.List;

/**
 * Created by admin on 2016/8/15.
 */
public class City_info {

    private List<City> city_info;

    public List<City> getCity_info() {
        return city_info;
    }

    public void setCity_info(List<City> city_info) {
        this.city_info = city_info;
    }

    public class City {


        //"city":"南子岛","cnty":"中国","id":"CN101310230","lat":"11.26","lon":"114.20","prov":"海南"
        private String city;
        private String cnty;
        private String id;
        private String lat;
        private String lon;
        private String prov;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCnty() {
            return cnty;
        }

        public void setCnty(String cnty) {
            this.cnty = cnty;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getProv() {
            return prov;
        }

        public void setProv(String prov) {
            this.prov = prov;
        }



    }


}
