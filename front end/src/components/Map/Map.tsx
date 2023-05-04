import React from 'react';
import 'leaflet/dist/leaflet.css';
import { Map as WorldMap,TileLayer,Marker,Popup } from 'react-leaflet';
import { Icon, LeafletMouseEvent } from 'leaflet';
import markerIconPng from "leaflet/dist/images/marker-icon.png";
import './Map.css'

const Map:React.FC<{onClick : (event:LeafletMouseEvent) => void,latitude : number | undefined,longitude : number | undefined}> = (props) => {
    
    
    return (
        <div>
            <WorldMap style={{height: '85vh',width:'100%',margin:'auto', marginTop:"45px"}} center={[21.0000,78.0000]} zoom={4} scrollWheelZoom={true} doubleClickZoom={true} animate={true} easeLinearity={0.35} onclick={props.onClick}>
                <TileLayer attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                <Marker position={[props.latitude ? props.latitude : 0,props.longitude ? props.longitude : 0]} icon={new Icon({iconUrl: markerIconPng, iconSize: [25, 41], iconAnchor: [12, 41]})}>
                    <Popup>
                        Location selected
                    </Popup>
                </Marker>
            </WorldMap>
        </div>
    )
}

export default Map
