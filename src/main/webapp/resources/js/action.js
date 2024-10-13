function confirmacionEliminar(id)
{
  let varId = document.getElementById('idRegistro');
  varId.value = id;
}

const sweetAlert = (title, text, icon) => {
  return Swal.fire({
    title: `${title}`,
    text: `${text}`,
    icon: `${icon}`,
    showConfirmButton: true,
    timer: 4000,
    allowOutsideClick: false,
    heightAuto: false,
  });
};
