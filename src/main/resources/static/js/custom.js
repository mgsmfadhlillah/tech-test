function dateConverter(datex) {
  return moment(datex).format('D MMM, YYYY');
}

const callTable = (tabID, url, field) => {
  return $("#"+tabID).DataTable({
    "sAjaxSource" : url,
    "sAjaxDataProp": "",
    "aoColumns": field
  });
}

const formatRupiah = (angka, prefix) => {
  var number_string = angka.replace(/[^,\d]/g, '').toString(),
      split = number_string.split(','),
      sisa = split[0].length % 3,
      rupiah = split[0].substr(0, sisa),
      ribuan = split[0].substr(sisa).match(/\d{3}/gi);

  if(ribuan){
    separator = sisa ? '.' : '';
    rupiah += separator + ribuan.join('.');
  }

  rupiah = split[1] !== undefined ? rupiah + ',' + split[1] : rupiah;
  return prefix === undefined ? rupiah : (rupiah ? 'Rp. ' + rupiah : '');
}

$.extend( true, $.fn.dataTable.defaults, {
  "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
  "pageLength": 10,
  "lengthChange": true,
  "deferRender": true,
  "responsive": true,
  "searching": true,
  "processing": true,
  select: 'single',
  "drawCallback": function () {
    $('.dataTables_paginate > .pagination').addClass('pagination-sm');
  },
  "dom": "<'top'<'row'<'col-sm-6 d-flex justify-content-start'l><'col-sm-6'f>>>rt<'bottom'<'row'<'col-sm-6'i><'col-sm-6'p>>><'clear'>"
});


$.fn.serializeObject = function() {
  var o = {};
  var a = this.serializeArray();
  $.each(a, function() {
    if (o[this.name]) {
      if (!o[this.name].push) {
        o[this.name] = [o[this.name]];
      }
      o[this.name].push(this.value || '');
    } else {
      o[this.name] = this.value || '';
    }
  });
  return o;
};

async function postData(url = '', data = {}, type = 'text/plain') {
  const response = await fetch(url, {
    method: 'POST',
    mode: 'cors',
    cache: 'no-cache',
    credentials: 'same-origin',
    headers: {
      'Content-Type': type
    },
    redirect: 'follow',
    referrerPolicy: 'no-referrer',
    body: JSON.stringify(data)
  });
  return response.json();
}

async function getData(url = '', type = 'text/plain', opt = {}){
  const res = await fetch(url, opt);
  return res.json();
}

var swalAlert = function (title = '', body = '', type = ''){

  Swal.fire(title, body, type).then((result) => {
    if(result.value) {
      location.reload();
    }
  });
}