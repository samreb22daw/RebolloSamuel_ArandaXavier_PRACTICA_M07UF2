const ciudad = document.getElementById('ciudad').innerHTML;

let latitud;
let longitud;
const url = `https://geocoding-api.open-meteo.com/v1/search?name=${ciudad}`;
let dadesOpenMeteo = [];


fetch(url)
    .then(response => response.json())
    .then(data => {
        latitud = data.results[0].latitude.toString();
        longitud = data.results[0].longitude.toString();

        fetch(`https://api.open-meteo.com/v1/forecast?latitude=${latitud}&longitude=${longitud}&hourly=temperature_2m&current_weather=true&timezone=auto&daily=precipitation_probability_max`)
            .then(response => response.json())
            .then(data => {
                console.log("data:",data)
                let tempActual = data.current_weather.temperature;
                let weatherCode = data.current_weather.weathercode;
                let precipitacionMax = data.daily.precipitation_probability_max[0];
                let dia = data.current_weather.time;
                let diaHora = dia.replace("T", " ");
                document.getElementById('ciudad').innerHTML = ciudad;
                document.getElementById('temperatura').innerHTML = tempActual;
                document.getElementById('precipitacion').innerHTML = precipitacionMax;
                document.getElementById('dia-hora').innerHTML = diaHora + "h";

                console.log("weatherCode:",weatherCode);
                document.getElementById('icono').src = `/assets/weather-icons/${weatherCode}.png`


            }).catch(error => console.log("Error obteniendo la latitud y longitud de la ciudad: ",error));
    }).catch(error => console.log("Error obteniendo la latitud y longitud de la ciudad: ",error));


