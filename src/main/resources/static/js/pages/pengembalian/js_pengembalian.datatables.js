const fields = [
  { "mData": "history.user.fullname" },
  { "mData": "history.user.phoneNumber" },
  { "mData": "history.noFaktur" },
  {
    "mData": "history.pekerjaan.ukuran.namaUkuran",
    "render": function ( data, type, row ) {
      return row.history.pekerjaan.ukuran.namaUkuran+"-"+row.history.bahan.namaBahan;
    }
  },
  { "mData": "returned" },
  { "mData": "remaining" },
  { "mData": "history.jumlah" }
];

$(document).ready(function(){
  callTable("returnTable", url_return_list, fields)
});