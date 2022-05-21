const submitCnf = (cnf) => {
  let url = '';
  let body = '';
  let data = '';
  switch (cnf) {
    case 'bahan':
      url = url_submit_new_cnf+'/bahan';
      body = 'Konfigurasi bahan telah berhasil ditambahkan.';
      data = $("#form-bahan").serializeObject();
      break;
    case 'ukuran':
      url = url_submit_new_cnf+'/ukuran';
      body = 'Konfigurasi ukuran telah berhasil ditambahkan.';
      data = $("#form-ukuran").serializeObject();
      break;
    case 'pekerjaan':
      url = url_submit_new_cnf+'/pekerjaan';
      body = 'Konfigurasi jenis pekerjaan telah berhasil ditambahkan.';
      data = $("#form-jenis_pekerjaan").serializeObject();
      break;
    case 'upah':
      url = url_submit_new_cnf+'/upah';
      body = 'Konfigurasi upah telah berhasil ditambahkan.';
      data = $("#form-upah").serializeObject();
      break;
    default:
      swalAlert('ERROR', 'Something error', 'error');
  }

  postData(url, data)
    .then(x => {
      if(x.status_code === "OK"){
        swalAlert('Berhasil!', body,'success');
      }
      else{
        swalAlert(x.status_code, x.status_desc+'.','error');
      }
    }).catch(err => {
    swalAlert('Gagal!', 'ERROR : '+err,'error');
  });
}

let getOptions = function(){
  if(isClicked === false){
    getData(url_jenis_pekerjaan_list)
        .then(data => {
          if(data.length >= 1){
            $.each(data, function (k, v){
              $("#fr-slct_upahpekerjaan").append("<option value='"+v["id"]+"' data-kode='"+v["kodeKerjaan"]+"'>"+v["namaKerjaan"]+"</option>")
            });
            isClicked = true
          }else{
            isClicked = false
            swalAlert(data.status_code, data.status_desc+'.', 'error');
          }
        }).catch(err => {
      isClicked = false
      swalAlert('Gagal!', 'ERROR : '+err, 'error');
    });
    getData(url_ukuran_list)
        .then(data => {
          console.log(data)
          if(data.length >= 1){
            $.each(data, function (k, v){
              $("#fr-slct_upahukuran").append("<option value='"+v["id"]+"' data-ukuran='"+v["ukuran"]+"'>"+v["namaUkuran"]+"</option>")
            });
            isClicked = true
          }else{
            isClicked = false
            swalAlert(data.status_code, data.status_desc+'.', 'error');
          }
        }).catch(err => {
      isClicked = false
      swalAlert('Gagal!', 'ERROR : '+err, 'error');
    });
  }

}

$("#fr-id_upahharga").on({
  keyup: function() {
    formatCurrency($(this))
    $("#hd-id_jumlah").val(currencyDeformatting($(this)))
  }
});

const currencyDeformatting = (o) => {
  let input_val = o.val()
  return input_val.replace(/Rp. /g, '').replace(/\./g, '')
}

function formatNumber(n) {
  return n.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ".")
}


function formatCurrency(input, blur) {
  // appends $ to value, validates decimal side
  // and puts cursor back in right position.

  // get input value
  var input_val = input.val();

  // don't validate empty input
  if (input_val === "") { return; }

  // original length
  var original_len = input_val.length;

  // initial caret position
  var caret_pos = input.prop("selectionStart");

  // check for decimal
  if (input_val.indexOf(",") >= 0) {

    // get position of first decimal
    // this prevents multiple decimals from
    // being entered
    var decimal_pos = input_val.indexOf(",");

    // split number by decimal point
    var left_side = input_val.substring(0, decimal_pos);
    var right_side = input_val.substring(decimal_pos);

    // add commas to left side of number
    left_side = formatNumber(left_side);

    // validate right side
    right_side = formatNumber(right_side);

    // On blur make sure 2 numbers after decimal
    if (blur === "blur") {
      right_side += "00";
    }

    // Limit decimal to only 2 digits
    right_side = right_side.substring(0, 2);

    // join number by .
    input_val = "Rp. " + left_side + "," + right_side;

  } else {
    // no decimal entered
    // add commas to number
    // remove all non-digits
    input_val = formatNumber(input_val);
    input_val = "Rp. " + input_val;

    // final formatting
    if (blur === "blur") {
      input_val += ",00";
    }
  }

  // send updated string to input
  input.val(input_val);

  // put caret back in the right position
  var updated_len = input_val.length;
  caret_pos = updated_len - original_len + caret_pos;
  input[0].setSelectionRange(caret_pos, caret_pos);
}