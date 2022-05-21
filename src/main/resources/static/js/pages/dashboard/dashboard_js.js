let ctx = document.getElementById('myChart').getContext('2d');
let ct2 = document.getElementById('secondChart').getContext('2d');
let bdColor = [], bgColor = [];
let myChart = new Chart(ctx, {
  type: 'bar',
  data: {
    datasets: [{
      label: 'Selesai',
      data: [{x:'Mawar - 120x200', y:20}, {x:'Anggrek - 120x200', y:10}, {x:'Melati - 120x200', y:70}, {x:'Anggrek - 160x200', y:45}, {x:'Mawar - 180x200', y:82}],
      backgroundColor: 'rgba(19,112,150,0,2)',
      borderColor: 'rgba(19,112,150,1)',
      borderWidth: 1
    },{
      label: 'Belum Selesai',
      data: [{x:'Mawar - 120x200', y:25}, {x:'Anggrek - 120x200', y:33}, {x:'Melati - 120x200', y:23}, {x:'Anggrek - 160x200', y:35}, {x:'Mawar - 180x200', y:12}],
      backgroundColor: 'rgba(190,12,15,0,2)',
      borderColor: 'rgba(190,12,15,1)',
      borderWidth: 1
    }]
  },
  options: {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Chart.js Bar Chart'
      }
    }
  }
});

let seChart = new Chart(ct2, {
  type: 'bar',
  data: {
    datasets: [{
      label: 'Jumlah Barang Belum Selesai',
      data: [{x:'AA', y:20}, {x:'BB', y:10}, {x:'CC', y:70}, {x:'DD', y:45}],
      backgroundColor: bgColor,
      borderColor: bdColor,
      borderWidth: 1
    }]
  },
  options: {
    scales: {
      y: {
        beginAtZero: true
      }
    }
  }
}, generateColor(4));

function generateColor(length) {
  for(let i = 0; i<length; i++){
    let o = Math.round, r = Math.random, s = 255;
    let red = o(r()*s), green = o(r()*s), blue = o(r()*s)
    bgColor.push(createRGBA(red, green, blue, '0.2'))
    bdColor.push(createRGBA(red, green, blue, '1'))
  }
  bgColor = []
  bdColor = []
}

function createRGBA(r,g,b,op){
  return 'rgba(' + r + ',' + g + ',' + b + ',' + op + ')';
}

// getData(url+'/'+type)
//     .then(data => {
//       if(data.length > 0){
//         data.forEach(key =>{
//           id = type === "mitra" ? key['userId'] : key['id']
//           val = type === "mitra" ? key[keyName]+'-'+key['phoneNumber'] : key[keyName]
//           input += '<option data-value = "'+id+'" data-desc = "'+val+'">'+val+'</option>'
//         })
//         elementById.insertAdjacentHTML('afterbegin',input)
//       }else{
//         swalAlert(data.status_code, data.status_desc+'.', 'error');
//       }
//     }).catch(err => {
//   swalAlert('Gagal!', 'ERROR : '+err, 'error');
// });