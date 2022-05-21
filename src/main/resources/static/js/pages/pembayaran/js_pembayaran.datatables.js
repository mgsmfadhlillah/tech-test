const fields = [
  { "mData": "user.fullname" },
  { "mData": "user.phoneNumber" },
  {
    "mData": "balance",
    "render": $.fn.dataTable.render.number('.', ',', 2,'Rp ')
  },
  {
    "mData": "modifiedDate",
    "type"  : "date",
    "render": function(data, type, row){
      const dates = row.modifiedDate === null ? row.createdDate : row.modifiedDate
      return dateConverter(dates)
    }
  }
];

$(document).ready(function(){
  callTable("payoutTable", url_payout_list, fields)
});