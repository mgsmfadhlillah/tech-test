const field =
  [
    { "mData": "noFaktur" },
    { "mData": "user.fullname" },
    { "mData": "user.phoneNumber" },
    { "mData": "pekerjaan.pekerjaan.namaKerjaan" },
    { "mData": "pekerjaan.ukuran.namaUkuran" },
    { "mData": "bahan.namaBahan" },
    { "mData": "jumlah" },
    {
      "mData": "createdDate",
      "type" : "date",
      "render": function(data, type, row){
        return dateConverter(data);
      }
    }
  ]

$(document).ready(function() {
  addFormFaktur()
  createTablist()
  getFormList('mitraList', url_form_faktur, 'mitra')
});

var getFormList = function (target = '', url = '', type = '') {
  let input = '', id = '', keyName = '', val = ''
  let elementById = document.getElementById(target)
  switch (type) {
    case "bahan": keyName = 'namaBahan'; break;
    case "ukuran": keyName = 'namaUkuran'; break;
    case "mitra": keyName = 'fullname'; break;
    default:
      swalAlert('ERROR', 'Something error', 'error');

  }
  getData(url+'/'+type)
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
const checkOpt = (element, val) => {
  let list = element.attr('list');
  return $('#' + list + ' option').filter(function () {
    return ($(this).val() === val);
  });
}
const priceCallback = (element) => {
  const id = $(element).attr('list').split('-');
  const bahan = $('#bahanList-'+id[1]);
  const ukuran = $('#ukuranList-'+id[1]);
  // if(ukuran.val()){
  if(bahan.val() && ukuran.val()){
    const isBahan = checkOpt(bahan, bahan.val());
    const isUkuran = checkOpt(ukuran, ukuran.val());
    if(isUkuran.length > 0 && isBahan.length > 0) {
      let optBahan = $('#bahanListOpt-'+id[1])[0].querySelector('[data-desc="'+bahan.val()+'"]').getAttribute('data-value');
      let optUkuran = $('#ukuranListOpt-'+id[1])[0].querySelector('[data-desc="'+ukuran.val()+'"]').getAttribute('data-value');
      let headers = {headers : {"pekerjaan-id" : optBahan, "ukuran-id" : optUkuran}}

      getData(url_get_price, 'text/plain', headers)
        .then(data => {
          if(data.status_code === "OK"){
            let content = JSON.parse(data.content)
            $('#form-upah-'+id[1]).text('@Rp.'+formatRupiah(content.harga)+',-')
            $('#hidden-upah-'+id[1]).val(content.harga)
          }else{
            swalAlert(data.status_code, data.status_desc+'.', 'error');
          }
        }).catch(err => {
        swalAlert('Gagal!', 'ERROR : '+err, 'error');
      });
    }
  } else {
    $('#form-upah-'+id[1]).text('@Rp.0,-')
    $('#hidden-upah-'+id[1]).val(0)
  }
}

var totalCallback = function(element) {
  element.value < 0 ? element.value = 0 : element.value
  const list = $(".upah-counter").length;
  const id = $(element).attr('id').split('-');
  let upah = $("#hidden-upah-"+id[2]),
    gtForm = $("#hidden-gt"),
    gtVal = 0,
    currJumlah = 0
  if(upah.val() > 0){
    $("#badge-counter").removeClass('visually-hidden')
    for(let i = 1; i <= list; i++){
      currJumlah += parseInt($("#form-jumlah-"+i).val())
      gtVal += parseInt($("#hidden-upah-"+i).val()) * parseInt($("#form-jumlah-"+i).val())
    }
    $("#badge-counter").text(currJumlah)
    $("#grand-total").text(formatRupiah('Rp.'+gtVal.toString())+',-')
    gtForm.val(gtVal)
  }else{
    element.value = 0
    swalAlert('Gagal!', 'Silahkan isi ukuran terlebih dahulu', 'error')
  }
}

var removeFormFaktur = function(element) {
  const id = $(element).attr('id').split('-');
  $("#form-"+id[2]).remove();
}

var addFormFaktur = function (x = 1) {
  let li = '<li id="form-'+x+'" class="list-group-item lh-sm faktur"></li>';
  let flexRow = '<div class="d-flex justify-content-between"></div>'
  let divFormFaktur = '<div class="form-faktur"></div>';
  let bahanInput = '<input class="form-control form-control-sm form-control-plaintext border-0 border-bottom border-danger rounded-0" onchange=priceCallback(this) list="bahanListOpt-'+x+'" id="bahanList-'+x+'" placeholder="Pilih Bahan" autocomplete="off">';
  let bahanDataList = '<datalist id="bahanListOpt-'+x+'"></datalist>';
  let ukuranInput = '<input class="form-control form-control-sm form-control-plaintext border-0 border-bottom border-danger rounded-0" onchange=priceCallback(this) list="ukuranListOpt-'+x+'" id="ukuranList-'+x+'" placeholder="Pilih Ukuran" autocomplete="off">';
  let ukuranDataList = '<datalist id="ukuranListOpt-'+x+'"></datalist>';
  let spanFormFaktur = '<span class="text-muted text-end form-faktur">';
  let smallTotal = '<small id="form-upah-'+x+'">@Rp.0,-</small>';
  let hideTotal = '<input type="hidden" id="hidden-upah-'+x+'" class="upah-counter"/>';
  let jumlahInput = '<input id="form-jumlah-'+x+'" type="number" class="form-control form-control-sm form-control-plaintext text-end text-muted border-0 border-bottom border-danger rounded-0" onchange="totalCallback(this)" placeholder="Jumlah"/>';
  let removeBtn = x === 1 ? '' : '<div class="d-flex justify-content-end"><button id="remove-button-'+x+'" class="btn btn-danger lh-sm align-items-center py-0" onclick="removeFormFaktur(this)"><svg class="bi d-block mx-auto mb-1" width="14" height="14"><use xlink:href="#trash"/></svg></button></div>'

  $(li).append(removeBtn).append(
    $(flexRow).append(
      $(divFormFaktur).append(bahanInput).append(bahanDataList).append(ukuranInput).append(ukuranDataList)
    ).append(
      $(spanFormFaktur).append(smallTotal).append(hideTotal).append(jumlahInput)
    )
  ).insertBefore("#btn-add-faktur")

  getFormList('bahanListOpt-'+x, url_form_faktur, 'bahan')
  getFormList('ukuranListOpt-'+x, url_form_faktur, 'ukuran')
}

$("#btn-add-faktur").on("click", function (){
  let lastChildIDNumber = $('ul#listCheckOut > li.faktur').last().attr('id').split('-')
  addFormFaktur((parseInt(lastChildIDNumber[1])+1))
});

function selectPekerjaan(o){
  const id = "#"+o.getAttribute("id")
  const kode = $(id)[0].querySelector('[value="'+$(id).val()+'"]').getAttribute('data-kode')
  $("#fr-id_nofaktur").val(kode+getFakturID())
}

let chooseListCallback = function(x){
  var id = $(x).data('idval').split("-")

  $('#'+$(x).attr('id')).on('shown.bs.tab', function(){
    if ( ! $.fn.DataTable.isDataTable( "#"+$(x).data('idval') ) ) {
      callTable($(x).data('idval'), url_get_tablelist+'/'+id[1], field)
    }
  });
}

function getFakturID(){
  // return '/' + Math.random().toString().substr(2, 6);
  return '-' + Math.round(+new Date()/1000);
}

function createTable(tableName = '') {
  let div = '<div></div>'
  let table = '<table id="'+tableName+'" className="table table-striped" style="width:100%"></table>'
  let thead = '<thead></thead>'
  let thtab = '<tr><th scope="col">No Faktur</th><th scope="col">Mitra</th><th scope="col">No Handphone</th><th scope="col">Pekerjaan</th><th scope="col">Ukuran</th><th scope="col">Bahan</th><th scope="col">Jumlah</th><th scope="col">Tanggal</th></tr>'
  let tbody = '<tbody></tbody>'
  return $(div).append($(table).append($(thead).append(thtab)).append(tbody)).html()
}
let createTablist = function(){
  let navButton, btnActv, tabPane, tabActv;
  let needClick;

  getData(url_get_pekerjaan_name)
    .then(data => {
      if(data.length >= 1){
        $.each(data, function (k, v){
          if(k === 0){
            needClick = '#pills-'+v["kodeKerjaan"]+'-tab'
          }else{
            btnActv = '';
            tabActv = '';
          }
          navButton = '<button onclick="chooseListCallback(this)" class="flex-sm-fill text-sm-center btn btn-outline-primary" id="pills-'+v["kodeKerjaan"]+'-tab" data-bs-toggle="pill" data-bs-target="#pills-'+v["kodeKerjaan"]+'" type="button" role="tab" aria-controls="pills-'+v["kodeKerjaan"]+'" aria-selected="true" data-idval="'+v["kodeKerjaan"]+'-'+v["id"]+'" data-val="'+v["namaKerjaan"]+'">'+v["namaKerjaan"]+'</button>'
          tabPane = '<div class="tab-pane fade" id="pills-'+v["kodeKerjaan"]+'" role="tabpanel" aria-labelledby="pills-'+v["kodeKerjaan"]+'-tab">'+createTable(v["kodeKerjaan"]+'-'+v["id"])+'</div>'
          $("#ui-slct_pekerjaan").append("<option value='"+v["id"]+"' data-kode='"+v["kodeKerjaan"]+"'>"+v["namaKerjaan"]+"</option>")
          $("#pills-tab").append(navButton)
          $("#pills-tabContent").append(tabPane)

        });
      }else{
        swalAlert(data.status_code, data.status_desc+'.', 'error');
      }
      $(needClick).click()
    }).catch(err => {
    swalAlert('Gagal!', 'ERROR : '+err, 'error');
  });

}



$("#print-faktur").on("click", function (e){
  let json = {}, html = {}
  let list = [], ls = []
  let no_faktur;

  $("ul#listCheckOut > li.faktur").each(function(k,v){
    let checkout = {}, co_html = {}
    let id = $(v).attr('id').split('-')
    no_faktur = $("#fr-id_nofaktur").val()

    checkout.bahanID = $("#bahanListOpt-"+id[1])[0].querySelector('[data-desc="'+$("#bahanList-"+id[1]).val()+'"]').getAttribute('data-value')
    checkout.ukuranID = $("#ukuranListOpt-"+id[1])[0].querySelector('[data-desc="'+$("#ukuranList-"+id[1]).val()+'"]').getAttribute('data-value')
    checkout.pekerjaanID = $("#ui-slct_pekerjaan").val()
    checkout.jumlah = $("#form-jumlah-"+id[1]).val()
    checkout.userID = $("#mitraList")[0].querySelector('[data-desc="'+$("#fr-id_mitra").val()+'"]').getAttribute('data-value')


    co_html.upah = $("#form-upah-"+id[1]).text()
    co_html.bahan = $("#bahanList-"+id[1]).val()
    co_html.ukuran = $("#ukuranList-"+id[1]).val()
    co_html.jumlah = checkout.jumlah

    list[k] = checkout
    ls[k] = co_html
  });
  json.data = list
  json.nofaktur = no_faktur

  html.data = ls
  html.nofaktur = no_faktur
  html.items = $("span#badge-counter").text()
  html.total = $("strong#grand-total").text()
  html.pekerjaan = $( "#ui-slct_pekerjaan option:selected" ).text()
  html.user = $("#fr-id_mitra").val()

  printPage(html, json)
});