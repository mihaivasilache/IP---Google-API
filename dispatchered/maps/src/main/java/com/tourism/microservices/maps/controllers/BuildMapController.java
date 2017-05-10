package com.tourism.microservices.maps.controllers;

import com.tourism.microservices.maps.models.*;
import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by gbalan on 5/2/2017.
 */



@RestController
@RequestMapping("maps")
public class BuildMapController {

    public String createPointContent(PointOfInterest pr)
    {
        String result = "\nvar poi={}\n" +
                "poi['location']= { lat: " + pr.getLocation().getX() + ", lng: " + pr.getLocation().getY() + "}\n"+
                "poi['content']=" +
                "'<div id=\"infowindow-content\">" +
                    "<img id=\"place-icon\" src=\"" + pr.getIcon() + "\"height=\"16\" width=\"16\"><br><span id=\"place-name\"  class=\"title\">Name: " + pr.getName().replace("\'", " ").replace("\"", " ") +
                "</span><br><span id=\"place-address\">Address: " + pr.getAddress() + "</span></div>'\n points.push(poi)\n";
        return result;

    }

    public String createStepContent(RouteStep pr)
    {
        String result = "\nvar step={}\n" +
                "step['start_location']= { lat: " + pr.getStartLocation().getX() + ", lng: " + pr.getStartLocation().getY() + "}\n"+
                "step['end_location']= { lat: " + pr.getEndLocation().getX() + ", lng: " + pr.getEndLocation().getY() + "}\n"+
                "step['instruction']='" + pr.getInstructions() + "'\n" +
                "step['distance']='" + pr.getDistance() + "'\n" +
                "step['duration']='" + pr.getDuration() + "'\n " +
                "step['travel_mode']='" + pr.getTravelMode() + "'\n " +
                " points.push(step)\n";
        return result;
    }

    public String getRouteHtml(String steps)
    {
        String result = "";
        result += "<!DOCTYPE html>\n" +
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
                "\n" + steps +
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
        return result;
    }

    public String getPointHtml(String pois)
    {
        String result = "<!DOCTYPE html>\n" +
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
                " <div id=\"map\" style=\"max-height: 80%;\"></div>" +
                "    <script>\n" +
                "    var points = [];\n" +
                "    var counter = 1;\n" +
                "    var markers = [];\n" +
                "\n" + pois + "\n" +
                "    function initMap(zoom_ = 16) {\n" +
                "        map = new google.maps.Map(document.getElementById('map'), {\n" +
                "          center: points[0]['location'],\n" +
                "          zoom: zoom_\n" +
                "        });\n" +
                "        infoWindow = new google.maps.InfoWindow;\n" +
                "        infoWindow.open(map)\n" +
                "        infoWindow.setPosition(points[0]['location'])\n" +
                "        infoWindow.setContent(points[0]['content'])\n" +
                " addMarker(points[0]['location'])"+
                "        \n" +
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
                " addMarker(points[counter]['location'])"+
                "      }\n" +
                "            \n" +
                "function addMarker(location) {\n" +
                "        var marker = new google.maps.Marker({\n" +
                "          position: location,\n" +
                "          map: map\n" +
                "        });\n" +
                "        markers.push(marker);\n" +
                "      }" +
                "    </script>\n" +
                "    <script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyDXYDYmpNXAo01aw71oMT6KJXoI1aTTyvg&libraries=places&callback=initMap\"\n" +
                "        async defer></script>\n" +
                "  </body>\n" +
                "</html>";
        return result;
    }

    @RequestMapping(value = "/points", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> getPoints(@RequestBody List<PointOfInterest> poisList)
    {
        String response = "";
        for (PointOfInterest cs : poisList)
        {
            response += createPointContent(cs);
        }
        response = getPointHtml(response);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @RequestMapping(value = "/route", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> getRoute(@RequestBody List<RouteStep> steps)
    {
        String response = "";
        for (RouteStep rd : steps)
        {
            response += createStepContent(rd);
        }
        response = getRouteHtml(response);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
