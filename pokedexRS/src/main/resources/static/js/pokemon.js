// Cuando la ventana (página) haya cargado completamente, ejecutamos esta función
window.onload = function () {
    // 1. Obtener el contexto del lienzo donde se dibujará la gráfica
    var ctx = document.getElementById('statsChart').getContext('2d');

    // 2. Acceder a la variable global 'pokemonStats' inyectada desde el HTML
    var stats = window.pokemonStats;

    // 3. Crear una nueva instancia de Chart.js para generar la gráfica de barras
    var statsChart = new Chart(ctx, {
        type: 'bar', // Tipo de gráfico: barra
        data: {
            labels: ['Salud', 'Ata', 'Def', 'AtEsp', 'DefEsp', 'Vel'], // Etiquetas de las estadísticas
            datasets: [{
                label: 'Estadísticas del Pokémon', // Título del conjunto de datos
                data: [
                    stats.hp,             // Salud (HP)
                    stats.attack,         // Ataque
                    stats.defense,        // Defensa
                    stats.specialAttack,  // Ataque Especial
                    stats.specialDefense, // Defensa Especial
                    stats.speed           // Velocidad
                ],
                // Colores de fondo de las barras
                backgroundColor: [
                    'rgba(255, 99, 132, 0.6)',   // Rojo
                    'rgba(54, 162, 235, 0.6)',   // Azul
                    'rgba(255, 206, 86, 0.6)',   // Amarillo
                    'rgba(75, 192, 192, 0.6)',   // Verde
                    'rgba(153, 102, 255, 0.6)',  // Morado
                    'rgba(255, 159, 64, 0.6)'    // Naranja
                ],
                // Colores del borde de las barras
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1 // Grosor del borde de las barras
            }]
        },
        options: {
            responsive: true, // Hace que la gráfica se ajuste al tamaño del contenedor
            maintainAspectRatio: true, // Mantiene la proporción original de la gráfica
            scales: {
                y: {
                    beginAtZero: true // Asegura que el eje Y comience desde 0
                }
            }
        }
    });
};
