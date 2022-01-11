import React from 'react';
import { Chart, ArcElement, Tooltip, Legend } from 'chart.js';
import { Pie } from 'react-chartjs-2';

Chart.register(ArcElement, Tooltip, Legend);
var ctx = document.getElementById("myChart");
var yellow='rgba(255, 195, 0, .7)';
var green= 'rgba(131, 239, 56, .7 )';
var red ='rgba(239, 65, 56, .7 )';

var fullyellow='rgba(255, 195, 0, 1)';
var fullgreen= 'rgba(131, 239, 56,1 )';
var fullred ='rgba(239, 65, 56, 1)';

function PieChartGraph({data_outer, data_inner, title, color}) {

    var ChartData = {
      labels: [],
      datasets: [

          {
        data: [300, 50, 100],
        backgroundColor: [
          yellow,
          green,
          red
        ],
        hoverBackgroundColor: [
            fullyellow,
            fullgreen,
            fullred
        ]
      },
          {
        data: [30, 5, 100],
        backgroundColor: [
          yellow,
          green,
          red
        ],
        hoverBackgroundColor: [
            fullyellow,
            fullgreen,
            fullred
        ]
    },
    {
    data: [1],
    borderWidth: [0],
    backgroundColor: [
        yellow
    ],
    hoverBackgroundColor: [
        yellow
    ]
    }]
    };
    var options = {
        callbacks: {
          label: function(tooltipItem, data) {
            return 'Date: ' + tooltipItem[0].xLabel + ' GMT+2';
          }
      },
      plugins: {
        title: {
          display: true,
          text: 'Pie Chart',
          color: 'black',
          borderWidth: 2,
          font: {
            size: 15,
          },
          padding: {
            top: 3,
            bottom: 3,
          },
          animation: {
            animateScale: true,
          },
        },
      },
    };

    options["plugins"]["title"]["text"]=title
    ChartData["datasets"][0]["data"]=data_outer
    ChartData["datasets"][1]["data"]=data_inner
    ChartData["datasets"][2]["backgroundColor"]=color
    ChartData["datasets"][2]["hoverBackgroundColor"]=color
  return <Pie data={ChartData} options={options} />;
}

export default PieChartGraph;
