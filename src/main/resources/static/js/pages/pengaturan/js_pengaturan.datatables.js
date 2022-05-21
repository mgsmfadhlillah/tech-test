let cnf_bahan ,cnf_ukuran, cnf_jenis_pekerjaan, cnf_pekerjaan, isClicked = false;

const bahanField = [{ "mData": "namaBahan" }, { "mData": "panjangBahan" }]
const ukuranField = [{ "mData": "namaUkuran" }, { "mData": "ukuran" }]
const pekerjaanField = [{ "mData": "namaKerjaan" }, { "mData": "kodeKerjaan" }]
const upahField = [{ "mData": "pekerjaan.namaKerjaan" }, { "mData": "ukuran.namaUkuran" }, { "mData": "harga", "render": $.fn.dataTable.render.number('.', ',', 2,'Rp ') }]

$(document).ready(function() {
  var triggerTabList = [].slice.call(document.querySelectorAll('#v-pills-tab button'))
  triggerTabList.forEach(function (triggerEl) {
    var tabTrigger = new bootstrap.Tab(triggerEl)
    var isClicked = false

    triggerEl.addEventListener('click', function (event) {
      let tabID = triggerEl.getAttribute("id")
      if(!isClicked){
        switch (tabID) {
          case "v-pills-bahan-tab" :
            cnf_bahan = callTable("bahanTable", url_bahan_list, bahanField)
            break;
          case "v-pills-ukuran-tab" :
            cnf_ukuran = callTable("ukuranTable", url_ukuran_list, ukuranField)
            break;
          case "v-pills-pekerjaan-tab" :
            cnf_jenis_pekerjaan = callTable("jenis_pekerjaanTable", url_jenis_pekerjaan_list, pekerjaanField)
            break;
          case "v-pills-upah-tab" :
            cnf_pekerjaan = callTable("pekerjaanTable", url_pekerjaan_list, upahField)
            getOptions();
            break;
          default:
            swalAlert('ERROR', 'Something error', 'error');
        }
        isClicked = true
      }
      event.preventDefault()
      tabTrigger.show()
    })
  });
  $("#v-pills-bahan-tab").click();
});