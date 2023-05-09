const ciudad = "Barcelona";

// Primera petición para obtener la latitud y longitud de una ciudad

let latitud;
let longitud;
const url = `https://geocoding-api.open-meteo.com/v1/search?name=${ciudad}`;


fetch(url)
    .then(response => response.json())
    .then(data => {
        latitud = data.results[0].latitude.toString();
        longitud = data.results[0].longitude.toString();

        fetch(`https://api.open-meteo.com/v1/forecast?latitude=${latitud}&longitude=${longitud}&hourly=temperature_2m&current_weather=true&timezone=auto`)
            .then(response => response.json())
            .then(data => {
                console.log(data.current_weather);
            }).catch(error => console.log("Error obteniendo la latitud y longitud de la ciudad: ",error));
    }).catch(error => console.log("Error obteniendo la latitud y longitud de la ciudad: ",error));
