import React, { Component } from 'react';
import {
  withGoogleMap,
  GoogleMap,
  Marker,
  Polyline
} from "react-google-maps";
import { Form, Button, Col , ToggleButton } from "react-bootstrap";
 

function distance(lat1, lng1, lat2, lng2) {
	if ((lat1 == lat2) && (lng1 == lng2)) {
		return 0;
	}
	else {
		var radlat1 = Math.PI * lat1/180;
		var radlat2 = Math.PI * lat2/180;
		var theta = lng1-lng2;
		var radtheta = Math.PI * theta/180;
		var dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
		if (dist > 1) {
			dist = 1;
		}
		dist = Math.acos(dist);
		dist = dist * 180/Math.PI;
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344 
		return dist;
	}
}

class Map extends Component {
    constructor(props) {
      super(props);
      this.handleSubmit = this.handleSubmit.bind(this);
      this.handleDistanceChange = this.handleDistanceChange.bind(this);
      this.handlePlateChange = this.handlePlateChange.bind(this);
      this.handleTypeChange = this.handleTypeChange.bind(this);
      this.addCar = this.addCar.bind(this);
      this.onMapClick = this.onMapClick.bind(this);
      this.setLocation = this.setLocation.bind(this);
      this.showDistance = this.showDistance.bind(this);
      this.alertDistance = this.alertDistance.bind(this);
      this.hideLabel = this.hideLabel.bind(this);

      this.settingLocation = false;

      this.hotelIconMarker = require('../assets/icn-hotel.png');
      this.stationIconMarker = require('../assets/icn-gas-station.png');
      this.restaurantIconMarker = require('../assets/icn-restaurant.png');
      this.pathIconMarker = require('../assets/icn-path.png');
      this.firstPathIconMarker = require('../assets/icn-first-location.png');
      this.currentPathIconMarker = require('../assets/icn-current-location.png');

      this.stateChanged = false;

      this.newRestaurants = [];
      this.newStations = [];
      this.newHotels = [];
      this.newLocations = [];

      this.newFirstLocation = null;
      this.newCurrentLocation = null;
      this.clickedMarker = null;

      this.state = {
        distance: 1000,
        type: "",
        plate: "",
        markers: [],
        currentLocation: null,
        currentCenter: null,
        clickedMarker: null
      }
      this.apiKey = "AIzaSyDOS8jz32dPSNYqJWyehISYmERu85puNSQ";
      this.apiURL = "http://127.0.0.1:8080/api/";
      this.currentCenter = { lat: 38.7223, lng: -9.18 };
    }

    hideLabel() {
      document.getElementById("label").hidden = true;
    }

    addCar() {
       var plate = window.prompt("Please Enter car plate:","");
       if(plate == "" || plate == null) {
         return;
       }
       var headers = new Headers();
       headers.append("Content-Type", "application/json");
       fetch(this.apiURL + "trucks",
       {
         method: "Post",
         body: JSON.stringify(
           {
            licensePlate: plate
           }
         ),
         headers: headers
       }).then(
         response => {
           response.json()
         }
       )
    }

    setLocation() {
      console.log(this.settingLocation)
      this.settingLocation = !this.settingLocation;
    }

    showDistance(marker) {
      this.hideLabel();
      this.clickedMarker = {
        lat: marker.latLng.lat(),
        lng: marker.latLng.lng()
      };
      console.log(this.currentCenter, this.clickedMarker)
      this.stateChanged = true;
    }

    alertDistance(e) {
      console.log(e.tb.screenX, e.tb.screenY);
      document.getElementById("label").hidden = false;
      document.getElementById("label").innerText = "distance is " + distance(this.currentCenter.lat, this.currentCenter.lng, this.clickedMarker.lat, this.clickedMarker.lng).toString().substr(0,3) + "km";
      document.getElementById("label").style.top = e.tb.screenY.toString() + "px";
      document.getElementById("label").style.left = e.tb.screenX.toString() + "px";
      document.getElementById("label").style.position = "absolute";
      document.getElementById("label").style.width = "100px";
      document.getElementById("label").style.background = "#3f704d";
      document.getElementById("label").style.color = "white";
      document.getElementById("label").style.opacity = 0.8;
    }

    onMapClick(t, map, coord) {
      if(!this.settingLocation) {
        return;
      }
      var headers = new Headers();
      headers.append("Content-Type", "application/json");
      fetch(this.apiURL + "trucks/" + this.state.plate,
      {
        method: "Post",
        body: JSON.stringify(
          {
           lng: t.latLng.lng(),
           lat: t.latLng.lat()
          }
        ),
        headers: headers
      }).then(
        response => {
          response.json()
        }
      )
    }

    handleSubmit(event) {
      event.preventDefault();
      this.newLocations = [];
      this.clickedMarker = null;
      this.hideLabel();
      this.setState({
        markers: []
      })
      this.getLocations(this.state.type, this.state.distance);
      this.getTruck(this.state.plate);

    }

    handleTypeChange(e) {
      this.setState(
        {
          type: e.target.value
        }
      );
    }

    handlePlateChange(e) {
      this.setState(
        {
          plate: e.target.value
        }
      );
    }

    handleDistanceChange(e) {
      this.setState(
        {
          distance: e.target.value
        }
      );
    }

    getLocations(type, distance) {
      var headers = new Headers();
      headers.append("Content-Type", "application/json");

      if(type == null && distance == null) {
        return;
      }
      if (type === "all") {
        fetch(this.apiURL + "pois", {
          method: "Post",
          body: JSON.stringify({
            "type": "hotel",
            "distance": distance,
            "lng": this.currentCenter.lng,
            "lat": this.currentCenter.lat
          }),
          headers: headers
        })
        .then((response) => {
          return response.json();
        })
        .then((result) => {
          console.log("hotel", result.results);
          this.newLocations = this.newLocations.concat(result.results.map(
            e => {
              var lat = e.geometry.location.lat;
              var lng = e.geometry.location.lng;
              return {
                lat: lat,
                lng: lng,
                type: "hotel"
              }
            }
          ));
          fetch(this.apiURL + "pois", {
            method: "Post",
            body: JSON.stringify({
              "type": "restaurant",
              "distance": distance,
              "lng": this.currentCenter.lng,
              "lat": this.currentCenter.lat
            }),
            headers: headers
          })
          .then((response) => {
            return response.json();
          })
          .then((result) => {
            console.log("restaurant", result.results);
            this.newLocations = this.newLocations.concat(result.results.map(
              e => {
                var lat = e.geometry.location.lat;
                var lng = e.geometry.location.lng;
                return {
                  lat: lat,
                  lng: lng,
                  type: "restaurant"
                }
              }
            ));
            fetch(this.apiURL + "pois", {
              method: "Post",
              body: JSON.stringify({
                "type": "gas_station",
                "distance": distance,
                "lng": this.currentCenter.lng,
                "lat": this.currentCenter.lat
              }),
              headers: headers
            })
            .then((response) => {
              return response.json();
            })
            .then((result) => {
              console.log("gas_station", result.results);
              this.newLocations = this.newLocations.concat(result.results.map(
                e => {
                  var lat = e.geometry.location.lat;
                  var lng = e.geometry.location.lng;
                  return {
                    lat: lat,
                    lng: lng,
                    type: "gas_station"
                  }
                }
              ));
              this.stateChanged = true;
              console.log(type, this.newLocations)
            });
          });
        });
      }
      else {
        fetch(this.apiURL + "pois", {
          method: "Post",
          body: JSON.stringify({
            "type": type,
            "distance": distance,
            "lng": this.currentCenter.lng,
            "lat": this.currentCenter.lat
          }),
          headers: headers
        })
        .then((response) => {
          return response.json();
        })
        .then((result) => {
          this.newLocations = this.newLocations.concat(result.results.map(
            e => {
              var lat = e.geometry.location.lat;
              var lng = e.geometry.location.lng;
              return {
                lat: lat,
                lng: lng,
                type: type
              }
            }
          ));
          this.stateChanged = true;
          console.log(type, this.newLocations)
        });
      }
    }

    getTruck(plate) {
      if(plate === "") {
        return;
      }
      fetch(this.apiURL + "trucks/" + plate)
      .then((response) => {
        return response.json();
      })
      .then((result) => {
        this.newLocations = this.newLocations.concat(result.locations.map(
          (e, index) => {
            var pathType = "path";
            if (index === 0) {
              pathType = "first";
            }
            else if (index === result.locations.length - 1) {
              pathType = "current";
              this.currentCenter = {
                lat: e.lat,
                lng: e.lng
              }
            }
            return {
              lat: e.lat,
              lng: e.lng,
              type: pathType
            }
          }
        ));
        this.stateChanged = true;
      });
    }

    resetMarkers() {
      if (this.stateChanged) {
        if(this.clickedMarker == null) {
          this.setState({
            clickedMarker: this.newLocation
          })
        }
        this.setState({
          markers: this.newLocations,
          currentCenter: this.newLocation,
          clickedMarker: this.clickedMarker
        });
        this.stateChanged = false;
        console.log(this.state);
      }
    }

    componentDidMount() {
      this.intervalId = setInterval(this.resetMarkers.bind(this), 100);
    }
    componentWillUnmount() {
      clearInterval(this.intervalId);
    }


    render() {
      const TruckMap = withGoogleMap(props => (
        <GoogleMap
          defaultCenter = {this.currentCenter }
          defaultZoom = { 14 }
          onClick={this.onMapClick}
        >
          <Polyline 
              path={[ 
                this.currentCenter,
                this.state.clickedMarker,
              ] } 
              options={{ 
                strokeColor: '#3f704d',
                strokeOpacity: 1,
                strokeWeight: 3,
                icons: [{ 
                  icon: require("../assets/icn-arrow-down.png"),
                  offset: '0',
                  repeat: '10px'
                }],
              }}

              onClick={
                this.alertDistance
              }
          /> 
            
          {
            this.state.markers.map(
              marker => {
                if(marker.type === "hotel") {
                  return (
                    <Marker position={{ lat: marker.lat, lng: marker.lng }} icon={
                      {
                        url: this.hotelIconMarker,
                        scaledSize: {width: 50, height: 50}
                      }
                    }  onClick={this.showDistance}>
                    </Marker>
                  );
                }
                if(marker.type === "restaurant") {
                  return (
                    <Marker position={{ lat: marker.lat, lng: marker.lng }} icon={
                      {
                        url: this.restaurantIconMarker,
                        scaledSize: {width: 50, height: 50}
                      }
                    }  onClick={this.showDistance}>
                    </Marker>
                  );
                }
                if(marker.type === "gas_station") {
                  return (
                    <Marker position={{ lat: marker.lat, lng: marker.lng }} icon={
                      {
                        url: this.stationIconMarker,
                        scaledSize: {width: 50, height: 50}
                      }
                    } onClick={this.showDistance}>
                    </Marker>
                  );
                }
                if(marker.type === "path") {
                  return (
                    <Marker position={{ lat: marker.lat, lng: marker.lng }} icon={
                      {
                        url: this.pathIconMarker,
                        scaledSize: {width: 20, height: 20}
                      }
                    } >
                    </Marker>
                  );
                }
                if(marker.type === "first") {
                  return (
                    <Marker position={{ lat: marker.lat, lng: marker.lng }} icon={
                      {
                        url: this.firstPathIconMarker,
                        scaledSize: {width: 20, height: 20}
                      }
                    } >
                    </Marker>
                  );
                }
                if(marker.type === "current") {
                  return (
                    <Marker position={{ lat: marker.lat, lng: marker.lng }} icon={
                      {
                        url: this.currentPathIconMarker,
                        scaledSize: {width: 50, height: 50}
                      }
                    } >
                    </Marker>
                  );
                }
              }
            )
          }
        </GoogleMap>
    ));
    return(
        <div>
          <TruckMap
            containerElement={ <div style={{ height: '97vh', width: '99vw', position: "absolute"}} /> }
            mapElement={ <div style={{ height: '100%'} } /> }
          />
          <span id="label" hidden="true"></span>
          <Form onSubmit={this.handleSubmit} style={{ position: "relative", transform: "translate(0px, 70px)", marginLeft: "10px"}}>
            <Form.Row>
              <Form.Group as={Col} md="2" controlId="plate">
                <Form.Control
                  type="text"
                  placeholder="License Plate"
                  defaultValue={this.state.plate} 
                  onChange={this.handlePlateChange}
                />
              </Form.Group>
              <Form.Group as={Col} md="2" controlId="type">
                <Form.Control as="select"
                              defaultValue={this.state.type} 
                              onChange={this.handleTypeChange}>
                  <option>Looking for?</option>
                  <option value="all">All</option>
                  <option value="hotel">Hotel</option>
                  <option value="restaurant">Restaurant</option>
                  <option value="gas_station">Gast Station</option>
                </Form.Control>
              </Form.Group>
              <Form.Group as={Col} md="2" controlId="distance">
                <Form.Control
                  type="number"
                  placeholder="Distance"
                  defaultValue={this.state.distance} 
                  onChange={this.handleDistanceChange}
                />
              </Form.Group>
              <Button type="submit" style={{ height: '40px' }}>Apply</Button>
              <button onClick={this.addCar} style={{ height: '40px', marginLeft: "10px" }}>Add Car</button>
              <Form.Check 
                type="switch"
                id="custom-switch"
                label="Set Location"
                style={{ marginLeft: "10px", fontSize: "18px", transform: "translate(0px, 7px)"}}
                onChange={this.setLocation}
              />
            </Form.Row>
          </Form>
        </div>
    );
    }
};

export default Map;