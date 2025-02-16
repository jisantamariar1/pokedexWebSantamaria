// Función para limpiar el campo de búsqueda sin recargar la página
function clearSearch() {
    // 1. Selecciona el campo de búsqueda dentro del formulario y establece su valor en vacío ('')
    document.querySelector('input[name="search"]').value = '';

    // 2. Redirige al usuario a la página principal ('/') para eliminar cualquier parámetro de búsqueda en la URL
    window.location.href = '/';
}
