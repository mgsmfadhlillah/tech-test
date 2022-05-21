var table;
const field = [
  { "mData": "fullname" },
  { "mData": "phoneNumber" },
  { "mData": "address" },
  { "mData": "email" },
  {
    "mData": "createdTime",
    "type"  : "date",
    "render": function(data, type, row){
      return dateConverter(data);
    }
  },
  {
    "mData": "enabled",
    "render": function(data, type, row){
      let email = String('"'+row.email+'"');
      var btn = "<button type='button' class='btn btn-sm btn-primary me-1' onclick='edit("+email+")'>Edit</button>";
      if(data == 1){
        let txt = "User ini akan di nonaktifkan dan tidak dapat login";
        let str = String('"'+txt+'"');
        btn += "<button type='button' class='btn btn-sm btn-outline-success' onclick='activation("+email+","+str+")'>Aktif</button>";
      }else{
        let txt = "User ini akan diberikan akses untuk login";
        let str = String('"'+txt+'"');
        btn += "<button type='button' class='btn btn-sm btn-outline-danger' onclick='activation("+email+","+str+")'>Tidak Aktif</button>";
      }
      return btn;
    }
  }
];

$(document).ready(function() {
  table = callTable("myTable", url_mitra_list, field)
});


function activation(email, text){
  if(email === undefined){
    Swal.fire('Oops...', 'Silakan pilih data terlebih dahulu.', 'error');
  }else{
    Swal.fire({
      title: 'Apakah anda yakin?',
      text: text,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Ya!'
    }).then((result) => {
      if (result.value) {
        $.ajax({
          url: url_mitra_activation+"/"+email,
          data: '',
          type: 'GET',
          success: function (data, textStatus, xhr) {
            if(data.status_code === "OK"){
              swalAlert('Pembaruan Data!', 'User dengan '+email+' telah '+data.content+'.', 'success')
            }else{
              swalAlert('Gagal!', 'Message : '+data.content+'.', 'error')
            }
          },
          error: function (data, textStatus, xhr) {
            swalAlert('Gagal!', 'Message : '+data.responseJSON.status_desc+'.', 'error')
          }
        });
      }
    });
  }

}

function edit(email){
  alert("edit");
}