package com.tourism.microservices.controllers;

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

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> get()
    {
        String response = "<!DOCTYPE html>\n" +
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
                "    <div id=\"map\" style=\"max-height: 80%;\"></div>\n" +
                "    <div id=\"infowindow-content\" class=\"hidden\">\n" +
                "      <img id=\"place-icon\" src=\"\" height=\"16\" width=\"16\"><br>\n" +
                "      <span id=\"place-name\"  class=\"title\"></span><br>\n" +
                "      <span id=\"place-address\"></span>\n" +
                "    </div>\n" +
                "    </div>\n" +
                "    <script>\n" +
                "      // Note: This example requires that you consent to location sharing when\n" +
                "      // prompted by your browser. If you see the error \"The Geolocation service\n" +
                "      // failed.\", it means you probably did not give permission for the browser to\n" +
                "      // locate you.\n" +
                "      function initMap(zoom_ = 17) {\n" +
                "        map = new google.maps.Map(document.getElementById('map'), {\n" +
                "          center: {lat: -34.397, lng: 150.644},\n" +
                "          zoom: zoom_\n" +
                "        });\n" +
                "        infoWindow = new google.maps.InfoWindow;\n" +
                "      \t\n" +
                "        // Try HTML5 geolocation.\n" +
                "        // if (navigator.geolocation) {\n" +
                "        //   navigator.geolocation.getCurrentPosition(function(position) {\n" +
                "        //     var pos = {\n" +
                "        //       lat: position.coords.latitude,\n" +
                "        //       lng: position.coords.longitude\n" +
                "        //     };\n" +
                "        //     origin.lat = pos.lat;\n" +
                "        //     origin.lng = pos.lng;\n" +
                "        //     map.addListener('dblclick', function(event) {\n" +
                "        //         addMarker(event.latLng);\n" +
                "        //     });\n" +
                "            \n" +
                "        //     infoWindow.setPosition(pos);\n" +
                "        //     infoWindow.setContent('Location found.');\n" +
                "        //     infoWindow.open(map);\n" +
                "        //     map.setCenter(pos);\n" +
                "        //   }, function() {\n" +
                "        //     handleLocationError(true, infoWindow, map.getCenter());\n" +
                "        //   });\n" +
                "        // } else {\n" +
                "        //   // Browser doesn't support Geolocation\n" +
                "        //   handleLocationError(false, infoWindow, map.getCenter());\n" +
                "        // }\n" +
                "      }\n" +
                "      function addMarker(location) {\n" +
                "        var marker = new google.maps.Marker({\n" +
                "          position: location,\n" +
                "          map: map\n" +
                "        });\n" +
                "        markers.push(marker);\n" +
                "      }\n" +
                "\n" +
                "      ClickEventHandler.prototype.calculateAndDisplayRoute = function(placeId) {\n" +
                "        var me = this;\n" +
                "        this.directionsService.route({\n" +
                "          origin: this.origin,\n" +
                "          destination: {placeId: placeId},\n" +
                "          travelMode: 'WALKING'\n" +
                "        }, function(response, status) {\n" +
                "          if (status === 'OK') {\n" +
                "            me.directionsDisplay.setDirections(response);\n" +
                "          } else {\n" +
                "            window.alert('Directions request failed due to ' + status);\n" +
                "          }\n" +
                "        });\n" +
                "      };\n" +
                "\n" +
                "      \n" +
                "    </script>\n" +
                "    <!-- <script async defer\n" +
                "    src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyDXYDYmpNXAo01aw71oMT6KJXoI1aTTyvg&libraries=places&callback=initMap\">\n" +
                "    </script> -->\n" +
                "    <script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyDXYDYmpNXAo01aw71oMT6KJXoI1aTTyvg&libraries=places&callback=initMap\"\n" +
                "        async defer></script>\n" +
                "  </body>\n" +
                "</html>";
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
