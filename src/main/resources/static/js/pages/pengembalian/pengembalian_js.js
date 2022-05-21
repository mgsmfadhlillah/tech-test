$(document).ready(function() {
  getFormList('mitraList', url_get_mitra+'/mitra', 'mitra')
});

const callbackGetFormList = () => {
  let fakturList = ''
  let url = url_get_fakturnumber+'/'+$('#mitraList')[0].querySelector('[data-desc=\''+$("#fr-id_mitra").val()+'\']').getAttribute('data-value')

  getData(url)
      .then(data => {
        let nofaktur = data.map(item => item.history.noFaktur).filter((value, index, self) => self.indexOf(value) === index)
        let div = document.getElementById("fakturList")
        nofaktur.forEach(k =>{
          fakturList += '<option data-value="'+k+'" data-desc = "'+k+'">'+k+'</option>'
        });
        localStorage.removeItem("rawData")
        localStorage.setItem("rawData", JSON.stringify(data))
        div.innerHTML = ""
        div.insertAdjacentHTML('afterbegin', fakturList)
      }).catch(err => {
    swalAlert('Gagal!', 'ERROR : '+err, 'error');
  });
}

const dataFiltered = function (data){
  const rawData = JSON.parse(localStorage.getItem("rawData"))
  let i = 0
  return rawData.filter(function(d) {
    return d.history.noFaktur === data;
  });
};

const callbackGetPekerjaan = (_this) => {
  let optList = ''
  let div = document.getElementById("pekerjaanList")
  const data = dataFiltered(_this)
  console.log(data)
  for(let i = 0; i < Object.keys(data).length; i++){
    optList += '<option data-value="'+data[i].id+'" data-desc = "'+data[i].history.noFaktur+'" data-remaining = "'+data[i].remaining+'">'+data[i].id+'-'+data[i].history.pekerjaan.pekerjaan.namaKerjaan+'-'+data[i].history.bahan.namaBahan+'-'+data[i].history.pekerjaan.ukuran.namaUkuran+'</option>'
  }
  div.innerHTML = ""
  div.insertAdjacentHTML('afterbegin', optList)
}

const callbackGetJumlah = (_this) => {
  let val = $("#fr-id_pekerjaan").val()
  let x = val.split('-');
  let max = $('#pekerjaanList')[0].querySelector('[data-value="'+x[0]+'"]').getAttribute('data-remaining');
  $("#fr-id_jumlah").attr("max", max)
  $("#pekerjaanNote").text("Maksimal : "+max)

}

const callbackCheckJumlah = (_this) => {
  _this.value < 0 ? _this.value = 0 : _this.value
  const max = _this.getAttribute('max')
  if(max === undefined || max === null){
    _this.value = 0
    swalAlert('Gagal', 'Silahkan isi kolom yang lain terlebih dahulu', 'error')
  }else{
    if(parseInt(_this.value) > parseInt(max)){
      swalAlert('Gagal', 'Tidak dapat mengisi melebihi jumlah yang sudah ditentukan', 'error')
    }
  }
}

function getFormList(target = '', url = '', type = ''){
  let input = '', id = '', keyName = '', val = ''
  let elementById = document.getElementById(target)
  switch (type) {
    case "faktur": keyName = 'namaBahan'; break;
    case "pekerjaan": keyName = 'namaUkuran'; break;
    case "mitra": keyName = 'fullname'; break;
    default:
      swalAlert('ERROR', 'Something error', 'error');

  }
  getData(url)
      .then(data => {
        if(data.length > 0){
          data.forEach(key =>{
            id = type === "mitra" ? key['userId'] : key['id']
            val = type === "mitra" ? key[keyName]+'-'+key['phoneNumber'] : key[keyName]
            input += '<option data-value = "'+id+'" data-desc = "'+val+'">'+val+'</option>'
          })
          elementById.insertAdjacentHTML('afterbegin',input)
        }else{
          swalAlert('Gagal', 'Data belum tersedia', 'warning');
        }
      }).catch(err => {
    swalAlert('Gagal!', 'ERROR : '+err, 'error');
  });
}

$("#print-pengembalian").on('click', function(e){
  let request = {}
  const id = $("#fr-id_pekerjaan").val().split("-")
  request.return_id = id[0]
  request.sum = $("#fr-id_jumlah").val()
  postData(url_submit_return, request, 'application/json')
      .then(x => {
        if(x.status_code === "OK"){
          swalAlert('Berhasil!', '','success');
        }
        else{
          swalAlert(x.status_code, x.status_desc+'.','error');
        }
      }).catch(err => {
    swalAlert('Gagal!', 'ERROR : '+err,'error');
  });
})