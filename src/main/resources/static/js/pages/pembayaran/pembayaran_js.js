$(document).ready(function() {
  getFormList('mitraList', url_get_mitra)
});

const callbackGetFormList = () => {
  let saldoId = $("#fr-id_Saldo")
  let jumlahId = $("#fr-id_jumlah")
  const balance = $('#mitraList')[0].querySelector('[data-desc=\''+$("#fr-id_mitra").val()+'\']').getAttribute('data-balance')
  saldoId.val("")
  jumlahId.attr("max", balance)
  saldoId.val('Rp.'+formatRupiah(balance)+',-')
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
    }else{
      $("#fr-id_jumlah").val(_this.value)
    }
  }
}

function getFormList(target = '', url = ''){
  let input = '', id = '', val = ''
  let elementById = document.getElementById(target)
  getData(url)
      .then(data => {
        if(data.length > 0){
          data.forEach(key =>{
            id = key.user.userId
            val = key.user.fullname+'-'+key.user.phoneNumber
            input += '<option data-value = "'+id+'" data-desc = "'+val+'" data-balance = "'+key.balance+'">'+val+'</option>'
          })
          elementById.insertAdjacentHTML('afterbegin',input)
        }else{
          swalAlert('Gagal', 'Data belum tersedia', 'warning');
        }
      }).catch(err => {
    swalAlert('Gagal!', 'ERROR : '+err, 'error');
  });
}

// Jquery Dependency

$("#fr-id_jumlah").on({
  keyup: function() {
    // new Intl.NumberFormat('id-ID', { style: 'currency', currency: 'IDR' }).format(number)
    formatCurrency($(this))
    const max = this.getAttribute('max')
    const sum = currencyDeformatting($(this))

    if(parseInt(sum) > parseInt(max)){
      swalAlert('Gagal', 'Tidak dapat mengisi melebihi jumlah yang sudah ditentukan', 'error')
      $("#fr-id_jumlah").val(max)
      $("#hd-id_jumlah").val(max)
      formatCurrency($(this))
    }else{
      $("#hd-id_jumlah").val(sum)
    }
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


$("#print-pembayaran").on('click', function(e){
  let req = {}
  req.user_id = $('#mitraList')[0].querySelector('[data-desc=\''+$("#fr-id_mitra").val()+'\']').getAttribute('data-value')
  req.debit = $('#hd-id_jumlah').val()
  postData(url_submit_balance, req, 'application/json')
      .then(x => {
        if(x.status_code === "OK"){
          swalAlert('Berhasil!', 'Data berhasil di submit','success');
        }
        else{
          swalAlert(x.status_code, x.status_desc+'.','error');
        }
      }).catch(err => {
    swalAlert('Gagal!', 'ERROR : '+err,'error');
  });
})
