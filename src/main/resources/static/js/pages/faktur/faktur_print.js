let windowPrint
const printPage = function(x, o){
  $("#printArea").append(templatePrintPage(x))
  windowPrint = window.open('', '', 'width='+screen.width+',height='+screen.height)
  windowPrint.document.write(document.getElementById("printArea").innerHTML);
  windowPrint.document.close();
  windowPrint.focus();
  windowPrint.onload = function(){
    windowPrint.print();
    windowPrint.onunload = function () {
      windowPrint.close();
      windowPrint.addEventListener("afterprint", printConfirmation(o));
    };
  }
}

const printConfirmation = function(json){
  Swal.fire({
    title: 'Apakah telah berhasil print?',
    text: "Klik Ya untuk menyimpan",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Ya',
    cancelButtonText: 'Tidak'
  }).then((result) => {
    if (result.isConfirmed) {
      windowPrint.close();
      $("#printArea").html("")
      postData(url_submit_faktur, json, 'application/json')
          .then(x => {
            if(x.status_code === "OK"){
              swalAlert('Berhasil!', 'Data Faktur telah tersimpan','success');
            }
            else{
              swalAlert(x.status_code, x.status_desc+'.','error');
            }
          }).catch(err => {
        swalAlert('Gagal!', 'ERROR : '+err,'error');
      });
    }else{
      $("#printArea").html("")
    }
  })
}
const templatePrintPage = (json) => {
  let list = ""
  $.each(json.data, function (k, v){
    list +=
    "                    <li class=\"list-group-item lh-sm faktur\"><div class=\"d-flex justify-content-between\">" +
    "                      <div class=\"form-faktur\">" +
    "                        <span class=\"form-control form-control-sm form-control-plaintext border-0 border-bottom border-danger rounded-0\">"+v.bahan+"</span>" +
    "                        <span class=\"form-control form-control-sm form-control-plaintext border-0 border-bottom border-danger rounded-0\">"+v.ukuran+"</span>" +
    "                      </div>" +
    "                      <span class=\"text-muted text-end form-faktur\">" +
    "                        <small>"+v.upah+"</small>" +
    "                        <span class=\"form-control form-control-sm form-control-plaintext text-end text-muted border-0 border-bottom border-danger rounded-0\">"+v.jumlah+"</span>" +
    "                      </div>" +
    "                    </li>"
  })
  let html = "" +
      "<html><head><title>Print Faktur</title><link href='"+css+"' rel=\"stylesheet\"></head>" +
      "<body>" +
      "            <div class=\"row\">\n" +
      "              <div class=\"col-md-12\">\n" +
      "                <h4 class=\"mb-3 text-primary\">No Faktur : "+json.nofaktur+"</h4>\n" +
      "                <div class=\"row g-3\">\n" +
      "                  <div class=\"col-sm-6\">\n" +
      "                    <label for=\"fr-id_mitra\" class=\"form-label col-form-label-sm mb-0\">Mitra</label>\n" +
      "                    <span class=\"form-control form-control-sm form-control-plaintext border-0 border-bottom border-danger rounded-0\">"+json.user+"</span>\n" +
      "                  </div>\n" +
      "                  <div class=\"col-sm-6\">\n" +
      "                    <label for=\"fr-id_pekerjaan\" class=\"form-label col-form-label-sm mb-0\">Pekerjaan</label>\n" +
      "                    <span class=\"form-control form-control-sm form-control-plaintext border-0 border-bottom border-danger rounded-0\">"+json.pekerjaan+"</span>\n" +
      "                  </div>\n" +
      "                </div>\n" +
      "              </div>\n" +
      "              <div class=\"col-md-12 mt-5\">\n" +
      "                <h4 class=\"d-flex justify-content-between align-items-center mb-3\">\n" +
      "                  <span class=\"text-primary\">Check Out</span>\n" +
      "                  <span class=\"badge bg-primary rounded-pill\">"+json.items+"</span>\n" +
      "                </h4>\n" +
      "                <form>\n" +
      "                  <ul class=\"list-group mb-3\">\n" +
                           list+
      "                    <li class=\"list-group-item d-flex justify-content-between text-white bg-primary\">\n" +
      "                      <span>Total</span>\n" +
      "                      <strong>Rp "+json.total+"</strong>\n" +
      "                    </li>\n" +
      "                  </ul>\n" +
      "                </form>\n" +
      "              </div>\n" +
      "            </div>\n" +
      "</body>";
  return html
}
