package com.tourism.microservices.controllers;

import com.tourism.microservices.models.PointOfInterest;
import com.tourism.microservices.models.RouteStep;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.ws.Response;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Iulian on 16.05.2017.
 */
class BuildMapControllerTest {
    @Test
    void createPointContent() {
        PointOfInterest ps = new PointOfInterest();
        ps.setLocationId("ChIJ9T_5iuTKj4ARe3GfygqMnbk");
        ps.setName("San Jose");
        ps.setAddress(null);
        ps.setDescription(null);
        ps.setIcon("https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png");
        Point2D.Double p2d = new Point2D.Double();
        p2d.setLocation(37.3382082, -121.8863286);
        ps.setLocation(p2d);

        BuildMapController obj = new BuildMapController();
        String response = obj.createPointContent(ps);

        String expectedResponse = "\nvar poi={}\n" +
                "poi['location']= { lat: 37.3382082, lng: -121.8863286}\n" +
                "poi['content']='<div id=\"infowindow-content\"><img id=\"place-icon\" src=\"https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png\"height=\"16\" width=\"16\"><br><span id=\"place-name\"  class=\"title\">Name: San Jose</span><br><span id=\"place-address\">Address: null</span></div>'\n" +
                " points.push(poi)\n";
        assertEquals(response, expectedResponse);
    }

    @Test
    void createStepContent() {
        RouteStep step1 = new RouteStep();
        step1.setDistance("0.3 mi");
        step1.setDuration("2 mins");
        step1.setTravelMode("DRIVING");
        step1.setInstructions("Head <b>northeast</b> on <b>E Santa Clara St</b> toward <b>N 6th St</b>");
        Point2D.Double tempPoint = new Point2D.Double();
        tempPoint.setLocation(37.3382088, -121.8863279);
        step1.setStartLocation(tempPoint);
        tempPoint = new Point2D.Double();
        tempPoint.setLocation(37.3406964, -121.8810378);
        step1.setEndLocation(tempPoint);


        BuildMapController obj = new BuildMapController();
        String response = obj.createStepContent(step1);


        String expectedResponse = "\nvar step={}\n" +
                "step['start_location']= { lat: 37.3382088, lng: -121.8863279}\n" +
                "step['end_location']= { lat: 37.3406964, lng: -121.8810378}\n" +
                "step['instruction']='Head <b>northeast</b> on <b>E Santa Clara St</b> toward <b>N 6th St</b>'\n" +
                "step['distance']='0.3 mi'\n" +
                "step['duration']='2 mins'\n" +
                " step['travel_mode']='DRIVING'\n" +
                "  points.push(step)\n";
        assertEquals(response, expectedResponse);
    }

    @Test
    void getRouteHtml() {
        List<RouteStep> listOfRoutes = new ArrayList<RouteStep>();
        RouteStep step1 = new RouteStep();
        step1.setDistance("0.3 mi");
        step1.setDuration("2 mins");
        step1.setTravelMode("DRIVING");
        step1.setInstructions("Head <b>northeast</b> on <b>E Santa Clara St</b> toward <b>N 6th St</b>");
        Point2D.Double tempPoint = new Point2D.Double();
        tempPoint.setLocation(37.3382088, -121.8863279);
        step1.setStartLocation(tempPoint);
        tempPoint = new Point2D.Double();
        tempPoint.setLocation(37.3406964, -121.8810378);
        step1.setEndLocation(tempPoint);

        listOfRoutes.add(step1);

        step1 = new RouteStep();
        step1.setDistance("0.3 mi");
        step1.setDuration("2 mins");
        step1.setTravelMode("DRIVING");
        step1.setInstructions("Head <b>northeast</b> on <b>E Santa Clara St</b> toward <b>N 6th St</b>");
        tempPoint = new Point2D.Double();
        tempPoint.setLocation(37.3382088, -121.8863279);
        step1.setStartLocation(tempPoint);
        tempPoint = new Point2D.Double();
        tempPoint.setLocation(37.3406964, -121.8810378);
        step1.setEndLocation(tempPoint);

        listOfRoutes.add(step1);

        String response = "";
        BuildMapController obj = new BuildMapController();
        for (RouteStep rd : listOfRoutes)
        {
            response += obj.createStepContent(rd);
        }
        response = obj.getRouteHtml(response);

        String expectedResponse = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Geolocation</title>\n" +
                "    <meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">\n" +
                "    <meta charset=\"utf-8\">\n" +
                "      <!-- Latest compiled and minified CSS -->\n" +
                "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
                "\n" +
                "        <!-- jQuery library -->\n" +
                "        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js\"></script>\n" +
                "        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-social/5.1.1/bootstrap-social.css\">\n" +
                "\n" +
                "        <!-- Latest compiled JavaScript -->\n" +
                "        <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n" +
                "        <script> \n" +
                "        var map, infoWindow;\n" +
                "    \tvar origin = {lat: -34.397, lng: 150.644};\n" +
                "\t    var origin2;\n" +
                "        </script>\n" +
                "    <style>\n" +
                "      /* Always set the map height explicitly to define the size of the div\n" +
                "       * element that contains the map. */\n" +
                "      #map {\n" +
                "        height: 100%;\n" +
                "      }\n" +
                "      /* Optional: Makes the sample page fill the window. */\n" +
                "      html, body {\n" +
                "        height: 100%;\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }  \n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "  <nav class=\"navbar navbar-inverse\">\n" +
                "      <div class=\"container-fluid\">\n" +
                "        <div class=\"collapse navbar-collapse\" id=\"myNavbar\">\n" +
                "\n" +
                "          <ul class=\"nav navbar-nav navbar-left\">\n" +
                "          \t\t<button onclick=\"viewPrevious()\" class=\"btn navbar-btn\">Previous</button>\n" +
                "          </ul>\n" +
                "          <ul class=\"nav navbar-nav navbar-right\">\n" +
                "              <button onclick=\"viewNext()\" class=\"btn navbar-btn\">Next</button>\n" +
                "          </ul>\n" +
                "          \n" +
                "         </div>\n" +
                "       </div>\n" +
                "   </nav>\n" +
                "   <div class=\"well\" id=\"stepsDisplay\">Step: 0 / 0</div>\n" +
                " <div id=\"map\" style=\"max-height: 80%;\"></div>  \n" +
                "   <script>\n" +
                "    var points = [];\n" +
                "    var counter = 1;\n" +
                "    var markers = [];\n" +
                "    var directionsService;\n" +
                "    var directionsDisplay;\n" +
                "\n" +
                "\n" +
                "var step={}\n" +
                "step['start_location']= { lat: 37.3382088, lng: -121.8863279}\n" +
                "step['end_location']= { lat: 37.3406964, lng: -121.8810378}\n" +
                "step['instruction']='Head <b>northeast</b> on <b>E Santa Clara St</b> toward <b>N 6th St</b>'\n" +
                "step['distance']='0.3 mi'\n" +
                "step['duration']='2 mins'\n" +
                " step['travel_mode']='DRIVING'\n" +
                "  points.push(step)\n" +
                "\n" +
                "var step={}\n" +
                "step['start_location']= { lat: 37.3382088, lng: -121.8863279}\n" +
                "step['end_location']= { lat: 37.3406964, lng: -121.8810378}\n" +
                "step['instruction']='Head <b>northeast</b> on <b>E Santa Clara St</b> toward <b>N 6th St</b>'\n" +
                "step['distance']='0.3 mi'\n" +
                "step['duration']='2 mins'\n" +
                " step['travel_mode']='DRIVING'\n" +
                "  points.push(step)\n" +
                "\n" +
                "    function initMap(zoom_ = 16) {\n" +
                "    \tdirectionsService = new google.maps.DirectionsService;\n" +
                "        directionsDisplay = new google.maps.DirectionsRenderer;\n" +
                "        map = new google.maps.Map(document.getElementById('map'), {\n" +
                "          center: points[0]['location'],\n" +
                "          zoom: zoom_\n" +
                "        });\n" +
                "        directionsDisplay.setMap(map);\n" +
                "        calculateAndDisplayRoute(0);     \n" +
                "      }\n" +
                "\n" +
                "\tfunction calculateAndDisplayRoute(id) {\n" +
                "        directionsService.route({\n" +
                "          origin: points[id]['start_location'],\n" +
                "          destination: points[id]['end_location'],\n" +
                "          travelMode: points[id]['travel_mode']\n" +
                "        }, function(response, status) {\n" +
                "          if (status === 'OK') {\n" +
                "            directionsDisplay.setDirections(response);\n" +
                "          } else {\n" +
                "            window.alert('Directions request failed due to ' + status);\n" +
                "          }\n" +
                "        });\n" +
                "        var handle = document.getElementById('stepsDisplay');\n" +
                "        handle.innerHTML = 'Step: ' + id.toString() + ' / ' + points.length.toString() + ' <br> Instruction: ' + points[id]['instruction'] + ' <br> Distance: ' + points[id]['distance'] + ' <br> Duration: ' + points[id]['duration'];\n" +
                "      }\n" +
                "\n" +
                "           \n" +
                "      function viewNext()\n" +
                "      {\n" +
                "        if (counter == points.length)\n" +
                "        {\n" +
                "          counter = 0\n" +
                "        }\n" +
                "        calculateAndDisplayRoute(counter);\n" +
                "        counter = counter + 1;\n" +
                "      }\n" +
                "       \n" +
                "      function viewPrevious()\n" +
                "      {\n" +
                "\n" +
                "      \tcounter = counter - 1;\n" +
                "        if (counter == -1)\n" +
                "        {\n" +
                "          counter = points.length - 1;\n" +
                "        }\n" +
                "        calculateAndDisplayRoute(counter);\n" +
                "        \n" +
                "      }\n" +
                "\n" +
                "   </script>\n" +
                "    <script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyDXYDYmpNXAo01aw71oMT6KJXoI1aTTyvg&libraries=places&callback=initMap\"\n" +
                "        async defer></script>\n" +
                "  </body>\n" +
                "</html>";

        assert expectedResponse.equals(response);
    }

    @Test
    void getPointHtml() {
        List<PointOfInterest> allPoints = new ArrayList<PointOfInterest>();
        PointOfInterest ps = new PointOfInterest();
        ps.setLocationId("ChIJ9T_5iuTKj4ARe3GfygqMnbk");
        ps.setName("San Jose");
        ps.setAddress(null);
        ps.setDescription(null);
        ps.setIcon("https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png");
        Point2D.Double p2d = new Point2D.Double();
        p2d.setLocation(37.3382082, -121.8863286);
        ps.setLocation(p2d);

        allPoints.add(ps);

        ps = new PointOfInterest();
        ps.setLocationId("ChIJ9T_5iuTKj4ARe3GfygqMnbk");
        ps.setName("San Jose");
        ps.setAddress(null);
        ps.setDescription(null);
        ps.setIcon("https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png");
        p2d = new Point2D.Double();
        p2d.setLocation(37.3382082, -121.8863286);
        ps.setLocation(p2d);

        allPoints.add(ps);


        String response = "";
        BuildMapController obj = new BuildMapController();
        for (PointOfInterest cs : allPoints)
        {
            response += obj.createPointContent(cs);
        }
        response = obj.getPointHtml(response);

        String expectedResponse = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Geolocation</title>\n" +
                "    <meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">\n" +
                "    <meta charset=\"utf-8\">\n" +
                "      <!-- Latest compiled and minified CSS -->\n" +
                "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
                "\n" +
                "        <!-- jQuery library -->\n" +
                "        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js\"></script>\n" +
                "        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-social/5.1.1/bootstrap-social.css\">\n" +
                "\n" +
                "        <!-- Latest compiled JavaScript -->\n" +
                "        <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n" +
                "        <script> \n" +
                "        var map, infoWindow;\n" +
                "    \tvar origin = {lat: -34.397, lng: 150.644};\n" +
                "\t    var origin2;\n" +
                "        </script>\n" +
                "    <style>\n" +
                "      /* Always set the map height explicitly to define the size of the div\n" +
                "       * element that contains the map. */\n" +
                "      #map {\n" +
                "        height: 100%;\n" +
                "      }\n" +
                "      /* Optional: Makes the sample page fill the window. */\n" +
                "      html, body {\n" +
                "        height: 100%;\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }  \n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "  <nav class=\"navbar navbar-inverse\">\n" +
                "      <div class=\"container-fluid\">\n" +
                "        <div class=\"collapse navbar-collapse\" id=\"myNavbar\">\n" +
                "          <ul class=\"nav navbar-nav navbar-right\">\n" +
                "              <button onclick=\"viewNext()\" class=\"btn navbar-btn\">Next</button>\n" +
                "          </ul>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "        </nav>\n" +
                " <div id=\"map\" style=\"max-height: 80%;\"></div>    <script>\n" +
                "    var points = [];\n" +
                "    var counter = 1;\n" +
                "    var markers = [];\n" +
                "\n" +
                "\n" +
                "var poi={}\n" +
                "poi['location']= { lat: 37.3382082, lng: -121.8863286}\n" +
                "poi['content']='<div id=\"infowindow-content\"><img id=\"place-icon\" src=\"https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png\"height=\"16\" width=\"16\"><br><span id=\"place-name\"  class=\"title\">Name: San Jose</span><br><span id=\"place-address\">Address: null</span></div>'\n" +
                " points.push(poi)\n" +
                "\n" +
                "var poi={}\n" +
                "poi['location']= { lat: 37.3382082, lng: -121.8863286}\n" +
                "poi['content']='<div id=\"infowindow-content\"><img id=\"place-icon\" src=\"https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png\"height=\"16\" width=\"16\"><br><span id=\"place-name\"  class=\"title\">Name: San Jose</span><br><span id=\"place-address\">Address: null</span></div>'\n" +
                " points.push(poi)\n" +
                "\n" +
                "    function initMap(zoom_ = 16) {\n" +
                "        map = new google.maps.Map(document.getElementById('map'), {\n" +
                "          center: points[0]['location'],\n" +
                "          zoom: zoom_\n" +
                "        });\n" +
                "        infoWindow = new google.maps.InfoWindow;\n" +
                "        infoWindow.open(map)\n" +
                "        infoWindow.setPosition(points[0]['location'])\n" +
                "        infoWindow.setContent(points[0]['content'])\n" +
                " addMarker(points[0]['location'])        \n" +
                "      }\n" +
                "\n" +
                "           \n" +
                "      function viewNext()\n" +
                "      {\n" +
                "\n" +
                "\n" +
                "        if (counter == points.length)\n" +
                "        {\n" +
                "          counter = 0\n" +
                "        }\n" +
                "\n" +
                "        console.log(counter)\n" +
                "        console.log(points[counter])\n" +
                "        map.setCenter(points[counter]['location'])\n" +
                "        infoWindow.setPosition(points[counter]['location'])\n" +
                "        infoWindow.setContent(points[counter]['content'])\n" +
                "        counter = counter + 1\n" +
                " addMarker(points[counter]['location'])      }\n" +
                "            \n" +
                "function addMarker(location) {\n" +
                "        var marker = new google.maps.Marker({\n" +
                "          position: location,\n" +
                "          map: map\n" +
                "        });\n" +
                "        markers.push(marker);\n" +
                "      }    </script>\n" +
                "    <script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyDXYDYmpNXAo01aw71oMT6KJXoI1aTTyvg&libraries=places&callback=initMap\"\n" +
                "        async defer></script>\n" +
                "  </body>\n" +
                "</html>";

        assertEquals(response, expectedResponse);

    }

    @Test
    void getPoints() {
        List<PointOfInterest> allPoints = new ArrayList<PointOfInterest>();
        PointOfInterest ps = new PointOfInterest();
        ps.setLocationId("ChIJ9T_5iuTKj4ARe3GfygqMnbk");
        ps.setName("San Jose");
        ps.setAddress(null);
        ps.setDescription(null);
        ps.setIcon("https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png");
        Point2D.Double p2d = new Point2D.Double();
        p2d.setLocation(37.3382082, -121.8863286);
        ps.setLocation(p2d);

        allPoints.add(ps);

        ps = new PointOfInterest();
        ps.setLocationId("ChIJ9T_5iuTKj4ARe3GfygqMnbk");
        ps.setName("San Jose");
        ps.setAddress(null);
        ps.setDescription(null);
        ps.setIcon("https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png");
        p2d = new Point2D.Double();
        p2d.setLocation(37.3382082, -121.8863286);
        ps.setLocation(p2d);

        allPoints.add(ps);

        BuildMapController obj = new BuildMapController();
        ResponseEntity<String> result;
        result = obj.getPoints(allPoints);
        String expectedResult = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Geolocation</title>\n" +
                "    <meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">\n" +
                "    <meta charset=\"utf-8\">\n" +
                "      <!-- Latest compiled and minified CSS -->\n" +
                "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
                "\n" +
                "        <!-- jQuery library -->\n" +
                "        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js\"></script>\n" +
                "        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-social/5.1.1/bootstrap-social.css\">\n" +
                "\n" +
                "        <!-- Latest compiled JavaScript -->\n" +
                "        <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n" +
                "        <script> \n" +
                "        var map, infoWindow;\n" +
                "    \tvar origin = {lat: -34.397, lng: 150.644};\n" +
                "\t    var origin2;\n" +
                "        </script>\n" +
                "    <style>\n" +
                "      /* Always set the map height explicitly to define the size of the div\n" +
                "       * element that contains the map. */\n" +
                "      #map {\n" +
                "        height: 100%;\n" +
                "      }\n" +
                "      /* Optional: Makes the sample page fill the window. */\n" +
                "      html, body {\n" +
                "        height: 100%;\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }  \n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "  <nav class=\"navbar navbar-inverse\">\n" +
                "      <div class=\"container-fluid\">\n" +
                "        <div class=\"collapse navbar-collapse\" id=\"myNavbar\">\n" +
                "          <ul class=\"nav navbar-nav navbar-right\">\n" +
                "              <button onclick=\"viewNext()\" class=\"btn navbar-btn\">Next</button>\n" +
                "          </ul>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "        </nav>\n" +
                " <div id=\"map\" style=\"max-height: 80%;\"></div>    <script>\n" +
                "    var points = [];\n" +
                "    var counter = 1;\n" +
                "    var markers = [];\n" +
                "\n" +
                "\n" +
                "var poi={}\n" +
                "poi['location']= { lat: 37.3382082, lng: -121.8863286}\n" +
                "poi['content']='<div id=\"infowindow-content\"><img id=\"place-icon\" src=\"https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png\"height=\"16\" width=\"16\"><br><span id=\"place-name\"  class=\"title\">Name: San Jose</span><br><span id=\"place-address\">Address: null</span></div>'\n" +
                " points.push(poi)\n" +
                "\n" +
                "var poi={}\n" +
                "poi['location']= { lat: 37.3382082, lng: -121.8863286}\n" +
                "poi['content']='<div id=\"infowindow-content\"><img id=\"place-icon\" src=\"https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png\"height=\"16\" width=\"16\"><br><span id=\"place-name\"  class=\"title\">Name: San Jose</span><br><span id=\"place-address\">Address: null</span></div>'\n" +
                " points.push(poi)\n" +
                "\n" +
                "    function initMap(zoom_ = 16) {\n" +
                "        map = new google.maps.Map(document.getElementById('map'), {\n" +
                "          center: points[0]['location'],\n" +
                "          zoom: zoom_\n" +
                "        });\n" +
                "        infoWindow = new google.maps.InfoWindow;\n" +
                "        infoWindow.open(map)\n" +
                "        infoWindow.setPosition(points[0]['location'])\n" +
                "        infoWindow.setContent(points[0]['content'])\n" +
                " addMarker(points[0]['location'])        \n" +
                "      }\n" +
                "\n" +
                "           \n" +
                "      function viewNext()\n" +
                "      {\n" +
                "\n" +
                "\n" +
                "        if (counter == points.length)\n" +
                "        {\n" +
                "          counter = 0\n" +
                "        }\n" +
                "\n" +
                "        console.log(counter)\n" +
                "        console.log(points[counter])\n" +
                "        map.setCenter(points[counter]['location'])\n" +
                "        infoWindow.setPosition(points[counter]['location'])\n" +
                "        infoWindow.setContent(points[counter]['content'])\n" +
                "        counter = counter + 1\n" +
                " addMarker(points[counter]['location'])      }\n" +
                "            \n" +
                "function addMarker(location) {\n" +
                "        var marker = new google.maps.Marker({\n" +
                "          position: location,\n" +
                "          map: map\n" +
                "        });\n" +
                "        markers.push(marker);\n" +
                "      }    </script>\n" +
                "    <script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyDXYDYmpNXAo01aw71oMT6KJXoI1aTTyvg&libraries=places&callback=initMap\"\n" +
                "        async defer></script>\n" +
                "  </body>\n" +
                "</html>";
        assertEquals(result, new ResponseEntity<String>(expectedResult, HttpStatus.OK));
    }

    @Test
    void getRoute() {
        List<RouteStep> listOfRoutes = new ArrayList<RouteStep>();
        RouteStep step1 = new RouteStep();
        step1.setDistance("0.3 mi");
        step1.setDuration("2 mins");
        step1.setTravelMode("DRIVING");
        step1.setInstructions("Head <b>northeast</b> on <b>E Santa Clara St</b> toward <b>N 6th St</b>");
        Point2D.Double tempPoint = new Point2D.Double();
        tempPoint.setLocation(37.3382088, -121.8863279);
        step1.setStartLocation(tempPoint);
        tempPoint = new Point2D.Double();
        tempPoint.setLocation(37.3406964, -121.8810378);
        step1.setEndLocation(tempPoint);

        listOfRoutes.add(step1);

        step1 = new RouteStep();
        step1.setDistance("0.3 mi");
        step1.setDuration("2 mins");
        step1.setTravelMode("DRIVING");
        step1.setInstructions("Head <b>northeast</b> on <b>E Santa Clara St</b> toward <b>N 6th St</b>");
        tempPoint = new Point2D.Double();
        tempPoint.setLocation(37.3382088, -121.8863279);
        step1.setStartLocation(tempPoint);
        tempPoint = new Point2D.Double();
        tempPoint.setLocation(37.3406964, -121.8810378);
        step1.setEndLocation(tempPoint);

        listOfRoutes.add(step1);

        BuildMapController obj = new BuildMapController();
        ResponseEntity<String> result;
        result = obj.getRoute(listOfRoutes);

        String expectedResult = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Geolocation</title>\n" +
                "    <meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">\n" +
                "    <meta charset=\"utf-8\">\n" +
                "      <!-- Latest compiled and minified CSS -->\n" +
                "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
                "\n" +
                "        <!-- jQuery library -->\n" +
                "        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js\"></script>\n" +
                "        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-social/5.1.1/bootstrap-social.css\">\n" +
                "\n" +
                "        <!-- Latest compiled JavaScript -->\n" +
                "        <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n" +
                "        <script> \n" +
                "        var map, infoWindow;\n" +
                "    \tvar origin = {lat: -34.397, lng: 150.644};\n" +
                "\t    var origin2;\n" +
                "        </script>\n" +
                "    <style>\n" +
                "      /* Always set the map height explicitly to define the size of the div\n" +
                "       * element that contains the map. */\n" +
                "      #map {\n" +
                "        height: 100%;\n" +
                "      }\n" +
                "      /* Optional: Makes the sample page fill the window. */\n" +
                "      html, body {\n" +
                "        height: 100%;\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }  \n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "  <nav class=\"navbar navbar-inverse\">\n" +
                "      <div class=\"container-fluid\">\n" +
                "        <div class=\"collapse navbar-collapse\" id=\"myNavbar\">\n" +
                "\n" +
                "          <ul class=\"nav navbar-nav navbar-left\">\n" +
                "          \t\t<button onclick=\"viewPrevious()\" class=\"btn navbar-btn\">Previous</button>\n" +
                "          </ul>\n" +
                "          <ul class=\"nav navbar-nav navbar-right\">\n" +
                "              <button onclick=\"viewNext()\" class=\"btn navbar-btn\">Next</button>\n" +
                "          </ul>\n" +
                "          \n" +
                "         </div>\n" +
                "       </div>\n" +
                "   </nav>\n" +
                "   <div class=\"well\" id=\"stepsDisplay\">Step: 0 / 0</div>\n" +
                " <div id=\"map\" style=\"max-height: 80%;\"></div>  \n" +
                "   <script>\n" +
                "    var points = [];\n" +
                "    var counter = 1;\n" +
                "    var markers = [];\n" +
                "    var directionsService;\n" +
                "    var directionsDisplay;\n" +
                "\n" +
                "\n" +
                "var step={}\n" +
                "step['start_location']= { lat: 37.3382088, lng: -121.8863279}\n" +
                "step['end_location']= { lat: 37.3406964, lng: -121.8810378}\n" +
                "step['instruction']='Head <b>northeast</b> on <b>E Santa Clara St</b> toward <b>N 6th St</b>'\n" +
                "step['distance']='0.3 mi'\n" +
                "step['duration']='2 mins'\n" +
                " step['travel_mode']='DRIVING'\n" +
                "  points.push(step)\n" +
                "\n" +
                "var step={}\n" +
                "step['start_location']= { lat: 37.3382088, lng: -121.8863279}\n" +
                "step['end_location']= { lat: 37.3406964, lng: -121.8810378}\n" +
                "step['instruction']='Head <b>northeast</b> on <b>E Santa Clara St</b> toward <b>N 6th St</b>'\n" +
                "step['distance']='0.3 mi'\n" +
                "step['duration']='2 mins'\n" +
                " step['travel_mode']='DRIVING'\n" +
                "  points.push(step)\n" +
                "\n" +
                "    function initMap(zoom_ = 16) {\n" +
                "    \tdirectionsService = new google.maps.DirectionsService;\n" +
                "        directionsDisplay = new google.maps.DirectionsRenderer;\n" +
                "        map = new google.maps.Map(document.getElementById('map'), {\n" +
                "          center: points[0]['location'],\n" +
                "          zoom: zoom_\n" +
                "        });\n" +
                "        directionsDisplay.setMap(map);\n" +
                "        calculateAndDisplayRoute(0);     \n" +
                "      }\n" +
                "\n" +
                "\tfunction calculateAndDisplayRoute(id) {\n" +
                "        directionsService.route({\n" +
                "          origin: points[id]['start_location'],\n" +
                "          destination: points[id]['end_location'],\n" +
                "          travelMode: points[id]['travel_mode']\n" +
                "        }, function(response, status) {\n" +
                "          if (status === 'OK') {\n" +
                "            directionsDisplay.setDirections(response);\n" +
                "          } else {\n" +
                "            window.alert('Directions request failed due to ' + status);\n" +
                "          }\n" +
                "        });\n" +
                "        var handle = document.getElementById('stepsDisplay');\n" +
                "        handle.innerHTML = 'Step: ' + id.toString() + ' / ' + points.length.toString() + ' <br> Instruction: ' + points[id]['instruction'] + ' <br> Distance: ' + points[id]['distance'] + ' <br> Duration: ' + points[id]['duration'];\n" +
                "      }\n" +
                "\n" +
                "           \n" +
                "      function viewNext()\n" +
                "      {\n" +
                "        if (counter == points.length)\n" +
                "        {\n" +
                "          counter = 0\n" +
                "        }\n" +
                "        calculateAndDisplayRoute(counter);\n" +
                "        counter = counter + 1;\n" +
                "      }\n" +
                "       \n" +
                "      function viewPrevious()\n" +
                "      {\n" +
                "\n" +
                "      \tcounter = counter - 1;\n" +
                "        if (counter == -1)\n" +
                "        {\n" +
                "          counter = points.length - 1;\n" +
                "        }\n" +
                "        calculateAndDisplayRoute(counter);\n" +
                "        \n" +
                "      }\n" +
                "\n" +
                "   </script>\n" +
                "    <script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyDXYDYmpNXAo01aw71oMT6KJXoI1aTTyvg&libraries=places&callback=initMap\"\n" +
                "        async defer></script>\n" +
                "  </body>\n" +
                "</html>";

        assertEquals(result, new ResponseEntity<String>(expectedResult, HttpStatus.OK));
    }

}