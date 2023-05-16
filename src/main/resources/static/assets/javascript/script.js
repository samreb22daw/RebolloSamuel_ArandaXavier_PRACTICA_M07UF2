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

            fetch(`https://api.open-meteo.com/v1/forecast?latitude=${latitud}&longitude=${longitud}&hourly=temperature_2m&current_weather=true&timezone=auto&daily=precipitation_probability_mean,apparent_temperature_max,weathercode`)
                .then(response => response.json())
                .then(data => {
                        console.log("data:",data)
                        let tempActual = data.current_weather.temperature + "ºC";
                        let weatherCode = data.current_weather.weathercode;
                        let precipitacionMax = data.daily.precipitation_probability_mean[0]+"%";
                        let diaHora = data.current_weather.time;
                        console.log("diaHora:",diaHora);
                        // diaHora: 2023-05-16T11:00
                        let dia = diaHora.slice(0,10);
                        let hora = diaHora.slice(11,16)+"h";

                        document.getElementById('ciudad').innerHTML = ciudad;
                        document.getElementById('temperatura').innerHTML = tempActual;
                        document.getElementById('precipitacion').innerHTML = precipitacionMax;
                        document.getElementById('dia').innerHTML = dia;
                        document.getElementById('hora').innerHTML = hora;

                        console.log("weatherCode:",weatherCode);
                        document.getElementById('icono').src = `/assets/weather-icons/${weatherCode}.png`

                        for(let i = 1; i < 7; i++){
                                console.log("DIARIO:",data.daily.time[i]);
                                document.getElementById(`dia${i}`).innerHTML = data.daily.time[i];
                                document.getElementById(`temp${i}`).innerHTML = data.daily.apparent_temperature_max[i]+"ºC";
                                document.getElementById(`precipitacion${i}`).innerHTML = data.daily.precipitation_probability_mean[i]+"%";
                                document.getElementById(`imagen${i}`).src = `/assets/weather-icons/${data.daily.weathercode[i]}.png`;

                        }



                }).catch(error => console.log("Error obteniendo la latitud y longitud de la ciudad: ",error));
    }).catch(error => console.log("Error obteniendo la latitud y longitud de la ciudad: ",error));


