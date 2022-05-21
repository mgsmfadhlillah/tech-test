$("#btn-submit-new").on("click", function (){

  let formData = $("#form-add-mitra").serializeObject();

  postData(url_new_mitra, formData, 'application/json')
    .then(data => {
      if(data.status_code === "OK"){
        swalAlert('Berhasil!', 'Akun anda telah berhasil didaftarkan.','success');
      }
      else{
        swalAlert(data.status_code, data.status_desc+'.', 'error');
      }
    }).catch(err => {
      swalAlert('Gagal!', 'ERROR : '+err, 'error');
  });
});